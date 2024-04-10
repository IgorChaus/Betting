package com.example.betting.presentation.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.betting.Utils.BaseFragment
import com.example.betting.R
import com.example.betting.appComponent
import com.example.betting.databinding.PlayerScreenBinding
import com.example.betting.presentation.viewmodels.PlayerViewModel
import com.example.betting.domain.models.Player
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import javax.inject.Inject


class PlayerScreen : BaseFragment<PlayerScreenBinding>() {

    private lateinit var item: Player

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PlayerViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): PlayerScreenBinding {
        return PlayerScreenBinding.inflate(inflater, container, attachToRoot)
    }

    private fun parsArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(KEY_ITEM, Player::class.java)?.let {
                item = it
            }
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable<Player>(KEY_ITEM)?.let {
                item = it
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roundCorners(view)
        bindViews()
        viewModel.checkPlayer(item)
        viewModel.isPlayerFavorite.observe(viewLifecycleOwner){
            if(it){
                binding.iconFavorites.setImageResource(R.drawable.icon_favorite_selected)
            } else
                binding.iconFavorites.setImageResource(R.drawable.icon_favorite_not_selected)
        }
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
        Glide.with(this).load(item.photo).into(binding.playerImage)
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
                viewModel.pressButton(item)
            }
        }
    }

    companion object{
        private const val CUTTING_SIZE_IN_DPI = 25
        private const val RADIUS_IN_DPI = 10
        const val KEY_ITEM = "item"
    }

}