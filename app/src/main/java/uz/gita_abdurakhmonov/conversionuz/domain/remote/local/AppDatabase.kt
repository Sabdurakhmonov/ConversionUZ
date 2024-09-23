package uz.gita_abdurakhmonov.conversionuz.domain.remote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.SavedData

@Database(entities = [SavedData::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun saved():AppDao
}