package com.adl.mobileroom

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.adl.mobileroom.database.MobileRoomDatabase
import com.adl.mobileroom.database.model.MobileModel
import com.location.aravind.getlocation.GeoLocator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tambah_kontak.*
import kotlinx.android.synthetic.main.item_kontak.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


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

        btnGPS.isEnabled = false

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                111
            )
        else
            btnGPS.isEnabled = true

        btnGPS.setOnClickListener {
            var city: String = editTextGPS.text.toString()
            var gc = Geocoder(this, Locale.getDefault())
            var addresses = gc.getFromLocationName(city, 2)
            var address: Address = addresses.get(0)
            textGPS.visibility = View.VISIBLE
            textGPS.setText("${address.latitude} \n ${address.longitude} \n ${address.locality}")
        }
    }
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(
                requestCode, permissions, grantResults)
            if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                button.isEnabled = true
        }

    }
