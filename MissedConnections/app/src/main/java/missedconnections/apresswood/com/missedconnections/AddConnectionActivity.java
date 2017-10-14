package missedconnections.apresswood.com.missedconnections;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;


public class AddConnectionActivity extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        AdapterView.OnItemSelectedListener {
    protected MenuListener menuListener;
    private String selectedLocationId;
    private Place selectedPlace;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_add_connection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedLocationId = extras.getString("selectedLocation");
        }

        buildGoogleApiClient();
        initializeUI();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_am:
                break;
            case R.id.radio_pm:
                break;
            case R.id.female:
                break;
            case R.id.male:
                break;
            default:
                break;
        }
    }

    protected void initializeUI() {
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(),
                FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.add_connection_header), iconFont);

        TextView sideMenuButton = findViewById(R.id.menu_button);
        menuListener = new MenuListener(this);
        sideMenuButton.setOnClickListener(getSideMenuListener());

        TextView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        TextView locationText = findViewById(R.id.location_text);
        getPlaceById(locationText);

        Spinner hairColorType = findViewById(R.id.hair_color_spinner);
        hairColorType.setOnItemSelectedListener(this);

        Spinner ethnicityType = findViewById(R.id.ethnicity_spinner);
        ethnicityType.setOnItemSelectedListener(this);

        Spinner height = findViewById(R.id.height_spinner);
        height.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> hairAdapter = ArrayAdapter.createFromResource(this,
                R.array.hair_colors, android.R.layout.simple_spinner_item);
        hairAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hairColorType.setAdapter(hairAdapter);

        ArrayAdapter<CharSequence> ethnicityAdapter = ArrayAdapter.createFromResource(this,
                R.array.ethnicities, android.R.layout.simple_spinner_item);
        ethnicityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ethnicityType.setAdapter(ethnicityAdapter);

        ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(this,
                R.array.height, android.R.layout.simple_spinner_item);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height.setAdapter(heightAdapter);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call request when button is clicked
                //still need to make database though....
            }
        });
    }

    protected void getPlaceById(final TextView locationText) {
        Places.GeoDataApi.getPlaceById(googleApiClient, selectedLocationId).setResultCallback(
                new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            selectedPlace = places.get(0);
                            locationText.setText(selectedPlace.getName());
                        }

                        places.release();
                    }
                });
    }

    protected View.OnClickListener getSideMenuListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener.getDrawerLayout().isDrawerOpen(Gravity.END)) {
                    menuListener.getDrawerLayout().closeDrawer(Gravity.END);
                } else {
                    menuListener.getDrawerLayout().openDrawer(Gravity.END);
                }
            }
        };
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        //do nothing for now
    }

    @Override
    public void onConnectionSuspended(int i) {
        //do nothing for now
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // will be used later to insert into DB
        parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing for now
    }
}
