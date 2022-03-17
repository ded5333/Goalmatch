package com.ded.goalmatch.mainfragment.ui.main

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ded.goalmatch.FootballMatch
import com.ded.goalmatch.MainActivity
import com.ded.goalmatch.R
import com.ded.goalmatch.mainfragment.ui.reviewstatistics.ReviewStatisticsFragment

class MainMatchesAdapter(var mainFootball: ArrayList<FootballMatch>, var mainActivity: MainActivity) :
    RecyclerView.Adapter<MainMatchesAdapter.MatchesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_matches, parent, false)
        return MatchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(mainFootball[position],mainActivity)



    }

    override fun getItemCount(): Int {
        return mainFootball.size
    }




    class MatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        private var competition: TextView
        private var tittle: TextView
        private var imgEmblem: ImageView
        private var date: TextView
        private var typeVideo: TextView

        fun bind(matchesItem: FootballMatch, mainActivity: MainActivity) {
            competition.text = matchesItem.competition
            tittle.text = matchesItem.title
            imgEmblem.load(matchesItem.thumbnail)

//     var dateFormatter = DateFormatter()
//
//            val data = dateFormatter.date(matchesItem.videos.get(0).embed)
//
//
//            date.text = matchesItem.date
            var data = matchesItem.date.substringBefore("T")
            val stringBuilder = SpannableStringBuilder()
            var online = SpannableString("Online Match")


            date.text = data
            if(matchesItem.videos.get(0).title.equals("Live Stream")){

                online.setSpan(ForegroundColorSpan(Color.RED),0,online.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                typeVideo.text = online

            }
            else {
                typeVideo.text = "Review Match"

            }
            var matchesViewUrl = matchesItem.matchviewUrl


            itemView.setOnClickListener {
//                lateinit var mainActivity: MainActivity
                var fragmentManager: androidx.fragment.app.FragmentManager = mainActivity.supportFragmentManager

                val data = matchesItem.videos.get(0).embed

                val resultStr: String = data.substring(data.indexOf("'http") + 1, data.indexOf("' f"))

                MainActivity.resultStr = resultStr
                mainActivity.showAd()

            //    var resultStr = matchesItem.matchviewUrl

//                var bundle: Bundle? = Bundle()
//                bundle?.putString("matchesViewUrl",resultStr)
//                fragmentManager.beginTransaction()
//                    .replace(R.id.navContainer,ReviewStatisticsFragment::class.java,bundle)
//                    .setReorderingAllowed(true)
//                    .addToBackStack("name")
//                    .commit()





            }

        }

        init {
            competition = itemView.findViewById(R.id.txtCompetition)
            tittle = itemView.findViewById(R.id.txtTittle)
            imgEmblem = itemView.findViewById(R.id.imgEmblem)
            date = itemView.findViewById(R.id.txtDate)
            typeVideo = itemView.findViewById(R.id.txtTypeVideo)



        }



//        itemView.setOnClickListener(android.view.View.OnClickListener (
//        {
//            v:android.view.View? ->   var bundle: Bundle? = Bundle()
//            bundle.putSerializable("CAR", car)
//            carTicketFragment = CarTicketFragment()
//            fragmentManager = mainActivity.getSupportFragmentManager()
//            fragmentManager.beginTransaction()
//                .replace(R.id.navContainer, CarTicketFragment::class.java, bundle)
//                .setReorderingAllowed(true)
//                .addToBackStack("name") // name can be null
//                .commit()
//        }))

    }
}