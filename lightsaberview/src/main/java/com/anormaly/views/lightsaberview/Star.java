package com.anormaly.views.lightsaberview;

import android.support.annotation.ColorInt;

import java.util.Random;

/**
 * Created by ichigo on 12/07/17.
 */

public class Star
{
    public final Calculate calculate;
    private int x;
    private int y;
    private int radius;
    @ColorInt private int glow;

    public Calculate getCalculate()
    {
        return calculate;
    }

    public Star()
    {
        calculate = new Calculate();
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public int getGlow()
    {
        return glow;
    }

    public void setGlow(int glow)
    {
        this.glow = glow;
    }

    public class Calculate {
        public int screenWidth;
        public int screenHeight;

        private int maxRadius = 10;
        private int xOffset = 360;
        public Calculate()
        {
        }

        public int getScreenWidth()
        {
            return screenWidth;
        }

        public void setScreenWidth(int screenWidth)
        {
            this.screenWidth = screenWidth;
        }

        public int getScreenHeight()
        {
            return screenHeight;
        }

        public void setScreenHeight(int screenHeight)
        {
            this.screenHeight = screenHeight;
        }

        public void setXY(){
            setX(new Random().nextInt(10)==0?new Random().nextInt(screenWidth):(-1)*new Random().nextInt(getxOffset()));
            setY(new Random().nextInt(screenHeight));
        }

        public void calculateRadius(){
            setRadius(new Random().nextInt(maxRadius)+1);
        }

        public int getxOffset()
        {
            return xOffset;
        }

        public void setxOffset(int xOffset)
        {
            this.xOffset = xOffset;
        }
    }
}
