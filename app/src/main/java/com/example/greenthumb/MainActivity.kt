// Garbage can icon
// <a href="https://www.flaticon.com/free-icons/recycle-bin" title="recycle bin icons">Recycle bin icons created by lakonicon - Flaticon</a>
// Watering can icon
// <a href="https://www.flaticon.com/free-icons/watering-can" title="watering can icons">Watering can icons created by Mihimihi - Flaticon</a>
// Plus icon
// <a href="https://www.flaticon.com/free-icons/plus" title="plus icons">Plus icons created by dmitri13 - Flaticon</a>
// Coffee Shop plants
// Photo by <a href="https://unsplash.com/@ceydaciftci?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Ceyda Çiftci</a> on <a href="https://unsplash.com/photos/green-potted-plants-on-brown-wooden-seat-dDVU6D_6T80?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
// Main app background
// Photo by <a href="https://unsplash.com/@anniespratt?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Annie Spratt</a> on <a href="https://unsplash.com/photos/green-leaf-hX_hf2lPpUU?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>

package com.example.greenthumb

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.greenthumb.ui.theme.GreenThumbTheme
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    // TODO: Make adding plant give an indication of the plant being added.
    // TODO: Make next plant in list not appear expanded when deleting.
    // TODO: Overhaul UI.
    // Make entire card clickable for dropdown
    // Icons for launcher and buttons
    // Format card info
    // Format add plant
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val plantDB = Room.databaseBuilder(
            applicationContext,
            PlantDatabase::class.java, "greenthumb-database"
        ).allowMainThreadQueries().build()

        val plantDao = plantDB.plantDao()

        //Only need this for hardcoding plants to test
        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        plantDao.insertAll(Plant(
            "Purple Heart",
            7,
            LocalDate.parse("25/11/2023", dateFormatter),
            "Bright",
            "Cat"
        ))

        plantDao.insertAll(Plant(
            "Aloe Vera",
            14,
            LocalDate.parse("10/11/2023", dateFormatter),
            "Indirect",
            "Cat"
        ))

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
    Surface(modifier = Modifier.fillMaxSize(), color = Color(246, 245, 250, 255)) {
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
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.titlescreen),
                contentScale = ContentScale.FillBounds
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .background(
                    Color(36, 130, 50, 200),
                    RoundedCornerShape(10.dp)
                )
                .border(
                    2.dp,
                    Color(4, 15, 15, 255),
                    RoundedCornerShape(10.dp)
                )
                .padding()
                .clickable { onContinueClicked() }
        ) {
            Column (
                modifier = Modifier,

            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ){
                    Text(text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge
                            .copy(color = Color(246, 245, 250, 255))
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Tap to start!",
                        color = Color(246, 245, 250, 255))
                }
            }
        }
    }
}

@Composable
fun SeparateCards(plants: List<Plant>, plantDao: PlantDao){
    Box(modifier = with(Modifier){
        fillMaxSize()
        .paint(
            painterResource(id = R.drawable.mainscreen),
            contentScale = ContentScale.FillBounds)
    }) {
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
                PlantCard(plant, plantDao, plantArray)
            }
        }
    }
}

@Composable
fun AddNewPlantCard(plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(36, 130, 50, 210),
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .border(2.dp, Color(4, 15, 15, 255), RoundedCornerShape(10.dp))
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
            .padding(8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        Column (
            modifier = Modifier
                .padding(8.dp)
                .weight(3f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(text = "New plant", style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Normal,
                color = Color(246, 245, 250, 255)
            ))
        }
        Column (
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            IconButton(onClick = { expandedCard = !expandedCard }) {
                Icon(
                    painter = painterResource(id = R.drawable.addplant),
                    contentDescription = "Add new plant",
                    modifier = Modifier.size(35.dp)
                )
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
                    // Using NumberPicker library found here:
                    // https://github.com/ChargeMap/Compose-NumberPicker
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
fun PlantCard(plant: Plant, plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(36, 130, 50, 200)
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .border(2.dp, Color(144, 103, 198, 255), RoundedCornerShape(10.dp))
    ) {
        CardContent(plant, plantDao, plantArray)
    }
}



@Composable
fun CardContent(plant: Plant, plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    var expandedCard by remember { mutableStateOf(false) }
    ///////////////////////////////////////////////////////////////////////////////////////
    //Need to add checkbox, display stuff properly (Name, number of days until next water,
    // last watered, light reqs and pet saftey), images for light.
    // Stretch goals -> Maybe leaf backgrounds for each plant tab?
    ///////////////////////////////////////////////////////////////////////////////////////
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
                .weight(11f)
                .padding(8.dp)
        ){
            Text(text = plant.getName(), style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Thin,
                fontSize = 30.sp,
                color = Color(246, 245, 250, 255)
            ))
        }
        Column (
            modifier = Modifier
                .weight(4f)
                .weight(1f)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            if(plant.getLastWatered() != null) {
                Text(
                    text = daysUntilNextWaterTitle(plant, plantDao),
                    modifier = Modifier.padding(0.dp, 16.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color(246, 245, 250, 255)
                    )
                )
            }
        }
        Column (
            modifier = Modifier
                .weight(1f)
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        ){
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

        Row (
            modifier = Modifier
                .padding(16.dp, 4.dp)
        ){
            if (plant.getLastWatered() != null) {
                Text(
                    text = "Last watered on: " +
                            plant.getLastWatered().month.toString() + " " +
                            plant.getLastWatered().dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color(246, 245, 250, 255)
                    )
                )
            }
        }

        Row (
            modifier = Modifier
                .padding(16.dp, 4.dp)
        ){
            if (plant.getLastWatered() != null) {
                Text(text = daysUntilNextWaterBody(plant, plantDao),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(246, 245, 250, 255)
                        )
                )
            }
        }

        Row (
            modifier = Modifier
                .padding(16.dp, 4.dp)
        ){
            Text(text = "Light Requirements: " + plant.getLightReq(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color(246, 245, 250, 255)
                )
            )
        }

        Row (
            modifier = Modifier
                .padding(16.dp, 4.dp)
        ){
            Text(text = "Toxicity: " + plant.getToxicity(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Normal,
                    color = Color(246, 245, 250, 255)
                )
            )
        }

        Row {
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(40.dp, 8.dp)
                    .background(Color(144, 103, 198, 255), RoundedCornerShape(30.dp))
                    .border(2.dp, Color(4, 15, 15, 255), RoundedCornerShape(30.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                IconButton(onClick = { plantWatered(plant, plantDao, plantArray) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.plantwatered),
                        contentDescription = "Plant watered today",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(40.dp, 8.dp)
                    .background(Color(142, 68, 61, 255), RoundedCornerShape(30.dp))
                    .border(2.dp, Color(4, 15, 15, 255), RoundedCornerShape(30.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { deletePlant(plant, plantDao, plantArray) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.deleteentry),
                        contentDescription = "Remove plant from list",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

fun daysUntilNextWaterTitle(plant: Plant, plantDao: PlantDao): String {
    val localDate: LocalDate = LocalDate.now()
    val lastWater = Period.between(plant.getLastWatered(), localDate).days
    val daysPassed = plant.getWateringCycle() - lastWater;
    if(daysPassed >= 0){
        return "" + daysPassed + " day(s)";
    } else {
        return "" + daysPassed.absoluteValue + " day(s) ago";
    }
}

fun daysUntilNextWaterBody(plant: Plant, plantDao: PlantDao): String {
    val localDate: LocalDate = LocalDate.now()
    val lastWater = Period.between(plant.getLastWatered(), localDate).days
    val daysPassed = plant.getWateringCycle() - lastWater;
    if(daysPassed > 0){
        return "Water in: " + daysPassed + " day(s).";
    } else if (daysPassed == 0){
        return "Water today!";
    } else {
        return "Overdo for watering by: " + daysPassed.absoluteValue + " day(s).";
    }
}

fun deletePlant(plant: Plant, plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {
    // Deletes from DB
    plantDao.delete(plant);

    // Deletes from mutable list triggering new list to be made
    for (listedPlant in plantArray) {
        if (plant.getPlant_id() == listedPlant.getPlant_id()){
            plantArray.remove(listedPlant);
            // Essential to avoid java.util.concurrentmodificationexception
            break;
        }
    }
}

fun plantWatered(plant: Plant, plantDao: PlantDao, plantArray: SnapshotStateList<Plant>) {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate: LocalDate = LocalDate.now()

    plant.setLastWatered(localDate)

    for ((index, listedPlant) in plantArray.withIndex()) {
        if (plant.getPlant_id() == listedPlant.getPlant_id()){
            plantArray[index] =  plant;
            break;
        }
    }
    plantDao.update(plant);
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