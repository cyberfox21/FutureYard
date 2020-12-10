package com.tsib.futureyard.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment
import com.tsib.futureyard.Constants.AR_RECYCLE_SIZE
import com.tsib.futureyard.Constants.CAMERA
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.Constants.descriptions
import com.tsib.futureyard.Constants.photos
import com.tsib.futureyard.Constants.subtitles
import com.tsib.futureyard.Constants.titles
import com.tsib.futureyard.R
import com.tsib.futureyard.main.horizontalrecycler.ArCard
import com.tsib.futureyard.main.horizontalrecycler.ArRecyclerAdapter
import java.util.ArrayList

class CameraFragment : Fragment() {

    private lateinit var rootview: View
    private lateinit var arFragment: ArFragment
    private lateinit var btnTakePhoto: MaterialButton
    private lateinit var arRecycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "$CAMERA onCreateView()")
        // получаем корневой элемент фрагмента
        rootview = inflater.inflate(R.layout.fragment_camera, container, false)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$CAMERA onViewCreated()")
        initFields() // инициализируем views
        initListeners() // ставим слушатели событий на кнопку фото и ar поверхность
    }

    private fun initFields() {
        Log.d(TAG, "$CAMERA initFields()")
        btnTakePhoto = rootview.findViewById(R.id.btn_take_photo)
        arFragment = childFragmentManager.
        findFragmentById(R.id.scene_form_fragment) as ArFragment
        arRecycler = rootview.findViewById(R.id.ar_fragment_recycler)
        arRecycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        arRecycler.adapter = ArRecyclerAdapter(generateList())
    }

    private fun generateList(): ArrayList<ArCard> {
        var list: ArrayList<ArCard> = ArrayList()
        for(i in 0 until AR_RECYCLE_SIZE){
            list.add(ArCard(photos[i], titles[i], subtitles[i], descriptions[i]))
        }
        return list
    }

    private fun initListeners() {
        Log.d(TAG, "$CAMERA initListeners()")
        btnTakePhoto.setOnClickListener {
            takePhoto() // по клику на кнопку фото делаем захват экрана
        }
        arFragment.setOnTapArPlaneListener {
                hitResult, _, _ ->
            placeModel(hitResult) // по клику на ar поверхность устанавливаем модель
        }
    }

    private fun takePhoto() {
        Log.d(TAG, "$CAMERA takePhoto()")
    }

    private fun placeModel(hitResult: HitResult){
        Log.d(TAG, "$CAMERA placeModel()")
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        //createModel(anchorNode, selected)
    }


//    inner class WritingArFragment : ArFragment() {
//
//        override fun getAdditionalPermissions(): Array<String?> {
//            val additionalPermissions =
//                super.getAdditionalPermissions()
//            val permissionLength =
//                additionalPermissions?.size ?: 0
//            val permissions =
//                arrayOfNulls<String>(permissionLength + 1)
//
//            permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE
//            if (permissionLength > 0) {
//                System.arraycopy(
//                    additionalPermissions,
//                    0,
//                    permissions,
//                    1,
//                    additionalPermissions!!.size
//                )
//            }
//            return permissions
//        }
//    }
}