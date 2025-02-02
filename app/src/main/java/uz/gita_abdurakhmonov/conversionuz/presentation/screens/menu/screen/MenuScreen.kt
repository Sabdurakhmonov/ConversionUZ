package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.screen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita_abdurakhmonov.conversionuz.R
import uz.gita_abdurakhmonov.conversionuz.databinding.ScreenMenuBinding
import uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.adapter.VPAdapter

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.screen_menu) {
    private val binding by viewBinding(ScreenMenuBinding::bind)
    private val button = mutableListOf<View>()
    private lateinit var vpAdapter: VPAdapter
    private val viewModel by viewModels<MenuViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vpAdapter = VPAdapter(requireActivity())
        button.addAll(
            listOf(
                binding.btnCurrency,
                binding.btnCrypto,
                binding.btnLiked,
                binding.btnSetting
            )
        )
        binding.btnCurrency.isSelected = true
        selectBtn()
        clickBtn()
        binding.viewPager.adapter = vpAdapter
        binding.viewPager.isUserInputEnabled = false

        vpAdapter.clickItemCurrency().onEach {
            viewModel.listener1(it)
        }.launchIn(lifecycleScope)

        vpAdapter.clickItemCrypto().onEach {
            viewModel.listener2(it)
        }.launchIn(lifecycleScope)

        vpAdapter.clickItemFavCurr().onEach {
            viewModel.clickItemFavCurr(it)
        }.launchIn(lifecycleScope)
        vpAdapter.clickItemFavCrypto().onEach {
            viewModel.clickItemFavCrypto(it)
        }.launchIn(lifecycleScope)


    }

    @SuppressLint("ResourceAsColor")
    private fun selectBtn() {
        if (binding.btnCrypto.isSelected) {
            binding.icCry.setImageResource(R.drawable.ic_bitcoin_sel)
            binding.txCrypto.setTextColor(Color.BLUE)
        } else {
            binding.icCry.setImageResource(R.drawable.ic_bitcoin)
            binding.txCrypto.setTextColor(Color.DKGRAY)
        }
        if (binding.btnCurrency.isSelected) {
            binding.icCur.setImageResource(R.drawable.ic_currency_sel)
            binding.txCurrency.setTextColor(Color.BLUE)
        } else {
            binding.icCur.setImageResource(R.drawable.ic_currency)
            binding.txCurrency.setTextColor(Color.DKGRAY)
        }
        if (binding.btnSetting.isSelected) {
            binding.icSet.setImageResource(R.drawable.ic_info_sel)
            binding.txSetting.setTextColor(Color.BLUE)
        } else {
            binding.icSet.setImageResource(R.drawable.ic_info)
            binding.txSetting.setTextColor(Color.DKGRAY)
        }
        if (binding.btnLiked.isSelected) {
            binding.icLiked.setImageResource(R.drawable.ic_favourite_sel)
            binding.txLiked.setTextColor(Color.BLUE)
        } else {
            binding.icLiked.setImageResource(R.drawable.ic_favourite)
            binding.txLiked.setTextColor(Color.DKGRAY)
        }
    }

    private fun clickBtn() {
        button.forEach {
            it.setOnClickListener { view ->
                selectBtn()
                when (view.tag.toString()) {
                    "1" -> {
                        viewModel.currentPosition = 0
                        binding.viewPager.currentItem = 0
                        view.isSelected = true
                        binding.apply {
                            btnCrypto.isSelected = false
                            btnSetting.isSelected = false
                            btnLiked.isSelected = false
                        }
                        selectBtn()
                    }

                    "2" -> {
                        viewModel.currentPosition = 1
                        binding.viewPager.currentItem = 1
                        view.isSelected = true
                        binding.apply {
                            btnLiked.isSelected = false
                            btnSetting.isSelected = false
                            btnCurrency.isSelected = false
                        }
                        selectBtn()
                    }

                    "3" -> {
                        viewModel.currentPosition = 2
                        binding.viewPager.currentItem = 2
                        view.isSelected = true
                        binding.apply {
                            btnSetting.isSelected = false
                            btnCrypto.isSelected = false
                            btnCurrency.isSelected = false
                        }
                        selectBtn()
                    }

                    "4" -> {
                        viewModel.currentPosition = 3
                        binding.viewPager.currentItem = 3
                        view.isSelected = true
                        binding.apply {
                            btnLiked.isSelected = false
                            btnCrypto.isSelected = false
                            btnCurrency.isSelected = false
                        }
                        selectBtn()
                    }
                }
            }
        }
    }
}
