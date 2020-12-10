package com.tsib.futureyard.main.horizontalrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsib.futureyard.R
import kotlinx.android.synthetic.main.card_ar.view.*

class ArRecyclerAdapter(list: ArrayList<ArCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val cardlist = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArRecyclerHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.card_ar, parent, false))
    }

    override fun getItemCount(): Int {
        return cardlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArRecyclerHolder).bind(cardlist[position])
    }

    inner class ArRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(card: ArCard){
            itemView.card_image.setImageResource(card.photo)
            itemView.card_title.text = card.title
            itemView.card_subtitle.text = card.subtitle
            itemView.card_description.text = card.description
        }
    }

}