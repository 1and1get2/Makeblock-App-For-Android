package com.lee.loollool.Modles;

import android.widget.TextView;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


public class MeTemperature extends MeModule {
	static String devName = "temperature";
	public MeTemperature(int port, int slot) {
		super(devName, MeModule.DEV_TEMPERATURE, port, slot);
		// TODO Auto-generated constructor stub
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.temperature_other;
		shouldSelectSlot = true;
	}
	public MeTemperature(JSONObject jobj) {
		super(jobj);
		viewLayout = R.layout.dev_value_view;
		imageId = R.mipmap.temperature_other;
		shouldSelectSlot = true;
	}
	

	public String getScriptRun(String var){
		varReg = var;
		String code = var+" = temperature("+getPortString(port)+","+getSlotString(slot)+")\n";
		return code;
	}
	
	public byte[] getQuery(int index){
		byte[] query = buildQuery(type, port, slot, index);
		return query;
	}
	
	public void setEchoValue(String value){
		TextView txt =  (TextView)view.findViewById(R.id.textValue);
		//double centi = ((double)Integer.parseInt(value)*0.0625);
		txt.setText(value+" ℃");
		return;
	}


}
