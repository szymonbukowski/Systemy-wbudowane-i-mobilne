package com.example.bmi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    lateinit var historyRecords:ArrayList<BmiDataPack>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initResultList()
        //testRecyclerRecords()
        initRecyclerView()
    }

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
    private fun initResultList(){
        val sharedPref = getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)
        val gson = Gson()
        val defVal = gson.toJson(ArrayList<BmiDataPack>())
        historyRecords = Gson().fromJson<ArrayList<BmiDataPack>>(sharedPref.getString(MainActivity.historyArrayKey,defVal))

    }
    fun initRecyclerView(){
        val recycler = his_recycler
        val adapter = HistoryAdapter(historyRecords)
        Toast.makeText(this,historyRecords.size.toString(),Toast.LENGTH_SHORT).show()
        if(historyRecords.size == 0) his_titleTV.text = "history empty"
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }
    fun testRecyclerRecords(){
        historyRecords.add(BmiDataPack(getColor(R.color.roz_pompejanski),'m',"25.21","category","123","321"))
        historyRecords.add(BmiDataPack(getColor(R.color.roz_pompejanski),'m',"25.21","category","123","321"))
        historyRecords.add(BmiDataPack(getColor(R.color.roz_pompejanski),'m',"25.21","category","123","321"))
        historyRecords.add(BmiDataPack(getColor(R.color.roz_pompejanski),'m',"25.21","category","123","321"))

    }
}
