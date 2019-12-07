package com.spisoft.spedittexture_master;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;

import com.spisoft.spedittexture.EasyTextEditor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyTextEditor easyTextEditor0 = findViewById(R.id.bet0);
        EasyTextEditor easyTextEditor = findViewById(R.id.bet);
//        easyTextEditor.setPadding(R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20);
        easyTextEditor.setInfo("cc").setBackgroundResource(R.drawable.background_button_shape_2);
        easyTextEditor.setHint("Hint");

        final EasyTextEditor easyTextEditor2 = findViewById(R.id.bet2);
        easyTextEditor2.setBackgroundResource(R.drawable.background_button_shape_2);
        Typeface TF_Tahoma = Typeface.createFromAsset(getBaseContext() .getAssets(), "tahoma.ttf" + "");

        easyTextEditor0.setNextFocus(easyTextEditor)
                .setUses(MainActivity.this, false, false)
                .setInfo(getResources().getString(R.string.app_name))
                .setHint("hint")
                .setTypeFace(TF_Tahoma)
                .setMode(EasyTextEditor.startMode.Typing)
                .setList(new String[] { "Android List View", "Adapter implementation"});

//                .SetUses(MainActivity.this, false, false);
        easyTextEditor.setNextFocus(easyTextEditor2).setMode(EasyTextEditor.startMode.Voice);
    }
}
