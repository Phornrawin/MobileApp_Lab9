package com.example.phornrawin.mobileapp_lab9;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng kaset = new LatLng(13.844632, 100.571841);
        CameraPosition cp = new CameraPosition.Builder()
                .target(kaset)
                .zoom(50)
                .bearing(90)
                .tilt(30)
                .build();
        // Add a marker in Sydney and move the camera

        mMap.addMarker(new MarkerOptions().position(kaset).title("Marker in KU"));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                final View dialog = getLayoutInflater().inflate(R.layout.location, null);
                new AlertDialog.Builder(MapsActivity.this)
                        .setView(dialog)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView titleTextView = dialog.findViewById(R.id.et_title);
                                String title = titleTextView.getText().toString();
                                mMap.addMarker(new MarkerOptions().position(latLng).title(title));
                            }
                        })
                        .create()
                        .show();
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                final View dialog = getLayoutInflater().inflate(R.layout.location, null);
                final TextView title = dialog.findViewById(R.id.et_title);
                title.setText(marker.getTitle());
                new AlertDialog.Builder(MapsActivity.this)
                        .setView(dialog)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String titleView = title.getText().toString();
                                marker.setTitle(titleView);
                                marker.showInfoWindow();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                marker.remove();
                            }
                        })
                        .create()
                        .show();

            }
        });
    }
}
