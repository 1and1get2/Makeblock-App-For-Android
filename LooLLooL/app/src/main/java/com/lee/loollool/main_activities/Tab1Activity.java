package com.lee.loollool.main_activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.loollool.adapter.ComplexListAdapter;
import com.lee.loollool.adapter.SeparatedListAdapter;
import com.lee.loollool.bean.ComplexItem;
import com.lee.loollool.bean.LocalLayout;
import com.lee.loollool.bean.MeDevice;
import com.lee.loollool.bean.MeLayout;
import com.lee.loollool.car_activities.CarActivity;
import com.lee.loollool.modeles_activities.LayoutView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import activities.loollool.lee.com.loollool.R;

public class Tab1Activity extends AppCompatActivity implements View.OnClickListener {

    public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";
    public final static String ITEM_TITLE_1 = "title1";
    public final static String ITEM_INDEX_1 = "index1";
    public final static String ITEM_IMAGE_1 = "image1";
    public final static String ITEM_TITLE_2 = "title2";
    public final static String ITEM_INDEX_2 = "index2";
    public final static String ITEM_IMAGE_2 = "image2";
    public final static String ITEM_TITLE_3 = "title3";
    public final static String ITEM_INDEX_3 = "index3";
    public final static String ITEM_IMAGE_3 = "image3";

    public static float screenWidth;
    public static float screenHeight;

    private ViewPager viewPager;
    private ImageView[] imageViews;
    private MyAdapter adapter;
    private ImageView imdenglu;
    private ImageView imlianji;
    private Button btndown;
    private TextView tvliaojie;
    private int currentItem;
    private ListView historyListView;
    private ArrayList<MeLayout> historyList;
    private ArrayList<MeLayout> exampleList;
    private SeparatedListAdapter sepadapter;
    private boolean isexit = false;
    private boolean hastask = false;
    private Timer timer = new Timer();
    private TimerTask task;

    Map<String, String> localizedStrings = new HashMap<String, String>();
    LocalLayout layouts;
    String[] Sections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        viewPager = (ViewPager) findViewById(R.id.vp);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        ImageView view1 = (ImageView) layoutInflater.inflate(R.layout.item, null);
        ImageView view2 = (ImageView) layoutInflater.inflate(R.layout.item, null);
        ImageView view3 = (ImageView) layoutInflater.inflate(R.layout.item, null);
        view1.setImageResource(R.mipmap.zhukongban4);
        view2.setImageResource(R.mipmap.huituyi4);
        view3.setImageResource(R.mipmap.dayinji3);
        ArrayList<ImageView> views = new ArrayList<ImageView>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        imageViews = new ImageView[views.size()];
        adapter = new MyAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new Mylistener());
        initindicator();
        initview();

        task = new TimerTask() {
            @Override
            public void run() {
                isexit = false;
                hastask = true;
            }
        };
        Display display = getWindowManager().getDefaultDisplay(); //得到设备的尺寸
        DisplayMetrics displayMetrics = new DisplayMetrics();    //
        display.getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        MeDevice.sharedManager().setWidth((int) screenWidth);
        MeDevice.sharedManager().setHeight((int) screenHeight);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        readLocalLayout();
    }

    private void initview() {
        imdenglu = (ImageView) findViewById(R.id.tab_1_denglu);
        imlianji = (ImageView) findViewById(R.id.Tab_1_lianji);
        btndown = (Button) findViewById(R.id.down_fragment_btn);
        tvliaojie = (TextView) findViewById(R.id.tab_1_liaoji);


        imdenglu.setOnClickListener(this);
        imlianji.setOnClickListener(this);
        btndown.setOnClickListener(this);
        tvliaojie.setOnClickListener(this);


    }

    private void initindicator() {
        ImageView imageView;
        View view = findViewById(R.id.indicator);
        for (int i = 0; i < imageViews.length; i++) {
            imageView = new ImageView(this);
            LinearLayout.LayoutParams Params_linear = new LinearLayout.LayoutParams(20, 20);
            Params_linear.setMargins(20, 10, 20, 10);
            imageView.setLayoutParams(Params_linear);
            imageViews[i] = imageView;
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.drawable.dot_focused);

            } else {

                imageViews[i].setBackgroundResource(R.drawable.dot_normal);
            }
            ((ViewGroup) view).addView(imageViews[i]);
        }


    }


    public Map<String, ?> createSimpleItem(String title, String caption) {
        Map<String, String> item = new HashMap<String, String>();
        item.put(ITEM_TITLE, title);
        item.put(ITEM_CAPTION, caption);
        return item;
    }

    public ComplexItem createComplexItem(String title1, int imageId1, int index1, String title2, int imageId2, int index2, String title3, int imageId3, int index3) {
        ComplexItem item = new ComplexItem();
        item.put(ITEM_TITLE_1, title1);
        item.put(ITEM_IMAGE_1, imageId1);
        item.put(ITEM_INDEX_1, index1);
        item.put(ITEM_TITLE_2, title2);
        item.put(ITEM_IMAGE_2, imageId2);
        item.put(ITEM_INDEX_2, index2);
        item.put(ITEM_TITLE_3, title3);
        item.put(ITEM_IMAGE_3, imageId3);
        item.put(ITEM_INDEX_3, index3);
        return item;
    }

    private void showpopupwindow() {
        //  readLocalLayout();

        Sections = new String[]{"< " + getString(R.string.history) + " >", "< " + getString(R.string.examples) + " >"};

        final RelativeLayout popupLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.popup_lvselect, null);
        PopupWindow popupWindow = new PopupWindow(this);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow.setWidth((int) (screenWidth * 0.5));
        popupWindow.setHeight((int) (screenHeight * 0.8));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popupLayout);
        popupWindow.showAtLocation(findViewById(R.id.centent), Gravity.LEFT | Gravity.TOP, screenWidth / 4, screenHeight / 10 + 25);
        setupViews(popupLayout);

        TextView tvpopup = (TextView) popupLayout.findViewById(R.id.tvpopup);
        tvpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(popupLayout.getContext());
                new AlertDialog.Builder(popupLayout.getContext()).setTitle("请输入项目的名字").setView(editText).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        MeLayout newlayout = new MeLayout(name);
                        historyList.add(0, newlayout);
                        setupViews(popupLayout);
                        try {
                            layouts.FileSave(newlayout.name + ".json", newlayout.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        pushToLayout(newlayout);
                    }
                }).setNegativeButton("取消", null).show();
            }
        });

    }


    private void setupViews(RelativeLayout popupLayout) {

        sepadapter = new SeparatedListAdapter(this);
        final List<Map<String, ?>> history = new LinkedList<Map<String, ?>>();
        for (MeLayout meLayout : historyList) {
            history.add(createSimpleItem(meLayout.name, meLayout.updateTime));
        }
        SimpleAdapter listHistoryAdapter = new SimpleAdapter(this, history, R.layout.list_simple
                , new String[]{ITEM_TITLE, ITEM_CAPTION}, new int[]{R.id.list_simple_title, R.id.list_simple_caption});
        sepadapter.addSection(Sections[0], listHistoryAdapter);
        List<ComplexItem> demos = new LinkedList<ComplexItem>();
        demos.add(createComplexItem(getString(R.string.distancemeasure), R.mipmap.distanceicon, 1, getString(R.string.temperaturemeasure), R.mipmap.temperatureicon, 2, getString(R.string.rgbcontrol), R.mipmap.rgbicon, 3));
        demos.add(createComplexItem(getString(R.string.robottank), R.mipmap.car_controller, 4, getString(R.string.roboticarmtank), R.mipmap.robotarmcar, 5, getString(R.string.balllauncher), R.mipmap.balllauncher, 6));
        demos.add(createComplexItem(getString(R.string.drinkcar), R.mipmap.beercar, 7, "", -1, -1, "", -1, -1));
        ComplexListAdapter listExamplesAdapter = new ComplexListAdapter(this, demos);
        listExamplesAdapter.delegate = this;
        sepadapter.addSection(Sections[1], listExamplesAdapter);

        historyListView = (ListView) popupLayout.findViewById(R.id.centerList);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.d("listview", "item " + position + " clicked");
                    MeLayout meLayout = historyList.get(position - 1);
                    pushToLayout(meLayout);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

            }
        });
        historyListView.setAdapter(sepadapter);

    }


    public void pushToLayout(MeLayout layout) {

        Intent intent = new Intent(this, LayoutView.class);
        intent.putExtra("layout", layout.toString()); // use json string between activities
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tab_1_denglu:

                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                Intent registerintent = new Intent(this, RegisterActivity.class);
                startActivity(registerintent);
                break;

            case R.id.Tab_1_lianji:
                Toast.makeText(this, "2222", Toast.LENGTH_SHORT).show();


                break;
            case R.id.down_fragment_btn:

                if (currentItem == 0) {
                    Toast.makeText(this, "体验功能1", Toast.LENGTH_SHORT).show();
                    showpopupwindow();
                } else if (currentItem == 1) {
                    Toast.makeText(this, "体验功能2", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CarActivity.class));

                } else if (currentItem == 2) {
                    Toast.makeText(this, "体验功能3", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(this, "3333", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tab_1_liaoji:
                Toast.makeText(this, "4444", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    void readLocalLayout() {
        //layouts.initLocalLayout();
        layouts = new LocalLayout(this);
        String[] filelist = layouts.fileList();
        historyList = new ArrayList<MeLayout>();
        for (String filename : filelist) {
            if (!filename.contains(".json"))
                continue;

            String jsonstr;
            try {
                jsonstr = layouts.FileRead(filename);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
            JSONObject json = layouts.toJson(jsonstr);
            MeLayout layout = new MeLayout(json, this);
            historyList.add(layout);
        }
        exampleList = new ArrayList<MeLayout>();

        localizedStrings.put("Distance Measure", Integer.toString(R.string.distancemeasure));
        localizedStrings.put("Temperature Measure", Integer.toString(R.string.temperaturemeasure));
        localizedStrings.put("RGB Control", Integer.toString(R.string.rgbcontrol));
        localizedStrings.put("General Controls", Integer.toString(R.string.generalcontrols));
        localizedStrings.put("Robot Tank", Integer.toString(R.string.robottank));
        localizedStrings.put("Robotic Arm Tank", Integer.toString(R.string.roboticarmtank));
        localizedStrings.put("Ball Launcher", Integer.toString(R.string.balllauncher));
        localizedStrings.put("Drink Car", Integer.toString(R.string.drinkcar));

        for (int i = 1; i < 8; i++) {
            String jsonstr;
            try {
                jsonstr = readTextFile(getAssets().open("" + i + ".json"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
            JSONObject json = layouts.toJson(jsonstr);
            MeLayout layout = new MeLayout(json, this);
            try {
//				if(localizedStrings.get(json.getString("name"))!=null){
//					Log.d("mb","name="+getString(Integer.parseInt(localizedStrings.get(json.getString("name")))));
//				}else{
//					Log.d("mb","s="+json.getString("name"));
//				}
                layout.setName(getString(Integer.parseInt(localizedStrings.get(json.getString("name")))));
            } catch (JSONException e) {

            }
            exampleList.add(layout);
        }
    }

    private String readTextFile(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];   //btye 类型的数组  数组长度是1024
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    public void openExample(int indexId) {
        MeLayout layout = exampleList.get(indexId - 1);
        pushToLayout(layout);
    }


    private class MyAdapter extends PagerAdapter {
        private ArrayList<ImageView> mViewlist;

        public MyAdapter(ArrayList<ImageView> viewlist) {
            this.mViewlist = viewlist;

        }

        @Override
        public int getCount() {

            return mViewlist.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViewlist.get(position));

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mViewlist.get(position));
            return mViewlist.get(position);
        }
    }


    private class Mylistener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setBackgroundResource(R.drawable.dot_normal);

            }
            imageViews[position].setBackgroundResource(R.drawable.dot_focused);
            currentItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            if (isexit == false) {
                isexit = true;
                Toast.makeText(getApplicationContext(), "再按一次返回键退出loollool", Toast.LENGTH_SHORT).show();
                if (!hastask) {
                    timer.schedule(task, 2000);
                }
            } else {
//                BluetoothAdapter.getDefaultAdapter().disable();
//                stopService(serviceIntent);
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}



