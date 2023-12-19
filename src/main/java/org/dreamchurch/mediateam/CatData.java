package org.dreamchurch.mediateam;

import javax.swing.*;

public class CatData {
    private ImageIcon catImage;
    private String catName;
    private int x;
    private int y;
    private int catIndex; // New property to store the index

    public CatData(ImageIcon catImage, String catName, int x, int y, int catIndex) {
        this.catImage = catImage;
        this.catName = catName;
        this.x = x;
        this.y = y;
        this.catIndex = catIndex;
    }

    public ImageIcon getCatImage() {
        return catImage;
    }

    public String getCatName() {
        return catName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCatIndex() {
        return catIndex;
    }

    // Add this method to update catName

}

