package com.ded.goalmatch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ded.goalmatch.mainfragment.ui.main.MainFragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {


    private lateinit var mAdView: AdView
    private  var mInterstitialAd: InterstitialAd? = null
     companion object{
          var resultStr:String = ""
     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdView = findViewById(R.id.adView)

        MobileAds.initialize(this) {
            val adView = AdView(this@MainActivity)
            //ca-app-pub-4433493293018663/9700347467
            if (BuildConfig.DEBUG) adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
            mAdView.setAdListener(object : AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Code to be executed when an ad request fails.
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.

                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.

                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.



                }
            })
        }
        loadInterstitialAd()

        val mainFragment = MainFragment()
        val manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.replace(R.id.navContainer, mainFragment)
        transaction.commit()


    }
    fun loadInterstitialAd() {
        val adRequest1 = AdRequest.Builder().build()
        var adKey = "ca-app-pub-4433493293018663/9790359990"
    //    if (BuildConfig.DEBUG) adKey = " ca-app-pub-3940256099942544/1033173712"
        InterstitialAd.load(this, adKey, adRequest1,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd

                    mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)

                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(resultStr)
                            startActivity(i)
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                        }

                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(resultStr)
                            startActivity(i)

                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                        }
                    }

                    Log.i("TAG", "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.message)
                    mInterstitialAd = null
                }
            })


    }

    fun showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.show(this)
            loadInterstitialAd()
        }
    }
}