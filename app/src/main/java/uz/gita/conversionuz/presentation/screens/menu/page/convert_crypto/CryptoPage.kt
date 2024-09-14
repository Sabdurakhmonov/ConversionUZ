package uz.gita.conversionuz.presentation.screens.menu.page.convert_crypto

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.conversionuz.R
import uz.gita.conversionuz.databinding.PageCryptoBinding
@AndroidEntryPoint
class CryptoPage:Fragment(R.layout.page_crypto) {
    private val binding by viewBinding(PageCryptoBinding::bind)
    private val viewModel by viewModels<CryptoViewModelImpl>()
}