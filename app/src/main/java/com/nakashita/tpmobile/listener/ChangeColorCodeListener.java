package com.nakashita.tpmobile.listener;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nakashita.tpmobile.activity.MainActivity;
import com.nakashita.tpmobile.modele.Color;

public class ChangeColorCodeListener implements View.OnClickListener {

    private Color color;
    private MainActivity activity;
    private int itemId;
    private int colorIdent;

    public ChangeColorCodeListener(int colorIdent, MainActivity activity, int itemId) {
        this.activity = activity;
        this.color = activity.getColors().getColor(colorIdent);
        this.itemId = itemId;
        this.colorIdent = colorIdent;
    }

    @Override
    public void onClick(View v) {
        //When the user clicks, the current color text changes (hexa or rgb)

        update();

        if(color !=null){
            if(color.getDisplayState() == Color.HEXA){
                ((TextView) activity.findViewById(itemId)).setText(color.getRgbString());
            } else {
                ((TextView) activity.findViewById(itemId)).setText(color.getHexaString());
            }
        } else {
            Toast.makeText(activity.getApplicationContext(), "You have to tap on generate", Toast.LENGTH_SHORT).show();
        }
    }

    private void update(){
        color = activity.getColors().getColor(colorIdent);
    }

}
