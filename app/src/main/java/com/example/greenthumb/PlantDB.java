package com.example.greenthumb;

import android.util.Log;

public class PlantDB {
    public void handleSQL(String plantName, String water, String light, String toxicity){
        // TODO:Add to database and then update app somehow to
        //  show changes. Maybe call onCreate again?
        Log.i("DATABASE", "Name of plant: " + plantName);
        Log.i("DATABASE", "Water req: " + water);
        Log.i("DATABASE", "Light req: " + light);
        Log.i("DATABASE", "Tox: " + toxicity);
    }


}
