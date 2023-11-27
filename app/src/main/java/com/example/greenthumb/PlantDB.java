package com.example.greenthumb;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlantDB extends SQLiteOpenHelper {

    SQLiteDatabase plantDB = this.getReadableDatabase();
    public static final int DATABASE_VERSION = 1;
    public long numOfPlants = 1 + DatabaseUtils.queryNumEntries(plantDB, "plants");

    public PlantDB(Context context) {
        super(context, "PlantDB", null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase plantDB) {
        plantDB.execSQL("CREATE TABLE plants (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR(256)," +
                " water int," +
                " last_water date," +
                " light VARCHAR(256)," +
                " toxicity VARCHAR(256))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase plantDB, int i, int i2) {
        Log.d("Table Upgrade", "From: " + i + " to " + DATABASE_VERSION);
        plantDB.execSQL("DROP TABLE IF EXISTS plants");
        onCreate(plantDB);
    }

    public void handleSQL(String plantName, String water, String light, String toxicity){
        // TODO:Add to database and then update app somehow to
        //  show changes. Maybe call onCreate again?
        Log.i("DATABASE", "Name of plant: " + plantName);
        Log.i("DATABASE", "Water req: " + water);
        Log.i("DATABASE", "Light req: " + light);
        Log.i("DATABASE", "Tox: " + toxicity);

        plantDB.execSQL("INSERT INTO plants(name, water, last_water, light, toxicity) VALUES " +
                "('" + plantName +
                "', " + water +
                ", '" + "2023-11-25" +
                "', '" + light +
                "', '" + toxicity + "')");
    }

    public String[][] getRecords() {

        if(numOfPlants > 0) {
            String[] projection = {"id", "name", "water", "last_water", "light", "toxicity"};
            String selection = "id < ?";
            String[] rangeDisplayed = {String.valueOf(numOfPlants)};

            String sortOrder = "id" + " ASC";

            Cursor c = plantDB.query(
                    "plants",
                    projection,
                    selection,
                    rangeDisplayed,
                    null,
                    null,
                    sortOrder
            );

            String[][] plantArray = new String[c.getCount()][5];

            c.moveToFirst();
            long rowID = c.getLong(c.getColumnIndexOrThrow("id"));

            String name = "";
            String water = "";
            String lastWatering = "";
            String light = "";
            String toxicity = "";

            do {
                int pos = c.getPosition();
                int columnCount = c.getColumnCount();

                for (int i = 0; i < columnCount; ++i) {
                    name = c.getString(1);
                    water = c.getString(2);
                    lastWatering = c.getString(3);
                    light = c.getString(4);
                    toxicity = c.getString(5);
                }

                plantArray[pos][0] = name;
                plantArray[pos][1] = water;
                plantArray[pos][2] = lastWatering;
                plantArray[pos][3] = light;
                plantArray[pos][4] = toxicity;
            } while (c.moveToNext());

            c.close();
            return plantArray;
        } else {
            return new String[0][0];
        }



    }
}
