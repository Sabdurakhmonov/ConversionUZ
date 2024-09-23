package uz.gita_abdurakhmonov.conversionuz.presentation.screens.menu.page.favourite

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita_abdurakhmonov.conversionuz.R
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import uz.gita_abdurakhmonov.conversionuz.databinding.PageFavouriteBinding
import uz.gita_abdurakhmonov.conversionuz.presentation.adapters.CryptoListAdapter
import uz.gita_abdurakhmonov.conversionuz.presentation.adapters.CurrencyListAdapter

@AndroidEntryPoint
class FavouritePage : Fragment(R.layout.page_favourite) {

    private val binding by viewBinding(PageFavouriteBinding::bind)
    private val currencyAdapter by lazy { CurrencyListAdapter() }
    private val cryptoAdapter by lazy { CryptoListAdapter() }

    private val viewModel: FavouriteViewModel by viewModels<FavouriteViewModelImpl>()
    private var checkTab = true
    private var tab = 0
    private var listCurrency = mutableListOf<ApiResponse.CursResponse>()
    private var listCrypto = mutableListOf<UIData>()
    val itemClickCurrency = MutableSharedFlow<UiData>()
    val itemClickCrypto = MutableSharedFlow<UiData>()
    private lateinit var dialog:AestheticDialog.Builder

    @SuppressLint("ResourceAsColor", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getSavedCurrency()
        binding.rvAdapter.adapter = currencyAdapter
        binding.rvAdapter.layoutManager = LinearLayoutManager(requireContext())
        init()
    }


    private fun init(){
        create()

        binding.btnAllCurrency.setOnClickListener {
            anime1()
            tab = 0
            viewModel.getSavedCurrency()
            create()
        }
        binding.btnAllCrypto.setOnClickListener {
            anime2()
            tab = 1
            viewModel.getSavedCrypto()
            create()
        }
        binding.btnClear.setOnClickListener {
            AestheticDialog.Builder( requireActivity(), DialogStyle.FLAT, DialogType.INFO)
                .setTitle("Delete")
                .setMessage("Hamma saqlangan valyuta kurlani o'chirmoqchimiz?")
                .setDarkMode(false)
                .setGravity(Gravity.CENTER)
                .setAnimation(DialogAnimation.SHRINK)
                .setOnClickListener(object : OnDialogClickListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onClick(dialog: AestheticDialog.Builder) {
                        dialog.dismiss()
                        if(tab==0){
                            viewModel.clearAllCurrency()
                            currencyAdapter.submitList(emptyList())
                            currencyAdapter.notifyDataSetChanged()
                            binding.lotteAnime.isInvisible = false
                            binding.btnClear.isInvisible = true
                        }else{
                            viewModel.clearAllCrypto()
                            cryptoAdapter.submitList(emptyList())
                            cryptoAdapter.notifyDataSetChanged()
                            binding.lotteAnime.isInvisible = false
                            binding.btnClear.isInvisible = true
                        }
                    }
                })
                .show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (tab == 0) {
            viewModel.getSavedCurrency()
        } else viewModel.getSavedCrypto()
        create()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun create() {
        when (tab) {
            0 -> {
                viewModel.currencyData.onEach {
                    listCurrency.addAll(it)
                    binding.rvAdapter.adapter = currencyAdapter
                    binding.rvAdapter.layoutManager = LinearLayoutManager(requireContext())
                    currencyAdapter.onClickListener { data ->
                        lifecycleScope.launch {
                            itemClickCurrency.emit(data)
                        }

                    }
                    if (it.isNotEmpty()) {
                        binding.btnClear.isInvisible = false
                        listCurrency.clear()
                        binding.lotteAnime.isInvisible = true
                        binding.txEmpty.isInvisible = true
                        currencyAdapter.submitList(emptyList())
                        currencyAdapter.submitList(it)
                        listCurrency.addAll(it)
                        currencyAdapter.notifyDataSetChanged()
                    } else {
                        binding.btnClear.isInvisible = true
                        currencyAdapter.submitList(emptyList())
                        currencyAdapter.notifyDataSetChanged()
                        binding.lotteAnime.isInvisible = false
                        binding.txEmpty.isInvisible = false
                    }
                }.launchIn(lifecycleScope)
            }

            else -> {
                viewModel.cryptoData.onEach {
                    listCrypto.addAll(it)
                    binding.rvAdapter.adapter = cryptoAdapter
                    binding.rvAdapter.layoutManager = LinearLayoutManager(requireContext())
                    cryptoAdapter.onClickListener { data ->
                        lifecycleScope.launch {
                            itemClickCrypto.emit(data)
                        }
                    }

                    if (it.isNotEmpty()) {
                        binding.btnClear.isInvisible = false
                        listCrypto.clear()
                        binding.lotteAnime.isInvisible = true
                        binding.txEmpty.isInvisible = true
                        cryptoAdapter.submitList(emptyList())
                        cryptoAdapter.submitList(it)
                        listCrypto.addAll(it)
                        cryptoAdapter.notifyDataSetChanged()
                    } else {
                        binding.btnClear.isInvisible = true
                        cryptoAdapter.submitList(emptyList())
                        currencyAdapter.notifyDataSetChanged()
                        binding.lotteAnime.isInvisible = false
                        binding.txEmpty.isInvisible = false
                    }
                }.launchIn(lifecycleScope)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun anime1() {
        binding.txCurrency.setTextColor(Color.BLUE)
        binding.txCrypto.setTextColor(Color.DKGRAY)
        if (!checkTab) {
            binding.selected.animate()
                .translationX(0f)
                .setDuration(230)
                .start()
            checkTab = true
        }
    }

    @SuppressLint("ResourceAsColor")
    fun anime2() {
        binding.txCrypto.setTextColor(Color.BLUE)
        binding.txCurrency.setTextColor(Color.DKGRAY)
        val xTab = binding.selected.x
        val xSel = binding.btnAllCrypto.x
        if (checkTab) {
            binding.selected.animate()
                .translationX(xSel - xTab)
                .setDuration(230)
                .start()
            checkTab = false
        }
    }

}