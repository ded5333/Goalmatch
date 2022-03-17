package com.ded.goalmatch.mainfragment.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ded.goalmatch.App
import com.ded.goalmatch.FootballMatch
import com.ded.goalmatch.MainFootball
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainVM : ViewModel() {
    private val _mutableEmplLiveData = MutableLiveData<ArrayList<FootballMatch>>()
    var mutableEmplLiveData: LiveData<ArrayList<FootballMatch>> = _mutableEmplLiveData
    val scope = CoroutineScope(Dispatchers.IO)
     var isInternet:Boolean = false

    private val _mutableEmplLiveIntermet = MutableLiveData<Boolean>()
    var mutableEmplLiveInternet: LiveData<Boolean> = _mutableEmplLiveIntermet


    fun loading(context: Context){

        isInternet = checkForInternet(context)
        if (!checkForInternet(context)) {
            isInternet = false
            _mutableEmplLiveIntermet.value = isInternet
        }else
        scope.launch {

                var mainFootball = App.api_video.getMatches()
                mainFootball.enqueue(object : Callback<MainFootball> {
                    override fun onResponse(
                        call: Call<MainFootball>,
                        response: Response<MainFootball>
                    ) {
                        var allMatches: MainFootball = response.body()!!
                        var mat = allMatches.response

                        val data = mat[0].videos[0].embed

                        val resultStr: String =
                            data.substring(data.indexOf("'http") + 1, data.indexOf("' f"))

                        isInternet = true
                        _mutableEmplLiveIntermet.value = isInternet




                        _mutableEmplLiveData.value = mat
                        for (match in mat) {
                            Log.d("TAG", "onResponse: " + mat.get(0).competition)
                        }
                    }

                    override fun onFailure(call: Call<MainFootball>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

        }
    }
    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
                isInternet = true
            _mutableEmplLiveIntermet.value = isInternet

        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}