package com.anormaly.views.lightsaberview;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

/**
 * Created by ichigo on 12/07/17.
 */

public class StarwarsText
{

    private final Calculate calculate;
    private String text;
    private int textWidth;
    private int textHeight;
    private Typeface starwarsFonts;
    private Paint textPaint;
    private int x;
    private int y;


    public StarwarsText()
    {
        calculate = new Calculate();
    }

    public Calculate getCalculate()
    {
        return calculate;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getTextWidth()
    {
        return textWidth;
    }

    public void setTextWidth(int textWidth)
    {
        this.textWidth = textWidth;
    }

    public int getTextHeight()
    {
        return textHeight;
    }

    public void setTextHeight(int textHeight)
    {
        this.textHeight = textHeight;
    }

    public Typeface getStarwarsFonts()
    {
        return starwarsFonts;
    }

    public void setStarwarsFonts(Typeface starwarsFonts)
    {
        this.starwarsFonts = starwarsFonts;
    }

    public Paint getTextPaint()
    {
        return textPaint;
    }

    public Paint getStrokePaint(){
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.parseColor("#ffd700"));
        return textPaint;
    }

    public Paint getFillPaint(){
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#ff000000"));
        return textPaint;
    }

    public void setTextPaint(Paint textPaint)
    {
        this.textPaint = textPaint;
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

    public class Calculate {
        private int textSize;

        public void calculateWidthAndHeight()
        {
            Rect textBounds = new Rect();
            getTextPaint().getTextBounds(getText(), 0, getText().length(), textBounds);
            setTextWidth((int) Math.ceil(getTextPaint().measureText(getText(), 0, getText().length())));
            setTextHeight(textBounds.height());
        }

        public void setTextPaint()
        {
            Paint textPaint = new Paint();
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setStrokeWidth(3);
            textPaint.setTextSize(textSize);
            textPaint.setColor(Color.BLUE);
            textPaint.setTypeface(getStarwarsFonts());
            textPaint.setAntiAlias(true);
            StarwarsText.this.setTextPaint(textPaint);
        }

        public void calculateTextSize(LightSaber saber){
            LightSaber.Calculate saberCalc = saber.getCalculate();
            int sW = saberCalc.getScreenWidth();
            int sH = saberCalc.getScreenHeight();
            textSize = sW/(getText().length());
        }

        public void calculateXY(LightSaber saber){
            LightSaber.Calculate saberCalc = saber.getCalculate();
            setX(saberCalc.getHiltOriginX()+saberCalc.getHiltDesiredWidth()/2-(getTextWidth()/2));
            setY(saberCalc.getScreenHeight()-getTextHeight()-saberCalc.getScreenHeight()/10);
        }
    }


}
