package com.google.map.clustering;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String name;
    private final PlaceType placeType;

    public MyItem(double lat, double lng, String name, PlaceType placeType) {
        mPosition = new LatLng(lat, lng);
        this.name = name;
        this.placeType = placeType;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getName() {
        return name;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }
}