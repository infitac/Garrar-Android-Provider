package com.garrar.driver.app.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.garrar.driver.app.R;

import java.util.ArrayList;

public class PlacesAutoCompleteTextViewAdapter extends ArrayAdapter<PlacesAutoCompleteTextViewAdapter.PlaceAutocomplete> implements Filterable {

    private static final String TAG = PlacesAutoCompleteTextViewAdapter.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mBounds;
    private ArrayList<PlaceAutocomplete> mResultList;
    PlacesClient placesClient = null;
    private CharacterStyle STYLE_BOLD;
    private CharacterStyle STYLE_NORMAL;
    private Context mContext;
    private int mLayoutResource;


    public PlacesAutoCompleteTextViewAdapter(Context context, int resource, LatLngBounds bounds,
                                             GoogleApiClient googleApiClient) {
        super(context, resource);
        mContext = context;
        mLayoutResource = resource;
        mBounds = bounds;
        STYLE_BOLD = new StyleSpan(Typeface.BOLD);
        STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);
        mGoogleApiClient = googleApiClient;

        if (!Places.isInitialized()) {
            Places.initialize(context, context.getString(R.string.google_map_key));
        }

        placesClient = Places.createClient(context);
    }

    @NonNull
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        PlaceAutocomplete placeAutocomplete = mResultList.get(position);
        itemView = LayoutInflater.from(mContext).inflate(mLayoutResource, parent, false);
        TextView  area = itemView.findViewById(R.id.area);
        TextView address = itemView.findViewById(R.id.address);
        area.setText(placeAutocomplete.area);
        address.setText(placeAutocomplete.address.toString()
                .replace(placeAutocomplete.area + ", ", ""));
        return itemView;
    }


    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d(TAG, "performFiltering: called");
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    mResultList = getAutocomplete(constraint);

                    if (mResultList != null) {
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public class PlaceAutocomplete {

        public CharSequence placeId;
        public CharSequence address, area;

        PlaceAutocomplete(CharSequence placeId, CharSequence area, CharSequence address) {
            this.placeId = placeId;
            this.area = area;
            this.address = address;
        }

        @Override
        public String toString() {
            return area.toString();
        }
    }

    private ArrayList getAutocomplete(CharSequence constraint) {
        if (mGoogleApiClient.isConnected()) {
            Log.i(TAG, "Starting autocomplete query for: " + constraint);


            // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
// and once again when the user makes a selection (for example when calling fetchPlace()).
            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
// Create a RectangularBounds object.
//            RectangularBounds bounds = RectangularBounds.newInstance(
//                    new LatLng(-33.880490, 151.184363),
//                    new LatLng(-33.858754, 151.229596));
// Use the builder to create a FindAutocompletePredictionsRequest.
            FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
// Call either setLocationBias() OR setLocationRestriction().
                    // .setLocationBias(bounds)
                    //.setLocationRestriction(bounds)
                    // .setCountry()
//                    .setCountry("IN")
//                    .setCountry("ec")
                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                    .setSessionToken(token)
                    .setQuery(constraint.toString())
                    .build();
            ArrayList<PlaceAutocomplete> resultList = new ArrayList<>();

            placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPlaceId());
                    Log.i(TAG, prediction.getPrimaryText(null).toString());


                    resultList.add(new PlaceAutocomplete(prediction.getPlaceId(), prediction.getPrimaryText(STYLE_NORMAL),
                            prediction.getFullText(STYLE_BOLD)));

                }
                Log.e(TAG, "getAutocomplete: count : " + resultList.size() );
                notifyDataSetChanged();
            }).addOnFailureListener((exception) -> {
//                if (exception instanceof ApiException) {
//                    ApiException apiException = (ApiException) exception;
//                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//                }
            });
//
//            // Submit the query to the autocomplete API and retrieve a PendingResult that will
//            // contain the results when the query completes.
//            PendingResult<AutocompletePredictionBuffer> results = Places
//                    .GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
//                            mBounds, mPlaceFilter);
//
//            // This method should have been called off the main UI thread. Block and wait for at most 60s
//            // for a result from the API.
//            AutocompletePredictionBuffer autocompletePredictions = results
//                    .await(30, TimeUnit.SECONDS);
//
//            // Confirm that the query completed successfully, otherwise return null
//            final Status status = autocompletePredictions.getStatus();
//            if (!status.isSuccess()) {
//                Log.e("", "Error getting autocomplete prediction API call: " + status.toString());
//                autocompletePredictions.release();
//                return null;
//            }
//
//            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
//                    + " predictions.");
//
//            // Copy the results into our own data structure, because we can't hold onto the buffer.
//            // AutocompletePrediction objects encapsulate the API response (place ID and description).
//
//            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
//            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
//            while (iterator.hasNext()) {
//                AutocompletePrediction prediction = iterator.next();
//                // Get the details of this prediction and copy it into a new PlaceAutocomplete object.
//                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(), prediction.getPrimaryText(STYLE_NORMAL),
//                        prediction.getFullText(STYLE_BOLD)));
//            }
//
//            // Release the buffer now that all data has been copied.
//            autocompletePredictions.release();
            Log.d(TAG, "getAutocomplete: count : " + resultList.size());
            return resultList;
        }
        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }
}