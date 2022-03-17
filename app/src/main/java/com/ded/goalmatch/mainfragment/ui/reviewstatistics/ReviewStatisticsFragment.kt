package com.ded.goalmatch.mainfragment.ui.reviewstatistics

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.ded.goalmatch.MainActivity
import com.ded.goalmatch.R


class ReviewStatisticsFragment : Fragment() {

    lateinit var webView: WebView
    lateinit var videoUrl: String
    lateinit var mainActivity: MainActivity
    var isStop = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            videoUrl = requireArguments().getString("matchesViewUrl").toString()

        }
        mainActivity = activity as MainActivity


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        webView = view.findViewById(R.id.simpleWebView)
//        webView.webViewClient = WebViewClient()
//        mainActivity.showAd()
//      //  var string = "<iframe src=\"https://www.scorebat.com/embed/\" frameborder=\"0\" width=\"600\" height=\"760\" allowfullscreen allow='autoplay; fullscreen' style=\"width:600px;height:760px;overflow:hidden;display:block;\" class=\"_scorebatEmbeddedPlayer_\"></iframe><script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = 'https://www.scorebat.com/embed/embed.js?v=arrv'; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'scorebat-jssdk'));</script>"
//
//
//        val webSettings: WebSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//        webSettings.allowFileAccess = true
//        webSettings.setAppCacheEnabled(true)
//
//
//        if (savedInstanceState == null) {
//            webView.loadUrl(videoUrl)
//        //    webView.loadData(string,"text/html",null)
//        }


        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(videoUrl)
        startActivity(i)
        Log.d("TAG", "onViewCreated: " + isStop)



    }

    override fun onStop() {
        super.onStop()
        isStop = true
        Log.d("TAG", "onStop: " + isStop)

    }




    override fun onResume() {
        super.onResume()
        if (isStop) {
            Log.d("TAG", "onResume: " + isStop)
            mainActivity.supportFragmentManager.popBackStack()
        }
    }
}