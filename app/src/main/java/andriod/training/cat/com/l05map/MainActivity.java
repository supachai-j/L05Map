package andriod.training.cat.com.l05map;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private String current_location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current_location = getString(R.string.strXML_default_loc);

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

}
