package gamerendering;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NewImage extends ImageView {
    public String type;

    NewImage(Image image, String type) {
        this(0, 0, image, type);
    }

    NewImage(double x, double y, Image image, String type) {
        super(image.impl_getUrl());
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }


    static void setDimensions(NewImage image, double h, double w) {
        image.setFitHeight(h);
        image.setFitWidth(w);
    }

    void moveRight(double translation) {
        setTranslateX(getTranslateX() + translation);
    }

    void moveLeft(double translation) {
        setTranslateX(getTranslateX() - translation);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }


}
