package net.linakis.colorandom

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

object RemoteConfigRepository {

    private const val featureColorName = "feature_color_name"

    @SuppressLint("StaticFieldLeak")
    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init(context: Application) {
        remoteConfig = Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })

            setDefaultsAsync(mapOf(featureColorName to false))

            addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    Log.d("RemoteConfigRepository", "Updated keys: " + configUpdate.updatedKeys)
                    if (configUpdate.updatedKeys.contains(featureColorName)) {
                        remoteConfig.activate()
                    }
                }

                override fun onError(error: FirebaseRemoteConfigException) {
                    Log.w(
                        "RemoteConfigRepository",
                        "Config update error with code: " + error.code,
                        error
                    )
                }
            })

        }

        // Fetch Remote Config values
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RemoteConfigRepository", "Remote config fetch completed")
                } else {
                    Log.w("RemoteConfigRepository", "Remote config fetch failed")
                }
            }
    }

    fun isFeatureColorNameEnabled() =
        ::remoteConfig.isInitialized && remoteConfig.getBoolean(featureColorName)
}
