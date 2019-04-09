package com.example.myfriends;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity {

    static final LatLng Pos = new LatLng(40, -79);

    private GoogleMap friendsMap;
    MapFragment mapFragment;
    private FriendBE friend = null;
    private ArrayList<FriendBE> listFriends = null;
    private boolean hasSearch;
    private  String addressForFriend;

    private EditText searchMap;
    Button saveAddressbtn;

    private RelativeLayout searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchMap = findViewById(R.id.edittext_maps_search);
        searchBar = findViewById(R.id.layout_search);
        searchBar.setVisibility(View.INVISIBLE);
        saveAddressbtn = findViewById(R.id.button_save_address);
        saveAddressbtn.setVisibility(View.INVISIBLE);


        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Intent intent = getIntent();
                friend = (FriendBE) intent.getSerializableExtra("friend");
                listFriends = (ArrayList<FriendBE>) intent.getSerializableExtra("listFriends");
                hasSearch = intent.getBooleanExtra("hasSearch", false);

                friendsMap = googleMap;


                if (hasSearch){
                    init();
                    searchBar.setVisibility(View.VISIBLE);
                    saveAddressbtn.setVisibility(View.VISIBLE);

                } else {
                    LatLng address = getLocationFromAddress(MapActivity.this,"Stormgade 42-32");
                    if (friend != null ){
                        address = getLocationFromAddress(MapActivity.this,friend.address);
                        friendsMap.addMarker(new MarkerOptions().position(address).title(friend.name));
                    } else if (listFriends != null){
                        for (FriendBE friendsInList: listFriends) {
                            if(!friendsInList.address.equals("Unknown")) {
                                address = getLocationFromAddress(MapActivity.this,friendsInList.address);
                                friendsMap.addMarker(new MarkerOptions().position(address).title(friendsInList.name));
                            }

                        }
                    }
                    friendsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 13));

                }

            }
        });
    }

    private void moveCamera(LatLng latLng){
        friendsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        friendsMap.addMarker(new MarkerOptions().position(latLng));
    }
    private void geoLocate(){
        String search = searchMap.getText().toString();
        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> listOfAddress = new ArrayList<>();
        try {
            listOfAddress = geocoder.getFromLocationName(search, 1);
        }
        catch (IOException e){
            Log.e("geo Locate", e.getMessage());
        }
        if (listOfAddress.size() > 0 ){
            Address address = listOfAddress.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()));
           // addressForFriend = address.getThoroughfare() + ", " + address.getLocality() + ", " + address.getCountryName();
            addressForFriend = address.getAddressLine(0);
            Log.d("found", address.toString());
        }
    }

    private void init(){
        searchMap.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO ||
                    event.getAction() == KeyEvent.ACTION_DOWN ||
                    event.getAction() == KeyEvent.KEYCODE_ENTER){
                    geoLocate();
                }
                return false;
            }
        });
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    public void saveAddress(View view) {
        if (addressForFriend != null) {
            Intent intent = new Intent();
            intent.putExtra("address", addressForFriend);
            setResult(Common.GET_MAP_ACTIVITY, intent);
            finish();
        }
        else Toast.makeText(this, "Address not found!", Toast.LENGTH_SHORT).show();


    }
}
