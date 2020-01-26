package com.example.filteritout

import ai.deepar.ar.DeepAR
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.filteritout.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var deepAR:DeepAR
    private lateinit var binding:ActivityMainBinding
    private lateinit var filterMutableList:MutableList<String?>

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

            //initialize hapa

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
                //initialize hapa
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
            previousMask.setOnClickListener(goToPrevious())
            nextMask.setOnClickListener { goToNext() }
            surface.setOnClickListener(deepAR.onClick())
        }

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
