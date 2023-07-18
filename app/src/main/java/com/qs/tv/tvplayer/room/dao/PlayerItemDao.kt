package com.qs.tv.tvplayer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.qs.tv.tvplayer.room.entity.PlayerItem

@Dao
interface PlayerItemDao {

    @Insert
    fun insert(item: PlayerItem)

    @Query("SELECT * FROM player")
    fun getListLiveData(): LiveData<List<PlayerItem>>

    @Query("SELECT * FROM player")
    fun getList(): List<PlayerItem>

    @Update
    fun update(item: PlayerItem)

    @Delete
    fun delete(item: PlayerItem)

    @Query("SELECT COUNT(*) FROM player")
    fun getCount(): Int

    @Query("SELECT * FROM player LIMIT 1")
    fun getFirstRow(): PlayerItem?

}