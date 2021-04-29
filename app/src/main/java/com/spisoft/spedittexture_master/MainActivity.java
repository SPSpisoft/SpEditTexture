package com.spisoft.spedittexture_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spisoft.spedittexture.EasyTextEditor;
import com.spisoft.spedittexture.TextureItem;

import java.util.ArrayList;
import java.util.List;

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
                .setHint("Hint").setUses(MainActivity.this, false, true)
                .setNextFocus(easyTextEditor0, "NExt")
                .setDialogMode(true)
                .setBtnOption(true)
                .addTextChangedListener(new EasyTextEditor.OnChangeTextListener() {
                    @Override
                    public void onEvent() {
                        Toast.makeText(MainActivity.this, ">> "+easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
                    }
                });

        easyTextEditor.setBtnOptionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "___00___ " ,Toast.LENGTH_SHORT).show();
            }
        });

        easyTextEditor.addTextChangedListenerMain(new EasyTextEditor.OnChangeTextListenerMain() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, "______ "+easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
            }
        });

//        easyTextEditor.setOnImeClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
//            }
//        });

        easyTextEditor.setOnImeClickListener(new EasyTextEditor.OnImeClickListener() {
            @Override
            public void onEvent() {
//                Toast.makeText(MainActivity.this, easyTextEditor.getText() ,Toast.LENGTH_SHORT).show();
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
        easyTextEditor2.setTextColor(R.color.colorPrimary).setOnFocusStart(true).setBtnOption(true);
        Typeface TF_Tahoma = Typeface.createFromAsset(getBaseContext() .getAssets(), "tahoma.ttf" + "");

        ArrayList<TextureItem> textureItems = new ArrayList<>();
        TextureItem textureItem1 = new TextureItem(1, null, "TEL", R.drawable.ic_camera_enhance_black_24dp);
        textureItems.add(textureItem1);
        TextureItem textureItem2 = new TextureItem(2, null, "MOB", R.drawable.ic_barcode_black_36dp);
        textureItems.add(textureItem2);
        TextureItem textureItem3 = new TextureItem(3, null, "FAX", R.drawable.ic_edit_location_grey_600_24dp);
        textureItems.add(textureItem3);
        easyTextEditor0.setNextFocus(easyTextEditor2, "بعدی")
                .setPrevFocus(easyTextEditor, "قبلی")
                .setUses(MainActivity.this, true, true)
                .setInfo("TEXT2")
                .setHint("لیستی")
                .setIcon(R.drawable.ic_barcode_black_18dp, true)
                .setOptional(true)
                .setTypeFace(TF_Tahoma)
                .setListItems(textureItems);
//                .setList(new String[] { "Android List View", "Adapter implementation"});

        easyTextEditor0.setOnSelectListItemListener(new EasyTextEditor.OnSelectListItemListener() {
            @Override
            public void onEvent(int code) {
                if(code == 1)
                    easyTextEditor2.setInputType(InputType.TYPE_CLASS_NUMBER);
                else if(code == 2)
                    easyTextEditor2.setInputType(InputType.TYPE_CLASS_TEXT);
                else
                    easyTextEditor2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

            }
        });
//        easyTextEditor0.buttonPlusView(true, R.drawable.ic_camera_enhance_black_24dp).setOnPlusClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"mmm",Toast.LENGTH_SHORT).show();
//            }
//        });
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

        easyTextEditor2.addTextChangedListener(new EasyTextEditor.OnChangeTextListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this,">>>>>>",Toast.LENGTH_SHORT).show();
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
