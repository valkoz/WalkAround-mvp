package ru.walkaround.walkaround.model;

import java.util.List;

public class Route {

    private String minutes;

    private String distance;

    private String cost;

    private List<Place> places;

    public Route(String minutes, String distance, String cost, List<Place> places) {
        this.minutes = minutes;
        this.distance = distance;
        this.cost = cost;
        this.places = places;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaceNames(List<Place> places) {
        this.places = places;
    }
}
