package dev.androidbroadcast.smartstudy

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint
import dev.androidbroadcast.smartstudy.presentation.NavGraphs
import dev.androidbroadcast.smartstudy.presentation.components.DrawerContent
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
        Intent(this, StudySessionTimerService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermission()

        setContent {
            if (!isBound) return@setContent

            SmartStudyTheme {
                // 1. NavController для детекции текущего экрана
                val navController = rememberNavController()
                val backStack by navController.currentBackStackEntryAsState()
                val currentDest: NavDestination? = backStack?.destination

                // 2. Проверяем, находимся ли мы на DashboardScreen
                val isOnDashboard = currentDest?.route == DashboardScreenRouteDestination.route

                // 3. Если главная — показываем Drawer + общий TopAppBar
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
                                    }
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("Smart Study") },
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
                    // 4. Во всех остальных случаях — просто хост навигации без Drawer/Scaffold
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
    }
}
