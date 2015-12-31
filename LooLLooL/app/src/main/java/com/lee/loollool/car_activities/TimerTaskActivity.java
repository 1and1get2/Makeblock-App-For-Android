package com.lee.loollool.car_activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import activities.loollool.lee.com.loollool.R;

public class TimerTaskActivity extends Activity {

    private int reclen = 5;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timer_task);

        imageView = (ImageView) findViewById(R.id.img_time);
        handler.postDelayed(runnable, 1000);

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int id = -1;
            reclen--;
            switch (reclen) {
                case 4:
                    id = (R.drawable.time_count_4);
                    break;
                case 3:
                    id = (R.drawable.time_count_3);
                    break;
                case 2:
                    id = (R.drawable.time_count_2);
                    break;
                case 1:
                    id = (R.drawable.time_count_1);
                    break;
                case 0:
                    id = (R.drawable.time_count_0);
                    break;
                default:
                    break;
            }
            if (reclen < 0) {
                finish();
            } else if (id != -1){
/*                //Set transparent paint - you need all of these three
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                paint.setColor(Color.TRANSPARENT);

                // Do you wanna soften?
                // Set paint transparency:
                // 0 = transparent ink, no visible effect
                // 255 = full ink, hole in the bitmap
                paint.setAlpha(192);

                // Do you want some blur?
                // Set blur radius in pixel
                paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));*/
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, options);
                if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(getResources(), id);
                }

                Bitmap b = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                b.setHasAlpha(true);
                //newBitmap.eraseColor(Color.TRANSPARENT);
                //Drawable d = new BitmapDrawable(getResources(), newBitmap);
                        //Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.ARGB_8888);
                //imageView.setImageDrawable(d);
                BitmapDrawable drawable = new BitmapDrawable(getResources(), b);
                drawable.setAlpha(100);

                //imageView.setImageBitmap(b);
                imageView.setImageDrawable(drawable);
            }
            handler.postDelayed(this, 1000);
        }
    };


}
