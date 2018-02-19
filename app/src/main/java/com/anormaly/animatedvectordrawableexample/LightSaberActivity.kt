package com.anormaly.animatedvectordrawableexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.anormaly.views.lightsaberview.LightSaberView

class LightSaberActivity : AppCompatActivity(), View.OnClickListener {

    private var view: LightSaberView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_saber)

        view = findViewById<View>(R.id.lightSaberView) as LightSaberView
        view!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        view!!.start()
    }

    public override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
