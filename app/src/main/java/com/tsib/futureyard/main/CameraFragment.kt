package com.tsib.futureyard.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.HitTestResult
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.FixedHeightViewSizer
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.tsib.futureyard.Constants.AR_RECYCLE_SIZE
import com.tsib.futureyard.Constants.CAMERA
import com.tsib.futureyard.Constants.TAG
import com.tsib.futureyard.Constants.descriptions
import com.tsib.futureyard.Constants.icons
import com.tsib.futureyard.Constants.images
import com.tsib.futureyard.Constants.subtitles
import com.tsib.futureyard.Constants.titles
import com.tsib.futureyard.R
import com.tsib.futureyard.main.horizontalrecycler.ArCard
import com.tsib.futureyard.main.horizontalrecycler.ArRecyclerAdapter
import kotlinx.android.synthetic.main.imgboard.*
import java.util.ArrayList
import java.util.function.Consumer
import com.google.ar.core.Config as Config

class CameraFragment : Fragment() {

    private lateinit var rootview: View
    private lateinit var arFragment: ArFragment
    private lateinit var btnTakePhoto: MaterialButton
    private lateinit var arRecycler: RecyclerView

    var viewRenderableList: MutableList<ViewRenderable>? = null

    var fonar: ViewRenderable? = null

    var selected: Int = 0

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

        // по умолчанию выбран 1 элемент
        selected = 0

        // находим views
        btnTakePhoto = rootview.findViewById(R.id.btn_take_photo)
        arFragment = childFragmentManager.
        findFragmentById(R.id.scene_form_fragment) as ArFragment
        arRecycler = rootview.findViewById(R.id.ar_fragment_recycler)

        // задаём свойства recyclerview
        arRecycler.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false)
        arRecycler.adapter = activity?.let { ArRecyclerAdapter(generateСardList(), this) }

        for(i in 0 until AR_RECYCLE_SIZE){
           // generateViewRenderableList()
        }
        //viewRenderableList = generateViewRenderableList()
        //Config().updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE

    }

    private fun initListeners() {

        Log.d(TAG, "$CAMERA initListeners()")

        // по клику на ar поверхность устанавливаем модель
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            placeModel(hitResult)
        }

        // по клику на кнопку фото делаем захват экрана
        btnTakePhoto.setOnClickListener {
            takePhoto()
        }

    }


    // генерируем массив карточек для recyclerview
    private fun generateСardList(): ArrayList<ArCard> {

        Log.d(TAG, "$CAMERA generateСardList()")

        var list: ArrayList<ArCard> = ArrayList()
        for(i in 0 until AR_RECYCLE_SIZE){
            list.add(
                ArCard(
                    icons[i],
                    titles[i],
                    subtitles[i],
                    descriptions[i]
                )
            )
        }
        return list
    }

    private fun generateViewRenderableList(image: Int) : ViewRenderable? {

        Log.d(TAG, "$CAMERA generateViewRenderableList()")

        var list = ArrayList<ViewRenderable>(AR_RECYCLE_SIZE)

       // var fonar: ViewRenderable


            var model: ViewRenderable? = null
        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    model = renderable as ViewRenderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageResource(image)
                }
            )
//                    Consumer { renderable: ViewRenderable ->
//                        Log.d(TAG, "$CAMERA renderable $renderable")
//                        list.add(renderable)
//                        val imageView: ImageView = renderable.view as ImageView
//                        imageView.setImageResource(images[i])

//            (ViewRenderable.builder()
//                .setView(context, R.layout.imgboard)
//                .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
//                .setSizer(FixedHeightViewSizer(1.7f)))
//                .build()
//                .thenAccept (
//                    Consumer { renderable: ViewRenderable ->
//                        viewRenderableList[i] = renderable
//                        val imageView: ImageView = renderable.view as ImageView
//                        imageView.setImageResource(images[i])
//                    }
//                )
//        }
//        Log.d(TAG, "$CAMERA $list")
//        return list
        return model
    }


    // устанавливаем модель на ar поверхность
    private fun placeModel(hitResult: HitResult){

        Log.d(TAG, "$CAMERA placeModel($selected)")

        val anchor = hitResult.createAnchor()
        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)
        createModel(anchorNode, selected)
    }

    private fun createModel(anchorNode: AnchorNode, selected: Int) {

        Log.d(TAG, "$CAMERA createModel()")

        var view: ViewRenderable? = null

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(10f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    view = renderable as ViewRenderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageResource(images[selected])
                }
            )

        val model = TransformableNode(arFragment.transformationSystem)
        model.localPosition = Vector3(0f, anchorNode.localPosition.y + 0.5f, 0f)
        model.setParent(anchorNode)
        model.setOnTapListener { _: HitTestResult, _: MotionEvent ->
            anchorNode.setParent(null)
        }
        model.renderable = view
        model.select()

    }

    // делаем захват экрана
    private fun takePhoto() {

        Log.d(TAG, "$CAMERA takePhoto()")

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