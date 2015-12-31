package com.lee.loollool.Modles;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


/**
 * Created by Administrator on 2015/11/24 0024.
 */
public class MeLCD extends MeModule implements TextWatcher, AdapterView.OnItemSelectedListener {

    static String devName = "lcd";
    private EditText ed_1;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner;
    private Context context;
    private byte len;
    private byte lin;
    private byte[] data;
    private int lcdlen;


    final String arr[] = new String[]{
            "第一行",
            "第二行",
            "第三行",
            "第四行",
            "第五行",
            "第六行",

    };


    public MeLCD(Context context, int port, int slot) {
        super(devName, MeModule.DEV_LCD, port, slot);
        viewLayout = R.layout.dev_edit_lcdview;
        imageId = R.mipmap.xianshiping_other;
        this.context = context;
    }

    public MeLCD(JSONObject json) {
        super(json);
        viewLayout = R.layout.dev_edit_lcdview;
        imageId = R.mipmap.xianshiping_other;

    }

    @Override
    public void setDisable() {
        ed_1 = (EditText) view.findViewById(R.id.editTxt_1);
        ed_1.setEnabled(false);
        ed_1.removeTextChangedListener(this);
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        spinner.setEnabled(false);
        arrayAdapter = new ArrayAdapter<String>(context, R.layout.items_show, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(null);


    }

    @Override
    public void setEnable(Handler handler) {
        mHandler = handler;
        ed_1 = (EditText) view.findViewById(R.id.editTxt_1);
        ed_1.setEnabled(true);
        ed_1.addTextChangedListener(this);
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        spinner.setEnabled(true);
        arrayAdapter = new ArrayAdapter<String>(context, R.layout.items_show, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);

    }


    void sendNumber(int lcdlen, byte len, byte lin, byte[] dats) {
        if (lcdlen == 0) {
            lcdlen = 0;

        }
        if (len == 0) {
            len = 0;
        }
        if (lin == 0) {
            lin = 0;
        }
        if (dats.equals("")) {
            return;
        }
        //    byte[] wr = WriteLCD(lcdlen, len, lin, dats);
        mHandler.obtainMessage(MSG_VALUECHANGED, WriteLCD(lcdlen, len, lin, dats)).sendToTarget();
    }

    @Override
    public String getScriptRun(String var) {
        varReg = var;
        String code = "lcd(" + getPortString(port) + "," + var + ")\n";
        return code;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String num = s.toString();
        Log.d("ll", String.valueOf(data));
        byte[] numBytesbyet = num.getBytes();
        byte[] numbyte = new byte[4];
        numbyte[0] = 1;
        numbyte[1] = 2;
        numbyte[2] = 3;
        for (int i = 0; i < numBytesbyet.length; i++) {
            byte num1 = numBytesbyet[i];
            Log.d("lcd", String.valueOf(numbyte[1]));
            Log.d("lcd", String.valueOf(num1));


        }


    }


    public byte[] HasString2Bytes(String src) {
        byte[] ret = new byte[src.length()];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < src.length(); i++) {
            ret[i] = uniteBytes((byte) 0, tmp[i]);

        }
        return ret;
    }

    public byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String string = parent.getItemAtPosition(position).toString();
        if (string.contains("一")) {
            lin = 0x01;
        } else if (string.contains("二")) {
            lin = 0x02;
        } else if (string.contains("三")) {
            lin = 0x03;
        } else if (string.contains("四")) {
            lin = 0x04;
        } else if (string.contains("五")) {
            lin = 0x05;
        } else if (string.contains("六")) {
            lin = 0x06;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
