package com.example.ratingbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val submitBtn = findViewById<Button>(R.id.submitButton)
        val simpleRateBar = findViewById<RatingBar>(R.id.simpleRatingBar)
        var simpleRate = findViewById<RatingBar>(R.id.simpleRating)

        submitBtn.setOnClickListener {
            val totalStars = "Total Stars: "+simpleRateBar.numStars
            val rating = " Rating: "+simpleRateBar.rating
            Toast.makeText(this, """$totalStars$rating""".trimIndent(), Toast.LENGTH_LONG).show()
        }

        simpleRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(this, "rating$rating,$fromUser", Toast.LENGTH_LONG).show()
        }
    }
}