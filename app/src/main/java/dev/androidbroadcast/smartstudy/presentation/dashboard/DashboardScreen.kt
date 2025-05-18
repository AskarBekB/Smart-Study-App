package dev.androidbroadcast.smartstudy.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.androidbroadcast.smartstudy.R
import dev.androidbroadcast.smartstudy.domain.model.Session
import dev.androidbroadcast.smartstudy.domain.model.Subject
import dev.androidbroadcast.smartstudy.domain.model.Task
import dev.androidbroadcast.smartstudy.presentation.components.AddSubjectDialog
import dev.androidbroadcast.smartstudy.presentation.components.CountCard
import dev.androidbroadcast.smartstudy.presentation.components.SubjectCard
import dev.androidbroadcast.smartstudy.presentation.components.studySessionsList
import dev.androidbroadcast.smartstudy.presentation.components.taskList
import java.nio.file.WatchEvent

@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject("English", 10f, Subject.subjectCardColors[0], 0),
        Subject("Physics", 10f, Subject.subjectCardColors[1], 0),
        Subject("Maths", 10f, Subject.subjectCardColors[2], 0),
        Subject("Geography", 10f, Subject.subjectCardColors[3], 0),
        Subject("Chemistry", 10f, Subject.subjectCardColors[4], 0),
    )

    val tasks = listOf(
        Task(
            title = "Prepare notes",
            description = "",
            dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = false,
            0,1
        ),
        Task(
            title = "Do homework",
            description = "",
            dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isComplete = true,
            0,1
        ),
        Task(
            title = "Go Coaching",
            description = "",
            dueDate = 0L,
            priority = 3,
            relatedToSubject = "",
            isComplete = true,
            0,1
        ),
        Task(
            title = "Assignment",
            description = "",
            dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = false,
            0,1
        ),
        Task(
            title = "Make dinner",
            description = "",
            dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isComplete = true,
            0,1
        ),
    )

    val sessions = listOf(
        Session(
            relatedToSubject = "English",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Maths",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Biology",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Checmis",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Art",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
        Session(
            relatedToSubject = "Sport",
            date = 0L,
            duration = 2,
            sessionSubjectId = 0,
            sessionId = 0
        ),
    )

    var isAddSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    var subjectName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColors by remember { mutableStateOf(Subject.subjectCardColors.random()) }


    AddSubjectDialog(
        isOpen = isAddSubjectDialogOpen,
        subjectName = subjectName,
        goalHours = goalHours,
        onSubjectNameChange = {subjectName = it},
        onGoalHoursChange = {goalHours = it},
        selectedColors = selectedColors,
        onColorChange = {selectedColors = it},
        onDismissRequest = { isAddSubjectDialogOpen = false },
        onConfirmButtonClick = {
            isAddSubjectDialogOpen = false
        }
    )
    Scaffold(
        topBar = { DashboardScreenTopBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subjectCount = 5,
                    studiedHours = "10",
                    goalHours = "15"
                )
            }
            item {
                SubjectCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,
                    onAddIconClicked = { isAddSubjectDialogOpen = true }
                )
            }
            item {
                Button(
                    onClick = { TODO() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ){
                    Text(text = "Start Study Session")
                }
            }
            taskList(
                sectionTitle = "UPCOMING TASKS",
                emptyListText = "You don't have any upcoming tasks. \n" +
                        "Click the + button in subject screen to add new task.",
                tasks = tasks,
                onCheckBoxClick = {},
                onTaskCardClick = {}
            )
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            studySessionsList(
                sectionTitle = "RECENT STUDY SESSIONS",
                emptyListText = "You don't have any recent study sessions.\n" +
                "Start a study session so begin recording your progress.",
                sessions = sessions ,
                onClickDelete = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "SmartStudy",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier,
    subjectCount: Int,
    studiedHours: String,
    goalHours: String
) {
    Row(modifier = modifier) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Subject Count",
            count = "$subjectCount"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Studied Hours",
            count = studiedHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hours",
            count = goalHours
        )
    }
}


@Composable
private fun SubjectCardsSection(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subjects. \n Click the + button to add new subjects",
    onAddIconClicked: () -> Unit
){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SUBJECTS",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onAddIconClicked ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Subject"
                )
            }
        }
        if( subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.book_icon),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) { subject ->
                SubjectCard(
                    subjectName = subject.name,
                    gradientColors = subject.colors,
                    onClick = {}
                )
            }
        }
    }
}