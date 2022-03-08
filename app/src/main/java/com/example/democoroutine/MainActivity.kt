package com.example.democoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.democoroutine.model.CovidInfor
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var api: ApiService? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        api = Retrofit.Builder()
            .baseUrl("https://api.coronatracker.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            var list = getCovid()
            withContext(Dispatchers.Main){
                findViewById<TextView>(R.id.textView).text = list[0].toString()
            }
        }
    }

    fun getCovid(): List<CovidInfor> {
        var list: List<CovidInfor>? = listOf()
        try {
           list = api?.getCovidInfo()?.execute()?.body()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return list!!
    }

}


