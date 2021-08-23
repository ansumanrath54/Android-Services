package com.example.clockapp
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.clockapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startTheService()
        }
        binding.stopButton.setOnClickListener {
            stopTheService()
        }

    }

    private fun startTheService() {
        Toast.makeText(this,"SERVICE STARTED",Toast.LENGTH_SHORT).show()
        startService(Intent(this,MyService::class.java))
    }
    private fun stopTheService() {
        Toast.makeText(this,"SERVICE STOPPED",Toast.LENGTH_SHORT).show()
        stopService(Intent(this,MyService::class.java))
    }

    private fun isMyServiceRunning(mClass: Class<MyService>) : Boolean {
        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for(service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {
            if(mClass.name.equals(service.service.className)) {
                return true
            }
        }

        return false
    }
}