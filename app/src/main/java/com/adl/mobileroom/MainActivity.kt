package com.adl.mobileroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.adl.mobileroom.adapter.MobileRoomAdapter
import com.adl.mobileroom.database.MobileRoomDatabase
import com.adl.mobileroom.database.model.MobileModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db: MobileRoomDatabase

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

            //if(result.data?.hasExtra("data")!!){
                //lstMobile.add(result.data!!.extras?.getParcelable<MobileModel>("data")!!)
                //mobileadapter.notifyDataSetChanged()
            //}
                GlobalScope.launch {
                    lstMobile.clear()
                    lstMobile.addAll(ArrayList(getAllData()))

                    this@MainActivity.runOnUiThread({
                        mobileadapter.notifyDataSetChanged()
                    })
                }

            }
        }

    lateinit var mobileadapter: MobileRoomAdapter
    var lstMobile = ArrayList<MobileModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            MobileRoomDatabase::class.java, "mobiledb"
        ).build()

        GlobalScope.launch {

            lstMobile = ArrayList(getAllData())

            this@MainActivity.runOnUiThread({
                mobileadapter = MobileRoomAdapter(lstMobile)
                lstItemMobile.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = mobileadapter
                }
            })

        }

        button.setOnClickListener({
            val intent = Intent(this@MainActivity, TambahKontak::class.java)
            resultLauncher.launch(intent)
        })

    }

    fun getAllData(): List<MobileModel> {
        return MobileRoomDatabase.getInstance(this@MainActivity).mobileRoomDao().getAll()

    }
}