package uz.gita.conversionuz.presentation.screens.menu.page.convert_currency

import android.annotation.SuppressLint
import android.content.Context
import android.inputmethodservice.InputMethodService.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.conversionuz.R
import uz.gita.conversionuz.data.ui_data.UiData
import uz.gita.conversionuz.databinding.PageCurrencyBinding
import uz.gita.conversionuz.presentation.adapters.RvAdapter
import uz.gita.conversionuz.presentation.adapters.RvAdapterShimmer

@AndroidEntryPoint
class CurrencyPage:Fragment(R.layout.page_currency) {
    private val binding by viewBinding(PageCurrencyBinding::bind)
    private val viewModel by viewModels<CurrencyViewModelImpl>()
    private lateinit var adapter:RvAdapter
    private lateinit var adapterShimmer :RvAdapterShimmer
    val itemClick = MutableSharedFlow<UiData>()
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("AAA", "onViewCreated")
        viewModel.getAll()
        adapter = RvAdapter()
        adapterShimmer = RvAdapterShimmer()
        binding.rvAdapter.adapter = adapterShimmer

        viewModel.data.onEach {
            if(it.isNotEmpty()){
                binding.rvAdapter.adapter = adapter
                binding.lotteAnime.isInvisible = true
                adapter.submitList(it)
            }
        }.launchIn(lifecycleScope)

        binding.rvAdapter.layoutManager = LinearLayoutManager(requireContext())
        adapter.setOnItemClickCurrency {
            lifecycleScope.launch {
                itemClick.emit(it)
            }
        }
        binding.btnSearch.setOnClickListener {
            viewModel.clickSearch()
        }
        if (binding.rvAdapter.isFocusable){
            viewModel.clickBack()
        }

        binding.btnBack.setOnClickListener {
            viewModel.clickBack()
        }
        binding.searchView.addTextChangedListener {
            viewModel.search(it.toString())
        }
        viewModel.showPlaceHolder.onEach {
            it.onSuccess {
                binding.lotteAnime.isInvisible = false
                adapter.submitList(emptyList())
            }.onFailure { binding.lotteAnime.isInvisible = true }

        }.launchIn(lifecycleScope)

        viewModel.searchBack.onEach {
            closeKeyboard(requireContext(),binding.searchView)
            binding.btnBack.isVisible = false
            binding.searchView.isVisible = false
            binding.title.isVisible = true
            binding.btnSearch.isVisible = true
        }.launchIn(lifecycleScope)

        viewModel.searchData.onEach {
            binding.apply {
                btnSearch.isVisible = false
                searchView.isFocusable = true
                btnBack.isVisible = true
                searchView.isVisible = true
                title.isVisible = false
                focusEditText(requireContext(),searchView)
            }
        }.launchIn(lifecycleScope)
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d("AAA", "onResume:")
//        viewModel.data.onEach {
//            Log.d("AAA", "onResume: ${it.size}")
//            binding.rvAdapter.adapter = adapter
//            binding.lotteAnime.isInvisible = true
//            adapter.submitList(it)
//        }.launchIn(lifecycleScope)
//    }


    @SuppressLint("SetTextI18n")
    private fun closeKeyboard(context: Context, editText: EditText) {
        if (view != null) {
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            editText.setText("")
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }

    private fun focusEditText(context: Context,editText: EditText){
        editText.requestFocus()
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT)
    }
}