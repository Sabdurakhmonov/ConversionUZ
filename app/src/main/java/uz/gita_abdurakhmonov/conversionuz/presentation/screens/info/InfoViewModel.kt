package uz.gita_abdurakhmonov.conversionuz.presentation.screens.info

import kotlinx.coroutines.flow.Flow
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData

interface InfoViewModel {
    val data:Flow<List<UiData>>
    val errorMessage:Flow<String>
    fun getAll(type:String)
    fun clickBack()
    abstract fun save(uiData: UiData)
}