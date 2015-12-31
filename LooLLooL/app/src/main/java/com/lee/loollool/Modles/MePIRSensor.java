package com.lee.loollool.Modles;

import android.widget.ImageView;

import com.lee.loollool.bean.MeModule;

import org.json.JSONObject;

import activities.loollool.lee.com.loollool.R;


public class MePIRSensor extends MeModule {
	static String devName = "pirsensor";
	public MePIRSensor(int port, int slot) {
		super(devName, MeModule.DEV_PIRMOTION, port, slot);
		// TODO Auto-generated constructor stub
		viewLayout = R.layout.dev_switch_view;
		imageId = R.mipmap.pirmotion_other;
	}
	
	public MePIRSensor(JSONObject jobj) {
		super(jobj);
		viewLayout = R.layout.dev_switch_view;
		imageId = R.mipmap.pirmotion_other;
	}

	public String getScriptRun(String var){
		varReg = var;
		String code = var+" = pirsensor("+getPortString(port)+")\n";
		return code;
	}
	
	public byte[] getQuery(int index){
		byte[] query = buildQuery(type, port, slot, index);
		return query;
	}
	
	public void setEchoValue(String value){
		int v = (int) Float.parseFloat(value);
		ImageView img = (ImageView)view.findViewById(R.id.switchImg);
		if(v>0){
			img.setImageResource(R.mipmap.switch_on);
		}else{
			img.setImageResource(R.mipmap.switch_off);
		}
		return;
	}
}
