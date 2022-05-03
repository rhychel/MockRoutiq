package com.rhymartmanchus.mockroutiq

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rhymartmanchus.mockroutiq.databinding.ActivityMainBinding
import com.rhymartmanchus.mockroutiq.databinding.LayoutProvidedByBinding
import com.rhymartmanchus.mockroutiq.databinding.LayoutRatingBinding
import com.rhymartmanchus.mockroutiq.databinding.LayoutRouteStatsBinding
import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject lateinit var presenter: MainContract.Presenter
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.takeView(this)
        presenter.onViewCreated()

        registerListener()
    }

    private fun registerListener() {
        binding.btnReadMore.setOnClickListener {
            if((binding.tvDescription.tag as String) == "ellipsized") {
                with(binding.tvDescription) {
                    maxLines = Int.MAX_VALUE
                    ellipsize = null
                    tag = "expanded"
                }
                binding.btnReadMore.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_chevron_up), null)
                binding.btnReadMore.text = "Lees minder"
            } else {
                with(binding.tvDescription) {
                    maxLines = 7
                    ellipsize = TextUtils.TruncateAt.END
                    tag = "ellipsized"
                }
                binding.btnReadMore.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_chevron), null)
                binding.btnReadMore.text = "Lees meer"
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun renderDetails(routeDetail: RouteDetail) {
        routeDetail.renderRouteImage()
        routeDetail.renderRouteName()
        routeDetail.renderStartPoint()
        routeDetail.renderImages(this)
        routeDetail.renderPois(this)
        routeDetail.renderRatings()
        routeDetail.renderInfoIcons()
        routeDetail.renderDescription()
        routeDetail.renderProvider()
    }

    private fun RouteDetail.renderProvider() {
        providedBy?.let {
            val providedBy = getString(R.string.provided_by, it.name)
            val spannable = SpannableString(providedBy)
            spannable.setSpan(
                UnderlineSpan(),
                "Provided by: ".length,
                providedBy.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.tvProvidedBy.text = spannable

            val providedByLayout = LayoutProvidedByBinding.bind(binding.incProvidedBy.root)

            Picasso.get()
                .load(it.imageUrl)
                .into(providedByLayout.ivProvidedBy)
        }
    }

    private fun RouteDetail.renderRatings() {
        val layoutRating = LayoutRatingBinding.bind(binding.incRating.root)
        val toStars = (averageRating.toDouble() / 10.0) * 5
        layoutRating.root.children.forEachIndexed { index, view ->
            if(index < toStars) {
                (view as ImageButton).setImageResource(R.drawable.routiq_rating_filled)
            } else {
                (view as ImageButton).setImageResource(R.drawable.routiq_rating_empty)
            }
        }

        binding.tvBasedOn.text = getString(R.string.based_on, ratingCount)
    }

    private fun RouteDetail.renderInfoIcons() {
        val stats = LayoutRouteStatsBinding.bind(binding.incStats.root)

        stats.tvDistance.text = getString(R.string.distance, distance/1000)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millisTime.toLong()
        stats.tvTime.text = getString(R.string.time, SimpleDateFormat("mm:ss").format(calendar.time))
        stats.tvCalorie.text = getString(R.string.calorie, 0)
        stats.tvElevation.text = getString(R.string.elevation, elevationMeters)
    }

    private fun RouteDetail.renderDescription() {
        binding.tvDescription.text = description
        // description

        // Lees meer (read more)
    }

    private fun RouteDetail.renderPois(context: Context) {
        val placeOfInterestAdapter = PlaceOfInterestAdapter(placeOfInterests)

        binding.rvPois.adapter = placeOfInterestAdapter
        binding.rvPois.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvPois.isNestedScrollingEnabled = false
    }

    private fun RouteDetail.renderImages(context: Context) {
        val imagesAdapter = ImagesAdapter(images)

        binding.rvImages.adapter = imagesAdapter
        binding.rvImages.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvImages.isNestedScrollingEnabled = false
    }

    private fun RouteDetail.renderRouteImage() {
        Picasso.get()
            .load(mapImageUrl)
            .into(binding.ivRouteImage)
    }

    private fun RouteDetail.renderRouteName() {
        binding.tvName.text = name
    }

    private fun RouteDetail.renderStartPoint() {
        binding.tvStartPoint.text = startLocation
    }

}