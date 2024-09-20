package uz.gita.conversionuz.presentation.screens.menu.page.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.coroutines.flow.channelFlow
import uz.gita.conversionuz.presentation.screens.menu.page.convert_crypto.CryptoPage
import uz.gita.conversionuz.presentation.screens.menu.page.convert_currency.CurrencyPage
import uz.gita.conversionuz.presentation.screens.menu.page.favourite.FavouritePage
import uz.gita.conversionuz.presentation.screens.menu.page.setting.SettingPage

class VPAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return 4
    }
    private val currency = CurrencyPage()
    private val crypto = CryptoPage()
    private val favourite = FavouritePage()

    fun clickItemCurrency() = channelFlow {
        currency.itemClick.collect{ trySend(it) }
    }
    fun clickItemCrypto() = channelFlow{
        crypto.itemClick.collect{trySend(it)}
    }
    fun clickItemFavCurr() = channelFlow {
        favourite.itemClickCurrency.collect{ trySend(it) }
    }
    fun clickItemFavCrypto() = channelFlow {
        favourite.itemClickCrypto.collect{ trySend(it) }
    }


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> currency
            1 -> crypto
            2 -> favourite
            else -> SettingPage()
        }
    }
}