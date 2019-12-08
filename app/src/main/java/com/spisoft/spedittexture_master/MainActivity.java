package com.spisoft.spedittexture_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        easyTextEditor.setHint("Hint").setUses(MainActivity.this, true, true);
        easyTextEditor.setEnable(true);

        final EasyTextEditor easyTextEditor2 = findViewById(R.id.bet2);
        easyTextEditor2.setBackgroundResource(R.drawable.background_button_shape_2);
        easyTextEditor2.setTextColor(R.color.colorPrimary);
        Typeface TF_Tahoma = Typeface.createFromAsset(getBaseContext() .getAssets(), "tahoma.ttf" + "");

        easyTextEditor0.setNextFocus(easyTextEditor)
                .setUses(MainActivity.this, true, true)
                .setInfo(getResources().getString(R.string.app_name))
                .setHint("hint")
                .setTypeFace(TF_Tahoma)
                .setMode(EasyTextEditor.startMode.Typing)
                .setList(new String[] { "Android List View", "Adapter implementation"});

//                .SetUses(MainActivity.this, false, false);
        easyTextEditor.setNextFocus(easyTextEditor2)
                .setTextColor(Color.RED)
                .setMode(EasyTextEditor.startMode.Voice);

        easyTextEditor2.setOnActionClickListener(new EasyTextEditor.OnEditorActionListener() {
            @Override
            public void onEvent() {
                easyTextEditor2.setTextColor(Color.GREEN);
            }
        });

        final LinearLayout LlyPrice = findViewById(R.id.llyPrice);
        final LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LlyPrice.removeAllViews();

        final View vi = inflater.inflate(R.layout.view_kind_price, null);
        vi.setBackgroundResource(R.drawable.background_button_shape_3);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LlyPrice.addView(vi, layoutParams);
        TextView TvTitle_BP = vi.findViewById(R.id.tvTitle);
        TextView TvPercent_BP = vi.findViewById(R.id.tvPercent);
        final EasyTextEditor EtTitle_BP = vi.findViewById(R.id.etPrice);
        TvTitle_BP.setText("lkjlkj");
        TvPercent_BP.setText("++");
        EtTitle_BP.setText("123456789");

    }
}
