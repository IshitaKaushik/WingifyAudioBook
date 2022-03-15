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
    val books: MutableLiveData<List<Result?>> = MutableLiveData<List<Result?>>()
    val booksLive: LiveData<List<Result?>>
    get() = books
    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication())


    fun fetchData() {
        val newsAPI = RetrofitHelper.getInstance().create(AudioBookInterface::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = newsAPI.getAudioBookDetails()
                if (result != null || result.body() != null) {
                    val audioBooks = result.body()
                    if (audioBooks != null) {
                        withContext(Dispatchers.Main) {
                            books.value = audioBooks.results
                            sharedPreferences.edit()
                                .putString("localData", Gson().toJson(audioBooks)).commit()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            errorInAPIAccess()

                        }
                    }
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorInAPIAccess()

                }

            }

        }

    }

    private fun errorInAPIAccess(){
        try {
            val temp = Gson().fromJson(
                sharedPreferences.getString("localData", ""),
                AudioBookDetails::class.java
            )
                if (temp != null) {
                    books.value = temp.results
                }
        } catch (e: Exception) {
            Log.v("AudioBook", e.message.toString())
        }


    }

    fun mapData(keyName: String): MutableMap<String, ArrayList<Result>>{
        val dataMap = mutableMapOf<String , ArrayList<Result>>()

        for(item in books.value!!) {
            if (keyName == "artistName") {
                if (item != null) {
                    if (dataMap.containsKey(item.artistName)) {
                        val temp: ArrayList<Result>? = dataMap.get(item.artistName)
                        if (temp != null) {
                            temp.add(item)
                            dataMap[item.artistName] = temp
                        }
                    } else {
                        val temp = ArrayList<Result>()
                        temp.add(item)
                        dataMap[item.artistName] = temp
                    }
                }
            } else {
                if (item != null) {
                    if (dataMap.containsKey(item.primaryGenreName)) {
                        val temp: ArrayList<Result>? = dataMap.get(item.primaryGenreName)
                        if (temp != null) {
                            temp.add(item)
                            dataMap[item.primaryGenreName] = temp
                        }
                    } else {
                        val temp = ArrayList<Result>()
                        temp.add(item)
                        dataMap[item.primaryGenreName] = temp
                    }
                }
            }
        }

        return dataMap
    }


}