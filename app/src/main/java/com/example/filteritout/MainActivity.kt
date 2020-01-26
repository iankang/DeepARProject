package com.example.filteritout

import ai.deepar.ar.AREventListener
import ai.deepar.ar.CameraResolutionPreset
import ai.deepar.ar.DeepAR
import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.Camera
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Surface
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.filteritout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),SurfaceHolder.Callback,AREventListener {

    lateinit var deepAR:DeepAR
    private lateinit var binding:ActivityMainBinding
    private lateinit var filterMutableList:MutableList<String?>
    private val defaultCameraDevice:Int = Camera.CameraInfo.CAMERA_FACING_FRONT
    private var cameraDevice:Int = defaultCameraDevice
    private lateinit var cameraGrabber:CameraGrabber

    var screensOrientation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        deepAR.setLicenseKey(getString(R.string.license_key))
//        deepAR.initialize(this,this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE),1)


        }
        else{

            initialize()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1 && grantResults.size>0){
            for(grantResult in grantResults){
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    return
                }
                initialize()
            }
        }
    }

    fun initialize(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initializeDeepAR()
        initializeFilters()
        initializeViews()
    }

    private fun initializeViews() {

        binding.apply {
            previousMask.setOnClickListener{goToPrevious()}
            nextMask.setOnClickListener { goToNext() }
            surface.setOnClickListener{deepAR.onClick()}
            surface.holder.addCallback(this@MainActivity)
            recordButton.setOnClickListener{deepAR.takeScreenshot()}
            switchCamera.setOnClickListener{
                if(cameraGrabber.currCameraDevice == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    cameraDevice = Camera.CameraInfo.CAMERA_FACING_BACK
                }
                else{
                    cameraDevice = Camera.CameraInfo.CAMERA_FACING_FRONT
                }
                cameraGrabber.changeCameraDevice(cameraDevice)
            }

        }



    }

    private fun goToNext() {

    }

    private fun goToPrevious() {

    }

    private fun initializeFilters() {
        filterMutableList.add("none")
        filterMutableList.add("aviators")
        filterMutableList.add("bigmouth")
        filterMutableList.add("dalmatian")
        filterMutableList.add("flowers")
        filterMutableList.add("koala")
        filterMutableList.add("lion")
        filterMutableList.add("smallface")
        filterMutableList.add("teddycigar")
        filterMutableList.add("kanye")
        filterMutableList.add("tripleface")
        filterMutableList.add("sleepingmask")
        filterMutableList.add("fatify")
        filterMutableList.add("obama")
        filterMutableList.add("mudmask")
        filterMutableList.add("pug")
        filterMutableList.add("slash")
        filterMutableList.add("twistedface")
        filterMutableList.add("grumpycat")    }

    private fun initializeDeepAR() {
        deepAR = DeepAR(this)
        deepAR.setLicenseKey(getString(R.string.license_key))
        deepAR.initialize(this,this)

        cameraGrabber = CameraGrabber(cameraDevice)
        screensOrientation = getScreenOrientation()

        when(screensOrientation){
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> cameraGrabber.screenOrientation = 90
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE -> cameraGrabber.screenOrientation = 270
            else -> cameraGrabber.screenOrientation = 0
        }

        cameraGrabber.resolutionPreset = CameraResolutionPreset.P640x480

    }

    private fun getScreenOrientation(): Int {
        val rotation = windowManager.defaultDisplay.rotation
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val orientation: Int
        // if the device's natural orientation is portrait:
        orientation = if ((rotation == Surface.ROTATION_0
                    || rotation == Surface.ROTATION_180) && height > width ||
            (rotation == Surface.ROTATION_90
                    || rotation == Surface.ROTATION_270) && width > height
        ) {
            when (rotation) {
                Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                else -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        } else {
            when (rotation) {
                Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                else -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
        return orientation
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun shutdownFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun faceVisibilityChanged(p0: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun videoRecordingFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun videoRecordingPrepared() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initialized() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun error(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun screenshotTaken(p0: Bitmap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun effectSwitched(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun videoRecordingStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun videoRecordingFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun imageVisibilityChanged(p0: String?, p1: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
