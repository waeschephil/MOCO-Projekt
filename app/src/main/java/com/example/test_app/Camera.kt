package com.example.test_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.test_app.databinding.ActivityCameraBinding
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import java.lang.Exception

class Camera : AppCompatActivity() {

    private var imageCapture : ImageCapture? = null
    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnBack.setOnClickListener() {startActivity(Intent(this,MainActivity::class.java))}

        if (grantPermissions()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }




    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener( Runnable {
            //Nötig um den Kamera Lifecycle an den Lifecycle Owner zu binden
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            //Vorschau
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.createSurfaceProvider())
                }
            imageCapture = ImageCapture.Builder()
                .build()
            //Setze die Hauptkamera als Default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                //Entkoppeln aller Use Cases
                cameraProvider.unbindAll()
                //Use Case an die Kamera binden
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun grantPermissions() =
        REQUIRED_PERMISSIONS.all { ContextCompat.checkSelfPermission(baseContext,it)== PackageManager.PERMISSION_GRANTED }



    companion object {
        private const val TAG = "CameraXBasic"
        //private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}