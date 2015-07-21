package jp.android.myapp.shopmapapplication03;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;



import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        double latitude = 39.613144;
        double longitude = 141.148321;

        Intent Listintent = getIntent();
        final String shop_name = Listintent.getStringExtra("Name");
        final String shop_add = Listintent.getStringExtra("Add");
        final String shop_kind = Listintent.getStringExtra("Kind");
        final String shop_menu = Listintent.getStringExtra("Menu");
        final String shop_contents = Listintent.getStringExtra("Contents");

        try{

            Address address = getLatLongFromLocationName(shop_add);
            latitude = address.getLatitude();
            longitude = address.getLongitude();

        }catch (IOException e){

            e.printStackTrace();

        }

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20);
        mMap.moveCamera(cu);

        //MyLocation
        mMap.setMyLocationEnabled(true);

        //ZoomControl
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Marker
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(shop_name));

        //Compass
        mMap.getUiSettings().setCompassEnabled(true);

        //Markerがクリックされたとき
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(final Marker marker) {

                AlertDialog.Builder List = new AlertDialog.Builder(MapsActivity.this);
                List.setTitle(shop_name);
                List.setMessage("カテゴリ：" + shop_kind + "\n" + "おすすめ：" + shop_menu + "\n" + "情報：" + shop_contents);

                List.setPositiveButton("閉じる",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                List.show();

                return false;
            }
        });

    }

    private Address getLatLongFromLocationName(String locationName) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
        Address address = addressList.get(0);

        return address;
    }
}
