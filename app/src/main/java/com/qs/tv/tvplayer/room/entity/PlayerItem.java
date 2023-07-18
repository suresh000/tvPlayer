package com.qs.tv.tvplayer.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.qs.tv.tvplayer.room.ColumnInfoKeys;

@Entity(tableName = "player")
public class PlayerItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ColumnInfoKeys.KEY_ID)
    private int id;

    @ColumnInfo(name = ColumnInfoKeys.KEY_NAME)
    private String name;

    @ColumnInfo(name = ColumnInfoKeys.KEY_PATH)
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
