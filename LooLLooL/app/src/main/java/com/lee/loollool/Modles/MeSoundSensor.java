package com.lee.loollool.Modles;

import android.widget.TextView;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


public class MeSoundSensor extends MeModule {
	static String devName = "soundsensor";
	public MeSoundSensor(int port, int slot) {
		super(devName, MeModule.DEV_SOUNDSENSOR, port, slot);
		// TODO Auto-generated constructor stub
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.soundsensor_other;
	}
	
	public MeSoundSensor(JSONObject jobj) {
		super(jobj);
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.soundsensor_other;
	}

	public String getScriptRun(String var){
		varReg = var;
		String code = var+" = soundsensor("+getPortString(port)+")\n";
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
