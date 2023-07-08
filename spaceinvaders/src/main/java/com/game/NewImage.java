package com.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NewImage extends ImageView {
    public boolean isDead;
    public String type;

    public NewImage(double x, double y, Image im, String type) {
        super(im.getUrl());
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    public static void setDimensions(NewImage im, double h, double w) {
        im.setFitHeight(h);
        im.setFitWidth(w);
    }

    public void moveRight(double tra) {
        setTranslateX(getTranslateX() + tra);
    }

    public void moveLeft(double tra) {
        setTranslateX(getTranslateX() - tra);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }

    public boolean equals(NewImage im1) {
        return im1.type == "enemy";
    }

}
