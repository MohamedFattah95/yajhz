package com.yajhz.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yajhz.R
import com.yajhz.data.model.api.SellerModel
import com.yajhz.databinding.ItemTrendingBinding
import com.yajhz.ui.base.BaseViewHolder
import com.yajhz.utils.ImageUtils

class TrendingAdapter(private val mList: MutableList<SellerModel>?) :
    RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_SELLER = 1
    }

    private var mCallback: Callback? = null

    fun setCallback(callback: Callback?) {
        mCallback = callback
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {

            VIEW_TYPE_SELLER -> SellerViewHolder(
                ItemTrendingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            VIEW_TYPE_EMPTY -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_empty_view, parent, false)
            )

            else -> EmptyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_empty_view, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList != null && mList.size > 0) {
            VIEW_TYPE_SELLER
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (mList != null && mList.size > 0) {
            mList.size
        } else {
            0
        }
    }

    fun addItems(list: List<SellerModel>?) {
        mList?.addAll(list!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mList?.clear()
        notifyDataSetChanged()
    }

    interface Callback {

    }

    class EmptyViewHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView!!) {
        override fun onBind(position: Int) {}
    }

    @SuppressLint("NonConstantResourceId")
    inner class SellerViewHolder(private val binding: ItemTrendingBinding) :
        BaseViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            val model: SellerModel = mList?.get(position)!!

            ImageUtils.loadImage(model.logo!!, binding.ivImg, itemView.context)

        }

    }
}