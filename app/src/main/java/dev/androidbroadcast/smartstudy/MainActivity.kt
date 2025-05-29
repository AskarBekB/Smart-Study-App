package dev.androidbroadcast.smartstudy

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.smartstudy.data.local.SettingsManager
import dev.androidbroadcast.smartstudy.presentation.NavGraphs
import dev.androidbroadcast.smartstudy.presentation.components.DrawerContent
import dev.androidbroadcast.smartstudy.presentation.destinations.BookScreenDestination
import dev.androidbroadcast.smartstudy.presentation.destinations.DashboardScreenRouteDestination
import dev.androidbroadcast.smartstudy.presentation.destinations.SessionScreenRouteDestination
import dev.androidbroadcast.smartstudy.presentation.session.StudySessionTimerService
import dev.androidbroadcast.smartstudy.presentation.theme.SmartStudyTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: StudySessionTimerService

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as StudySessionTimerService.StudySessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StudySessionTimerService::class.java).also {
            bindService(it, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()

        setContent {
            val (darkTheme, fontScale) = rememberSettingsState()
            if (!isBound) return@setContent

            SmartStudyTheme(darkTheme = darkTheme, fontScale = fontScale) {
                MainScaffold(timerService)
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    @Composable
    private fun rememberSettingsState(): Pair<Boolean, Float> {
        val context = LocalContext.current
        val settingsManager = remember { SettingsManager(context) }

        var isDarkTheme by remember { mutableStateOf(settingsManager.isDarkThemeEnabled()) }
        var fontScale by remember { mutableStateOf(settingsManager.getFontScale()) }

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(Unit) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    isDarkTheme = settingsManager.isDarkThemeEnabled()
                    fontScale = settingsManager.getFontScale()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        return isDarkTheme to fontScale
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainScaffold(timerService: StudySessionTimerService) {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        val isOnDashboard = currentRoute == DashboardScreenRouteDestination.route

        if (isOnDashboard) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        DrawerContent(
                            onSettingsClick = {
                                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                                scope.launch { drawerState.close() }
                            },
                            onBooksClick = {
                                navController.navigate(BookScreenDestination.route)
                            }
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Study Smart") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                ) { padding ->
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(padding),
                        dependenciesContainerBuilder = {
                            dependency(SessionScreenRouteDestination) { timerService }
                        }
                    )
                }
            }
        } else {
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(SessionScreenRouteDestination) { timerService }
                }
            )
        }
    }
}

