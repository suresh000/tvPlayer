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

    @ColumnInfo(name = ColumnInfoKeys.KEY_STORAGE_PATH)
    private String storagePath;

    @ColumnInfo(name = ColumnInfoKeys.KEY_URL)
    private String url;

    @ColumnInfo(name = ColumnInfoKeys.KEY_STORAGE_THUMBNAIL_PATH)
    private String storageThumbnailPath;

    @ColumnInfo(name = ColumnInfoKeys.KEY_THUMBNAIL_URL)
    private String thumbnailUrl;

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

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStorageThumbnailPath() {
        return storageThumbnailPath;
    }

    public void setStorageThumbnailPath(String storageThumbnailPath) {
        this.storageThumbnailPath = storageThumbnailPath;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
