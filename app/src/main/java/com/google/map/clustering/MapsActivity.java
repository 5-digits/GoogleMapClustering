package com.google.map.clustering;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {

    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;
    private GoogleMap mMap;
    private MyItem clusterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpCluster();
    }

    private void setUpCluster() {


        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.7483221, 72.88330078), 5));

        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        mClusterManager
                .setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
                    @Override
                    public boolean onClusterItemClick(MyItem item) {
                        clusterItem = item;
                        return false;
                    }
                });


        addItems();

        mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                new MarkerAdapter());

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {


        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < Constants.latitude.length; i++) {

            PlaceType placeType = PlaceType.CAFE;


            if (i >= 20 && i <= 39) {
                placeType = PlaceType.RESTAURANT;
            }

            MyItem offsetItem = new MyItem(Constants.latitude[i], Constants.longitude[i], Constants.name[i], placeType);
            mClusterManager.addItem(offsetItem);
        }
    }

    //added with edit
    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {
        Toast.makeText(this, myItem.getName() + " Clicked", Toast.LENGTH_SHORT).show();
    }

    public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MarkerAdapter() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.item_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.txtTitle));
            ImageView ivPlace = ((ImageView) myContentsView
                    .findViewById(R.id.ivPlace));

            tvTitle.setText(clusterItem.getName());
            ivPlace.setImageResource(clusterItem.getPlaceType() == PlaceType.RESTAURANT ? R.drawable.ic_food : R.drawable.ic_cafe);

            return myContentsView;
        }
    }
}
