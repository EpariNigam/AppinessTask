package com.test.nigam.appiness.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.nigam.appiness.databinding.ItemDataBinding
import com.test.nigam.appiness.model.Item

class Adapter(context: Context) : RecyclerView.Adapter<Adapter.Holder>() {
    private val inflater = LayoutInflater.from(context)
    private val dataList: MutableList<Item> = mutableListOf()

    inner class Holder(val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemDataBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.item = dataList[position]
    }

    fun setData(list: List<Item>?) {
        dataList.clear()
        list?.let { dataList.addAll(it) }
        notifyDataSetChanged()
    }
}