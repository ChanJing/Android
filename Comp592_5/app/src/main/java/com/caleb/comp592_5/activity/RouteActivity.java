package com.caleb.comp592_5.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.caleb.comp592_5.adapter.MyItemDecoration;
import com.caleb.comp592_5.adapter.PrivateRouteAdapter;
import com.caleb.comp592_5.adapter.PublicRouteAdapter;
import com.caleb.comp592_5.behavior.DisableableAppBarLayoutBehavior;
import com.caleb.comp592_5.common.logger.Log;
import com.caleb.comp592_5.data.LocationData;
import com.caleb.comp592_5.response.Distance;
import com.caleb.comp592_5.response.Duration;
import com.caleb.comp592_5.response.MyLeg;
import com.caleb.comp592_5.response.MyResponse;
import com.caleb.comp592_5.response.MyRoute;
import com.google.android.gms.maps.model.LatLng;
import com.caleb.comp592_5.common.activities.SampleActivityBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Caleb on 9/1/2016.
 */
public class RouteActivity extends SampleActivityBase
        implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = "RouteActivity";
    private static final String ORIGIN = "Origin";
    private static final String DESTINATION = "Destination";
    private static final String CURRENTLOCATION = "CurrentLocation";
    private static final String TARGET = "Target";

    /**
     * This is the API key for Downloading
     */
    private static final String APIKEY = "AIzaSyBu29xMUxV4FpoXW3ss73V_oETPSkaGl-k";

    /**
     * Format Google Maps Directions API request
     */
    private static final String APIREQUEST = "https://maps.googleapis.com/maps/api/directions/json?";

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout.LayoutParams params;
    private Toolbar toolbar;
    EditText origin;
    EditText destination;
    private LocationData originLocation = null;
    private LocationData destinationLocation= null;
    private LocationData currentLocation = null;

    private MenuItem menuItemEditFinish = null;


    private RecyclerView recyclerViewPrivate = null;
    private RecyclerView recyclerViewPublic = null;
    private RecyclerView.Adapter adapterPrivate = null;
    private RecyclerView.LayoutManager layoutManagerPrivate = null;
    private RecyclerView.Adapter adapterPublic = null;
    private RecyclerView.LayoutManager layoutManagerPublic = null;

    ProgressDialog progressDialog;
    JSONObject jsonResponse;
    JSONObject jsonResponsePrivate;
    JSONObject jsonResponsePublic;



    ArrayList<HashMap<String, String>> basicInfosPrivate = new ArrayList<>();
    ArrayList<HashMap<String, String>> basicInfosPublic = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(savedInstanceState != null){
//            if(savedInstanceState.getParcelable("originLocation") != null) {
//                originLocation = savedInstanceState.getParcelable("originLocation");
//                Log.e("originLocation "," is exist");
//            }
//
//            if(savedInstanceState.getParcelable("destinationLocation") != null) {
//                destinationLocation = savedInstanceState.getParcelable("destinationLocation");
//                Log.e("destinationLocation"," is exist");
//            }
//
//            currentLocation = savedInstanceState.getParcelable("currentLocation");
//            Log.e("currentLocation ","is exist");
//
//
//            if(destinationLocation != null)
//                destination.setText(destinationLocation.getAddress(), TextView.BufferType.EDITABLE);
//            if(originLocation != null)
//                origin.setText(originLocation.getAddress(), TextView.BufferType.EDITABLE);
//        }
        setContentView(R.layout.activity_route);

        prepareContent();

        prepareRecyclerView();


        SavedData();
        prepareToolbar();

    }



    protected void prepareContent(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);


        appBarLayout.addOnOffsetChangedListener(this);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        ((DisableableAppBarLayoutBehavior) layoutParams.getBehavior()).setEnabled(false);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        params = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        origin = (EditText)findViewById(R.id.input_origin);
        destination = (EditText)findViewById(R.id.input_destination);
    }
    protected void prepareToolbar(){
        Intent intent = getIntent();
        if(intent != null) {
            if (intent.getParcelableExtra(ORIGIN) != null) {
                originLocation = intent.getParcelableExtra(ORIGIN);
                origin.setText(originLocation.getAddress(), TextView.BufferType.EDITABLE);
            }

            if (intent.getParcelableExtra(DESTINATION) != null) {
                destinationLocation = intent.getParcelableExtra(DESTINATION);
                destination.setText(destinationLocation.getAddress(), TextView.BufferType.EDITABLE);
            }
            if (intent.getParcelableExtra(CURRENTLOCATION) != null)
                currentLocation = intent.getParcelableExtra(CURRENTLOCATION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
        menuItemEditFinish = menu.findItem(R.id.action_edit_finish);


        origin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
//                    Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                    v.clearFocus();
                    Intent intent = new Intent(v.getContext(), SearchActivity.class);
                    intent.putExtra(TARGET,"origin");
                    intent.putExtra(DESTINATION,destinationLocation);
                    intent.putExtra(CURRENTLOCATION,currentLocation);
                    intent.putExtra(ORIGIN,originLocation);
                    startActivity(intent);
                }else {
//                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        destination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
//                    Toast.makeText(getApplicationContext(), "got the focus", Toast.LENGTH_LONG).show();
                    v.clearFocus();
                    Intent intent = new Intent(v.getContext(), SearchActivity.class);
                    intent.putExtra(TARGET,"destination");
                    intent.putExtra(DESTINATION,destinationLocation);
                    intent.putExtra(CURRENTLOCATION,currentLocation);
                    intent.putExtra(ORIGIN,originLocation);
                    startActivity(intent);
                }else {
//                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_finish) {
            if(item.getTitle().equals("finish")){
                lockAppBarClosed();
                item.setTitle("edit");
                if(originLocation != null&&destinationLocation != null) {
                    if (originLocation.getAddress() != null && destinationLocation.getAddress() != null) {
                        showProgressDialog();
                        buildUrlRequestData(originLocation, destinationLocation);
                    }
                }else{
                    Toast toast = Toast.makeText(this, "Origin or Destination is NULL", Toast.LENGTH_LONG);
                    toast.show();
                }
            }else{
                unlockAppBarOpen();
                item.setTitle("finish");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lockAppBarClosed() {
        appBarLayout.setExpanded(false, true);
//        appBarLayout.setActivated(false);
    }

    public void unlockAppBarOpen() {
        appBarLayout.setExpanded(true, true);
//        appBarLayout.setActivated(true);
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset)
    {
        Log.i("onOptionsItemSelected", String.valueOf(offset));
        if(menuItemEditFinish != null) {
            // Collapsed
            if (offset == 0) {
                menuItemEditFinish.setTitle("finish");
                collapsingToolbarLayout.setTitle("");
            }
            // Not collapsed
            else if(offset > -316){
                menuItemEditFinish.setTitle("edit");
                collapsingToolbarLayout.setTitle("");
            }
            else {
                menuItemEditFinish.setTitle("edit");
                collapsingToolbarLayout.setTitle
                        (origin.getText().subSequence(0,Math.min(origin.getText().length(),10))
                                + " >-->--> "
                                + destination.getText().subSequence(0,Math.min(destination.getText().length(),10)));
            }

        }
    }

    /**
     * Prepare RecyclerView
     * mRecyclerView, mLayoutManager, mRecyclerView, mAdapter
     */
    void prepareRecyclerView(){
        recyclerViewPrivate = (RecyclerView) findViewById(R.id.recycler_view_private);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewPrivate.setHasFixedSize(true);

        // use a linear layout manager
        layoutManagerPrivate = new LinearLayoutManager(this);
        recyclerViewPrivate.setLayoutManager(layoutManagerPrivate);

        adapterPrivate = new PrivateRouteAdapter(this, basicInfosPrivate );
        recyclerViewPrivate.setAdapter(adapterPrivate);
        recyclerViewPrivate.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST));


        recyclerViewPublic = (RecyclerView) findViewById(R.id.recycler_view_public);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewPublic.setHasFixedSize(true);

        layoutManagerPublic  = new LinearLayoutManager(this);
        recyclerViewPublic.setLayoutManager(layoutManagerPublic);

        adapterPublic = new PublicRouteAdapter(this, basicInfosPublic);
        recyclerViewPublic.setAdapter(adapterPublic);
        recyclerViewPublic.addItemDecoration(new MyItemDecoration(this,MyItemDecoration.VERTICAL_LIST));
    }



    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class UrlDownloadTask extends AsyncTask<String, Integer, ArrayList<HashMap<String, String>>> {
        String style = null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            android.util.Log.i("onPreExecute", Thread.currentThread().getName());
        }

        //Override this method to perform a computation on a background thread.
        // The specified parameters are the parameters passed to execute(Params...) by the caller of this task.
        // This method can call publishProgress(Progress...) to publish updates on the UI thread.
        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... urlParams){
            android.util.Log.i("doInBackground", Thread.currentThread().getName());
            style = null;
            // params comes from the execute() call: params[0] is the url.
            try{
                //This method can be invoked from doInBackground(Params...) to publish updates on the UI thread while the background computation is still running.
                // Each call to this method will trigger the execution of onProgressUpdate(Progress...) on the UI thread.
                // onProgressUpdate(Progress...) will not be called if the task has been canceled.
                publishProgress(1);
                Log.e("URL is ", urlParams[0]);

                String response = requestData(urlParams[0]);
                try {
                    jsonResponse = new JSONObject(response);
                    if(urlParams[0].contains("driving")){
                        style = "driving";
                        jsonResponsePrivate = new JSONObject(response);
                    } else if(urlParams[0].contains("transit")){
                        style = "tranist";
                        jsonResponsePublic = new JSONObject(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return getRouteInfos(jsonResponse);
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if("driving".equals(style)){
                getRouteInfosPrivate(result);
                adapterPrivate.notifyDataSetChanged();
            } else{
                getRouteInfosPublic(result);
                adapterPublic.notifyDataSetChanged();
            }
            dismissProgressDialog();
        }

        //Runs on the UI thread after publishProgress(Progress...) is invoked.
        @Override
        protected void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress);

            android.util.Log.i("onProgressUpdate", Thread.currentThread().getName());
            if(progress[0] == 1){
                showProgressDialog();
            }
        }
    }

    /**
     * Request Data form Internet though URL
     * @param strUrl
     * @return
     * @throws IOException
     */
    private String requestData(String strUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            httpURLConnection.connect();

            // Reading data from url
            inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();

            bufferedReader.close();

        } catch (Exception e) {
            android.util.Log.d("Exception when download", e.toString());
        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return data;
    }

    /**
     * Display Progress Dialog while downloading Data from Internet
     * set Cannot be canceled by touch OR BackButton
     */
    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Dialog Title");
        progressDialog.setMessage("Dialog Message");

        //Sets whether this dialog is cancelable with the BACK key.
        progressDialog.setCancelable(false);

        //Sets whether this dialog is canceled when touched outside the window's bounds.
        //If setting to true, the dialog is set to be cancelable if not already set.
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * Dismiss Progress Dialog
     * Be called after Download
     */
    void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void launchPolylineActivityPrivate(int position){
        Intent intent = new Intent(this, PolylineActivity.class);
        JSONArray jRoutes = jsonResponsePrivate.optJSONArray("routes");
        JSONObject selectedRoute = jRoutes.optJSONObject(position);
        intent.putExtra("route",selectedRoute.toString());
        startActivity(intent);
    }

    public void launchPolylineActivityPublic(int position) {
        Intent intent = new Intent(this, PolylineActivity.class);
        JSONArray jRoutes = jsonResponsePublic.optJSONArray("routes");
        JSONObject selectedRoute = jRoutes.optJSONObject(position);
        intent.putExtra("route",selectedRoute.toString());
        startActivity(intent);
    }


    /**
     * Create two URLs using originLocation, destinationLocation.
     * One is for private drive
     * Another is for public transportation
     * Then create and launch two UrlDownloadTask in parallel.
     * @param originLocation
     * @param destinationLocation
     */
    public void buildUrlRequestData(LocationData originLocation,LocationData destinationLocation){

        UrlDownloadTask urlDownloadTaskPrivate = new UrlDownloadTask();
        UrlDownloadTask urlDownloadTaskPublic = new UrlDownloadTask();

        String urlPrivate = buildUrl(originLocation,destinationLocation,"driving");
        String urlPublic = buildUrl(originLocation,destinationLocation,"transit");

        urlDownloadTaskPrivate.execute(urlPrivate);
        urlDownloadTaskPublic.execute(urlPublic);
        dismissProgressDialog();
    }

    private String buildUrl(LocationData origin,LocationData destination,String style){
        String url;
        LatLng originLatlng = origin.getLatLng();
        LatLng destinationLatlng = destination.getLatLng();

        url = APIREQUEST+
                "origin="+originLatlng.latitude+","+originLatlng.longitude+"&"+
                "destination="+destinationLatlng.latitude+","+destinationLatlng.longitude+"&"+
                "mode="+style+"&"+
                "alternatives=true&"+
                "key+"+APIKEY;
        return url;
    }

    private ArrayList<HashMap<String, String>> getRouteInfos(JSONObject response){
        JSONArray jLegs;
        JSONObject jLeg;
        String distance;
        String duration;
        ArrayList<HashMap<String, String>> routeInfos = new ArrayList<>();
        JSONArray jRoutes = response.optJSONArray("routes");
        //Log.e("The length of Routes is", "" +jRoutes.length());
        for(int i=0;i<jRoutes.length();i++){
            jLegs = ( (JSONObject)jRoutes.opt(i)).optJSONArray("legs");
            jLeg = (JSONObject)jLegs.opt(0);
            distance = jLeg.optJSONObject("distance").optString("text");
            duration = jLeg.optJSONObject("duration").optString("text");

            HashMap<String, String> basicInfo = new HashMap<>();
            basicInfo.put("distance", distance);
            basicInfo.put("duration", duration);
            routeInfos.add(basicInfo);
        }
        return routeInfos;
    }

    private void getRouteInfosPrivate(ArrayList<HashMap<String, String>> result){
        basicInfosPrivate.clear();
        //Log.e("The size of result is", "" +result.size());
        for(int i = 0; i < result.size(); i ++){
            basicInfosPrivate.add(result.get(i));
            Log.e("basicInfosPrivate" , basicInfosPrivate.toString());
        }
    }

    private void getRouteInfosPublic(ArrayList<HashMap<String, String>> result){
        basicInfosPublic.clear();
        Log.e("The result is", "" +result.toString());
        for(int i = 0; i < result.size(); i ++){
            basicInfosPublic.add(result.get(i));
            Log.e("basicInfosPublic" , basicInfosPublic.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (originLocation != null) {
            editor.putString("originLocationId", originLocation.getId());
            editor.putString("originLocationAddress", originLocation.getAddress());
            Double lat = originLocation.getLatLng().latitude;
            editor.putString("originLocationLat", lat.toString());
            Double lng = originLocation.getLatLng().longitude;
            editor.putString("originLocationlng", lng.toString());
        }

        if(destination != null){
            editor.putString("destinationLocationId", destinationLocation.getId());
            editor.putString("destinationLocationAddress", destinationLocation.getAddress());
            Double lat = destinationLocation.getLatLng().latitude;
            editor.putString("destinationLocationLat", lat.toString());
            Double lng = destinationLocation.getLatLng().longitude;
            editor.putString("destinationLocationlng", lng.toString());
        }

        if(currentLocation != null){
            editor.putString("currentLocationId", currentLocation.getId());
            editor.putString("currentLocationAddress", currentLocation.getAddress());
            Double lat = currentLocation.getLatLng().latitude;
            editor.putString("currentLocationLat", lat.toString());
            Double lng = currentLocation.getLatLng().longitude;
            editor.putString("currentLocationlng", lng.toString());
        }


        editor.commit();
        super.onSaveInstanceState(savedInstanceState);
    }

    private void SavedData() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref != null){

            if(sharedPref.getString("originLocationId",null) != null){
                originLocation = new LocationData(
                        new LatLng(
                                Double.parseDouble(sharedPref.getString("originLocationLat",null)),
                                Double.parseDouble(sharedPref.getString("currentLocationlng",null))),
                        sharedPref.getString("originLocationAddress", null),
                        sharedPref.getString("originLocationId",null)

                );
                origin.setText(originLocation.getAddress(), TextView.BufferType.EDITABLE);
            }

            if(sharedPref.getString("destinationLocationId",null) != null){
                destinationLocation = new LocationData(
                        new LatLng(
                                Double.parseDouble(sharedPref.getString("destinationLocationLat",null)),
                                Double.parseDouble(sharedPref.getString("destinationLocationlng",null))),
                        sharedPref.getString("destinationLocationAddress", null),
                        sharedPref.getString("destinationLocationId",null)

                );
                destination.setText(destinationLocation.getAddress(), TextView.BufferType.EDITABLE);
            }

            if(sharedPref.getString("currentLocationId",null) != null){
                currentLocation = new LocationData(
                        new LatLng(
                                Double.parseDouble(sharedPref.getString("currentLocationLat",null)),
                                Double.parseDouble(sharedPref.getString("currentLocationlng",null))),
                        sharedPref.getString("currentLocationAddress", null),
                        sharedPref.getString("currentLocationId",null)

                );
            }
        }
    }
}
