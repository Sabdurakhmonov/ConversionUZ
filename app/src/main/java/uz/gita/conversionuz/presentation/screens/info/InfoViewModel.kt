package uz.gita.conversionuz.presentation.screens.info

import kotlinx.coroutines.flow.Flow
import uz.gita.conversionuz.data.ui_data.UiData

interface InfoViewModel {
    val data:Flow<List<UiData>>
    val errorMessage:Flow<String>
    fun getAll(type:String)
    fun clickBack()
    abstract fun save(uiData: UiData)
}