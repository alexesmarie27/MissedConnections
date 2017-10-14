package missedconnections.apresswood.com.missedconnections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AddConnectionActivity extends Activity implements View.OnClickListener {
    protected MenuListener menuListener;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_add_connection);

        initializeUI();
        initializePlacePicker();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
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
    }

    protected void initializePlacePicker() {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception exception) {
            Log.d("EXCEPTION: ", exception.getMessage());
        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
