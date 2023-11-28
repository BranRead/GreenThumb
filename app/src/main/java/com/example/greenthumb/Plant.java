package com.example.greenthumb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    public Date lastWatered;

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

    public Date getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(Date lastWatered) {
        this.lastWatered = lastWatered;
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
