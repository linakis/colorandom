package net.linakis.colorandom

import net.linakis.colorandom.SharedPreferencesManager.PREF_NAME
import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

object FlipperHelper {

    private val networkFlipperPlugin = NetworkFlipperPlugin()
    private var started = false

    fun start(application: Application) {
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(application)) {
            SoLoader.init(application, false)
            val client = AndroidFlipperClient.getInstance(application)
            client.addPlugin(InspectorFlipperPlugin(application, DescriptorMapping.withDefaults()))
            client.addPlugin(networkFlipperPlugin)
            client.addPlugin(SharedPreferencesFlipperPlugin(application, PREF_NAME))
            client.start()
        }
        started = true
    }

    fun getNetworkPlugin(): NetworkFlipperPlugin {
        if (started.not()) {
            throw IllegalStateException("FlipperHelper.start() must be called before accessing the network plugin.")
        }
        return networkFlipperPlugin
    }
}