package uz.gita_abdurakhmonov.conversionuz.data.ui_data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedData(
    @PrimaryKey(autoGenerate = false)
    val svId:Int,
    val type:String
)
