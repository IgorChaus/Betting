package com.example.betting

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.betting.databinding.ItemScreenBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel


class ItemScreen : Fragment() {

    private var _binding: ItemScreenBinding? = null
    private val binding: ItemScreenBinding
        get() = _binding ?: throw RuntimeException("ItemScreenBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ItemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roundCorners(view)
    }

    private fun roundCorners(view: View){

        val displayMetrics = view.context.resources.displayMetrics
        val cunningCornerInFloat = (displayMetrics.densityDpi / 160f) * CUTTING_SIZE_IN_DPI
        val radiusCornerInFloat = (displayMetrics.densityDpi / 160f) * RADIUS_IN_DPI

        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, radiusCornerInFloat)
            .setTopLeftCorner(CornerFamily.ROUNDED, radiusCornerInFloat)
            .setBottomRightCorner(CornerFamily.ROUNDED, radiusCornerInFloat)
            .setBottomLeftCorner(CornerFamily.CUT, cunningCornerInFloat)
            .build()

        val colorContainer = ResourcesCompat.getColor(resources,R.color.blue_600,null)

        val cardContainer = view.findViewById<LinearLayout>(R.id.card_container)
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(colorContainer)
        }

        ViewCompat.setBackground(cardContainer, shapeDrawable)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private const val CUTTING_SIZE_IN_DPI = 25
        private const val RADIUS_IN_DPI = 10
    }

}