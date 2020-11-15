package com.nakashita.tpmobile.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.storage.ColorsJsonFileStorage;

public class DeleteDialogFragment extends DialogFragment {

    private final Updatable updatable;
    private final int id;

    public DeleteDialogFragment(Updatable updatable, int id) {
        this.updatable = updatable;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity()).setMessage(R.string.save_dialog_title).setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ColorsJsonFileStorage.get(getContext()).delete(DeleteDialogFragment.this.id);
                updatable.update();
                Toast.makeText(getContext(), "The bundle has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(R.string.dialog_negative_button, null).create();
    }
}