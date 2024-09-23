package uz.gita_abdurakhmonov.conversionuz.presentation.screens.info

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.format
import uz.gita_abdurakhmonov.conversionuz.R
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import uz.gita_abdurakhmonov.conversionuz.databinding.BottomSheetBinding
import uz.gita_abdurakhmonov.conversionuz.databinding.ScreenInfoBinding
import uz.gita_abdurakhmonov.conversionuz.presentation.adapters.RvAdapter

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val adapter by lazy { RvAdapter() }

    //    private val adapterShimmer by lazy { RvAdapterShimmer() }
    private val viewModel by viewModels<InfoViewModelImpl>()
    private val args: InfoScreenArgs by navArgs()
    private lateinit var view: View
    private lateinit var viewBind: BottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.errorMessage.onEach {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(lifecycleScope)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val uiData: UiData = args.data

        binding.btnBack.setOnClickListener {
            viewModel.clickBack()
        }
        binding.btnPlus.setOnClickListener {
            Log.d("AAA", "onViewCreated:xlik")
            viewModel.save(uiData)
        }
        when (uiData) {
            is UIData -> {
                binding.ccy.text = uiData.symbol
                binding.summa1.hint = "10"
                binding.convert1.text = "1 ${uiData.symbol}  = ${uiData.priceUsd} USD"
                binding.convert2.isInvisible = false
                binding.convert2.text = "1 ${uiData.symbol} = ${uiData.priceBtc} BTC"
                val url = when (uiData.symbol.lowercase()) {
                    "xrp" -> {
                        "https://cryptologos.cc/logos/${uiData.symbol.lowercase()}-${uiData.symbol.lowercase()}-logo.png?v=034"
                    }
                    "steth" -> "https://cryptologos.cc/logos/steth-steth-logo.png?v=035"
                    "dot" -> "https://cryptologos.cc/logos/${uiData.namEid}-new-${uiData.symbol.lowercase()}-logo.png?v=034"
                    "op" -> "https://cryptologos.cc/logos/optimism-ethereum-op-logo.png?v=035"
                    "theta" -> "https://cryptologos.cc/logos/theta-network-theta-logo.png?v=035"
                    "bgb" -> "https://cryptologos.cc/logos/${uiData.namEid}-new-${uiData.symbol.lowercase()}-logo.png?v=034"
                    "bonk" -> "https://cryptologos.cc/logos/${uiData.namEid}1-${uiData.symbol.lowercase()}-logo.png?v=034"
                    "jasmy" -> "https://cryptologos.cc/logos/jasmy-jasmy-logo.png?v=035"
                    "bchsv" -> "https://cryptologos.cc/logos/bitcoin-cash-bch-logo.png?v=035"
                    "core" -> "https://cryptologos.cc/logos/core-dao-core-logo.png?v=035"
                    "not" -> "https://firebasestorage.googleapis.com/v0/b/bookapp-fd7a6.appspot.com/o/notcoin.webp?alt=media&token=33883fa8-d834-424c-8daa-5de62f49c4fe"
                    "cro" -> "https://cryptologos.cc/logos/cronos-cro-logo.png?v=035"
                    "ordi" -> "https://cryptologos.cc/logos/ordi-ordi-logo.png?v=035"
                    else -> "https://cryptologos.cc/logos/${uiData.namEid}-${uiData.symbol.lowercase()}-logo.png?v=034"
                }
                Glide
                    .with(binding.root)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.free_img_crypto)
                    .into(binding.flag1)

                binding.summa1.addTextChangedListener {
                    if (it.toString().isEmpty()) {
                        binding.summa2.text = (10 * uiData.priceBtc.toFloat()).toString()
                    } else {
                        val rate = (it.toString().toFloat() * uiData.priceBtc.toFloat())
                        binding.summa2.text = format("%f",rate)

                    }
                }

                binding.summa2.text = (10*uiData.priceBtc.toFloat()).toString()
                binding.ccy1.text = "BTC"
                val url1 = "https://cryptologos.cc/logos/bitcoin-btc-logo.png?v=035"
                Glide
                    .with(binding.root)
                    .load(url1)
                    .centerCrop()
                    .placeholder(R.drawable.free_img_crypto)
                    .into(binding.flag2)
            }

            is ApiResponse.CursResponse -> {
                binding.ccy.text = uiData.ccy
                var name1 = uiData.ccy.lowercase().substring(0, 2)
                if (name1 == "xd") name1 = "sd"
                val url1 = "https://flagcdn.com/w1280/$name1.png"
                Glide
                    .with(binding.root)
                    .load(url1)
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .into(binding.flag1)

                binding.summa1.addTextChangedListener {
                    if (it.toString().isEmpty()) {
                        binding.convert1.text = "1 ${uiData.ccy} = ${uiData.rate} UZS"
                        binding.summa2.text = (100 * uiData.rate.toFloat()).toString()
                    } else {
                        binding.convert1.text ="${it} ${uiData.ccy} = ${it.toString().toFloat() * uiData.rate.toFloat()} UZS"
                        binding.summa2.text =
                            (it.toString().toFloat() * uiData.rate.toFloat()).toString()
                    }
                }


                binding.summa2.text = uiData.rate
                binding.ccy1.text = "UZB"
                binding.convert2.isInvisible = true
                binding.convert1.text = "1 ${uiData.ccy} = ${uiData.rate} UZS"

                val url = "https://flagcdn.com/w1280/uz.png"
                Glide
                    .with(binding.root)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .into(binding.flag2)

            }
        }
        binding.btnMore.setOnClickListener {
            when (uiData) {
                is UIData -> {
                    viewModel.getAll("crypto")
                }

                is ApiResponse.CursResponse -> {
                    viewModel.getAll("currency")
                }
            }
            btnSheet()
            viewModel.data.onEach {
                if (it.isNotEmpty()) {
                    viewBind.rvAdapter.adapter = adapter
                    adapter.submitList(it)
                }

            }.launchIn(lifecycleScope)
        }
    }

    private fun btnSheet() {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        view = layoutInflater.inflate(R.layout.bottom_sheet, null, false)
        viewBind = BottomSheetBinding.bind(view)
        dialog.setContentView(viewBind.root)
        viewBind.rvAdapter.layoutManager = LinearLayoutManager(requireContext())
        dialog.show()

        adapter.setOnItemClickCurrency {
            glide2(it)
            dialog.dismiss()
        }
        adapter.setOnItemClickCrypto {
            glide1(it)
            dialog.dismiss()
        }

    }

    private fun glide1(data: UIData) {
        val uiData = args.data as UIData
        binding.summa1.hint = "10"
        binding.ccy1.text = data.symbol

        var rate = convertCurrency(10.0,uiData.priceBtc.toDouble(),data.priceBtc.toDouble())
        binding.summa2.text = format("%.3f",rate)

        rate = convertCurrency(1.0,uiData.priceUsd.toDouble(),data.priceUsd.toDouble())
        binding.convert1.text = format("1 ${uiData.symbol}  = %.4f USD",rate)

        binding.convert2.isInvisible = false
        rate = convertCurrency(1.0,uiData.priceBtc.toDouble(),data.priceBtc.toDouble())
        binding.convert2.text = format("1 ${uiData.symbol} = %.4f ${data.symbol}",rate)

        val url = when (data.symbol.lowercase()) {
            "xrp" -> {
                "https://cryptologos.cc/logos/${data.symbol.lowercase()}-${data.symbol.lowercase()}-logo.png?v=034"
            }

            "steth" -> "https://cryptologos.cc/logos/steth-steth-logo.png?v=035"
            "dot" -> "https://cryptologos.cc/logos/${data.namEid}-new-${data.symbol.lowercase()}-logo.png?v=034"
            "op" -> "https://cryptologos.cc/logos/optimism-ethereum-op-logo.png?v=035"
            "theta" -> "https://cryptologos.cc/logos/theta-network-theta-logo.png?v=035"
            "bgb" -> "https://cryptologos.cc/logos/${data.namEid}-new-${data.symbol.lowercase()}-logo.png?v=034"
            "bonk" -> "https://cryptologos.cc/logos/${data.namEid}1-${data.symbol.lowercase()}-logo.png?v=034"
            "jasmy" -> "https://cryptologos.cc/logos/jasmy-jasmy-logo.png?v=035"
            "bchsv" -> "https://cryptologos.cc/logos/bitcoin-cash-bch-logo.png?v=035"
            "core" -> "https://cryptologos.cc/logos/core-dao-core-logo.png?v=035"
            "not" -> "https://firebasestorage.googleapis.com/v0/b/bookapp-fd7a6.appspot.com/o/notcoin.webp?alt=media&token=33883fa8-d834-424c-8daa-5de62f49c4fe"
            "cro" -> "https://cryptologos.cc/logos/cronos-cro-logo.png?v=035"
            "ordi" -> "https://cryptologos.cc/logos/ordi-ordi-logo.png?v=035"
            else -> "https://cryptologos.cc/logos/${data.namEid}-${data.symbol.lowercase()}-logo.png?v=034"
        }
        Glide
            .with(binding.root)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.free_img_crypto)
            .into(binding.flag2)

        binding.summa1.addTextChangedListener {
            if (it.toString().isEmpty()) {
                binding.summa2.text = (10 * data.priceBtc.toFloat()).toString()
            } else {
                rate = it.toString().toDouble()*data.priceUsd.toDouble()
                binding.convert1.text = format("${it.toString()} ${uiData.symbol} = %.4f USD",rate)
                rate = it.toString().toDouble()*data.priceBtc.toDouble()
                binding.convert2.text= format("${it.toString()} ${uiData.symbol} = %.4f ${data.symbol}",rate)
                binding.summa2.text = (it.toString().toFloat() * data.priceBtc.toFloat()).toString()
            }
        }
    }

    private fun glide2(data: ApiResponse.CursResponse) {
        val uiData = args.data as ApiResponse.CursResponse
        val rate = convertCurrency(100.0, uiData.rate.toDouble(), data.rate.toDouble())
        val rateX = convertCurrency(1.0,uiData.rate.toDouble(),data.rate.toDouble())
        binding.convert1.text = format("1 ${uiData.ccy} = %.3f ${data.ccy}",rateX)
        binding.summa2.text = format("%.2f", rate)
        binding.ccy1.text = data.ccy

        var name = data.ccy.lowercase().substring(0, 2)
        if (name == "xd") name = "sd"
        val url = "https://flagcdn.com/w1280/$name.png"
        Glide
            .with(binding.root)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(binding.flag2)

        binding.summa1.addTextChangedListener {
            if (data.ccy == "UZB") {
                binding.ccy1.text = "UZS"
                if (it.toString().isEmpty()) {
                    binding.summa2.text = (100 * data.rate.toFloat()).toString()
                } else {
                    binding.summa2.text =
                        (it.toString().toFloat() * data.rate.toFloat()).toString()
                }
            } else {

                val firstToSum = uiData.rate.toDouble()
                val second = data.rate.toDouble()

                if (it.toString().isEmpty()) {
                    val rate = convertCurrency(100.0, firstToSum, second)
                    binding.summa2.text = format("%.2f", rate)
                } else {
                    val rateX = convertCurrency(it.toString().toDouble(),uiData.rate.toDouble(),data.rate.toDouble())
                    binding.convert1.text = format("${it.toString()} ${uiData.ccy} = %.4f ${data.ccy}",rateX)
                    val amount = it.toString().toDouble()
                    val rate = convertCurrency(amount, firstToSum, second)
                    binding.summa2.text = format("%.2f", rate)
                }
            }
        }
    }

    fun convertCurrency(amount: Double, fromRate: Double, toRate: Double): Double {
        // Avval so'mga aylantiramiz, keyin kerakli valyutaga
        val inSom = amount * fromRate
        return inSom / toRate
    }
}