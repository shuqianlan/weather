package com.ilifesmart

import android.graphics.SurfaceTexture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.ilifesmart.weather.R

class TextureViewTestActivity : AppCompatActivity() {
    companion object {
        val TAG = "TextureViewTestActivity";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_texture_view_test)


        findViewById<Button>(R.id.addTextureView).setOnClickListener {
            TextureView(this).also {
                it.surfaceTextureListener = (object : TextureView.SurfaceTextureListener {
                    override fun onSurfaceTextureAvailable(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        Log.d(TAG, "onSurfaceTextureAvailable: ")
                    }

                    override fun onSurfaceTextureSizeChanged(
                        surface: SurfaceTexture,
                        width: Int,
                        height: Int
                    ) {
                        Log.d(TAG, "onSurfaceTextureSizeChanged: ")
                    }

                    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                        Log.d(TAG, "onSurfaceTextureDestroyed: ")
                        return true
                    }

                    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                        Log.d(TAG, "onSurfaceTextureUpdated: ")
                    }

                })

                findViewById<ConstraintLayout>(R.id.textureViewContainer).addView(it)
            }

        }
    }
}