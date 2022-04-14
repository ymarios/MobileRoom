package com.adl.mobileroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.adl.mobileroom.database.MobileRoomDatabase
import com.adl.mobileroom.database.model.MobileModel
import kotlinx.android.synthetic.main.activity_tambah_kontak.*
import kotlinx.android.synthetic.main.item_kontak.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TambahKontak : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_kontak)

        val gender = resources.getStringArray(R.array.Gender)
        val status = resources.getStringArray(R.array.Status)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, gender
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@TambahKontak,
                        getString(R.string.selected_item) + "" +
                                "" + gender[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        if (spinner2 != null) {
            val adapter2 = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, status
            )
            spinner2.adapter = adapter2

            spinner2.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@TambahKontak,
                        getString(R.string.selected_item2) + "" +
                                "" + status[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        btnAdd.setOnClickListener({
            val mobiledata = MobileModel (0, txtInputNama.text.toString(), spinner.selectedItem.toString(), txtInputUmur.text.toString(), spinner2.selectedItem.toString())

            GlobalScope.launch {
                MobileRoomDatabase.getInstance(this@TambahKontak).mobileRoomDao().insertMobile(mobiledata)

                val intent = Intent()
                intent.putExtra("data",mobiledata)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        })

    }
}