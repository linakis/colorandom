package net.linakis.colorandom

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.linakis.colorandom.api.RetrofitClient
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

        // protect the color-name feature with a remote feature flag
        if (RemoteConfigRepository.isFeatureColorNameEnabled()) {
            lifecycleScope.launch(Dispatchers.Main) {
                val colorName = fetchColorName(randomColor)
                name.text = colorName
            }
        } else {
            name.text = ""
        }
    }

    private fun getRandomColor(): Int {
        return Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    }

    private suspend fun fetchColorName(color: Int): String {
        val colorService = RetrofitClient.apiService
        val response =
            colorService.getColorName("${Color.red(color)},${Color.green(color)},${Color.blue(color)}")
        return response.name.colorName
    }
}