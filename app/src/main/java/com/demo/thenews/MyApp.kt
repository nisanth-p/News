package com.demo.thenews

import android.app.Application
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.LeakCanary
import leakcanary.OnHeapAnalyzedListener
import shark.HeapAnalysis

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.config = LeakCanary.config.copy(onHeapAnalyzedListener = LeakUploader())
        // var crashlytics = FirebaseCrashlytics.getInstance()
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)

        val crashlytics = Firebase.crashlytics
        crashlytics.setCustomKeys {
            key("my_string_key", "foo") // String value
            key("my_bool_key", true)    // boolean value
            key("my_double_key", 1.0)   // double value
            key("my_float_key", 1.0f)   // float value
            key("my_int_key", 1)        // int value
        } }
}

class LeakUploader : OnHeapAnalyzedListener {

    private val defaultListener = DefaultOnHeapAnalyzedListener.create()

    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {

        // Delegate to default behavior (notification and saving result)
        defaultListener.onHeapAnalyzed(heapAnalysis)
        // TODO("Upload heap analysis to server")
    }

}