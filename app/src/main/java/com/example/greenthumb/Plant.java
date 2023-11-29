package com.example.greenthumb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Plant {
    @PrimaryKey(autoGenerate = true)
    public int plant_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "watering_cycle")
    public int wateringCycle;

    @ColumnInfo(name = "last_watered")
    public LocalDate lastWatered;

    @ColumnInfo(name = "light_req")
    public String lightReq;

    @ColumnInfo(name = "toxicity")
    public String toxicity;

    public Plant(String name, int wateringCycle, String lightReq, String toxicity){
        this.name = name;
        this.wateringCycle = wateringCycle;
        this.lightReq = lightReq;
        this.toxicity = toxicity;
    }

    @Ignore
    public Plant(String name, int wateringCycle, LocalDate lastWatered, String lightReq, String toxicity){
        this.name = name;
        this.wateringCycle = wateringCycle;
        this.lastWatered = lastWatered;
        this.lightReq = lightReq;
        this.toxicity = toxicity;
    }

    public String getLastWaterDate(){
        String month = this.getLastWatered().getMonth().toString();
        int day = this.getLastWatered().getDayOfMonth();
        String dayString = String.valueOf(this.getLastWatered().getDayOfMonth());
        String daySuffix;

        for (int i = 0; i < month.length(); i++) {
            Character c = month.charAt(i);
            if(i > 0){
                month = month.replace(month.charAt(i), Character.toLowerCase(c));
            }
        }

        if(day == 1 || day == 11 || day == 21 || day == 31){
            daySuffix = "st";
        } else if(day == 2 || day == 12 || day == 22) {
            daySuffix = "nd";
        } else if(day == 3 || day == 13 || day == 23) {
            daySuffix = "rd";
        } else {
            daySuffix = "th";
        }

        return "Last watered on: " + month + ", " + dayString + daySuffix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWateringCycle() {
        return wateringCycle;
    }

    public void setWateringCycle(int wateringCycle) {
        this.wateringCycle = wateringCycle;
    }

    public LocalDate getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDate lastWatered) {
        this.lastWatered = lastWatered;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public String getLightReq() {
        return lightReq;
    }

    public void setLightReq(String lightReq) {
        this.lightReq = lightReq;
    }

    public String getToxicity() {
        return toxicity;
    }

    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }
}
