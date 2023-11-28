package com.example.greenthumb

import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.greenthumb.ui.theme.GreenThumbTheme

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val plantDB = Room.databaseBuilder(
            applicationContext,
            PlantDatabase::class.java, "greenthumb-database"
        ).allowMainThreadQueries().build()

        val plantDao = plantDB.plantDao()

        val plants: List<Plant> = plantDao.getAll();

        setContent {
            GreenThumbTheme {
                // A surface container using the 'background' color from the theme
                MyApp(plants, plantDao)
            }
        }
    }
}

@Composable
fun MyApp(plants: List<Plant>, plantDao: PlantDao){
    var isTitleScreen by rememberSaveable {mutableStateOf(true)}
    Surface(modifier = Modifier.fillMaxSize(), color = Color(234, 242, 239, 255)) {
        if(isTitleScreen) {
            TitleScreen(onContinueClicked = { isTitleScreen = false })
        } else {
            SeparateCards(plants, plantDao)
        }
    }
}

@Composable
fun TitleScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.app_name))
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Enter the Jungle...")
        }
    }
}



@Composable
fun SeparateCards(plants: List<Plant>, plantDao: PlantDao){


    val plantArray = remember {
        mutableStateListOf<Plant>()
    }

    for (plant in plants) {
        plantArray.add(plant)
    }

    LazyColumn (modifier = Modifier.padding(vertical = 4.dp)) {
        items (1){
            AddNewPlantCard(plantDao, plantArray)
        }
        items (items = plantArray) {plant ->
            PlantCard(plant)
        }
//        items(1){
//            Button(onClick = {  }) {
//
//            }
//        }
    }
}

@Composable
fun AddNewPlantCard(plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(102, 119, 97, 255),
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .border(2.dp, Color(13, 9, 10, 255), RoundedCornerShape(10.dp))
        ) {
            CardContentEntry(plantDao, plantArray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardContentEntry(plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    var expandedCard by remember { mutableStateOf(false) }

    Row {
        var modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ){
            Text(text = "New plant", style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Thin,
                fontSize = 32.sp
            ))
        }
        Column {
            Button(onClick = { expandedCard = !expandedCard }) {
                Text(text = "Add")
            }
        }
    }
    if (expandedCard){
        Row ( modifier = Modifier
            .padding()
        ) {
            Column {
                var plantName by remember { mutableStateOf("") }
                var water by remember { mutableStateOf(0) }
                var light by remember { mutableStateOf("") }
                var toxicity by remember { mutableStateOf("") }

                Row{
                    TextField(
                        value = plantName,
                        onValueChange = { plantName = it },
                        label = { Text(text = "Plant name")}
                    )
                }
                Row{
                    NumberPicker(
                        value = water,
                        range = 1..30,
                        onValueChange = {
                            water = it
                        }
                    )
                }
                Row{
                    TextField(
                        value = light,
                        onValueChange = { light = it },
                        label = { Text(text = "Light req.")}
                    )
                }
                Row{
                    TextField(
                        value = toxicity,
                        onValueChange = { toxicity = it },
                        label = { Text(text = "Toxicity")}
                    )
                }
                Button(onClick = { captureInput(plantName, water.toInt(), light, toxicity, plantDao, plantArray) }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(102, 119, 97, 255)
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(2.dp, Color(13, 9, 10, 255), RoundedCornerShape(10.dp))
    ) {
        CardContent(plant)
    }
}



@Composable
fun CardContent(plant: Plant) {
    var expandedCard by remember { mutableStateOf(false) }

    Row {
        var modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ){
            Text(text = plant.getName(), style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Thin,
                fontSize = 32.sp
            ))
        }
        Column {
            IconButton(onClick = { expandedCard = !expandedCard }) {
                Icon(
                    imageVector = if (expandedCard) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expandedCard) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    },
                )
            }
        }
    }
    if (expandedCard){
        Row {
            Text(text = plant.getWateringCycle().toString())
            Text(text = plant.getLightReq())
        }
    }
}




fun captureInput(plantName: String, water: Int, light: String, toxicity: String, plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {


    Log.i("USER_SUBMIT", "Name of plant: $plantName");
    Log.i("USER_SUBMIT", "Water req: $water");
    Log.i("USER_SUBMIT", "Light req: $light");
    Log.i("USER_SUBMIT", "Tox: $toxicity");

    plantDao.insertAll(Plant(plantName, water, light, toxicity))
    Log.i("INSERT", "" + plantDao.getAll());
    plantArray.add(Plant(plantName, water, light, toxicity))
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PlantWateringTheme {
//        Main("Android")
//    }
//}