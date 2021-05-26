package com.example.fury.flappy_bird;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class FlappyBirdBaseObject {

    protected float x,y;
    protected int width, height;
    protected Rect rect;
    protected Bitmap bm;

    public FlappyBirdBaseObject() {

    }

    public FlappyBirdBaseObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public Rect getRect() {
        return new Rect((int)this.x+10, (int)this.y+10, (int)this.x+ this.width+10, (int)this.y+this.height+10);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
