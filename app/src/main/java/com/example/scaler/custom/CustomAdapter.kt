package com.example.scaler.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.scaler.listener.RecyclerViewListener
import com.example.scaler.listener.ViewAttachListener

class CustomAdapter<T>(private val activity:AppCompatActivity?): RecyclerView.Adapter<CustomViewHolder<T>>(){

    private var isLoading = false
    private val customModelList:MutableList<Any> = arrayListOf()


    private var onClickListener: Any? = null
    private var anyObject: Any? = null
    private var _layout: Int? = null

    lateinit var recyclerViewListener: RecyclerViewListener<T>
    internal lateinit var viewAttachListener: ViewAttachListener<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        var layout = _layout
        if(layout==null){
            layout = recyclerViewListener.getViewLayout(viewType)
        }
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater,layout, parent, false)
        return CustomViewHolder<T>(binding,anyObject,onClickListener)
    }

    override fun onViewDetachedFromWindow(holder: CustomViewHolder<T>) {
        if(this::viewAttachListener.isInitialized){
            viewAttachListener.onViewDetachedFromWindow(holder,holder.itemView,holder.adapterPosition)
        }
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: CustomViewHolder<T>) {
        if(this::viewAttachListener.isInitialized){
            viewAttachListener.onViewAttachedToWindow(holder,holder.itemView,holder.adapterPosition)
        }
        super.onViewAttachedToWindow(holder)
    }


    fun setOnClickListener(onClickListener:Any?){
        this.onClickListener = onClickListener
    }

    fun setItemLayout(layout:Int){
        this._layout = layout
    }

    fun setAnyObject(anyObject:Any?){
        this.anyObject = anyObject
    }

    override fun onBindViewHolder(holder: CustomViewHolder<T>, position: Int) {
        @Suppress("UNCHECKED_CAST")
        holder.bind(data = customModelList[position] as T)
    }

    fun addItems(itemList: MutableList<Any>){
        addItems(itemList,true)
    }

    fun addItems(itemList: MutableList<Any>,notifyDataSetChanged:Boolean){
        val start = customModelList.size
        customModelList.addAll(itemList)
        if(notifyDataSetChanged)
            notifyItemRangeInserted(start,itemList.size)
        isLoading = false
    }

    fun addItems(position: Int,itemList: MutableList<Any>){
        addItems(position,itemList,true)
    }

    fun addItems(position: Int,itemList: MutableList<Any>,notifyDataSetChanged:Boolean){
        customModelList.addAll(position,itemList)
        if(notifyDataSetChanged)
            notifyItemRangeInserted(position,itemList.size)
        isLoading = false
    }


    fun setLoading(isLoading:Boolean){
        this.isLoading = isLoading
    }

    fun clearItems(notifyDataSetChanged:Boolean){
        customModelList.clear()
        if(notifyDataSetChanged)
            notifyDataSetChanged()
    }

    fun clearItems(){
        clearItems(true)
    }


    fun addItem(item:Any){
        customModelList.add(item)
        notifyItemInserted(customModelList.size)
    }

    fun addItem(position:Int,item:Any){
        customModelList.add(position,item)
        notifyItemInserted(position)
    }

    fun setItem(position:Int,item:Any){
        customModelList.set(position,item)
        notifyItemChanged(position)
    }

    fun removeItem(position:Int){
        customModelList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItem(position: Int):Any{
        return customModelList.get(position)
    }

    fun getItems():MutableList<T>{
        return customModelList as MutableList<T>
    }

    override fun getItemCount():Int{
        if(this::recyclerViewListener.isInitialized){
            recyclerViewListener.setListSize(customModelList.size)
        }
        return customModelList.size
    }

}