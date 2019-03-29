package com.sbl.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{


    private ViewPager vp;
    //最后一页的按钮
    private TextView iv_login;
    private TextView tv_user_argument;
    LinearLayout llLogin ;

    private ImageView mSelectPoint;
    private LinearLayout mllPointGroup;
     int mPointMargin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_login = findViewById(R.id.iv_login);
        tv_user_argument = findViewById(R.id.tv_user_argument);
        llLogin = findViewById(R.id.ll_login);


        mSelectPoint =  findViewById(R.id.iv_select_point);
        mllPointGroup =  findViewById(R.id.point_group);

        initPoint();
        initViewPager();

        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"haha" , Toast.LENGTH_SHORT).show();
            }
        });
        tv_user_argument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"嘻嘻" , Toast.LENGTH_SHORT).show();
            }
        });



    }

    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //=======方案2
        for (int i = 0; i < 3; i++) {
            // 设置底部小圆点
            ImageView point = new ImageView(this);
            point= new ImageView(this);
            point.setImageResource(R.drawable.qs);

            //设置创建的小点之间的距离
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(dp2px(MainActivity.this,7), dp2px(MainActivity.this,7));
            if (i > 0) {
                params2.leftMargin = dp2px(MainActivity.this,15);//控制点之间得距离
            }

            point.setLayoutParams(params2);
            mllPointGroup.addView(point);

        }

        // 获取视图树对象，通过监听白点布局的显示，然后获取两个圆点之间的距离
        mSelectPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 此时layout布局已经显示出来了，可以获取小圆点之间的距离了
                mPointMargin = mllPointGroup.getChildAt(1).getLeft() - mllPointGroup.getChildAt(0).getLeft();

                // 将自己移除掉
                mSelectPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.vp);

        //View集合初始化好后，设置Adapter
        vp.setAdapter(new ViewPagerAdapter(MainActivity.this));
        //设置滑动监听
        vp.setOnPageChangeListener(this);
    }

    /**
     * 滑动得时候不断得调用该方法，这不就是通过layoutParms产生滑动啊
     * @param position      当前页面的位置
     * @param positionOffset     滑动页面的百分比
     * @param positionOffsetPixels     屏幕上滑动的像素
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        // 页面滑动的时候，动态的获取小圆点的左边距
        int leftMargin = (int) (mPointMargin * (position + positionOffset));
        // Log.d("GuideActivity", "leftMargin:" + leftMargin);

        // 获取布局参数，然后设置布局参数
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSelectPoint.getLayoutParams();
        // 修改参数
        params.leftMargin = leftMargin;
        // 重新设置布局参数
        mSelectPoint.setLayoutParams(params);   //初始化的时候就会调用该方法，所以选中的小点在第一个位置
    }

    /**
     * 滑动后的监听
     * @param position
     */
    @Override
    public void onPageSelected(int position) {

        //判断是否是最后一页，若是则显示按钮
        if (position ==2){
            llLogin.setVisibility(View.VISIBLE);
            mllPointGroup.setVisibility(View.GONE);
            mSelectPoint.setVisibility(View.GONE);

        }else {
            llLogin.setVisibility(View.GONE);
            mllPointGroup.setVisibility(View.VISIBLE);
            mSelectPoint.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
