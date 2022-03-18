package com.ded.goalmatch.mainfragment.ui.main

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ded.goalmatch.FootballMatch
import com.ded.goalmatch.MainActivity
import com.ded.goalmatch.R

class MainMatchesAdapter(
    var mainFootball: ArrayList<FootballMatch>,
    var mainActivity: MainActivity
) :
    RecyclerView.Adapter<MainMatchesAdapter.MatchesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_matches, parent, false)
        return MatchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(mainFootball[position], mainActivity)

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


            var data = matchesItem.date.substringBefore("T")
            var online = SpannableString("Online Match")


            date.text = data
            if (matchesItem.videos.get(0).title.equals("Live Stream")) {

                online.setSpan(
                    ForegroundColorSpan(Color.RED),
                    0,
                    online.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                typeVideo.text = online

            } else {
                typeVideo.text = "Review Match"

            }

            itemView.setOnClickListener {

                val data = matchesItem.videos.get(0).embed

                val resultStr: String =
                    data.substring(data.indexOf("'http") + 1, data.indexOf("' f"))

                MainActivity.resultStr = resultStr
                mainActivity.showAd()

            }

        }

        init {
            competition = itemView.findViewById(R.id.txtCompetition)
            tittle = itemView.findViewById(R.id.txtTittle)
            imgEmblem = itemView.findViewById(R.id.imgEmblem)
            date = itemView.findViewById(R.id.txtDate)
            typeVideo = itemView.findViewById(R.id.txtTypeVideo)

        }


    }
}