package missedconnections.apresswood.com.missedconnections;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener {

    protected MenuListener menuListener;
    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initializeUI();
    }

    @Override
    public void onClick(View v) {
        initializePlacePicker();
    }

    protected void initializeUI() {
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(),
                FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.maps_header), iconFont);

        TextView sideMenuButton = findViewById(R.id.menu_button);
        menuListener = new MenuListener(this);
        sideMenuButton.setOnClickListener(getSideMenuListener());

        NavigationView nav = findViewById(R.id.main_drawer);
        Menu menu = nav.getMenu();
        MenuItem menuItem = menu.findItem(R.id.connect);
        SpannableString menuItemText = new SpannableString(menuItem.getTitle());
        menuItemText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(),
                R.color.colorAccent)), 0, menuItemText.length(), 0);
        menuItem.setTitle(menuItemText);

        TextView addConnectionButton = findViewById(R.id.add_connection_button);
        addConnectionButton.setOnClickListener(this);
    }

    protected void initializePlacePicker() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception exception) {
            Log.d("EXCEPTION: ", exception.getMessage());
        }
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
        GoogleMap map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    protected View.OnClickListener getSideMenuListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListener.getDrawerLayout().isDrawerOpen(Gravity.START)) {
                    menuListener.getDrawerLayout().closeDrawer(Gravity.START);
                } else {
                    menuListener.getDrawerLayout().openDrawer(Gravity.START);
                }
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                Intent addConnectionIntent = new Intent(this, AddConnectionActivity.class);
                addConnectionIntent.putExtra("selectedLocation", place.getId());
                this.startActivity(addConnectionIntent);
            }
        }
    }
}
