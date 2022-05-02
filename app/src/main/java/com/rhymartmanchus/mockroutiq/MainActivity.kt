package com.rhymartmanchus.mockroutiq

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rhymartmanchus.mockroutiq.databinding.ActivityMainBinding
import com.rhymartmanchus.mockroutiq.domain.RouteDetail
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
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
    }

    private fun RouteDetail.renderRatings() {
        // stars
        // reviews
    }

    private fun RouteDetail.renderInfoIcons() {
        // Distance
        // time
        // elevation
    }

    private fun RouteDetail.renderDescription() {
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