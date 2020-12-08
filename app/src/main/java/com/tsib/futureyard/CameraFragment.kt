package com.tsib.futureyard

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ux.ArFragment

class CameraFragment : Fragment() {

    private lateinit var rootview: View
    lateinit var arFragment: ArFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arFragment = childFragmentManager?.findFragmentById(R.id.scene_form_fragment) as ArFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootview = inflater.inflate(R.layout.fragment_camera, container, false)
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //initFields()
        //initListeners()
    }

//    private fun initFields() {
//        arFragment = childFragmentManager?.findFragmentById(R.id.scene_form_fragment) as ArFragment
//    }

//    private fun initListeners() {
//        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
//            placeModel(hitResult)
//        }
//    }

    private fun placeModel(hitResult: HitResult){
        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        Log.d("CHECKER", "ArFragment: placeModel()")
        //createModel(anchorNode, selected)
    }


    inner class WritingArFragment : ArFragment() {

        override fun getAdditionalPermissions(): Array<String?> {
            val additionalPermissions =
                super.getAdditionalPermissions()
            val permissionLength =
                additionalPermissions?.size ?: 0
            val permissions =
                arrayOfNulls<String>(permissionLength + 1)

            permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE
            if (permissionLength > 0) {
                System.arraycopy(
                    additionalPermissions,
                    0,
                    permissions,
                    1,
                    additionalPermissions!!.size
                )
            }
            return permissions
        }
    }
}