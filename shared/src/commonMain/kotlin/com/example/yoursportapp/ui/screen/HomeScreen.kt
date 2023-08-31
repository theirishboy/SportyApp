package com.example.yoursportapp.ui.screen


import YourSportApp.shared.SharedRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import dev.icerock.moko.resources.compose.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.yoursportapp.data.Exercise
import com.example.yoursportapp.data.SportSession
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    val items = listOf("Exercises","Workout","Profile")
    val icons = listOf(SharedRes.images.format_list_bulleted_symbol,SharedRes.images.fitness_center_black_24dp,SharedRes.images.account_circle_black_24dp)
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(painter = painterResource(icons[index]),modifier = Modifier.size(24.dp).background(color = Color.Transparent), contentDescription ="" ) },
                        label = { Text(item) },
                        selected =  false,
                        onClick = {},
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Bienvenue Jean Kevin")
                }
            )
        },
    ){innerPadding ->
        SportSessionsList(innerPadding)
    }
}
@Composable
fun SportSessionsList(innerPadding: PaddingValues) {
    val ExerciceList: MutableList<Exercise> = arrayListOf()
    LazyColumn( modifier = Modifier.fillMaxSize(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        items(count = 10 ){
            Spacer(modifier = Modifier.height(8.dp))
            SportSessionShortcut(sportSession = SportSession(1, LocalDate(2023,12,31),"mondaySession",1,
                ExerciceList))

        }
    }

}
@Composable
fun SportSessionShortcut(sportSession: SportSession) {
    Box(
        modifier = Modifier.height(110.dp).fillMaxSize(0.95f),
        contentAlignment = Alignment.Center
    ) {
        Card() {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.weight(0.7f)) {
                    Row {
                        Text(
                            text = sportSession.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold ,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Row {
                        Image(painterResource(SharedRes.images.calendar_today_symbol), modifier = Modifier.size(16.dp), contentDescription = "Date")
                        Text(
                            text = sportSession.date.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )}
                    Row {
                        Image(
                            painterResource(SharedRes.images.exercise_symbole),
                            modifier = Modifier.size(16.dp),
                            contentDescription = "Session content"
                        )
                        Text(
                            text = sportSession.exercises.size.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                }
                Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(0.3f)) {
                    Button(
                        onClick = {},
                        enabled = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(10.dp).fillMaxSize(),
                    ) {
                        Image(painter = painterResource(SharedRes.images.east_black_24dp), contentDescription = "go to workout")
                    }
                }




            }
        }
    }
}