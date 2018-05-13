package ru.walkaround.walkaround.model;


import com.google.android.gms.maps.model.LatLng;

public class Place {

    private String name;
    private String address;
    private int imageId;
    private int type;
    private LatLng latLng;
    private float rating;

    public Place(String name, String address, int imageId, LatLng latLng, int placeType, float rating) {

        this.name = name;
        this.address = address;
        this.imageId = imageId;
        this.latLng = latLng;
        this.type = placeType;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return imageId;
    }

    public void setImage(int imageId) {
        this.imageId = imageId;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String type) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
