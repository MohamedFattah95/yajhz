package com.yajhz.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yajhz.R
import com.yajhz.data.model.api.SellerModel
import com.yajhz.databinding.ItemPopularBinding
import com.yajhz.ui.base.BaseViewHolder
import com.yajhz.utils.CommonUtils
import com.yajhz.utils.ImageUtils

class PopularAdapter(private val mList: MutableList<SellerModel>?) :
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
                ItemPopularBinding.inflate(
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
        fun doFavorite(id: Int?)
    }

    class EmptyViewHolder internal constructor(itemView: View?) :
        BaseViewHolder(itemView!!) {
        override fun onBind(position: Int) {}
    }

    @SuppressLint("NonConstantResourceId")
    inner class SellerViewHolder(private val binding: ItemPopularBinding) :
        BaseViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            val model: SellerModel = mList?.get(position)!!

            binding.tvTitle.text = model.name
            binding.tvDest.text =
                CommonUtils.formatDistance(model.distance?.toDouble() ?: 0.0) + " KM"
            binding.tvRate.text = "(" + model.rate + ")"
            binding.ratingBar.rating = model.rate?.toFloat() ?: 0f
            if (model.is_favorite)
                binding.ivFav.setImageResource(R.drawable.ic_loved)
            else
                binding.ivFav.setImageResource(R.drawable.ic_love)

            Glide.with(itemView)
                .load(model.image)
                .placeholder(R.drawable.splash_bg)
                .into(binding.ivImg)

            itemView.setOnClickListener {

                model.is_favorite = !model.is_favorite
                notifyItemChanged(position)

                mCallback?.doFavorite(model.id)
            }

        }

    }
}