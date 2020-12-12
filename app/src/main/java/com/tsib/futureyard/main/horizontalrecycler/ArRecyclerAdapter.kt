package com.tsib.futureyard.main.horizontalrecycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsib.futureyard.Constants
import com.tsib.futureyard.Constants.ARRECYCLERADAPTER
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.R
import com.tsib.futureyard.main.CameraFragment
import kotlinx.android.synthetic.main.card_ar.view.*

@Suppress("DEPRECATION")
class ArRecyclerAdapter(list: ArrayList<ArCard>, cf: CameraFragment)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val cardlist = list
    val cf = cf

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Log.d(TAG, "$ARRECYCLERADAPTER onCreateViewHolder()")

        return ArRecyclerHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.card_ar, parent, false))
    }

    override fun getItemCount(): Int {
        return cardlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d(TAG, "$ARRECYCLERADAPTER onBindViewHolder()")

        (holder as ArRecyclerHolder).bind(cardlist[position])
    }

    inner class ArRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(card: ArCard){

            Log.d(TAG, "$ARRECYCLERADAPTER bind()")

            itemView.card_image.setImageResource(card.photo)
            itemView.card_title.text = card.title
            itemView.card_subtitle.text = card.subtitle
            itemView.card_description.text = card.description
            itemView.setOnClickListener {
                cf.selected = position
            }
        }
    }

}