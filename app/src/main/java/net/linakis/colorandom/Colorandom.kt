package net.linakis.colorandom

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class Colorandom : Application() {

    private lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate() {
        super.onCreate()

        SharedPreferencesManager.init(this)
        RemoteConfigRepository.init(this)
        FlipperHelper.start(this)
    }
}