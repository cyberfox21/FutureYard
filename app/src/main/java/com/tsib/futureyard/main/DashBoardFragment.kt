package com.tsib.futureyard.main

import android.R.attr.data
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.tsib.futureyard.Constants.DASHBOARD
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.R
import com.tsib.futureyard.main.gridrecyclerview.GridViewAdapter
import com.tsib.futureyard.main.gridrecyclerview.PhotoCard
import kotlinx.android.synthetic.main.fragment_dash_board.view.*


class DashBoardFragment : Fragment() {

    private lateinit var rootview: View

    private lateinit var resultList: ArrayList<PhotoCard>

    private lateinit var adapter: GridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "$DASHBOARD onCreateView()")

        rootview = inflater.inflate(R.layout.fragment_dash_board, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "$DASHBOARD onViewCreated()")

        resultList = ArrayList()
        val recyclerView: RecyclerView = rootview.findViewById(R.id.grid_recycler)
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), numberOfColumns)
        updateList()
        adapter = GridViewAdapter(resultList)
        recyclerView.adapter = adapter
    }

    private fun updateList() {

        Log.d(TAG, "updateList()")

        FirebaseDatabase.getInstance().reference.child("photos").child(FirebaseAuth.getInstance().uid.toString())
            .addChildEventListener(object : ChildEventListener {

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "$DASHBOARD ChildEventListener : onCancelled()")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(TAG, "$DASHBOARD ChildEventListener : onChildMoved()")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(TAG, "$DASHBOARD ChildEventListener : onChildChanged()")
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    resultList.add(snapshot.getValue(PhotoCard::class.java)!!)
                    adapter.notifyDataSetChanged()

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val message = snapshot.getValue(PhotoCard::class.java)
                    val index = message?.let { getItemIndex(it) }
                    if (index != null) {
                        resultList.removeAt(index)
                    }
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "$DASHBOARD ChildEventListener : onChildRemoved()")
                }
            })
    }

    private fun getItemIndex(photoCard: PhotoCard): Int {
        var index = -1
        for (i in 0 until resultList.size) {
            if (resultList[i].photo == photoCard.photo) {
                index = i
                break
            }
        }
        return index
    }
}