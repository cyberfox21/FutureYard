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

    lateinit var trashRenderable: ViewRenderable
    lateinit var lightRenderable: ViewRenderable
    lateinit var benchRenderable: ViewRenderable
    lateinit var flowerbedRenderable: ViewRenderable
    lateinit var bushRenderable: ViewRenderable
    lateinit var kianuRenderable: ViewRenderable

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
        setupModels()
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

        when(selected){
            0 -> {
                val trash = TransformableNode(arFragment.transformationSystem)
                trash.localPosition = Vector3(0f, 0f, 0f)
                trash.setParent(anchorNode)
                trash.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                trash.renderable = trashRenderable
                trash.select()
            }
            1 -> {
                val light = TransformableNode(arFragment.transformationSystem)
                light.localPosition = Vector3(0f, 0f, 0f)
                light.setParent(anchorNode)
                light.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                light.renderable = lightRenderable
                light.select()
            }
            2 -> {
                val bench = TransformableNode(arFragment.transformationSystem)
                bench.localPosition = Vector3(0f, 0f, 0f)
                bench.setParent(anchorNode)
                bench.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                bench.renderable = benchRenderable
                bench.select()
            }
            3 -> {
                val flowerbed = TransformableNode(arFragment.transformationSystem)
                flowerbed.localPosition = Vector3(0f, 0f, 0f)
                flowerbed.setParent(anchorNode)
                flowerbed.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                flowerbed.renderable = flowerbedRenderable
                flowerbed.select()
            }
            4 -> {
                val bush = TransformableNode(arFragment.transformationSystem)
                bush.localPosition = Vector3(0f, 0f, 0f)
                bush.setParent(anchorNode)
                bush.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                bush.renderable = bushRenderable
                bush.select()
            }
            else -> {
                val kianu = TransformableNode(arFragment.transformationSystem)
                kianu.localPosition = Vector3(0f, 0f, 1.5f)
                kianu.setParent(anchorNode)
                kianu.setOnTapListener { _: HitTestResult, _: MotionEvent ->
                    anchorNode.setParent(null)
                }
                kianu.renderable = kianuRenderable
                kianu.select()
            }

        }

    }

    // делаем захват экрана
    private fun takePhoto() {

        Log.d(TAG, "$CAMERA takePhoto()")

    }


    private fun setupModels() {

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    trashRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.trash))
                }
            )

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    lightRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.light))
                }
            )

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    benchRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.bench))
                }
            )

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    flowerbedRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.flowerbed))
                }
            )

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    bushRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.bush))
                }
            )

        ViewRenderable.builder()
            .setView(activity, R.layout.imgboard)
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.BOTTOM)
            .setSizer(FixedHeightViewSizer(1.7f))
            .build()
            .thenAccept(
                Consumer { renderable: ViewRenderable ->
                    kianuRenderable = renderable
                    val imageView: ImageView = renderable.view as ImageView
                    imageView.setImageDrawable(getDrawable(requireContext(), R.drawable.kianu))
                }
            )

    }
}