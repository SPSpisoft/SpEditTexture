package com.spisoft.spedittexture_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spisoft.spedittexture.EasyTextEditor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText VT = findViewById(R.id.vt);
        EasyTextEditor easyTextEditor0 = findViewById(R.id.bet0);
        final EasyTextEditor easyTextEditor = findViewById(R.id.bet);
//        easyTextEditor.setPadding(R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20,R.dimen.sps_lpr_sz_20);
        easyTextEditor
                .setHint("Hint").setUses(MainActivity.this, true, true)
                .setDialogMode(false)
                .setBackgroundResource(R.drawable.background_button_shape_2);
//        easyTextEditor.setOnImeClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
//            }
//        });

        easyTextEditor.setOnImeClickListener(new EasyTextEditor.OnImeClickListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
            }
        });
//        easyTextEditor
//                .setNextFocus(easyTextEditor0, "بعدی");
        easyTextEditor.setEnable(true);

        final EasyTextEditor easyTextEditor2 = findViewById(R.id.bet2);
        easyTextEditor2
                .setUses(MainActivity.this, true, true)
                .setDialogMode(true)
                .setOnFocusStart(true).setBackgroundResource(R.drawable.background_button_shape_2);
        easyTextEditor2.setTextColor(R.color.colorPrimary).setOnFocusStart(true);
        Typeface TF_Tahoma = Typeface.createFromAsset(getBaseContext() .getAssets(), "tahoma.ttf" + "");

        easyTextEditor0.setNextFocus(easyTextEditor2, "بعدی")
                .setPrevFocus(easyTextEditor, "قبلی")
                .setUses(MainActivity.this, true, true)
                .setInfo("TEXT2")
                .setHint("لیستی")
                .setOptional(true)
                .setTypeFace(TF_Tahoma)
                .setList(new String[] { "Android List View", "Adapter implementation"});


        easyTextEditor0.buttonPlusView(true, R.drawable.ic_camera_enhance_black_24dp).setOnPlusClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"mmm",Toast.LENGTH_SHORT).show();
            }
        });
//        vv.setImageResource(R.drawable.ic_camera_enhance_black_24dp);


//                .SetUses(MainActivity.this, false, false);
        easyTextEditor.setNextFocus(easyTextEditor0, "بعدی")
                .setPrevFocus(easyTextEditor0, easyTextEditor0.getHint())
                .setTextColor(Color.RED)
                .setThousandSP(true)
                .setMode(EasyTextEditor.startMode.Typing);

        easyTextEditor.buttonPlusView(true, R.drawable.ic_barcode_black_24dp).setOnPlusClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"kkjkjkj",Toast.LENGTH_SHORT).show();
            }
        });

        easyTextEditor2.setOnActionClickListener(new EasyTextEditor.OnEditorActionListener() {
            @Override
            public void onEvent() {
                easyTextEditor2.setTextColor(Color.GREEN);
            }
        });

        final LinearLayout LlyPrice = findViewById(R.id.llyPrice);
        final LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LlyPrice.removeAllViews();

        for (int i = 0; i < 4; i++) {
            final View vi = inflater.inflate(R.layout.view_kind_price, null);
            vi.setBackgroundResource(R.drawable.background_button_shape_3);
            final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LlyPrice.addView(vi, layoutParams);
            TextView TvTitle_BP = vi.findViewById(R.id.tvTitle);
            TextView TvPercent_BP = vi.findViewById(R.id.tvPercent);
            final EasyTextEditor EtTitle_BP = vi.findViewById(R.id.etPrice);
            TvTitle_BP.setText("AA"+i);
            TvPercent_BP.setText("++");
            EtTitle_BP.setText(String.valueOf(123*i));
            EtTitle_BP.setThousandSP(true);
        }

    }
}
