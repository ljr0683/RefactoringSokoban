package com.zetcode;

import java.awt.Image;

public class Actor {

    private final int SPACE = 64;

    private int x;
    private int y;
    private Image image;
    static protected int mode;
    

    public Actor(int x, int y) {
        
        this.x = x;
        this.y = y;
    }
    
    public Actor(int mode) {
    	this.mode = mode;
    }
    
    public static void setMode(int num) {
    	mode = num;
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    public boolean isLeftCollision(Actor actor) { //���ʿ� actor ��ü ����� true ��ȯ
        
        return x() - SPACE == actor.x() && y() == actor.y();
    }

    public boolean isRightCollision(Actor actor) { //�����ʿ� actor ��ü ����� true ��ȯ
        
        return x() + SPACE == actor.x() && y() == actor.y();
    }

    public boolean isTopCollision(Actor actor) { //���� actor ��ü ����� true ��ȯ
        
        return y() - SPACE == actor.y() && x() == actor.x();
    }

    public boolean isBottomCollision(Actor actor) { //�Ʒ��� actor ��ü ����� true ��ȯ
        
        return y() + SPACE == actor.y() && x() == actor.x();
    }
}