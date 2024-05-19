package com.yajhz.ui.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yajhz.R
import com.yajhz.data.model.api.CategoryModel
import com.yajhz.databinding.ItemCategoryBinding
import com.yajhz.ui.base.BaseViewHolder
import com.yajhz.utils.ImageUtils

class CategoriesAdapter(private val mList: MutableList<CategoryModel.Data>?) :
    RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_CATEGORY = 1
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

            VIEW_TYPE_CATEGORY -> ExploreCategoryViewHolder(
                ItemCategoryBinding.inflate(
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
            VIEW_TYPE_CATEGORY
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

    fun addItems(list: List<CategoryModel.Data>?) {
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
    inner class ExploreCategoryViewHolder(private val binding: ItemCategoryBinding) :
        BaseViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            val model: CategoryModel.Data = mList?.get(position)!!

            binding.tvName.text = model.name_en

            ImageUtils.loadImage(model.image!!, binding.ivImg, itemView.context)

        }

    }
}