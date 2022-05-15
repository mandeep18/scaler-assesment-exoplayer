package com.example.scaler.listener

interface RecyclerViewListener<T> {
    fun getViewLayout(key : Int): Int
    fun getItemViewType(model : T): Int
    fun setListSize(size : Int){}
    fun setCurrentItemPosition(position : Int){ }
    fun areItemsTheSame(newItem: T, oldItem: T):Boolean{ return false}
    fun areContentsTheSame(newItem:T,oldItem:T):Boolean{ return false}
    fun onRefresh() {}
}