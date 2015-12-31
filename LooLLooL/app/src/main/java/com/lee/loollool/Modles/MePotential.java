package com.lee.loollool.Modles;

import android.widget.TextView;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


public class MePotential extends MeModule {
	static String devName = "potentiometer";
	public MePotential(int port, int slot) {
		super(devName, MeModule.DEV_POTENTIALMETER, port, slot);
		// TODO Auto-generated constructor stubpotentiometer_111.png
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.potentiometer_other;
	}
	
	public MePotential(JSONObject jobj) {
		super(jobj);
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.potentiometer_other;
	}

	public String getScriptRun(String var){
		varReg = var;
		String code = var+" = potentiometer("+getPortString(port)+")\n";
		return code;
	}
	
	public byte[] getQuery(int index){
		byte[] query = buildQuery(type, port, slot, index);
		return query;
	}
	
	public void setEchoValue(String value){
		TextView txt =  (TextView)view.findViewById(R.id.textValue);
		txt.setText(value);
		return;
	}
}
