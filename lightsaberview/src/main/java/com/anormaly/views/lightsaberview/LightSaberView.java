package com.anormaly.views.lightsaberview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by ichigo on 11/07/17.
 */
public class LightSaberView extends View
{
    private static final String TAG = LightSaberView.class.getSimpleName();
    private LightSaber lightSaber;
    private Paint laserPaint;
    private Paint glowPaint;
    private Bitmap hiltBitmap;
    private Paint buttonPaint;
    private int heightRationToScreen = 12;
    private float saberHeight = 0f;
    private LightSaber.Calculate calculate;
    private Paint starPaint;
    private Star[] stars;
    private float rotationAngle = 720f;
    private StarwarsText starWarsText;
    private AnimationStateListener listener;

    // TODO: 13/07/17 Submit to issue tracker. Probably stacks with "https://issuetracker.google.com/issues/37126579"
    // onAnimationEnd issue hack :D Please don't send me to hell
    private static boolean isAnimationDone = false;

    public LightSaberView(Context context)
    {
        this(context,null);
    }

    public LightSaberView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public LightSaberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);



        initializeAttrs(attrs);
    }



    private void initializeAttrs(AttributeSet attrs)
    {
        lightSaber = new LightSaber();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.LightSaberView);

        try{
            lightSaber.setColor(typedArray.getColor(R.styleable.LightSaberView_color, lightSaber.getColor()));
            lightSaber.setGlowColor(typedArray.getColor(R.styleable.LightSaberView_glowColor,lightSaber.getGlowColor()));
            lightSaber.setGlowWidth(typedArray.getDimension(R.styleable.LightSaberView_glowWidth,lightSaber.getGlowWidth()));
            lightSaber.setLaserHeight(typedArray.getDimension(R.styleable.LightSaberView_laserHeight,lightSaber.getLaserHeight()));
        } finally
        {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        setPaints();
        increaseSaberHeight(1000);


        int measuredHeight;
        int measuredWidth;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredHeight = heightSize;
        int desiredWidth = widthSize;



        if (widthMode == MeasureSpec.AT_MOST)
        {
            measuredWidth = widthSize;
        } else if (widthMode == MeasureSpec.EXACTLY)
        {
            measuredWidth = widthSize;
        } else
        {
            measuredWidth = widthSize;
        }

        if (heightMode == MeasureSpec.AT_MOST)
        {
            measuredHeight = heightSize;
        } else if (widthMode == MeasureSpec.EXACTLY)
        {
            measuredHeight = heightSize;
        } else
        {
            measuredHeight = heightSize;
        }
        
        setMeasuredDimension(measuredWidth,measuredHeight);
    }

    private void setPaints()
    {
        laserPaint = new Paint();
        laserPaint.setStyle(Paint.Style.FILL);
        laserPaint.setColor(lightSaber.getColor());
        laserPaint.setStrokeWidth(lightSaber.getGlowWidth());

        glowPaint = new Paint();
        glowPaint.setStyle(Paint.Style.STROKE);
        glowPaint.setColor(lightSaber.getGlowColor());
        glowPaint.setStrokeWidth(lightSaber.getGlowWidth());

        starPaint = new Paint();
        starPaint.setColor(ContextCompat.getColor(getContext(),android.R.color.darker_gray));
        starPaint.setStyle(Paint.Style.FILL_AND_STROKE);




        starWarsText = new StarwarsText();
        starWarsText.setStarwarsFonts(Typeface.createFromAsset(getContext().getAssets(),"fonts/starwarshollow.ttf"));
        starWarsText.setText("STAR WARS");
        starWarsText.getCalculate().calculateTextSize(lightSaber);
        starWarsText.getCalculate().setTextPaint();
        starWarsText.getCalculate().calculateWidthAndHeight();

        hiltBitmap = drawableToBitmap(ContextCompat.getDrawable(getContext(),R.drawable.starwars));

        calculate = lightSaber.getCalculate();
        scaledHiltBitmap(heightRationToScreen);
        starWarsText.getCalculate().calculateXY(lightSaber);
        makeSky(120, (int) rotationAngle*10);

        //Set Coordinate

    }


    private void makeSky(int starsSize,int xOffset)
    {
        stars = new Star[starsSize];

        for(int i = 0;i<starsSize;i++){
            stars[i] = new Star();
            stars[i].getCalculate().setxOffset(xOffset);
            stars[i].getCalculate().setScreenHeight(calculate.getScreenHeight());
            stars[i].getCalculate().setScreenWidth(calculate.getScreenWidth());
            stars[i].getCalculate().setXY();
            stars[i].getCalculate().calculateRadius();

        }
    }

    private void scaledHiltBitmap(int heightRationToScreen)
    {
        allotScreenPixels();
        int hiltHeight = hiltBitmap.getHeight();
        int hiltWidth = hiltBitmap.getWidth();
        calculate.setHiltDesiredHeight(calculate.getScreenHeight()/heightRationToScreen);
        calculate.setHiltDesiredWidth(((hiltWidth*calculate.getHiltDesiredHeight())/hiltHeight));
        hiltBitmap = Bitmap.createScaledBitmap(hiltBitmap,calculate.getHiltDesiredWidth(),calculate.getHiltDesiredHeight(),false);
    }

    private void allotScreenPixels(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        calculate.setScreenHeight(displayMetrics.heightPixels);
        calculate.setScreenWidth(displayMetrics.widthPixels);
    }

    private void increaseSaberHeight(long delay)
    {
        final ObjectAnimator lightSaberAnimator = ObjectAnimator.ofFloat(this, LightSaberView.LIGHT_SABER_HEIGHT_PROPERTY, 0f, (calculate.getHiltDesiredHeight()*2));
        lightSaberAnimator.setDuration((long) (300));
        lightSaberAnimator.setInterpolator(new AccelerateInterpolator(4f));



        final ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(this, LightSaberView.LIGHT_SABER_ROTATE_PROPERTY, 0f, rotationAngle);
        rotationAnimator.setDuration((long) (1000));
        rotationAnimator.setInterpolator(new AnticipateOvershootInterpolator(1f));
        rotationAnimator.setStartDelay(100);

        final ObjectAnimator inverselightSaberAnimator = ObjectAnimator.ofFloat(this, LightSaberView.LIGHT_SABER_HEIGHT_PROPERTY, (calculate.getHiltDesiredHeight()*2),0f );
        inverselightSaberAnimator.setDuration((long) (300));
        inverselightSaberAnimator.setInterpolator(new DecelerateInterpolator(4f));
        //inverselightSaberAnimator.setStartDelay(100);
        final AnimatorSet animatoSet = new AnimatorSet();

        animatoSet.playSequentially(lightSaberAnimator,rotationAnimator);
        //animatoSet.play(lightSaberAnimator);
        animatoSet.addListener(new AnimatorListenerAdapter()
        {
            private int x = 0;
            @Override
            public void onAnimationEnd(Animator animation)
            {
                //super.onAnimationEnd(animation);
                //increaseSaberHeight();
                if(isAnimationDone){
                    isAnimationDone = false;
                }else
                {

                    Log.d(TAG, "onAnimationEnd: wth");
                    if (listener != null) listener.onAnimationEnd();
                    isAnimationDone = true;
                }

            }

            @Override
            public void onAnimationCancel(Animator animation)
            {
                super.onAnimationCancel(animation);
                Log.d(TAG, "onAnimationCancel: ");
            }
        });


        animatoSet.setStartDelay(delay);
        animatoSet.start();
    }

    public void start(){
        increaseSaberHeight(0);
    }

    float theta = 0f;
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawIntialAnimationSequence(canvas);
    }

    private void drawBackground(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);
        drawStars(canvas);
    }

    private void drawIntialAnimationSequence(Canvas canvas)
    {
        canvas.drawText(starWarsText.getText(),starWarsText.getX(),starWarsText.getY(),starWarsText.getFillPaint());
        canvas.drawText(starWarsText.getText(),starWarsText.getX(),starWarsText.getY(),starWarsText.getStrokePaint());
        //Conditional rotate
        canvas.rotate(theta, calculate.getRotatePivotX(), calculate.getRotatePivotY());



        float[] points = calculate.getSaberPoints(saberHeight);

        // Saber Inner Side
        canvas.drawRect(points[0],points[1],points[2],points[3],laserPaint);
        // Saber Stroke
        canvas.drawLine(points[0],points[3],points[0],points[1],glowPaint);
        canvas.drawLine(points[0],points[1],points[2],points[1],glowPaint);
        canvas.drawLine(points[2],points[1],points[2],points[3],glowPaint);



        // Hilt
        canvas.drawBitmap(hiltBitmap,calculate.getHiltOriginX(),calculate.getHiltOriginY(),null);
    }

    private void drawStars(Canvas canvas)
    {
        for(Star star: stars){
            //canvas.drawPoint(star.getX(),star.getY(),starPaint);
            canvas.drawCircle(star.getX()+(theta*10),star.getY(),star.getRadius(),starPaint);
        }
    }

    private static final Property<LightSaberView,Float> LIGHT_SABER_ROTATE_PROPERTY = new Property<LightSaberView, Float>(Float.class,"lightSaberRotate")
    {
        @Override
        public Float get(LightSaberView lightSaberView)
        {
            return lightSaberView.getTheta();
        }

        @Override
        public void set(LightSaberView object, Float value)
        {
            object.setTheta(value);
        }
    };

    private static final Property<LightSaberView,Float> LIGHT_SABER_HEIGHT_PROPERTY = new Property<LightSaberView, Float>(Float.class,"lightSaberHeight")
    {
        @Override
        public Float get(LightSaberView lightSaberView)
        {
            return lightSaberView.getSaberHeight();
        }

        @Override
        public void set(LightSaberView object, Float value)
        {
            object.setSaberHeight(value);
        }
    };

    public float getTheta()
    {
        return theta;
    }

    public void setTheta(float theta)
    {
        this.theta = theta;
        postInvalidate();
    }

    public float getSaberHeight()
    {
        return saberHeight;
    }

    public void setSaberHeight(float saberHeight)
    {
        this.saberHeight = saberHeight;
        postInvalidate();
    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    public void onAnimationStateListener(AnimationStateListener listener){
        this.listener = listener;
    }
}
