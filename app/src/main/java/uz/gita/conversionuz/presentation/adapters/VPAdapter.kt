package uz.gita.conversionuz.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import uz.gita.conversionuz.presentation.screens.menu.page.convert_crypto.CryptoPage
import uz.gita.conversionuz.presentation.screens.menu.page.convert_currency.CurrencyPage
import uz.gita.conversionuz.presentation.screens.menu.page.favourite.FavouritePage
import uz.gita.conversionuz.presentation.screens.menu.page.setting.SettingPage

class VPAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return 4
    }
    private val currency = CurrencyPage()

    fun clickItem() = channelFlow {
        currency.itemClick.collect{ trySend(it) }
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> currency
            1 -> CryptoPage()
            2 -> FavouritePage()
            else -> SettingPage()
        }
    }
}