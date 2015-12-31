package com.lee.loollool.bean;

import android.content.Context;
import android.util.Log;

import com.lee.loollool.Modles.MeButton;
import com.lee.loollool.Modles.MeCarController;
import com.lee.loollool.Modles.MeDigiSeg;
import com.lee.loollool.Modles.MeFengMingQi;
import com.lee.loollool.Modles.MeGripper;
import com.lee.loollool.Modles.MeJoystick;
import com.lee.loollool.Modles.MeLCD;
import com.lee.loollool.Modles.MeLightSensor;
import com.lee.loollool.Modles.MeLimitSwitch;
import com.lee.loollool.Modles.MeLineFollower;
import com.lee.loollool.Modles.MePIRSensor;
import com.lee.loollool.Modles.MePotential;
import com.lee.loollool.Modles.MeRgbLed;
import com.lee.loollool.Modles.MeServoMotor;
import com.lee.loollool.Modles.MeSoundSensor;
import com.lee.loollool.Modles.MeTemperature;
import com.lee.loollool.Modles.MeUltrasonic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/10 0010.
 */
public class MeLayout {
    static final String dbg = "Layout";
    public String name;
    public String createTime;
    public String updateTime;
    public int type = 0;
    public ArrayList<MeModule> moduleList;
    private Context mContext;


    public MeLayout(JSONObject json, Context context) {
        this.mContext = context.getApplicationContext();
        try {
            name = json.getString("name");
            if (json.has("type")) {
                type = json.getInt("type");
            }
            createTime = json.getString("createTime");
            updateTime = json.getString("updateTime");
            JSONArray moduleListJArray = json.getJSONArray("moduleList");
            moduleList = new ArrayList<MeModule>();
            for (int i = 0; i < moduleListJArray.length(); i++) {
                JSONObject jobj = (JSONObject) moduleListJArray.get(i);

                int modtype = jobj.getInt("type");

                MeModule mod = null;
                switch (modtype) {
                    case MeModule.DEV_ULTRASOINIC:
                        mod = new MeUltrasonic(jobj);
                        break;
                    case MeModule.DEV_TEMPERATURE:
                        mod = new MeTemperature(jobj);
                        break;
                    case MeModule.DEV_LIGHTSENSOR:
                        mod = new MeLightSensor(jobj);
                        break;
                    case MeModule.DEV_SOUNDSENSOR:
                        mod = new MeSoundSensor(jobj);
                        break;
                    case MeModule.DEV_LINEFOLLOWER:
                        mod = new MeLineFollower(jobj);
                        break;
                    case MeModule.DEV_POTENTIALMETER:
                        mod = new MePotential(jobj);
                        break;
                    case MeModule.DEV_LIMITSWITCH:
                        mod = new MeLimitSwitch(jobj);
                        break;
                    case MeModule.DEV_BUTTON:
                        mod = new MeButton(jobj);
                        break;
                    case MeModule.DEV_PIRMOTION:
                        mod = new MePIRSensor(jobj);
                        break;
                    case MeModule.DEV_DCMOTOR:
                        mod = new MeGripper(jobj);
                        break;
                    case MeModule.DEV_SERVO:
                        mod = new MeServoMotor(jobj);
                        break;
                    case MeModule.DEV_JOYSTICK:
                        mod = new MeJoystick(jobj);
                        break;
                    case MeModule.DEV_RGBLED:
                        mod = new MeRgbLed(jobj);
                        break;
                    case MeModule.DEV_SEVSEG:
                        mod = new MeDigiSeg(jobj);
                        break;
                    case MeModule.DEV_CAR_CONTROLLER:
                        mod = new MeCarController(jobj);
                        break;
                    case MeModule.DEV_LCD:
                        mod = new MeLCD(jobj);
                        break;
                    case MeModule.DEV_FENGMINGQI:
                        mod = new MeFengMingQi(jobj);
                        break;

//				case MeModule.DEV_GRIPPER_CONTROLLER:
//					mod = new MeGripper(jobj);
//					break;
                    default:
                        Log.i(dbg, "unknow module from json " + modtype);
                        break;
                }
                if (mod != null) {
                    mod.setScaleType(type);
                    moduleList.add(mod);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setName(String v) {
        name = v;
    }

    public String getTime() {
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(date);
    }

    public MeLayout(String name) {
        this.name = name;
        createTime = getTime();
        updateTime = createTime;
        moduleList = new ArrayList<MeModule>();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("createTime", createTime);
            json.put("updateTime", updateTime);
            JSONArray moduleListJArray = new JSONArray();
            // jarray sequence == arraylist sequence
            for (int i = 0; i < moduleList.size(); i++) {
                MeModule mod = moduleList.get(i);
                moduleListJArray.put(mod.toJson());
            }
            json.put("moduleList", moduleListJArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }


    public MeModule addModule(int type, int port, int slot, int x, int y) {
        MeModule mod;
        updateTime = getTime();
        Log.d("xy", String.valueOf(x));
        Log.d("xy", String.valueOf(y));
        switch (type) {
            case MeModule.DEV_ULTRASOINIC:
                mod = new MeUltrasonic(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_TEMPERATURE:
                mod = new MeTemperature(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_LIGHTSENSOR:
                mod = new MeLightSensor(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_SOUNDSENSOR:
                mod = new MeSoundSensor(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_LINEFOLLOWER:
                mod = new MeLineFollower(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_POTENTIALMETER:
                mod = new MePotential(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_LIMITSWITCH:
                mod = new MeLimitSwitch(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_BUTTON:
                mod = new MeButton(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_PIRMOTION:
                mod = new MePIRSensor(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_GRIPPER_CONTROLLER:
            case MeModule.DEV_DCMOTOR:
                mod = new MeGripper(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_SERVO:
                mod = new MeServoMotor(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_JOYSTICK:
                mod = new MeJoystick(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_RGBLED:
                mod = new MeRgbLed(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_SEVSEG:
                mod = new MeDigiSeg(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;
            case MeModule.DEV_LCD:
                mod = new MeLCD(mContext, port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;

            case MeModule.DEV_FENGMINGQI:
                mod = new MeFengMingQi(port, slot);
                mod.xPosition = x;
                mod.yPosition = y;
                break;


//			case MeModule.DEV_GRIPPER_CONTROLLER:{
//
//				mod = new MeGripper(port, slot);
//				mod.xPosition = x;
//				mod.yPosition = y;
//			}
//			break;
            default:
                Log.i(dbg, "unknow module " + type);
                mod = new MeModule(updateTime, MeModule.DEV_VERSION, 0, 0);
                break;
        }
        moduleList.add(mod);
        return mod;
    }

    public String toString() {
        return this.toJson().toString();
    }
}
