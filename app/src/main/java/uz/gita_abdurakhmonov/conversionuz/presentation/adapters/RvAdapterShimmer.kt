package uz.gita_abdurakhmonov.conversionuz.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita_abdurakhmonov.conversionuz.R

class RvAdapterShimmer:RecyclerView.Adapter<RvAdapterShimmer.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shimmer_item,parent,false)
        return ItemViewHolder(view)

    }
    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

    }
}