package missedconnections.apresswood.com.missedconnections;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

class MenuListener implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Activity activity;

    MenuListener(Activity activity) {
        this.activity = activity;
        NavigationView navigationView = activity.findViewById(R.id.main_drawer);

        drawerLayout = activity.findViewById(R.id.drawer_main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.connect:
                if (this.activity instanceof MapsActivity) {
                    break;
                } else {
                    Intent mapIntent = new Intent(activity, MapsActivity.class);
                    activity.startActivity(mapIntent);
                    break;
                }
            case R.id.connections:
                break;
            case R.id.profile:
                break;
            case R.id.settings:
                break;
            default:
                break;
        }

        if (activity instanceof AddConnectionActivity) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }
}
