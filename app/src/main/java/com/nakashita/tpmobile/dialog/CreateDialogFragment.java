package com.nakashita.tpmobile.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nakashita.tpmobile.R;
import com.nakashita.tpmobile.activity.SavedColorActivity;
import com.nakashita.tpmobile.modele.Color;
import com.nakashita.tpmobile.modele.ColorBundle;
import com.nakashita.tpmobile.storage.ColorsJsonFileStorage;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;


public class CreateDialogFragment extends DialogFragment {

    private View view;
    private Updatable updatable;

    public CreateDialogFragment(Updatable updatable){
        this.updatable = updatable;
    }

    //Used when destroy by screen rotation savedInstanceState
    public CreateDialogFragment(){
        this.updatable = null;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // ---------------------
        // Handling screen rotation
        // ---------------------
        if(savedInstanceState != null){
            updatable = (SavedColorActivity) getActivity();
        }

        view = requireActivity().getLayoutInflater().inflate(R.layout.create_colors_dialog, null);

        //Using the ColorPickerView library, more information at : https://github.com/skydoves/ColorPickerView
        // ---------------------
        // ColorPickers initialisation
        // ---------------------
        ColorPickerView colorPickerView1 = view.findViewById(R.id.colorPicker1);
        colorPickerView1.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                view.findViewById(R.id.previewColor1).setBackgroundColor(color);
            }
        });
        ColorPickerView colorPickerView2 = view.findViewById(R.id.colorPicker2);
        colorPickerView2.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                view.findViewById(R.id.previewColor2).setBackgroundColor(color);
            }
        });
        ColorPickerView colorPickerView3 = view.findViewById(R.id.colorPicker3);
        colorPickerView3.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                view.findViewById(R.id.previewColor3).setBackgroundColor(color);
            }
        });


        // ---------------------
        // ColorPickers / brightnessSlider binding
        // ---------------------
        colorPickerView1.attachBrightnessSlider((BrightnessSlideBar) (view.findViewById(R.id.brightnessSlide1)));
        colorPickerView2.attachBrightnessSlider((BrightnessSlideBar) (view.findViewById(R.id.brightnessSlide2)));
        colorPickerView3.attachBrightnessSlider((BrightnessSlideBar) (view.findViewById(R.id.brightnessSlide3)));

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.create_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.dialog_create_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Get the colorPicker generated colors from the view (getBackground)
                        //Color instances creation the colorPicker values
                        Color color1 = new Color(((ColorDrawable) view.findViewById(R.id.previewColor1).getBackground()).getColor());
                        Color color2 = new Color(((ColorDrawable) view.findViewById(R.id.previewColor2).getBackground()).getColor());
                        Color color3 = new Color(((ColorDrawable) view.findViewById(R.id.previewColor3).getBackground()).getColor());

                        String label = ((EditText) view.findViewById(R.id.createLabel)).getText().toString();

                        ColorBundle colors = new ColorBundle(label, color1, color2, color3);

                        ColorsJsonFileStorage.get(getContext()).insert(colors);
                        updatable.update();
                        Toast.makeText(getContext(), "This color bundle has been saved", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton(R.string.dialog_cancel_button, null).create();
    }
}
