package com.spisoft.spedittexture;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.github.johnkil.print.PrintView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class TextViewEditor extends RelativeLayout implements RecognitionListener {

    public static String ResultQrCode = "ResultQrCode";
    public static Typeface TF_Holo ;
    public static DecimalFormat dcFormat = new DecimalFormat("#,###");
    private InputMethodManager imm;
    private boolean SpeechStatus = false;
    private SpeechRecognizer speechRecognizer;
    private View rootView;
    private ProgressBar circleProgress;
    private EditText MText;
    private TextView MCnt;
    private ImageView MBtn;
    private PrintView MBtnVoice;
    private ImageView MBtnQrCode;
    private Context mContext;
    private int REQ_CODE_QRCODE = 107;
    private boolean UseSpeechToText = false , UseBarcodeScanner = false;
    private RelativeLayout ViewBase, ViewBaseText;
    private TextView MTHint;
    private EditText MTv;
    private View MiAbout, MiOptional;
    private int inputType;
    private int imeOptions;
    private int Index;
    private AlertDialog AlertDialogText;
    private String MInfo = null;
    private ListView listView;
    private ArrayAdapter<String> listAdapter;
    private String[] MListItems;
    private int MMode;
    private Typeface MTypeFace = null;
    private float MTextSize, ZTextSize;
    private int MTextColor = -1;
    private int MLayoutDirection;
    private boolean MThousandSP = false;
    OnEditorActionListener mActionListener;
    OnChangeTextListener mListener;
    OnClickListener mPlusListener;
    private boolean MMultiLine;
    private boolean MEnabled = true;
    private boolean MOnFocusStart = false;
    private int MNextFocusDownId;
    private String MHint;
    private boolean MOptional;
    private String MNextText, MPrevText;
    private View vNext, vPrev;
    private TextView MNextBtn, MPrevBtn;
    private RelativeLayout RlyBottom;
    private boolean ThousandsSeparator;
    private boolean MBtnShow;
    private int MBtnIcon;
    private RelativeLayout RlyText;

    public TextViewEditor(Context context) {
        super(context);
        init(context, null);
    }

    public TextViewEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TextViewEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(final Context context, AttributeSet attrs){

        mContext = context;
        TF_Holo = Typeface.createFromAsset(context.getAssets(), "holo-icon-font.ttf" + "");
        imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        this.setBackgroundColor(Color.TRANSPARENT);

        rootView = inflate(context, R.layout.sp_base_view, this);

        ViewBaseText = rootView.findViewById(R.id.viewBaseText);
        MTv = rootView.findViewById(R.id.mTextView);
//        MTv.setPaintFlags(MTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        SpannableString content = new SpannableString("Content");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        MTv.setText(content);

        MTv.setId(View.generateViewId());

        MTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenEditorDialog(context);
            }
        });

        MTv.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(MOnFocusStart && hasFocus) OpenEditorDialog(context);
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenEditorDialog(context);
            }
        });

        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyTextEditor, 0, 0);

            MMode = typedArray.getInt(R.styleable.EasyTextEditor_StartMode, 0);
            MMultiLine = typedArray.getBoolean(R.styleable.EasyTextEditor_MultiLine,false);
            ThousandsSeparator = typedArray.getBoolean(R.styleable.EasyTextEditor_ThousandsSeparator,false);
            inputType = typedArray.getInt(R.styleable.EasyTextEditor_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
            imeOptions = typedArray.getInt(R.styleable.EasyTextEditor_android_imeOptions, 0);
            MTv.setGravity(typedArray.getInt(R.styleable.EasyTextEditor_android_gravity, Gravity.NO_GRAVITY));
//            MTv.typedArray.getInt(R.styleable.EasyTextEditor_android_layout_gravity, Gravity.NO_GRAVITY);
//            MTypeFace = typedArray.getValue(R.styleable.EasyTextEditor_android_typeface,Typeface.NORMAL);
            MTextSize = typedArray.getDimension(R.styleable.EasyTextEditor_android_textSize, 17f);
            ZTextSize = typedArray.getDimension(R.styleable.EasyTextEditor_ZTextSize, 1);
            MTextColor = typedArray.getColor(R.styleable.EasyTextEditor_android_textColor, Color.BLACK);
            MLayoutDirection = typedArray.getInt(R.styleable.EasyTextEditor_android_layoutDirection, LayoutDirection.LOCALE);
            MNextFocusDownId = typedArray.getInt(R.styleable.EasyTextEditor_android_nextFocusDown, 0);
            MHint = typedArray.getString(R.styleable.EasyTextEditor_Hint);
//            MBackground = typedArray.getInt(R.styleable.EasyTextEditor_android_layoutDirection, LayoutDirection.LOCALE);
            typedArray.recycle();
        }

        if(MMultiLine) MTv.setSingleLine(false);
        else MTv.setSingleLine(true);
        if(MHint != null) MTv.setHint(MHint);
        if(MNextFocusDownId > 0) vNext = this.rootView.findViewById(MNextFocusDownId);
        MEnabled = this.isEnabled();
        MTv.setEnabled(MEnabled);
        if(MTypeFace != null) MTv.setTypeface(MTypeFace);
        MTv.setTextSize(MTextSize);
        MTv.setTextColor(MTextColor);
        ViewBaseText.setLayoutDirection(MLayoutDirection);
//        if(MThousandSP) MTv.setText(doubleToDecimal());
//        RelativeLayout RlyControl = findViewById(R.id.rlyControl);
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        final View rootView2 = layoutInflater.inflate(R.layout.sp_control, RlyControl);
//        SetViewDialog2(context, rootView2);

        MiOptional = rootView.findViewById(R.id.icOptional);
        MiAbout = rootView.findViewById(R.id.icAbout);
        MiAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, MInfo, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OpenEditorDialog(Context context) {
        if(MEnabled) {
            dialogEditText(context, null, null, null);
            AlertDialogText.show();
            if(!MOptional)
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    //TODO: --------------------------------------------- Save & Restore State -----------------------------------------------
    private static class SavedState extends BaseSavedState {
        String text;
        String tag;
        int index;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            text = in.readString();
            tag = in.readString();
            index = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(text);
            out.writeString(tag);
            out.writeInt(index);
        }

        public static final Creator<SavedState> CREATOR = new Creator<TextViewEditor.SavedState>() {
            public TextViewEditor.SavedState createFromParcel(Parcel in) {
                return new TextViewEditor.SavedState(in);
            }

            public TextViewEditor.SavedState[] newArray(int size) {
                return new TextViewEditor.SavedState[size];
            }
        };
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState myState = new SavedState(superState);
        myState.text = this.MTv.getText().toString();
//        myState.tag = this.MTv.getTag().toString();
//        myState.index = this.Index;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.MTv.setText(savedState.text);
    }

    //TODO: ---------------------------------------- Speech to text attribute -----------------------------------------
    @Override
    public void onReadyForSpeech(Bundle params) {
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        circleProgress.setProgress(Math.round(rmsdB)*8+20);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
    }

    @Override
    public void onEndOfSpeech() {
        MBtnVoice.setIconColor(Color.GRAY);
        SpeechStatus = false;
        circleProgress.setVisibility(View.GONE);
    }

    @Override
    public void onError(int error) {
        circleProgress.setVisibility(View.GONE);
        MBtnVoice.setIconColor(Color.GRAY);
    }

    @Override
    public void onResults(Bundle results) {
        MBtnVoice.setIconColor(Color.GRAY);
        SpeechStatus = false;
        circleProgress.setVisibility(View.GONE);
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        MText.setText(matches.get(0));
        MText.setSelection(matches.get(0).length());
//        MBtn.callOnClick();
        VerifyNext(imeOptions);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
//.20tText(matches.get(0));
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }

    //TODO: -------------------------------------------- Function Attributes ----------------------------------------------------

    public TextViewEditor setUses(Activity activity, boolean useBarcodeScanner, boolean useSpeechToText){
        UseSpeechToText = useSpeechToText;
        UseBarcodeScanner = useBarcodeScanner;

        GetPermission(activity);

        return this;
    }

    public interface OnPlusClickListener {
        void onEvent();
    }

    public void setOnPlusClickListener(OnClickListener eventListener) {
        mPlusListener = eventListener;
    }

    public interface OnChangeTextListener {
        void onEvent();
    }

    public void addTextChangedListener(OnChangeTextListener eventListener) {
        mListener = eventListener;
    }

    public interface OnEditorActionListener {
        void onEvent();
    }

    public void setOnActionClickListener(OnEditorActionListener eventListener) {
        mActionListener = eventListener;
    }

    public TextViewEditor buttonPlusView(boolean b, int icon){
        MBtnIcon = icon;
        MBtnShow = b;
        return this;
    }

    public String getText(){
        return MTv.getText().toString();
    }

    public TextViewEditor setText(String text){
        MTv.setText(text);
        return this;
    }

    public TextViewEditor setHint(String hint){
        MHint = hint;
        MTv.setHint(hint);
        return this;
    }

    public String getHint(){
        return MHint;
    }

    public TextViewEditor setPrevFocus(View prevFocusView, String prevText){
        vPrev = prevFocusView;
        MPrevText = prevText;
        return this;
    }

    public TextViewEditor setNextFocus(View nextFocusView, String nextText){
        vNext = nextFocusView;
        MNextText = nextText;
        imeOptions = EditorInfo.IME_ACTION_NEXT;
        return this;
    }

    public TextViewEditor setList(String[] listItems){
        MListItems = listItems;
        return this;
    }

    public TextViewEditor setTypeFace(Typeface typeFace){
        MTypeFace = typeFace;
        MTv.setTypeface(MTypeFace);
        return this;
    }

    public TextViewEditor setTextColor(int color){
        MTextColor = color;
        MTv.setTextColor(MTextColor);
        return this;
    }

    public TextViewEditor setThousandSP(boolean thousandSP){
        ThousandsSeparator = thousandSP;
        return this;
    }

//    public EasyTextEditor setTextSize(float textSize){
//        MTextSize = textSize;
//        return this;
//    }

    public TextViewEditor setInfo(String about){
        MInfo = about;
        if(MInfo != null) {
            MiAbout.setVisibility(VISIBLE);
        }
        return this;
    }

    public TextViewEditor setMultiLine(boolean multiLine){
        MMultiLine = multiLine;
        return this;
    }

    public TextViewEditor setOptional(boolean optional){
        MOptional = optional;
        if(MOptional) {
            MiOptional.setVisibility(VISIBLE);
            MMode = 3;
        }
        return this;
    }

    public TextViewEditor setEnable(boolean enabled){
        MEnabled = enabled;
        MTv.setEnabled(MEnabled);
        return this;
    }


    public TextViewEditor setOnFocusStart(boolean onFocusStart){
        MOnFocusStart = onFocusStart;
//        MTv.setEnabled(MEnabled);
        return this;
    }

    public enum startMode { Typing, Voice, BarcodeReader, SelectItem }

    public TextViewEditor setMode(startMode mode){
        MMode = mode.ordinal();
        switch (MMode){
            case 0:
                break;
            case 1:
                UseSpeechToText = true;
                break;
            case 2:
                UseBarcodeScanner = true;
                break;
        }
        return this;
    }

//    public static final int MODE_1 = 1;
    //TODO: --------------------------------------- Activity Result ----------------------------------------------------

    private static void myStartActivityForResult(FragmentActivity act, Intent in, int requestCode, OnActivityResult cb) {
        Fragment aux = new FragmentForResult(cb);
        FragmentManager fm = act.getSupportFragmentManager();
        fm.beginTransaction().add(aux, "FRAGMENT_TAG").commit();
        fm.executePendingTransactions();
        aux.startActivityForResult(in, requestCode);
    }

    private interface OnActivityResult {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @SuppressLint("ValidFragment")
    private static class FragmentForResult extends Fragment {
        private OnActivityResult cb;
        public FragmentForResult(OnActivityResult cb) {
            this.cb = cb;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (cb != null) cb.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }
    }
    //TODO: ------------------------------------------- Create Dialog -----------------------------------------------------------

    private void dialogEditText(final Context context, final List<String> myList, final TextView txtSel, final TextView SubTxtSel){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final AlertDialog.Builder alertDialogText = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        final View rootView = layoutInflater.inflate(R.layout.sp_dialog, null);
        alertDialogText.setView(rootView);

        SetViewDialog(context, rootView);

        AlertDialogText = alertDialogText.setCancelable(true).create();
        AlertDialogText.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    AlertDialogText.dismiss();
                }
                if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK){
                    promptSpeechInput(mContext);
                    return true;
                }
                return false;
            }
        });

        AlertDialogText.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        AlertDialogText.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                switch (MMode){
                    case 0:
                        break;
                    case 1:
                        MBtnVoice.callOnClick();
                        break;
                    case 3:
//                        CloseSoftKey();
                        imm.hideSoftInputFromWindow(MText.getWindowToken(), 0);
                        break;
                }
            }
        });

        AlertDialogText.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        AlertDialogText.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //TODO : SET DIALOG POSITION
//        Window window = AlertDialogSearch.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.TOP;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);

    }

    private void SetViewDialog(final Context context, View rootView) {
        ViewBase = rootView.findViewById(R.id.viewBase);
        circleProgress = rootView.findViewById(R.id.cProgress);
        MCnt = rootView.findViewById(R.id.mCnt);
        MBtn = rootView.findViewById(R.id.iSearch);
        MBtnVoice = rootView.findViewById(R.id.iVoice);
        MBtnQrCode = rootView.findViewById(R.id.iQr);
        MTHint = rootView.findViewById(R.id.tHint);
        RlyText = rootView.findViewById(R.id.lyText);
        RlyBottom = rootView.findViewById(R.id.rlyBottom);
        if(MBtnShow) MBtn.setVisibility(VISIBLE);
        if(MBtnIcon > 0) MBtn.setImageResource(MBtnIcon);

        if(vNext != null) {
            MNextBtn = rootView.findViewById(R.id.btnNext);
            MNextBtn.setVisibility(VISIBLE);
            if (MNextText != null) MNextBtn.setText(MNextText);
            MNextBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) { VerifyNext(imeOptions); }
            });
        }
        if(vPrev != null) {
            MPrevBtn = rootView.findViewById(R.id.btnPrev);
            MPrevBtn.setVisibility(VISIBLE);
            if (MPrevText != null) MPrevBtn.setText(MPrevText);
            MPrevBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) { VerifyPrev(); }
            });
        }

        RlyBottom.setLayoutDirection(MLayoutDirection);
        if(MTv.getHint() != null) MTHint.setText(MTv.getHint().toString());

        MText = rootView.findViewById(R.id.mText);
        MText.setText(MTv.getText().toString());
        MText.setSelection(MTv.getText().toString().length());
        if(MTypeFace != null) MText.setTypeface(MTypeFace);
        if(MMultiLine) MText.setSingleLine(false);
        else MText.setSingleLine(true);
        if(MTypeFace != null) MText.setTypeface(MTypeFace);
        MText.setTextSize(MTextSize*ZTextSize);
        if(MOptional){
            MText.setVisibility(GONE);
            UseSpeechToText = false;
            UseBarcodeScanner = false;
        }

        ViewBase.setLayoutDirection(MLayoutDirection);

        listView = rootView.findViewById(R.id.list);

        if(UseSpeechToText) MBtnVoice.setVisibility(VISIBLE);
        else MBtnVoice.setVisibility(GONE);

        if(UseBarcodeScanner) MBtnQrCode.setVisibility(VISIBLE);
        else MBtnQrCode.setVisibility(GONE);

        if(MListItems != null && MListItems.length > 0) {
            listView.setVisibility(VISIBLE);
            listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, MListItems);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemValue = (String) listView.getItemAtPosition(position);
                    MText.setText(itemValue);
                    VerifyNext(imeOptions);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemValue = (String) listView.getItemAtPosition(position);
                    MText.setText(itemValue);
                    AlertDialogText.dismiss();
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    return true;
                }
            });
        }

        //---------------------
//        MBtn.setIconFont(TF_Holo);
        MBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                AlertDialogText.hide();
                if(mPlusListener != null) mPlusListener.onClick(v);
            }
        });

        MBtnVoice.setIconFont(TF_Holo);

        MBtnVoice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(context);
            }
        });

//        MBtn.setVisibility(GONE);
        MText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                VerifyNext(actionId);
                if(mActionListener != null) mActionListener.onEvent();
                else if(MBtnShow) MBtn.callOnClick();
                return false;
            }
        });

        MText.setInputType(inputType);
        MText.setImeOptions(imeOptions | EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        if(inputType == InputType.TYPE_CLASS_NUMBER) RlyText.setLayoutDirection(LAYOUT_DIRECTION_LTR);

        MText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                eAddPrice.addTextChangedListener(new GlobalFncCls.NumberTextWatcherForThousand(eAddPrice));
                MTv.setText(s);
                ListFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mListener!=null)
                    mListener.onEvent();

                if(ThousandsSeparator) {
                    try {
                        MText.removeTextChangedListener(this);
                        String value = MText.getText().toString();

                        if (value != null && !value.equals("")) {
                            if (value.startsWith(".")) {
                                MText.setText("0.");
                            }
                            if (value.startsWith("0") && !value.startsWith("0.")) {
                                MText.setText("");
                            }
                            String str = MText.getText().toString().replaceAll(",", "");
                            if (!value.equals(""))
                                MText.setText(getDecimalFormattedString(str));
                            MText.setSelection(MText.getText().toString().length());
                        }
                        MTv.setText(MText.getText());
                        MText.addTextChangedListener(this);
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        MText.addTextChangedListener(this);
                    }
                }
            }
        });

        MBtnQrCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Intent intentQR = new Intent(context, QrCodeActivity.class);
                myStartActivityForResult((FragmentActivity) getContext(), intentQR, REQ_CODE_QRCODE, new OnActivityResult() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        if(data != null) MText.setText(data.getStringExtra(ResultQrCode));
                    }
                });
            }
        });

    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }

    private void ListFilter(String mText) {
        if(listView.getVisibility() == View.VISIBLE) {
            listAdapter.getFilter().filter(mText);
            listAdapter.notifyDataSetChanged();
        }
    }

    private void VerifyNext(int actionId) {
        AlertDialogText.dismiss();
        if(actionId == EditorInfo.IME_ACTION_NEXT){
            if(vNext != null) {
                imm.hideSoftInputFromWindow(MText.getWindowToken(), 0);
                if (vNext instanceof TextViewEditor) vNext.callOnClick();
                else vNext.requestFocus();
            }
        }
    }

    private void VerifyPrev() {
        AlertDialogText.dismiss();
            if(vPrev != null) {
                if (vPrev instanceof TextViewEditor) vPrev.callOnClick();
                else vPrev.requestFocus();
            }
    }

    //TODO : ----------------------------------------- Get Permission -------------------------------------------

    private void GetPermission(final Activity activity){
        String pCAMERA = Manifest.permission.CAMERA;
        String pRECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
        Collection<String> ListPermission = new ArrayList<>();
        if(UseBarcodeScanner) ListPermission.add(pCAMERA);
        if(UseSpeechToText) ListPermission.add(pRECORD_AUDIO);

        Dexter.withActivity(activity)
                .withPermissions(
                        ListPermission
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                List<PermissionDeniedResponse> ListDenied = report.getDeniedPermissionResponses();
                for(int i = 0; i < ListDenied.size(); i++){
                    if(ListDenied.get(i).getPermissionName().toUpperCase().contains("CAMERA"))
                        MBtnQrCode.setVisibility(GONE);
                    if(ListDenied.get(i).getPermissionName().toUpperCase().contains("RECORD_AUDIO"))
                        MBtnVoice.setVisibility(GONE);
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                for(int i = 0; i < permissions.size(); i++){
                    if(permissions.get(i).getName().toUpperCase().contains("CAMERA"))
                        if(UseSpeechToText) {
                            Toast.makeText(activity,"CAMERA ACCESS DENIED !",Toast.LENGTH_SHORT).show();
                        }
                    MBtnQrCode.setVisibility(GONE);
                    if(permissions.get(i).getName().toUpperCase().contains("RECORD_AUDIO"))
                        if(UseBarcodeScanner) {
                            Toast.makeText(activity,"AUDIO ACCESS DENIED !",Toast.LENGTH_SHORT).show();
                        }
                    MBtnVoice.setVisibility(GONE);
                }
            }
        }).check();
    }

    //TODO: --------------------------------------- speech to text ----------------------------------------

    private void promptSpeechInput(Context context) {
        imm.hideSoftInputFromWindow(MText.getWindowToken(), 0);
        if(SpeechStatus) {
            MBtnVoice.setIconColor(Color.GRAY);
            circleProgress.setVisibility(View.GONE);
            speechRecognizer.stopListening();
        }else {
            MBtnVoice.setIconColor(Color.RED);
            circleProgress.setVisibility(View.VISIBLE);
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            speechRecognizer.setRecognitionListener(this);
            Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS ,true);
            speechRecognizer.startListening(speechIntent);
        }
    }

    private static String doubleToDecimal(double mValue){
        dcFormat = new DecimalFormat("#,###");
        return arabicToDecimal(String.valueOf(dcFormat.format(mValue)));
    }

    private static String arabicToDecimal(String number) {

        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            else if (ch == 0x066C )
                ch = ',';
            chars[i] = ch;
        }
        return new String(chars);
    }
}
