package com.example.maptesting;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener, GoogleMap.OnMarkerClickListener {

    GoogleMap gMap;
    SeekBar seekWidth,seekBarRed;
    Button btnDraw, btnClear;

    Polyline polyline;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();

    int red = 0 , green = 0, blue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        seekWidth = findViewById(R.id.seek_width);
        seekBarRed = findViewById(R.id.seek_bar_red);

        btnDraw = findViewById(R.id.bt_draw);
        btnClear = findViewById(R.id.bt_clear);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(latLngList).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);
                polyline.setColor(Color.rgb(red,green,blue));

                setWidth();


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polyline != null) polyline.remove();
                for(Marker marker: markerList) marker.remove();
                latLngList.clear();
                markerList.clear();
                seekWidth.setProgress(1);
                seekBarRed.setProgress(0);

            }
        });

        seekBarRed.setOnSeekBarChangeListener(this);
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
        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = gMap.addMarker(markerOptions);

                latLngList.add(latLng);
                markerList.add(marker);


            }
        });




        List<LotLong> pointList = preparePointList();
        for (LotLong lotlong: pointList) {
            LatLng sydney = new LatLng(lotlong.getLag_point(), lotlong.getLong_point());
            googleMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(lotlong.getTitle()));
        }

        googleMap.setOnMarkerClickListener(this);
    }

    private List<LotLong> preparePointList() {
        List<LotLong> latLong = new ArrayList<>();
        latLong.add(new LotLong(22.1,22.3,"MC"));
        latLong.add(new LotLong(22.1,22.7,"PO1(23)"));
        latLong.add(new LotLong(22.1,22.8,"P03(24)"));
        Log.i("TAG", "preparePointList: " + latLong.size());
        return latLong;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seek_bar_red:
                red = i;
                break;


        }
        polyline.setColor(Color.rgb(red,green,blue));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setWidth() {
        seekWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int width = seekWidth.getProgress();
                if(polyline != null) {
                    polyline.setWidth(width);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        latLngList.add(latLng);
        if(latLngList.size() == 2){
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(latLngList).clickable(true);
            polyline = gMap.addPolyline(polylineOptions);
            polyline.setColor(Color.rgb(red,green,blue));

            latLngList.clear();
        }

        return false;
    }
}