package net.linakis.colorandom

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        imageView = findViewById<ImageView>(R.id.image).apply {
            setOnClickListener {
                colorandom()
            }
        }

        colorandom()
    }

    private fun colorandom() {
        val drawable = ContextCompat.getDrawable(applicationContext, R.drawable.colorandom)
        val randomColor = getRandomColor()

        drawable?.colorFilter =
            PorterDuffColorFilter(randomColor, PorterDuff.Mode.SRC_IN)

        imageView.setImageDrawable(drawable)
    }

    private fun getRandomColor(): Int {
        return Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }
}