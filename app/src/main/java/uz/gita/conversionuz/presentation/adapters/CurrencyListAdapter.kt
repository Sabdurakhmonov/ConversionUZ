package uz.gita.conversionuz.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import okhttp3.internal.format
import uz.gita.conversionuz.R
import uz.gita.conversionuz.data.response.ApiResponse

import uz.gita.conversionuz.databinding.ItemConversionBinding


class CurrencyListAdapter :
    ListAdapter<ApiResponse.CursResponse, CurrencyListAdapter.RVViewHolder>(UserDiffUtil) {
    private var listener: ((ApiResponse.CursResponse) -> Unit)? = null
    fun onClickListener(block:(ApiResponse.CursResponse)->Unit){
        listener = block
    }

    object UserDiffUtil : DiffUtil.ItemCallback<ApiResponse.CursResponse>() {
        override fun areItemsTheSame(
            oldItem: ApiResponse.CursResponse,
            newItem: ApiResponse.CursResponse
        ): Boolean {
            return oldItem.id == newItem.id

        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ApiResponse.CursResponse,
            newItem: ApiResponse.CursResponse
        ): Boolean {
            return oldItem == newItem

        }
    }


    inner class RVViewHolder(private val binding: ItemConversionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: ApiResponse.CursResponse) {
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
                .into(binding.flag)
        }
        init {
            itemView.setOnClickListener {
                listener?.invoke(currentList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversion, parent, false)
        return RVViewHolder(ItemConversionBinding.bind(view))


    }

    override fun onBindViewHolder(holder: RVViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}