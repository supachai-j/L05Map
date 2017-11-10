package andriod.training.cat.com.l05map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private String current_location = null;

    //Request Permission Location
    private static final int REQUEST_LOCATION = 1001;
    private String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current_location = getString(R.string.strXML_default_loc);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, REQUEST_LOCATION);
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                current_location = location.getLatitude() + "," + location.getLongitude();
                Toast.makeText(this, getString(R.string.strXML_your_current_toast, current_location), Toast.LENGTH_LONG).show();
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

        //Button click to sound page
        ImageButton imb_goto_cat_lak_si = (ImageButton) findViewById(R.id.lo_imb_goto_cat_lak_si);
        imb_goto_cat_lak_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri;
                uri = mapIntent(current_location, getString(R.string.strXML_cat_lak_si_loc));
                if (uri != null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }

            }
        });

        ImageButton imb_goto_cat_nonthaburi  = (ImageButton) findViewById(R.id.lo_imb_goto_cat_nonthaburi);
        imb_goto_cat_nonthaburi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri;
                uri = mapIntent(current_location, getString(R.string.strXML_cat_nonthaburi_loc));
                if (uri != null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }

            }
        });

    }
    public void gotoPage(Class dest_class, boolean transition) {
        Intent subIntent = new Intent(getApplicationContext(), dest_class);
        startActivity(subIntent);
        if (transition) overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }
    private String mapIntent (String loc_a, String loc_b) {
        if ((loc_a == null) || (loc_b == null)) return null;
        return "http://maps.google.com/maps?saddr=" + loc_a + "&daddr=" + loc_b;
    }

    @Override
    public void onLocationChanged(Location location) {
        current_location = location.getLatitude()+ "," + location.getLongitude();
        Toast.makeText(this, getString(R.string.strXML_your_current_toast, current_location), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission. ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            current_location = location.getLatitude() + "," + location.getLongitude();
                            Toast.makeText(this, getString(R.string.strXML_your_current_toast, current_location), Toast.LENGTH_LONG).show();
                        }
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } else {
                        Toast.makeText(this, getString(R.string.strXML_can_not_get_location_toast), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.strXML_can_not_get_permission_toast), Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

}
