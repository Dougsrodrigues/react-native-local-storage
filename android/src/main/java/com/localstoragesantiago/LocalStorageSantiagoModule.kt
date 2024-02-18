package com.localstoragesantiago

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LocalStorageSantiagoModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  private val _reactContext = reactContext
  private val scope = MainScope()
  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {

    promise.resolve(a * b)
  }

  @ReactMethod
  fun setValue(key: String, value: String, promise: Promise) {
    scope.launch {
      try {
        val preferencesManager = PreferencesManager(_reactContext)
        preferencesManager.setValue(key, value)
        promise.resolve(null)
      } catch (e: Exception) {
        promise.reject("Error setting value", e)
      }
    }

  }

  @ReactMethod
  fun getValue(key: String, promise: Promise) {
    val preferencesManager = PreferencesManager(_reactContext)
    val flow = preferencesManager.getValue(key)
    scope.launch {
      try {
        val value = flow.first()
        promise.resolve(value)
      } catch (e: Exception) {
        promise.reject("Error setting value", e)
      }
    }
  }

  companion object {
    const val NAME = "LocalStorageSantiago"
  }
}
