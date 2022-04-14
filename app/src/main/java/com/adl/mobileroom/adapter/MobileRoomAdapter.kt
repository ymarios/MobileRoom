package com.adl.mobileroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adl.mobileroom.R
import com.adl.mobileroom.database.MobileRoomDatabase
import com.adl.mobileroom.database.model.MobileModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MobileRoomAdapter (val data :ArrayList<MobileModel>) : RecyclerView.Adapter<MobileRoomVH>() {
    lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileRoomVH {

        this.parent = parent

        return MobileRoomVH(LayoutInflater.from(parent.context).inflate(R.layout.item_kontak,parent,false))

    }

    override fun onBindViewHolder(holder: MobileRoomVH, position: Int) {
        holder.bindData(this@MobileRoomAdapter,position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun deleteDataAt( position: Int){
        GlobalScope.launch {

            val deleteData = MobileRoomDatabase.getInstance(parent.context).mobileRoomDao()
                .deleteModel(data.get(position))

            data.clear()
            data.addAll( ArrayList(MobileRoomDatabase.getInstance(parent.context).mobileRoomDao().getAll()))
            val mainExecutor = ContextCompat.getMainExecutor(parent.context)

            // Execute a task in the main thread
            mainExecutor.execute {
                // You code logic goes here.
                notifyDataSetChanged()
            }
        }
    }


}