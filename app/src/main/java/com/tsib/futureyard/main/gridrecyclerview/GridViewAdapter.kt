package com.tsib.futureyard.main.gridrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tsib.futureyard.R
import kotlinx.android.synthetic.main.card_dashboard.view.*

class GridViewAdapter(resultList: ArrayList<PhotoCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList: List<PhotoCard> = resultList

    inner class PhotoCardViewHolder internal constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: PhotoCard) {
            Picasso.get()
                .load(model.photo)
                .resize(itemView.resources.displayMetrics.widthPixels, itemView.resources.displayMetrics.heightPixels)
                .centerInside()
                .into(itemView.shapeableImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoCardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_dashboard, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotoCardViewHolder).bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }
}