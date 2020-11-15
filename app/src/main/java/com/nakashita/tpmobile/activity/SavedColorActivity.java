package com.nakashita.tpmobile.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.adapter.BundleAdapter;
import com.nakashita.tpmobile.dialog.CreateDialogFragment;
import com.nakashita.tpmobile.dialog.DeleteDialogFragment;
import com.nakashita.tpmobile.dialog.Updatable;

import java.util.Objects;

public class SavedColorActivity extends AppCompatActivity implements Updatable {

    private RecyclerView list;

    public final static String EXTRA_BUNDLE = "EXTRA_BUNDLE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_color);

        list = (RecyclerView) findViewById(R.id.saved_recycler_view);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        // ---------------------
        // RecyclerView adapter
        // ---------------------
        list.setAdapter(new BundleAdapter(getApplicationContext()) {
            @Override
            public void onItemClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_BUNDLE, (Integer) v.getTag());
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(View v) {
                new DeleteDialogFragment(SavedColorActivity.this, (int) v.getTag()).show(getSupportFragmentManager(), "");
                return true;
            }
        });


        // ---------------------
        // Listener
        // ---------------------
        findViewById(R.id.fab_add_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateDialogFragment(SavedColorActivity.this).show(getSupportFragmentManager(), "");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // ---------------------
    // Update the recyclerview when data change
    // ---------------------
    public void update(){
        Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
        onResume();
    }
}


