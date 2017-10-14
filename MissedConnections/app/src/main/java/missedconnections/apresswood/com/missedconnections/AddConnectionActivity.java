package missedconnections.apresswood.com.missedconnections;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class AddConnectionActivity extends Activity implements View.OnClickListener {
    protected MenuListener menuListener;
    private int selectedLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_add_connection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedLocationId = Integer.parseInt(extras.getString("selectedLocation"));
        }

        initializeUI();
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
}
