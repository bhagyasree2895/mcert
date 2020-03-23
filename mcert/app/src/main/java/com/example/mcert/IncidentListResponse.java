package com.example.mcert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IncidentListResponse extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

   static  Model incident_model;
    private IncidentListAdapter incidentServer = null;
    private RecyclerView incidentrecycler = null;
    private GestureDetectorCompat gesture_detector = null;
    static List<Model> incident_array = new ArrayList<>();
    ArrayList<String> addressList=new ArrayList<String>();
    ArrayList<String> incidentNameList=new ArrayList<String>();
    JSONArray jsonArray ;

    private GoogleMap mMap;
    ArrayList<Double> loc=new ArrayList<Double>();
    String finalLoc="";


    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = incidentrecycler.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder holder = incidentrecycler.getChildViewHolder(view);
                if (holder instanceof IncidentListAdapter.IncidentViewHolder
                ) {
                    int position = holder.getAdapterPosition();


                    // handle single tap
                    Log.d("click", "clicked on item "+ position);
//                    TextView outputTV = findViewById(R.id.outputTV);
//                    outputTV.setText("Clicked on " + myModel.thePlanets.get(position).name);
//                    // Remove the selected data from the model
//                    myModel.thePlanets.remove(position);
//                    planetServer.notifyItemRemoved(position);

                    return true;  // Use up the tap gesture
                }
            }

            // we didn't handle the gesture so pass it on
            return false;
        }
    }

    public void onSubmit(View v){
        Intent ini = new Intent(this, TabsActivity.class);
        startActivity(ini);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_list_response);

        incident_model = Model.getModel();
        incidentServer = new IncidentListAdapter(incident_model,this);
        incidentrecycler = findViewById(R.id.IncidentRecycler);
        incidentrecycler.setAdapter(incidentServer);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        incidentrecycler.setLayoutManager(linearLayoutManager);

        gesture_detector = new GestureDetectorCompat(this, new RecyclerViewOnGestureListener());

        incidentrecycler.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return gesture_detector.onTouchEvent(e);
            }
        });


        try {
            //JSONObject jsonObj = new JSONObject(TabsActivity.response_incident_api);
            jsonArray= new JSONArray(TabsActivity.response_incident_api);
            //JSONArray data = perdata.getJSONArray("results");
           // Log.d("lolraja",jsonArray.toString() );
            incident_model.getIncidentsarray().clear();
            for (int i = 0; i<jsonArray.length(); i++){

                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String incident_name = jsonObj.getString("incidentName");
                String date_time = jsonObj.getString("dateAndTime");
                int incidentId =   Integer.parseInt(jsonObj.getString("incidentId"));
                String address=jsonObj.getString("address");
                Log.e("indcidentIddet",incidentId+"");
                Model.Incident newinc =new Model.Incident(incident_name + " " + date_time+" "+address,incidentId);
                incident_model.getIncidentsarray().add(newinc);
                incident_model.IncidentID=incidentId;
                addressList.add(address);
                incidentNameList.add(incident_name);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("lolraja",e.toString() );
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        //checkLocation(); //check whether location service is enable or not in your  phone

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        if(loc.size()==0){
//            LatLng sydney = new LatLng(35.7565,83.9705);
//            // Instantiates a new Polyline object and adds points to define a rectangle
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Maryville")
//                    .snippet("Maryville"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//            LatLng somehwre = new LatLng(32,83.9705);
//            mMap.addMarker(new MarkerOptions().position(new LatLng(32,83.9705)).title("Marker in Somewhere")
//                    .snippet("Somehwre")).setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.disaster)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(somehwre));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if (marker.getTitle()!=null){
                        Toast.makeText(IncidentListResponse.this, "Clicked "+marker.getTitle(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(IncidentListResponse.this, "Clicked "+marker.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent incident_intent = new Intent(IncidentListResponse.this, IncidentReportActivity.class);
                        startActivity(incident_intent);
                    }
                    return false;
                }
            });
           Geocoder coder = new Geocoder(this);
//            List<Address> address0 = null;
//            List<Address> address1 = null;
//            try {
//                address0 = coder.getFromLocationName("West Fertilizer Co.,1471 Jerry Mashek Drive,West, Texas, U.S",5);
//                //address.add(one);
//                address1= coder.getFromLocationName("1115 N College Dr, Maryville, Missouri, U.S",5);
//               // address.add(one);
//                //address.add(two);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address location0=address0.get(0);
//            Address location1=address1.get(0);
//
//            LatLng newLocation0 = new LatLng(location0.getLatitude(),location0.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(newLocation0).title("new Location 0")
//                    .snippet("newLocation")).setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.disaster)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation0));
//            LatLng newLocation1 = new LatLng(location1.getLatitude(),location1.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(newLocation1).title("new Location 1")
//                    .snippet("newLocation")).setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.disaster)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation1));


            for(int loop=0;loop<jsonArray.length();loop++) {
                try {
                    List<Address> addressNew = null;
                    addressNew = coder.getFromLocationName(addressList.get(loop), 5);
                    System.out.println("addressNew "+addressNew);
                    if(addressNew.size()>0) {
                        Address locationNew = addressNew.get(0);
                        LatLng newIncidentLoc = new LatLng(locationNew.getLatitude(), locationNew.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(newIncidentLoc).title(incidentNameList.get(loop))
                                .snippet(incidentNameList.get(loop))).setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.disaster)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newIncidentLoc));
                        //address1 = coder.getFromLocationName("1115 N College Dr, Maryville, Missouri, U.S", 5);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    private LocationManager locationManager;

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            TextView mLatitudeTextView=findViewById(R.id.latitude_textview);
            TextView mLongitudeTextView=findViewById(R.id.longitude_textview);

           // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
           // mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

//        String msg = "Updated Location: " +
//                Double.toString(location.getLatitude()) + "," +
//                Double.toString(location.getLongitude());
//        TextView mLatitudeTextView=findViewById(R.id.latitude_textview);
//        TextView mLongitudeTextView=findViewById(R.id.longitude_textview);
//        //mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
//       // mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
//        loc.add(location.getLatitude());
//        loc.add(location.getLongitude());
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        // You can now create a LatLng Object for use with maps
//        //LatLng latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
//        TextView locNametextview=findViewById(R.id.locNametextview);
//        LatLng currentlocation = new LatLng(loc.get(0),loc.get(1));
//        mMap.addMarker(new MarkerOptions().position(currentlocation).title("Marker in Maryville"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
//
//        try {
//
//            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
//            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            if (addresses.isEmpty()) {
//                locNametextview.setText("Waiting for Location");
//            }
//            else {
//                if (addresses.size() > 0) {
////                    finalLoc=addresses.get(0).getFeatureName() + ", " +
////                            addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " +
////                            addresses.get(0).getCountryName();
////                    //locNametextview.setText(addresses.get(0).getFeatureName() + ", " +
////                            addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " +
////                            addresses.get(0).getCountryName());
//
//                    //TextView incidentLocTV=findViewById(R.id.textView5);
//                    //incidentLocTV.setText(addresses.get(0).getFeatureName() + ", " +
//                    // addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " +
//                    //addresses.get(0).getCountryName());
//
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

//        private boolean checkLocation() {
//            if(!isLocationEnabled())
//                showAlert();
//            return isLocationEnabled();
//        }

//        private void showAlert() {
//            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//            dialog.setTitle("Enable Location")
//                    .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
//                            "use this app")
//                    .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            startActivity(myIntent);
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                        }
//                    });
//            dialog.show();
//        }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    public void backToIncidentReport(View v){
        Intent in = new Intent();
        in.putExtra("LocationName",finalLoc);
        setResult(11,in);

        finish();
    }
}
