package ru.walkaround.walkaround.model;


import com.google.android.gms.maps.model.LatLng;

public class Place {

    private String name;
    private int imageId;
    private PlaceType type;
    private LatLng latLng;

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

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public Place(String name, int imageId, LatLng latLng, PlaceType placeType) {

        this.name = name;
        this.imageId = imageId;
        this.latLng = latLng;
        this.type = placeType;
    }

}
