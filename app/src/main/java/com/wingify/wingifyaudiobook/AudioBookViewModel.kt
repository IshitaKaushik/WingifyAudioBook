package com.wingify.wingifyaudiobook

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AudioBookViewModel(application: Application): AndroidViewModel(application) {
    private val books: MutableLiveData<List<Result?>> = MutableLiveData<List<Result?>>()
    val booksLive: LiveData<List<Result?>>
    get() = books
    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication())


    fun fetchData() {
        val newsAPI = RetrofitHelper.getInstance().create(AudioBookInterface::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val result = newsAPI.getAudioBookDetails()
            if (result != null || result.body()!=null) {
                val audioBooks = result.body()
                if (audioBooks != null) {
                    withContext(Dispatchers.Main) {
                        books.value = audioBooks.results
                        sharedPreferences.edit()
                            .putString("localData", Gson().toJson(audioBooks)).commit()
                    }
                }
                else{
                    try {
                        val temp = Gson().fromJson(sharedPreferences.getString("localData", ""), AudioBookDetails::class.java)
                        withContext(Dispatchers.Main) {
                            if(temp != null) {
                                books.value = temp.results
                            }
                        }
                    }
                    catch (e: Exception){
                        Log.v("AudioBook", e.message.toString())
                    }
                }
            }
                else{
                    try {
                        books.value = listOf(
                            Gson().fromJson(
                                sharedPreferences.getString("localData", ""),
                                Result::class.java
                            )
                        )
                    }
                    catch (e: Exception){
                        books.value= emptyList()
                    }
                }
        }

    }


}