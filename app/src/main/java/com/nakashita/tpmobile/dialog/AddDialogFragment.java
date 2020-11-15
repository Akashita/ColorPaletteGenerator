package com.nakashita.tpmobile.dialog;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.modele.ColorBundle;
import com.nakashita.tpmobile.storage.ColorsJsonFileStorage;


public class AddDialogFragment extends DialogFragment {

    private final static String COLORS = "COLORS";

    private ColorBundle colors;
    private View view;

    public AddDialogFragment(ColorBundle colors) {
        this.colors = colors;
    }

    //Used when destroy by screen rotation savedInstanceState
    public AddDialogFragment() {
        this.colors = null;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // ---------------------
        // Handling screen rotation
        // ---------------------
        if(savedInstanceState != null){
            colors = (ColorBundle) savedInstanceState.getParcelable(COLORS);
        }

        view = requireActivity().getLayoutInflater().inflate(R.layout.save_colors_dialog, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.save_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                colors.setLabel(((EditText) view.findViewById(R.id.label)).getText().toString());
                ColorsJsonFileStorage.get(getContext()).insert(colors);
                Toast.makeText(getContext(), "This color bundle has been saved", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(R.string.dialog_negative_button, null).create();
    }


    // ---------------------
    // Handling screen rotation
    // ---------------------
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(COLORS, colors);
        super.onSaveInstanceState(outState);
    }
}