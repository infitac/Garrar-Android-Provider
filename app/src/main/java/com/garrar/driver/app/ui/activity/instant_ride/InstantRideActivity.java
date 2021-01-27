package com.garrar.driver.app.ui.activity.instant_ride;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.garrar.driver.app.data.network.model.EstimateFareNew;
import com.garrar.driver.app.ui.activity.addCourierDetail.AddCourierDetail;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.garrar.driver.app.MvpApplication;
import com.garrar.driver.app.base.BaseActivity;
import com.garrar.driver.app.common.AppConstant;
import com.garrar.driver.app.common.Constants;
import com.garrar.driver.app.common.SharedHelper;
import com.garrar.driver.app.data.network.model.EstimateFare;
import com.garrar.driver.app.data.network.model.TripResponse;
import com.garrar.driver.app.ui.adapter.PlacesAutoCompleteTextViewAdapter;
import com.garrar.driver.app.ui.countrypicker.Country;
import com.garrar.driver.app.ui.countrypicker.CountryPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.garrar.driver.app.R;
import com.garrar.driver.app.ui.adapter.PlacesAutoCompleteAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.garrar.driver.app.MvpApplication.PACKAGE_DETAIL;
import static com.garrar.driver.app.MvpApplication.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.garrar.driver.app.MvpApplication.RIDE_REQUEST;
import static com.garrar.driver.app.MvpApplication.instantRide;
import static com.garrar.driver.app.MvpApplication.mLastKnownLocation;
import static com.garrar.driver.app.MvpApplication.total_stop;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_ADD;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_ADD1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_ADD2;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LAT;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LAT1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LAT2;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LONG;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LONG1;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.DEST_LONG2;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.SRC_ADD;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.SRC_LAT;
import static com.garrar.driver.app.common.Constants.RIDE_REQUEST.SRC_LONG;

public class InstantRideActivity extends BaseActivity
        implements GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        InstantRideIView {
    private PlacesAutoCompleteTextViewAdapter mAutoCompleteSourceTextViewAdapter, mAutoCompleteDestinationTextViewAdapter;

    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0));
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    protected GoogleApiClient mGoogleApiClient;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.llPhoneNumberContainer)
    LinearLayout llPhoneNumberContainer;
    @BindView(R.id.etPhoneNumber)
    EditText etPhoneNumber;

    @BindView(R.id.cvLocationsContainer)
    CardView cvLocationsContainer;
    @BindView(R.id.countryImage)
    ImageView countryImage;
    @BindView(R.id.countryNumber)
    TextView tvCountryCode;

    @BindView(R.id.source)
    AutoCompleteTextView source;

    @BindView(R.id.source1)
    AutoCompleteTextView source1;

    @BindView(R.id.source2)
    AutoCompleteTextView source2;

    @BindView(R.id.llSource)
    LinearLayout llSource;

    @BindView(R.id.destination_layout)
    LinearLayout destination_layout;

    @BindView(R.id.destination_layout1)
    LinearLayout destination_layout1;

    @BindView(R.id.destination_layout2)
    LinearLayout destination_layout2;

    @BindView(R.id.img_add)
    ImageView img_add;

    @BindView(R.id.img_add1)
    ImageView img_add1;

    @BindView(R.id.img_add2)
    ImageView img_add2;

    @BindView(R.id.img_add3)
    ImageView img_add3;

    @BindView(R.id.reset_source)
    ImageView reset_source;

    @BindView(R.id.reset_source1)
    ImageView reset_source1;

    @BindView(R.id.reset_source2)
    ImageView reset_source2;

    private boolean isEnableIdle = false;
    private boolean canShowKeyboard, mLocationPermission, canEditSourceAddress = true;

    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocation;
    private BottomSheetBehavior mBottomSheetBehavior;
    String countryCode = "+91";
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    String countryFlag = "IN";
    CountryPicker mCountryPicker;
    private InstantRidePresenter<InstantRideActivity> presenter = new InstantRidePresenter<>();



    private AutoCompleteTextView selectedEditText;

    private boolean isLocationRvClick = false;
    private boolean isSettingLocationClick = false;
    private Boolean isEditable = true;

    private boolean mLocationPermissionGranted;
    private String s_address;
    private Double s_latitude;
    private Double s_longitude;
    private String d_address;
    private Double d_latitude;
    private Double d_longitude;

    private String actionName = Constants.LocationActions.SELECT_SOURCE;
    PlacesClient placesClient =null;

    int LAUNCH_COURIER_DETAIL_ACTIVITY = 1;
    @Override

    public int getLayoutId() {
        return R.layout.activity_instant_ride;
    }

    @Override
    public void initView() {
        buildGoogleApiClient();
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.instant_ride));


        com.google.android.libraries.places.api.Places.initialize(getApplicationContext(), getString(R.string.google_map_key));
        placesClient = com.google.android.libraries.places.api.Places.createClient(this);
        instantRide = new HashMap<>();
        instantRide.put("service_type", SharedHelper.getIntKey(this, AppConstant.SharedPref.SERVICE_TYPE));
        PACKAGE_DETAIL.put("service_type", RequestBody.create(MediaType.parse("text/plain"),SharedHelper.getIntKey(this, AppConstant.SharedPref.SERVICE_TYPE).toString()));

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            mGoogleMap = googleMap;
            try {
                mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            getLocationPermission();
            updateLocationUI();
            getDeviceLocation();
        });

        mBottomSheetBehavior = BottomSheetBehavior.from(cvLocationsContainer);

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this, R.layout.list_item_location, mGoogleApiClient, BOUNDS_INDIA, null);


        mAutoCompleteSourceTextViewAdapter = new PlacesAutoCompleteTextViewAdapter(this, R.layout.list_item_location, BOUNDS_INDIA, mGoogleApiClient);
        source.setThreshold(3);
        source.setAdapter(mAutoCompleteSourceTextViewAdapter);
        source.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PlacesAutoCompleteTextViewAdapter.PlaceAutocomplete item = mAutoCompleteSourceTextViewAdapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);
                Log.i("LocationPickActivity", "Autocomplete item selected: " + item.address);

                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                // Construct a request object, passing the place ID and fields array.
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                        .build();

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place myPlace = response.getPlace();
                    Log.e("Place", "Place found: " + myPlace.getAddress());

                    LatLng latLng = myPlace.getLatLng();
                    isLocationRvClick = true;
                    isSettingLocationClick = true;
                    setLocationText(String.valueOf(item.address), latLng,
                            isLocationRvClick, isSettingLocationClick, false);

                    mGoogleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(new LatLng(
                                    latLng.latitude,
                                    latLng.longitude
                            ), DEFAULT_ZOOM));

                }).addOnFailureListener((exception) -> {

                    Toast.makeText(getApplicationContext(), "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
                });
            }
        });

        source.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) selectedEditText = source;
        });

        /*source.setText(MvpApplication.RIDE_REQUEST.containsKey(SRC_ADD)
                ? TextUtils.isEmpty(Objects.requireNonNull(MvpApplication.RIDE_REQUEST.get(SRC_ADD)).toString())
                ? ""
                : String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_ADD))
                : "");*/

        source1.setThreshold(3);
        source1.setAdapter(mAutoCompleteSourceTextViewAdapter);
        source1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PlacesAutoCompleteTextViewAdapter.PlaceAutocomplete item = mAutoCompleteSourceTextViewAdapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);
                Log.i("LocationPickActivity", "Autocomplete item selected: " + item.address);

                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                // Construct a request object, passing the place ID and fields array.
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                        .build();

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place myPlace = response.getPlace();
                    Log.e("Place", "Place found: " + myPlace.getAddress());

                    LatLng latLng = myPlace.getLatLng();
                    isLocationRvClick = true;
                    isSettingLocationClick = true;
                    setLocationText(String.valueOf(item.address), latLng,
                            isLocationRvClick, isSettingLocationClick, false);

                    mGoogleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(new LatLng(
                                    latLng.latitude,
                                    latLng.longitude
                            ), DEFAULT_ZOOM));

                }).addOnFailureListener((exception) -> {

                    Toast.makeText(getApplicationContext(), "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
                });
            }
        });

        source1.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) selectedEditText = source1;
        });

        /*source1.setText(MvpApplication.RIDE_REQUEST.containsKey(SRC_ADD)
                ? TextUtils.isEmpty(Objects.requireNonNull(MvpApplication.RIDE_REQUEST.get(SRC_ADD)).toString())
                ? ""
                : String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_ADD))
                : "");*/


        source2.setThreshold(3);
        source2.setAdapter(mAutoCompleteSourceTextViewAdapter);
        source2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final PlacesAutoCompleteTextViewAdapter.PlaceAutocomplete item = mAutoCompleteSourceTextViewAdapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);
                Log.i("LocationPickActivity", "Autocomplete item selected: " + item.address);

                List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);

                // Construct a request object, passing the place ID and fields array.
                FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                        .build();

                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                    Place myPlace = response.getPlace();
                    Log.e("Place", "Place found: " + myPlace.getAddress());

                    LatLng latLng = myPlace.getLatLng();
                    isLocationRvClick = true;
                    isSettingLocationClick = true;
                    setLocationText(String.valueOf(item.address), latLng,
                            isLocationRvClick, isSettingLocationClick, false);

                    mGoogleMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(new LatLng(
                                    latLng.latitude,
                                    latLng.longitude
                            ), DEFAULT_ZOOM));

                }).addOnFailureListener((exception) -> {

                    Toast.makeText(getApplicationContext(), "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
                });
            }
        });

        source2.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) selectedEditText = source2;
        });

        /*source2.setText(MvpApplication.RIDE_REQUEST.containsKey(SRC_ADD)
                ? TextUtils.isEmpty(Objects.requireNonNull(MvpApplication.RIDE_REQUEST.get(SRC_ADD)).toString())
                ? ""
                : String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_ADD))
                : "");*/



//        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
//        rvLocation.setLayoutManager(mLinearLayoutManager);
//        rvLocation.setAdapter(mAutoCompleteAdapter);

//        etDestination.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                if (canShowKeyboard) hideKeyboard();
//                if (!s.toString().equals("") && mGoogleApiClient.isConnected()) {
//                    rvLocation.setVisibility(View.VISIBLE);
//                    mAutoCompleteAdapter.getFilter().filter(s.toString());
//                    if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED)
//                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else if (!mGoogleApiClient.isConnected()) Log.e("ERROR", "API_NOT_CONNECTED");
//                if (s.toString().equals("")) rvLocation.setVisibility(View.GONE);
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) hideKeyboard();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etPhoneNumber.setOnTouchListener((arg0, arg1) -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
           // rvLocation.setVisibility(View.GONE);
            return false;
        });

        source.setOnTouchListener((arg0, arg1) -> {
            canShowKeyboard = false;
            return false;
        });

        source1.setOnTouchListener((arg0, arg1) -> {
            canShowKeyboard = false;
            return false;
        });

        source2.setOnTouchListener((arg0, arg1) -> {
            canShowKeyboard = false;
            return false;
        });

//        rvLocation.addOnItemTouchListener(new RecyclerItemClickListener(this, (view, position) -> {
//                    if (mAutoCompleteAdapter.getItemCount() == 0) return;
//                    final PlacesAutoCompleteAdapter.PlaceAutocomplete item = mAutoCompleteAdapter.getItem(position);
//                    final String placeId = String.valueOf(item.placeId);
//                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
//                    placeResult.setResultCallback(places -> {
//                        if (places.getCount() == 1) {
//                            canShowKeyboard = true;
//                            setLocationText(String.valueOf(item.address), places.get(0).getLatLng());
//                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                            rvLocation.setVisibility(View.GONE);
//                        } else
//                            Toast.makeText(getApplicationContext(), "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
//                    });
//                })
//        );

        setCountryList();
    }

    private void setLocationText(@NonNull String address, @NonNull LatLng latLng) {
        canShowKeyboard = true;
        source.setText(address);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (canEditSourceAddress) {
            instantRide.put("s_latitude", latLng.latitude);
            instantRide.put("s_longitude", latLng.longitude);
            instantRide.put("s_address", address);
            PACKAGE_DETAIL.put("s_latitude",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(latLng.latitude)));
            PACKAGE_DETAIL.put("s_longitude",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(latLng.longitude)));
            PACKAGE_DETAIL.put("s_address",RequestBody.create(MediaType.parse("text/plain"),address));
            canEditSourceAddress = false;
        }
        instantRide.put("d_latitude", latLng.latitude);
        instantRide.put("d_longitude", latLng.longitude);
        instantRide.put("d_address", address);
        PACKAGE_DETAIL.put("d_latitude",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(latLng.latitude)));
        PACKAGE_DETAIL.put("d_longitude",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(latLng.longitude)));
        PACKAGE_DETAIL.put("d_address",RequestBody.create(MediaType.parse("text/plain"),address));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting())
            mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onCameraIdle() {
        try {
            CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
            if (isEnableIdle) {
                String address = getAddress(cameraPosition.target);
                System.out.println("onCameraIdle " + address);
                hideKeyboard();
                setLocationText(address, cameraPosition.target, isLocationRvClick, isSettingLocationClick, true);
                //setLocationText(address, cameraPosition.target);
              //  rvLocation.setVisibility(View.GONE);
            }
            isEnableIdle = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
//            hideKeyboard();
//            setLocationText(getAddress(cameraPosition.target), cameraPosition.target);
//            rvLocation.setVisibility(View.GONE);
//            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onCameraMove() {

    }

    void getDeviceLocation() {
        try {
            if (mLocationPermission) {
                Task<Location> locationResult = mFusedLocation.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastKnownLocation = task.getResult();
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                mLastKnownLocation.getLatitude(),
                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                    } else {
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mLocationPermission = true;
        else ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) return;
        try {
            if (mLocationPermission) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap.setOnCameraMoveListener(this);
                mGoogleMap.setOnCameraIdleListener(this);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermission = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermission = true;
                    updateLocationUI();
                    getDeviceLocation();
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("Google API Callback", "Connection Suspended");
        Log.v("Code", String.valueOf(i));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("Error Code", String.valueOf(connectionResult.getErrorCode()));
        Toast.makeText(this, "API_NOT_CONNECTED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_pick_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_done:
//                if (validate()) {
//                    showLoading();
//                    presenter.estimateFare(instantRide);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                if (!TextUtils.isEmpty(actionName) /*&& actionName.equals(Constants.LocationActions.SELECT_HOME) || actionName.equals(Constants.LocationActions.SELECT_WORK)*/){
                    /*Intent intent = new Intent();
                    intent.putExtra(SRC_ADD, s_address);
                    intent.putExtra(SRC_LAT, s_latitude);
                    intent.putExtra(SRC_LONG, s_longitude);
                    setResult(Activity.RESULT_OK, intent);
                    finish();*/
                    if(validate()){
                        startActivityForResult(new Intent(InstantRideActivity.this, AddCourierDetail.class).putExtra("type","0"), LAUNCH_COURIER_DETAIL_ACTIVITY);
                        //showLoading();
                        //presenter.estimateFare(instantRide);
                    }
                } else {
                    setResult(Activity.RESULT_OK, new Intent());
                    finish();
                }
               // setResult();

                updateLocationUI();
                return true;

//            case android.R.id.home:
//                Toast.makeText(getApplicationContext(), "Back button clicked", Toast.LENGTH_SHORT).show();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean validate() {
        if (etPhoneNumber.getText().toString().length() > 0) {
            instantRide.put("mobile", etPhoneNumber.getText().toString());
            instantRide.put("country_code", tvCountryCode.getText().toString());
            PACKAGE_DETAIL.put("mobile",RequestBody.create(MediaType.parse("text/plain"),etPhoneNumber.getText().toString()));
            PACKAGE_DETAIL.put("country_code",RequestBody.create(MediaType.parse("text/plain"),tvCountryCode.getText().toString()));
        } else {
            Toasty.error(this, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT, true).show();
            return false;
        }

        if (source.getText().toString().length() > 3){
            instantRide.put("d_address", source.getText().toString());
            PACKAGE_DETAIL.put("d_address",RequestBody.create(MediaType.parse("text/plain"),source.getText().toString()));
        }
        else {
            Toasty.error(this, getString(R.string.enter_destination), Toast.LENGTH_SHORT, true).show();
            return false;
        }
        return true;
    }

    @OnClick({R.id.source, R.id.qr_scan, R.id.img_add, R.id.img_add1, R.id.reset_source,R.id.reset_source1,R.id.reset_source2})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.qr_scan:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setPrompt("Scan a QRcode");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                break;

            case R.id.source:

            case R.id.reset_source:
                selectedEditText = source;
                source.setText("");
                source.requestFocus();
                break;
            case R.id.reset_source1:
                selectedEditText = source1;
                source1.setText("");
                source1.requestFocus();
                break;
            case R.id.reset_source2:
                selectedEditText = source2;
                source2.setText("");
                source2.requestFocus();
                break;
            case R.id.img_add:
                //img_add.setImageResource(R.drawable.ic_close);
                img_add.setImageDrawable(null);
                destination_layout1.setVisibility(View.VISIBLE);
                break;
            case R.id.img_add1:
                //img_add1.setImageResource(R.drawable.ic_close);
                img_add1.setImageDrawable(null);
                img_add.setVisibility(View.GONE);
                img_add1.setVisibility(View.GONE);
                img_add2.setVisibility(View.GONE);
                img_add3.setVisibility(View.GONE);
                destination_layout2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onSuccess(EstimateFareNew estimateFare) {
        hideLoading();
        showConfirmationDialog(Double.parseDouble(estimateFare.getTotal_estimated_fare().toString()), instantRide, estimateFare.getTotal_distance());
    }

    @Override
    public void onSuccess(TripResponse response) {
        hideLoading();
        mDialog.dismiss();
        finish();
    }

    private AlertDialog mDialog;

    private void showConfirmationDialog(double estimatedFare, Map<String, Object> params, Double distance) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_instant_ride, null);

        TextView tvPickUp = view.findViewById(R.id.tvPickUp);
        TextView tvDrop = view.findViewById(R.id.tvDrop);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvFare = view.findViewById(R.id.tvFare);
        TextView tvDrop1 = view.findViewById(R.id.tvDrop1);
        TextView tvDrop2 = view.findViewById(R.id.tvDrop2);
        TextView courier_detail = view.findViewById(R.id.courier_detail);
        RelativeLayout destination1 = view.findViewById(R.id.destination1);
        RelativeLayout destination2 = view.findViewById(R.id.destination2);

        tvPickUp.setText(Objects.requireNonNull(params.get("s_address")).toString());
        tvDrop.setText(Objects.requireNonNull(params.get("d_address")).toString());
        tvPhone.setText(Objects.requireNonNull(params.get("mobile")).toString());
        tvFare.setText("E£ "+String.valueOf(estimatedFare));

        if(params.get("p1_address") != null && params.get("p1_address").toString() != ""){
            destination1.setVisibility(View.VISIBLE);
            tvDrop1.setText(Objects.requireNonNull(params.get("p1_address")).toString());
        }
        if(params.get("p2_address") != null && params.get("p2_address").toString() != ""){
            destination2.setVisibility(View.VISIBLE);
            tvDrop2.setText(Objects.requireNonNull(params.get("p2_address")).toString());
        }

        courier_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(InstantRideActivity.this, AddCourierDetail.class).putExtra("type","1"), LAUNCH_COURIER_DETAIL_ACTIVITY);
            }
        });

        builder.setView(view);
        mDialog = builder.create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        view.findViewById(R.id.tvSubmit).setOnClickListener(view1 -> {
            showLoading();
            HashMap<String, RequestBody> map = new HashMap<>(MvpApplication.PACKAGE_DETAIL);
            map.put("distance", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(distance)));
            MultipartBody.Part filePart = null;
            if (MvpApplication.imgFile != null)
                try {
                    File compressedImageFile = new Compressor(InstantRideActivity.this).compressToFile(MvpApplication.imgFile);
                    filePart = MultipartBody.Part.createFormData("image", compressedImageFile.getName(),
                            RequestBody.create(MediaType.parse("image*//*"), compressedImageFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            presenter.requestInstantRide(map, filePart);
        });

        view.findViewById(R.id.tvCancel).setOnClickListener(view1 -> mDialog.dismiss());
        mDialog.show();
    }

    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        if (throwable != null)
            onErrorBase(throwable);
    }

    private void setCountryList() {
        mCountryPicker = CountryPicker.newInstance("Select Country");
        List<Country> countryList = Country.getAllCountries();
        Collections.sort(countryList, (s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        mCountryPicker.setCountriesList(countryList);

        setListener();
    }

    private void setListener() {
        mCountryPicker.setListener((name, code, dialCode, flagDrawableResID) -> {
            tvCountryCode.setText(dialCode);
            countryCode = dialCode;
            countryImage.setImageResource(flagDrawableResID);
            mCountryPicker.dismiss();
        });

        countryImage.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        tvCountryCode.setOnClickListener(v -> mCountryPicker.show(getSupportFragmentManager(), "COUNTRY_PICKER"));

        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Country country = getDeviceCountry(InstantRideActivity.this);
        countryImage.setImageResource(country.getFlag());
        tvCountryCode.setText(country.getDialCode());
        countryCode = country.getDialCode();
        countryFlag = country.getCode();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_COURIER_DETAIL_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getStringExtra("type").equals("0")) {
                    showLoading();
                    if (MvpApplication.RIDE_REQUEST.get(DEST_LAT1) != null && MvpApplication.RIDE_REQUEST.get(DEST_LAT2) != null) {
                        total_stop = "2";
                    } else if (MvpApplication.RIDE_REQUEST.get(DEST_LAT1) != null) {
                        total_stop = "1";
                    } else if (MvpApplication.RIDE_REQUEST.get(DEST_LAT2) != null) {
                        total_stop = "1";
                    } else {
                        total_stop = "0";
                    }
                    instantRide.put("total_stop", total_stop);
                    PACKAGE_DETAIL.put("total_stop", RequestBody.create(MediaType.parse("text/plian"), total_stop));
                    presenter.estimateFare(instantRide);
                }
            }
        }else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null)
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                else try {
                    String scanResult = result.getContents().trim();
                    System.out.println("RRR scanResult = " + scanResult);
                    JSONObject jObject = new JSONObject(scanResult);
                    etPhoneNumber.setText(jObject.optString("phone_number"));
                    tvCountryCode.setText(TextUtils.isEmpty(jObject.optString("country_code"))
                            ? countryCode : jObject.optString("country_code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    tvCountryCode.setText(countryCode);
                }
            }
        }
    }


    ///////////////////

    private void setLocationText(String address, LatLng latLng, boolean isLocationRvClick,
                                 boolean isSettingLocationClick, boolean isUpdateNeeded) {
        if (address != null && latLng != null) {
            isEditable = false;
            if(isUpdateNeeded)
                selectedEditText.setText(address);
            isEditable = true;
            instantRide.put("s_latitude", MvpApplication.RIDE_REQUEST.get(SRC_LAT));
            instantRide.put("s_longitude", MvpApplication.RIDE_REQUEST.get(SRC_LONG));
            instantRide.put("s_address", MvpApplication.RIDE_REQUEST.get(SRC_ADD));
            PACKAGE_DETAIL.put("s_latitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_LAT))));
            PACKAGE_DETAIL.put("s_longitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_LONG))));
            PACKAGE_DETAIL.put("s_address",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(MvpApplication.RIDE_REQUEST.get(SRC_ADD))));
            if (selectedEditText.getTag().equals("source")) {
                s_address = address;
                s_latitude = latLng.latitude;
                s_longitude = latLng.longitude;
                MvpApplication.RIDE_REQUEST.put(SRC_ADD, address);
                MvpApplication.RIDE_REQUEST.put(SRC_LAT, latLng.latitude);
                MvpApplication.RIDE_REQUEST.put(SRC_LONG, latLng.longitude);
            }

            if (selectedEditText.getTag().equals("destination")) {

                MvpApplication.RIDE_REQUEST.put(DEST_ADD, address);
                MvpApplication.RIDE_REQUEST.put(DEST_LAT, latLng.latitude);
                MvpApplication.RIDE_REQUEST.put(DEST_LONG, latLng.longitude);
                instantRide.put("d_latitude", latLng.latitude);
                instantRide.put("d_longitude", latLng.longitude);
                instantRide.put("d_address", address);
                PACKAGE_DETAIL.put("d_latitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.latitude)));
                PACKAGE_DETAIL.put("d_longitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.longitude)));
                PACKAGE_DETAIL.put("d_address",RequestBody.create(MediaType.parse("text/plain"), address));
                if (isLocationRvClick) {
                    //  Done functionality called...
//                    setResult(Activity.RESULT_OK, new Intent());
//                    finish();
                  //  setResult();
                    updateLocationUI();

                }
            }
            if (selectedEditText.getTag().equals("destination1")) {
                MvpApplication.RIDE_REQUEST.put(DEST_ADD1, address);
                MvpApplication.RIDE_REQUEST.put(DEST_LAT1, latLng.latitude);
                MvpApplication.RIDE_REQUEST.put(DEST_LONG1, latLng.longitude);
                instantRide.put("p1_latitude", latLng.latitude);
                instantRide.put("p1_longitude", latLng.longitude);
                instantRide.put("p1_address", address);
                PACKAGE_DETAIL.put("p1_latitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.latitude)));
                PACKAGE_DETAIL.put("p1_longitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.longitude)));
                PACKAGE_DETAIL.put("p1_address",RequestBody.create(MediaType.parse("text/plain"), address));
                if (isLocationRvClick) {
                    //  Done functionality called...
//                    setResult(Activity.RESULT_OK, new Intent());
//                    finish();
                    //region for change by Atul Mavani
                    //setResult();
                    //endregion

                }
            }
            if (selectedEditText.getTag().equals("destination2")) {
                MvpApplication.RIDE_REQUEST.put(DEST_ADD2, address);
                MvpApplication.RIDE_REQUEST.put(DEST_LAT2, latLng.latitude);
                MvpApplication.RIDE_REQUEST.put(DEST_LONG2, latLng.longitude);
                instantRide.put("p2_latitude", latLng.latitude);
                instantRide.put("p2_longitude", latLng.longitude);
                instantRide.put("p2_address", address);
                PACKAGE_DETAIL.put("p2_latitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.latitude)));
                PACKAGE_DETAIL.put("p2_longitude",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latLng.longitude)));
                PACKAGE_DETAIL.put("p2_address",RequestBody.create(MediaType.parse("text/plain"), address));
                if (isLocationRvClick) {
                    //  Done functionality called...
//                    setResult(Activity.RESULT_OK, new Intent());
//                    finish();
                    //region for change by Atul Mavani
                    //setResult();
                    //endregion

                }
            }
        } else {
            isEditable = false;
            selectedEditText.setText("");
//            locationsRv.setVisibility(View.GONE);
            isEditable = true;

            if (selectedEditText.getTag().equals("source")) {
                MvpApplication.RIDE_REQUEST.remove(SRC_ADD);
                MvpApplication.RIDE_REQUEST.remove(SRC_LAT);
                MvpApplication.RIDE_REQUEST.remove(SRC_LONG);
            }
            if (selectedEditText.getTag().equals("destination")) {
                MvpApplication.RIDE_REQUEST.remove(DEST_ADD);
                MvpApplication.RIDE_REQUEST.remove(DEST_LAT);
                MvpApplication.RIDE_REQUEST.remove(DEST_LONG);
            }
            if (selectedEditText.getTag().equals("destination1")) {
                MvpApplication.RIDE_REQUEST.remove(DEST_ADD1);
                MvpApplication.RIDE_REQUEST.remove(DEST_LAT1);
                MvpApplication.RIDE_REQUEST.remove(DEST_LONG1);
            }
            if (selectedEditText.getTag().equals("destination2")) {
                MvpApplication.RIDE_REQUEST.remove(DEST_ADD2);
                MvpApplication.RIDE_REQUEST.remove(DEST_LAT2);
                MvpApplication.RIDE_REQUEST.remove(DEST_LONG2);
            }
        }

        if (isSettingLocationClick) {
            hideKeyboard();
//            locationsRv.setVisibility(View.GONE);
        }
    }


    protected void setResult() {
        if (!TextUtils.isEmpty(actionName) && actionName.equals(Constants.LocationActions.SELECT_HOME) || actionName.equals(Constants.LocationActions.SELECT_WORK) || (s_address!=null && (!s_address.isEmpty() || !s_address.equalsIgnoreCase("")))){
            Intent intent = new Intent();
            intent.putExtra(SRC_ADD, s_address);
            intent.putExtra(SRC_LAT, s_latitude);
            intent.putExtra(SRC_LONG, s_longitude);

            setResult(Activity.RESULT_OK, intent);


            //finish();
        } else {
            setResult(Activity.RESULT_OK, new Intent());
           // finish();
        }
    }

}
