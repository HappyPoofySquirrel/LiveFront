package com.guvyerhopkins.livefront.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.guvyerhopkins.livefront.R
import com.guvyerhopkins.livefront.core.extensions.clickableSpan
import com.guvyerhopkins.livefront.core.network.Photo
import com.jsibbold.zoomage.ZoomageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Using this library for a zoomable image
 * https://github.com/jsibbold/zoomage
 */
private const val IMAGE_KEY = "IMAGE_KEY"

class ImageDetailActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context, photo: Photo): Intent {
            return Intent(context, ImageDetailActivity::class.java).apply {
                putExtra(IMAGE_KEY, photo)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        postponeEnterTransition()

        val photo = intent.extras!!.getParcelable<Photo>(IMAGE_KEY)!!
        Picasso.get() //I would like to address the additional flashing that is happending
            .load(photo.src?.large)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(findViewById<ZoomageView>(R.id.details_iv), object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    //tbd what needs to be done here
                }
            })

        findViewById<TextView>(R.id.details_photographer_tv).text =
            getString(R.string.image_photographer, photo.photographer)
        val photographerUrlTv = findViewById<TextView>(R.id.details_photographer_url_tv)
        photographerUrlTv.text = getString(
            R.string.image_photographer_url,
            photo.photographerUrl
        )

        photographerUrlTv.clickableSpan(photo.photographerUrl ?: "") {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(photo.photographerUrl)))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}