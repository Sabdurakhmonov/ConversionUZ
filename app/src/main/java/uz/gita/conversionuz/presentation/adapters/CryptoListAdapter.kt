package uz.gita.conversionuz.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.conversionuz.R
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.data.ui_data.UIData
import uz.gita.conversionuz.databinding.ItemCryptoBinding

class CryptoListAdapter : ListAdapter<UIData, CryptoListAdapter.RVViewHolder>(UserDiffUtil) {
    private var listener: ((UIData) -> Unit)? = null
    fun onClickListener(block: (UIData) -> Unit) {
        listener = block
    }

    object UserDiffUtil : DiffUtil.ItemCallback<UIData>() {
        override fun areItemsTheSame(oldItem: UIData, newItem: UIData): Boolean {
            return oldItem.symbol == newItem.symbol

        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: UIData, newItem: UIData): Boolean {
            return oldItem == newItem
        }
    }

    inner class RVViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: UIData) {

            binding.rate.text = "$${data.priceUsd}"
            binding.ccy.text = data.symbol
            binding.desCcy.text = data.namEid
            binding.infoPercent.text = data.percentChange1h
            if (data.percentChange24h.startsWith("-")) {
                binding.sparkLine.sparkLineColor = Color.RED
                binding.sparkLine.isGradientLine = true
            } else {
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
        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }
    }


    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_crypto, parent, false)
        return RVViewHolder(ItemCryptoBinding.bind(view))
    }
}