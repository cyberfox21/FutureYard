package com.tsib.futureyard.main.gridrecyclerview

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.tsib.futureyard.Constants.GRIDVIEWADAPTER
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.R
import kotlinx.android.synthetic.main.card_dashboard.view.*


class GridViewAdapter(resultList: ArrayList<PhotoCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photoList: List<PhotoCard> = resultList

    inner class PhotoCardViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        fun bind(model: PhotoCard) {

            Log.d(TAG, "$GRIDVIEWADAPTER bind()")

            Picasso.get()
                .load(model.photo)
                .resize(
                    itemView.resources.displayMetrics.widthPixels,
                    itemView.resources.displayMetrics.heightPixels
                )
                .centerInside()
                .into(itemView.shapeableImageView)

            itemView.setOnLongClickListener {
                generateDialog(itemView.context, model)
                return@setOnLongClickListener true
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        Log.d(TAG, "$GRIDVIEWADAPTER onCreateViewHolder()")

        return PhotoCardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_dashboard,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d(TAG, "$GRIDVIEWADAPTER onBindViewHolder()")

        (holder as PhotoCardViewHolder).bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    private fun generateDialog(ctx: Context, model: PhotoCard) {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(ctx.getString(R.string.sure_deleting))
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            ((FirebaseDatabase.getInstance().reference.child("photos").child(
                FirebaseAuth.getInstance().currentUser?.uid.toString()
            ).child(model.path).removeValue()))

            val photoRef: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.photo)

            photoRef.delete().addOnSuccessListener { // File deleted successfully
                Log.d(TAG, "onSuccess: deleted file")
            }.addOnFailureListener { // Uh-oh, an error occurred!
                Log.d(TAG, "onFailure: did not delete file")
            }


            notifyDataSetChanged()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }
}