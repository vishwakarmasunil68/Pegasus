package com.bjain.pegasus.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bjain.pegasus.R;
import com.bjain.pegasus.utils.GPSTracker;
import com.bjain.pegasus.utils.TagUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindUsNearActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_us_near);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Find Us Near You");


        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng bjain = new LatLng(28.625492, 77.383623);
        mMap.addMarker(new MarkerOptions().position(bjain).title("B. Jain Publishers Pvt. Ltd")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bjain));
        getLocation();
    }


    public void getLocation() {
        if (mMap != null) {
            gps = new GPSTracker(FindUsNearActivity.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                LatLng latlng = new LatLng(latitude, longitude);
                String address="";
                try{
                    address=getAddress(latitude,longitude);
                }catch (Exception e){
                    address="Current Location";
                    e.printStackTrace();
                }
                Log.d(TagUtils.getTag(),"latitude:-"+latitude+" ,longitude:-"+longitude);
                Marker user_marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                user_marker.setTag("userown");
                user_marker.showInfoWindow();

                float zoomLevel = 16.0f; //This goes up to 21
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoomLevel));
            } else {
                gps.showSettingsAlert();
            }
        }
    }

    public String getAddress(double latitude, double longitude) {
        String address = "";
//                    LocationAddress.getAddressFromLocation(latitude,longitude,LocationService.this,new GeocoderHandler());
        Geocoder geocoder = new Geocoder(FindUsNearActivity.this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                Log.d("sunil", strReturnedAddress.toString());
                address = strReturnedAddress.toString();
                return address;
            } else {
                Log.d("sunil", "No Address returned!");
                return "";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("sunil", "Canont get Address!");
            return "Current Location";
        }
    }
}
