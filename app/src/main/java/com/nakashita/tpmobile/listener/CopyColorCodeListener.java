package com.nakashita.tpmobile.listener;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.nakashita.tpmobile.activity.MainActivity;
import com.nakashita.tpmobile.modele.Color;

public class CopyColorCodeListener implements View.OnLongClickListener {

    private Color color;
    private MainActivity activity;
    private int colorIdent;


    public CopyColorCodeListener(int colorIdent, MainActivity activity) {
        this.color = activity.getColors().getColor(colorIdent);
        this.activity = activity;
        this.colorIdent = colorIdent;

    }


    @Override
    public boolean onLongClick(View v) {
        //When longClick, the current color code is saved into the clipboard.
        update();

        if(color != null) {
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip;
            if (color.getDisplayState() == Color.HEXA) {
                clip = ClipData.newPlainText("Color", color.getHexaString());
                Toast.makeText(activity.getApplicationContext(), "Hex code copied to clipboard", Toast.LENGTH_SHORT).show();

            } else {
                clip = ClipData.newPlainText("Color", color.getRgbString());
                Toast.makeText(activity.getApplicationContext(), "RGB code copied to clipboard", Toast.LENGTH_SHORT).show();

            }
            clipboard.setPrimaryClip(clip);
        } else {
            Toast.makeText(activity.getApplicationContext(), "You have to tap on generate", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void update(){
        color = activity.getColors().getColor(colorIdent);
    }
}
