package com.example.greenthumb

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenthumb.ui.theme.GreenThumbTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // TODO: Database files here
        val plants = List<ArrayList<String>>(3) { ArrayList<String>() };
        plants[0].add("Asparagus Fern");
        plants[0].add("7 Days");
        plants[0].add("Partial Sun");

        plants[1].add("Croton");
        plants[1].add("4 Days");
        plants[1].add("Full Sun");

        plants[2].add("Pothos");
        plants[2].add("10 Days");
        plants[2].add("Shade");

        setContent {
            GreenThumbTheme {
                // A surface container using the 'background' color from the theme
                MyApp(plants = plants)
            }
        }
    }
}

@Composable
fun MyApp(plants: List<ArrayList<String>>){
    var isTitleScreen by rememberSaveable {mutableStateOf(true)}
    Surface(modifier = Modifier.fillMaxSize(), color = Color(234, 242, 239, 255)) {
        if(isTitleScreen) {
            TitleScreen(onContinueClicked = { isTitleScreen = false })
        } else {
            SeparateCards(array = plants)
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
fun AddNewPlantCard(
    modifier: Modifier = Modifier
){
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(102, 119, 97, 255),
                ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .border(2.dp, Color(13, 9, 10, 255), RoundedCornerShape(10.dp))
        ) {
            CardContentEntry()
        }
    }
}

@Composable
fun SeparateCards(array: List<ArrayList<String>>){

    LazyColumn (modifier = Modifier.padding(vertical = 4.dp)) {
        items (1){
            AddNewPlantCard()
        }
        items(items = array) {plant ->
            PlantCard(plant)
        }
    }
}

@Composable
fun PlantCard(array: ArrayList<String>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(102, 119, 97, 255)
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(2.dp, Color(13, 9, 10, 255), RoundedCornerShape(10.dp))
    ) {
        CardContent(array)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardContentEntry() {
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
                var water by remember { mutableStateOf("") }
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
                    TextField(
                        value = water,
                        onValueChange = { water = it },
                        label = { Text(text = "Water req.")}
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
                Button(onClick = { captureInput(plantName, water, light, toxicity) }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Composable
fun CardContent(array: ArrayList<String>) {
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
            Text(text = array[0], style = MaterialTheme.typography.headlineLarge.copy(
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
            Text(text = array[1])
            Text(text = array[2])
        }
    }
    
}

fun captureInput(plantName: String, water: String, light: String, toxicity: String ) {
    val plantDB = PlantDB()

    Log.i("USER_SUBMIT", "Name of plant: $plantName");
    Log.i("USER_SUBMIT", "Water req: $water");
    Log.i("USER_SUBMIT", "Light req: $light");
    Log.i("USER_SUBMIT", "Tox: $toxicity");

    plantDB.handleSQL(plantName, water, light, toxicity);
    // TODO: Call onCreate after this is done
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    PlantWateringTheme {
//        Main("Android")
//    }
//}