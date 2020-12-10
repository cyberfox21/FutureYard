package com.tsib.futureyard

import android.graphics.drawable.Drawable

object Constants {
    const val TAG = "CHECKER"
    const val CAMERA = "CameraFragment:"
    const val AR_RECYCLE_SIZE = 6

    val photos: Array<Int> = arrayOf(
        R.drawable.ic_trash,
        R.drawable.ic_light,
        R.drawable.ic_bench,
        R.drawable.ic_flowerbed,
        R.drawable.ic_bush,
        R.drawable.ic_kianu
    )

    val titles = arrayOf(
        "Бачок",
        "Фонарь",
        "Скамейка",
        "Клумба",
        "Куст",
        "Киану Ривз"
    )
    val subtitles = arrayOf(
        "пластик",
        "железо",
        "дерево",
        "камни",
        "дерево",
        "брутальность"
    )
    val descriptions = arrayOf(
        "Cпециализированная ёмкость, служащая для сбора бытовых или строительных отходов. ",
        "Cтационарный источник света. Прибор для освещения отдельных участков пространства в темное время суток.",
        "Деревянное приспособление для сидения, размещаемое на открытом воздухе и достпуное каждому.",
        "Часть цветника, обычно в виде какой - либо геометрической. фигуры, имеющая замкнутую форму.",
        "Растение с древовидными ветвями, начинающимися почти от самой поверхности земли.",
        "Неподражаемый Киану Ривз ждёт вас в удивительном мире киберпанка."
    )
}