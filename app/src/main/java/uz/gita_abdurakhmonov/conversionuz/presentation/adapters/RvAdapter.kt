package uz.gita_abdurakhmonov.conversionuz.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import okhttp3.internal.format
import uz.gita_abdurakhmonov.conversionuz.R
import uz.gita_abdurakhmonov.conversionuz.data.response.ApiResponse
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UIData
import uz.gita_abdurakhmonov.conversionuz.data.ui_data.UiData
import uz.gita_abdurakhmonov.conversionuz.databinding.ItemConversionBinding
import uz.gita_abdurakhmonov.conversionuz.databinding.ItemCryptoBinding

class RvAdapter : RecyclerView.Adapter<RvAdapter.ItemViewHolder>() {
    private var list: List<UiData>? = null
    private var listener1: ((ApiResponse.CursResponse) -> Unit)? = null
    private var listener2: ((UIData) -> Unit)? = null
    fun setOnItemClickCurrency(block: (ApiResponse.CursResponse) -> Unit) {
        listener1 = block
    }
    fun setOnItemClickCrypto(block: (UIData) -> Unit) {
        listener2 = block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<UiData>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(data: UiData) {
            when (data) {
                is ApiResponse.CursResponse -> {
                    val binding = ItemConversionBinding.bind(view)
                    if (list != null) {
                        binding.ccy.text = data.ccy
                        binding.rate.text = "${data.rate} uzs"
                        binding.desCcy.text = data.ccyNmUZ

                        if (data.diff.startsWith("-")) {

                            binding.pointer.setImageResource(R.drawable.ic_down_right)
                        } else {
                            binding.pointer.setImageResource(R.drawable.ic_up_right)
                        }

                        binding.infoPercent.text =
                            format("%.2f", ((data.diff.toFloat() * 100) / data.rate.toFloat()))
                        var name = data.ccy.lowercase().substring(0, 2)
                        if (name == "xd") name = "sd"
                        val url = "https://flagcdn.com/w1280/$name.png"
                        Glide
                            .with(binding.root)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.img)
                            .into(binding.flag);
                    }
                }

                is UIData -> {
                    val binding = ItemCryptoBinding.bind(view)
                    if (list != null) {
                        binding.rate.text = "$${data.priceUsd}"
                        binding.ccy.text = data.symbol
                        binding.desCcy.text = data.namEid
                        binding.infoPercent.text = data.percentChange1h
                        if (data.percentChange24h.startsWith("-")) {
                            binding.sparkLine.sparkLineColor = Color.RED
                            binding.sparkLine.isGradientLine = true
//                            val list = ArrayList<Int>()
//                            for (i in 0..9){
//                                list[i] = 200 + Random().nextInt(100)
//                            }
//                            binding.sparkLine.setData(list)
                        } else {
//                            val list = ArrayList<Int>()
//                            for (i in 0..9){
//                                list[i] = 100 + Random().nextInt(200)
//                            }
//                            binding.sparkLine.setData(list)
                            binding.sparkLine.sparkLineColor = Color.YELLOW
                        }
                        if (data.percentChange1h.startsWith("-")) {
                            binding.pointer.setImageResource(R.drawable.ic_down_right)
                        } else {
                            binding.pointer.setImageResource(R.drawable.ic_up_right)
                        }
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
                            .into(binding.flag);
                    }
                }
            }

        }

        init {
            itemView.setOnClickListener {
                val data = list!![adapterPosition]
                when (data) {
                    is UIData -> {
                        Log.d("AAA", "data: $$data")
                       listener2?.invoke(data)
                    }

                    is ApiResponse.CursResponse -> {
                        Log.d("AAA", "data: $$data")
                        listener1?.invoke(data)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val data = list!![position]
        return when (data) {
            is UIData -> 0
            else -> 1
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            0 -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
                ItemViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_conversion, parent, false)
                ItemViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 10
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (list != null) {
            holder.bind(list!![position])
        }
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_anim)
        holder.itemView.startAnimation(animation)
    }
}