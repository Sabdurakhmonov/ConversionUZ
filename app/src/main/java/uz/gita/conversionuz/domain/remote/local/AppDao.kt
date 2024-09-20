package uz.gita.conversionuz.domain.remote.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.conversionuz.data.ui_data.SavedData

@Dao
interface AppDao {
    @Query("SELECT*FROM saveddata WHERE type = :type")
    fun getAllData(type: String): List<SavedData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addData(data: SavedData)

    @Query("DELETE FROM saveddata WHERE type = :type")
    fun delete(type: String)

    @Delete
    fun deleteData(data: SavedData)
}