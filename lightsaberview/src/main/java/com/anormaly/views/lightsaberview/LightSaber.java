package com.anormaly.views.lightsaberview;

import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;

/**
 * Created by ichigo on 11/07/17.
 */

public class LightSaber
{

    private final Calculate calculate;

    public Calculate getCalculate()
    {
        return calculate;
    }

    public static class Default {
        public static int COLOR = 13158600;
        public static int GLOW_COLOR = 6579300;
        public static float GLOW_WIDTH = 10;
        public static float LASER_HEIGHT = 100;
    }

    @ColorInt private int color = -1;
    @ColorInt private int glowColor = -1;
    @Dimension private float glowWidth;
    @Dimension private float laserHeight;


    public LightSaber()
    {
        calculate = new Calculate();
    }

    public int getGlowColor()
    {
        if(glowColor==-1) return Default.GLOW_COLOR;
        return glowColor;
    }

    public void setGlowColor(int glowColor)
    {

        this.glowColor = glowColor;
    }

    public float getGlowWidth()
    {
        if(glowWidth==0) return Default.GLOW_WIDTH;
        return glowWidth;
    }

    public void setGlowWidth(float glowWidth)
    {
        this.glowWidth = glowWidth;
    }

    public float getLaserHeight()
    {
        if(laserHeight==0) return Default.LASER_HEIGHT;
        return laserHeight;
    }

    public void setLaserHeight(float laserHeight)
    {

        this.laserHeight = laserHeight;
    }

    public int getColor()
    {

        if(color==-1) return Default.COLOR;
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public class Calculate {

        private int screenHeight=Integer.MIN_VALUE;
        private int screenWidth=Integer.MIN_VALUE;

        private int hiltDesiredHeight=Integer.MIN_VALUE;
        private int hiltDesiredWidth=Integer.MIN_VALUE;

        private int hiltOriginX=Integer.MIN_VALUE;
        private int hiltOriginY =Integer.MIN_VALUE;

        private int rotatePivotX=Integer.MIN_VALUE;
        private int rotatePivotY=Integer.MIN_VALUE;

        private int saberDesiredWidth = Integer.MIN_VALUE;

        private float[] saberPoints;

        public Calculate()
        {

        }

        public int getScreenHeight()
        {
            return screenHeight;
        }

        public void setScreenHeight(int screenHeight)
        {
            this.screenHeight = screenHeight;
        }

        public int getScreenWidth()
        {
            return screenWidth;
        }

        public void setScreenWidth(int screenWidth)
        {
            this.screenWidth = screenWidth;
        }

        public int getHiltDesiredHeight()
        {
            return hiltDesiredHeight;
        }

        public void setHiltDesiredHeight(int hiltDesiredHeight)
        {
            this.hiltDesiredHeight = hiltDesiredHeight;
        }

        public int getHiltDesiredWidth()
        {
            return hiltDesiredWidth;
        }

        public void setHiltDesiredWidth(int hiltDesiredWidth)
        {
            this.hiltDesiredWidth = hiltDesiredWidth;
        }

        public int getHiltOriginX()
        {
            if(hiltOriginX==Integer.MIN_VALUE) hiltOriginX = (getScreenWidth() / 2) - (getHiltDesiredWidth() / 2);
            return hiltOriginX;
        }

        public int getHiltOriginY()
        {
            if(hiltOriginY ==Integer.MIN_VALUE) hiltOriginY = (getScreenHeight() / 2) - (getHiltDesiredHeight());
            return hiltOriginY;
        }

        /**
         * Center of the screen
         * @return
         */
        public int getRotatePivotX()
        {
            if(rotatePivotX==Integer.MIN_VALUE) rotatePivotX = getScreenWidth() / 2;
            return rotatePivotX;
        }

        

        public int getRotatePivotY()
        {
            if(rotatePivotY==Integer.MIN_VALUE) rotatePivotY = (getScreenHeight() / 2)-(getHiltDesiredHeight()/2);
            return rotatePivotY;
        }



        public float[] getSaberPoints(float yOffset)
        {

            if(saberPoints==null){
                saberPoints = new float[] {
                        calculate.getHiltOriginX()+8, calculate.getHiltOriginY() - yOffset,
                        calculate.getHiltOriginX()+8 + (calculate.getSaberDesiredWidth()),calculate.getHiltOriginY()
                };
            }else{
                saberPoints[1] = getHiltOriginY()-yOffset;
            }
            return saberPoints;
        }

        public int getSaberDesiredWidth()
        {

            if(saberDesiredWidth==Integer.MIN_VALUE) saberDesiredWidth = hiltDesiredWidth-16;
            return saberDesiredWidth;
        }
    }



}
