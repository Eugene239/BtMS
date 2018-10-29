package ru.eupavlov.btms.common

import android.support.v7.widget.RecyclerView
import org.koin.standalone.KoinComponent

abstract class BaseListAdapter<T, E : RecyclerView.ViewHolder> : RecyclerView.Adapter<E>(), KoinComponent {
    val TAG = javaClass.name
    val list = mutableListOf<T>()

    open fun setValues(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}