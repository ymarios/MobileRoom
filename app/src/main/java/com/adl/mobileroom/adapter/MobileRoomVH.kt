package com.adl.mobileroom.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_kontak.view.*

class MobileRoomVH(view: View) : RecyclerView.ViewHolder(view) {

    val nama = view.lblNama
    val gender = view.lblGender
    val umur = view.lblUmur
    val status = view.lblStatus
    val delete = view.btnDelete

    fun bindData(adapter: MobileRoomAdapter, position: Int) {

        nama.setText(adapter.data.get(position).nama)
        gender.setText(adapter.data.get(position).gender)
        umur.setText(adapter.data.get(position).umur)
        status.setText(adapter.data.get(position).status)

        delete.setOnClickListener {
            adapter.deleteDataAt(position)


        }

    }
}