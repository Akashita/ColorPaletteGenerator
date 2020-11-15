package com.nakashita.tpmobile.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.dialog.AddDialogFragment;
import com.nakashita.tpmobile.listener.ChangeColorCodeListener;
import com.nakashita.tpmobile.listener.CopyColorCodeListener;
import com.nakashita.tpmobile.modele.ColorBundle;
import com.nakashita.tpmobile.storage.ColorsJsonFileStorage;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView .OnNavigationItemSelectedListener{

    private final static String COLORS = "COLORS";
    private final static String BUNDLE_SAVED = "BUNDLE_SAVED";

    private ColorBundle colors; //The color palette display on the activity
    private boolean current_bundle_is_saved; //If the current palette is saved into json file

    private ActionBarDrawerToggle drawerToggle;
    //---------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------------------
        // Data recovery : if the activity was destroyed by the OS
        // ---------------------
        if(savedInstanceState != null){
            colors = (ColorBundle) savedInstanceState.getParcelable(COLORS);
            current_bundle_is_saved = savedInstanceState.getBoolean(BUNDLE_SAVED);
        } else if(getIntent().getSerializableExtra(SavedColorActivity.EXTRA_BUNDLE) != null){
            int id = (Integer) getIntent().getSerializableExtra(SavedColorActivity.EXTRA_BUNDLE);
            colors = ColorsJsonFileStorage.get(getApplicationContext()).find(id);
            current_bundle_is_saved = true;
        } else {
            colors = new ColorBundle();
            current_bundle_is_saved = false;
        }

        //The color palette has been created (or recovered), time to update the view
        updateView();

        // ---------------------
        // Navigation Drawer configuration
        // ---------------------
        configureNavigationView();
        configureDrawerLayout();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // ---------------------
        // Listeners
        // ---------------------
        findViewById(R.id.ColorContainer1).setOnClickListener(new ChangeColorCodeListener(ColorBundle.COLOR1, this, R.id.ColorText1));
        findViewById(R.id.ColorContainer1).setOnLongClickListener(new CopyColorCodeListener(ColorBundle.COLOR1, this));

        findViewById(R.id.ColorContainer2).setOnClickListener(new ChangeColorCodeListener(ColorBundle.COLOR2, this, R.id.ColorText2));
        findViewById(R.id.ColorContainer2).setOnLongClickListener(new CopyColorCodeListener(ColorBundle.COLOR2, this));

        findViewById(R.id.ColorContainer3).setOnClickListener(new ChangeColorCodeListener(ColorBundle.COLOR3, this, R.id.ColorText3));
        findViewById(R.id.ColorContainer3).setOnLongClickListener(new CopyColorCodeListener(ColorBundle.COLOR3, this));

        //Floating action button listener
        //SAVE PALETTE
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_bundle_is_saved){
                    Toast.makeText(getApplicationContext(), "This color bundle is already saved", Toast.LENGTH_SHORT).show();
                } else {
                    new AddDialogFragment(colors).show(getSupportFragmentManager(), "");
                }
            }
        });

        //Extended floating action button listener
        //GENERATE PALETTE
        findViewById(R.id.extended_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
                updateView();
            }
        });
    }

    private void changeColor(){
        colors = new ColorBundle();
        current_bundle_is_saved = false;
    }

    private void updateView(){
        findViewById(R.id.ColorContainer1).setBackgroundColor(colors.getColor(ColorBundle.COLOR1).getHexa());
        findViewById(R.id.ColorContainer2).setBackgroundColor(colors.getColor(ColorBundle.COLOR2).getHexa());
        findViewById(R.id.ColorContainer3).setBackgroundColor(colors.getColor(ColorBundle.COLOR3).getHexa());
        ((TextView) findViewById(R.id.ColorText1)).setText(colors.getColor(ColorBundle.COLOR1).getHexaString());
        ((TextView) findViewById(R.id.ColorText2)).setText(colors.getColor(ColorBundle.COLOR2).getHexaString());
        ((TextView) findViewById(R.id.ColorText3)).setText(colors.getColor(ColorBundle.COLOR3).getHexaString());
    }

    public ColorBundle getColors() {
        return colors;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // ---------------------
    // Navigation drawer menu (=content)
    // ---------------------
    @SuppressLint("InflateParams")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.saved_color_button:
                Intent intent = new Intent(getApplicationContext(), SavedColorActivity.class);
                startActivity(intent);
                break;
            case R.id.credits:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(R.string.credits);
                alertDialog.setView(getLayoutInflater().inflate(R.layout.credit_alert, null));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
        }
        return true;
    }

    // ---------------------
    // Navigation Drawer configuration
    // ---------------------
    private void configureNavigationView(){
        //------------ Navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void configureDrawerLayout(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    // ---------------------
    // Handling screen rotation
    // ---------------------
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(COLORS, colors);
        outState.putBoolean(BUNDLE_SAVED, current_bundle_is_saved);
        super.onSaveInstanceState(outState);
    }
}

