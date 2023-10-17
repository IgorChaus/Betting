package com.example.betting.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.betting.BettingApp
import com.example.betting.R
import com.example.betting.databinding.ItemScreenBinding
import com.example.betting.viewmodel.FavoriteViewModel
import com.example.betting.viewmodel.FavoriteViewModelFactory
import com.example.betting.viewmodel.PlayerViewModel
import com.example.betting.viewmodel.PlayerViewModelFactory
import com.example.betting.wrappers.PlayerItemAdapter
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import javax.inject.Inject


class ItemScreen : Fragment() {

    private lateinit var item: PlayerItemAdapter

    private var _binding: ItemScreenBinding? = null
    private val binding: ItemScreenBinding
        get() = _binding ?: throw RuntimeException("ItemScreenBinding == null")

    val component by lazy{
        (requireActivity().application as BettingApp).component
    }

    @Inject
    lateinit var factory: PlayerViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, factory)[PlayerViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    private fun parsArgs() {
        requireArguments().getParcelable<PlayerItemAdapter>(KEY_ITEM)?.let {
            item = it
        }
    }

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
        bindViews()
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

        val colorContainer = ResourcesCompat.getColor(resources, R.color.blue_600,null)

        val cardContainer = view.findViewById<LinearLayout>(R.id.card_container)
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(colorContainer)
        }

        ViewCompat.setBackground(cardContainer, shapeDrawable)
    }

    fun bindViews(){
        Glide.with(this).load(item.photo).circleCrop().into(binding.playerImage)
        with(binding){
            tvTeamInCard.text = item.team
            tvFirstName.text = item.firstName
            tvLastName.text = item.lastName

            if (item.age == null)
                ageContainer.visibility = View.GONE
            else
                tvAge.text = item.age.toString()

            if(item.birthDate == null)
                birthDayContainer.visibility = View.GONE
            else
                tvBirthDay.text = item.birthDate

            if(item.birthPlace == null)
                birthPlaceContainer.visibility = View.GONE
            else
                tvBirthPlace.text = item.birthPlace

            if(item.birthCountry == null)
                birthCountryContainer.visibility = View.GONE
            else
                tvBirthCountry.text = item.birthCountry

            if(item.height == null)
                heightContainer.visibility = View.GONE
            else
                tvHeight.text = item.height

            if(item.weight == null)
                weightContainer.visibility = View.GONE
            else
                tvWeight.text = item.weight

            if(item.team == null)
                teamContainer.visibility = View.GONE
            else
                tvTeam.text = item.team

            if(item.leagueName == null)
                leagueContainer.visibility = View.GONE
            else
                tvLeagueItem.text = item.leagueName

            if(item.nationality == null)
                countryContainer.visibility = View.GONE
            else
                tvCountry.text = item.nationality

            tvBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            iconFavorites.setOnClickListener {
                viewModel.addPlayer(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun getInstance(item: PlayerItemAdapter): Fragment{
            return ItemScreen().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ITEM,item)
                }
            }
        }

        private const val CUTTING_SIZE_IN_DPI = 25
        private const val RADIUS_IN_DPI = 10

        private const val KEY_ITEM = "item"
    }

}