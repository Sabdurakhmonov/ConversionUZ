package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.screen

import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData

interface MenuViewModel {
    fun listener1(uiData: UiData)
    fun listener2(uiData: UiData)
    fun clickItemFavCrypto(uiData: UiData)
    fun clickItemFavCurr(uiData: UiData)
}