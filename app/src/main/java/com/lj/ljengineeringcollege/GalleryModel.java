package com.lj.ljengineeringcollege;

import java.io.Serializable;

public class GalleryModel implements Serializable {
    private String img;
    private String caption;


    public GalleryModel(String img, String caption) {
        this.img = img;
        this.caption = caption;
    }

    public GalleryModel() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
