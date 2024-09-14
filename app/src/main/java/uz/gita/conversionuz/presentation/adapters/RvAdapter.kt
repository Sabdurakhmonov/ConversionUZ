package uz.gita.conversionuz.presentation.adapters

import android.annotation.SuppressLint
import android.text.TextUtils.substring
import android.util.Half.toFloat
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import okhttp3.internal.format
import uz.gita.conversionuz.R
import uz.gita.conversionuz.data.response.ApiResponse
import uz.gita.conversionuz.databinding.ItemConversionBinding
import uz.gita.conversionuz.presentation.screens.menu.page.convert_currency.CurrencyPage

class RvAdapter : RecyclerView.Adapter<RvAdapter.ItemViewHolder>() {
    private var list: List<ApiResponse.CursResponse>? = null
    private var listener:((Int)->Unit)?=null
    fun setOnItemClickListener(block:(Int)->Unit){
        listener = block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ApiResponse.CursResponse>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemConversionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: ApiResponse.CursResponse) {
            if (list != null) {
                binding.ccy.text = data.ccy
                binding.rate.text = "${data.rate} uzs"
                binding.desCcy.text = data.ccyNmUZ

                if (data.diff.startsWith("-"))
                    binding.pointer.setImageResource(R.drawable.ic_down_right)
                else
                    binding.pointer.setImageResource(R.drawable.ic_up_right)
                binding.infoPercent.text = format("%.2f",((data.diff.toFloat() * 100) / data.rate.toFloat()))
                var name = data.ccy.lowercase().substring(0,2)
                if (name=="xd") name = "sd"
                val url = "https://flagcdn.com/w1280/$name.png"
                Glide
                    .with(binding.root)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.img)
                    .into(binding.flag);
            }
        }
        init {
            itemView.setOnClickListener {
                listener?.invoke(list!![adapterPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversion, parent, false)
        return ItemViewHolder(ItemConversionBinding.bind(view))
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