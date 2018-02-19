package com.anormaly.animatedvectordrawableexample

import android.content.Intent
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.graphics.drawable.Animatable2Compat
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT>=23) {
            (demo1.drawable as Animatable2).start()
            (demo1.drawable as Animatable2).registerAnimationCallback(object : Animatable2.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2).start()
                }
            })

            (demo2.drawable as Animatable2).start()
            (demo2.drawable as Animatable2).registerAnimationCallback(object : Animatable2.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2).start()
                }
            })

            (demo3.drawable as Animatable2).start()
            (demo3.drawable as Animatable2).registerAnimationCallback(object : Animatable2.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2).start()
                }
            })
        }else {
            (demo1.drawable as Animatable2Compat).start()
            (demo1.drawable as Animatable2Compat).registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2Compat).start()
                }
            })

            (demo2.drawable as Animatable2Compat).start()
            (demo2.drawable as Animatable2Compat).registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2Compat).start()
                }
            })

            (demo3.drawable as Animatable2Compat).start()
            (demo3.drawable as Animatable2Compat).registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    (drawable as Animatable2Compat).start()
                }
            })
        }

        lightSaberButton.setOnClickListener({
            startActivity(Intent(applicationContext,LightSaberActivity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}
