package com.sbl.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TitleActivity extends AppCompatActivity {

    private ViewPager viewPager ;
    private TextView tv_title ;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews ;
    private final int[] imageIds = {
            R.drawable.guide_one,
            R.drawable.guide_two,
            R.drawable.guide_three,
            R.drawable.qs
           };
    // 图片标题集合
    private final String[] imageDescriptions = {
            "第一页",
            "第二页！",
            "sisissisiis三三三三三三！",
            "三三三三三三三三三三三三三三三！"

    };

    /**
     * 上一次高亮显示的位置
     */
    private int prePosition = 0;
    /**
     * 是否已经拖动
     */
    private boolean isDragging = false;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem()+1 ;
            viewPager.setCurrentItem(item);
            //延时发消息(死循环)
            handler.sendEmptyMessageDelayed(0,2500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_title_activity);
        viewPager = findViewById(R.id.viewpaper);
        tv_title = findViewById(R.id.tv_title);
        ll_point_group = findViewById(R.id.point_group);
        imageViews = new ArrayList<>();
        for (int i=0;i<imageIds.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            //添加到集合中
            imageViews.add(imageView);

            //动态的添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_select);
            ll_point_group.addView(point);
            //设置间距(点放进线性布局中，和父布局保持一致，)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(point.getLayoutParams());
        //    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(dp2px(TitleActivity.this,28), dp2px(TitleActivity.this,7));
            params.weight =dp2px(TitleActivity.this,8) ;
            params.height = dp2px(TitleActivity.this,8);
            if (i==0){
                point.setEnabled(true);//显示红色
            }else {
                point.setEnabled(false);//显示灰色
            }

            params.leftMargin = dp2px(TitleActivity.this,8);
           point.setLayoutParams(params);

        }

        viewPager.setAdapter(new MypagerAdapter());
        //设置监听Viewpager页面的改变
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面滚动了的时候回调这个方法
             * @param i      当前页面的位置
             * @param v     滑动页面的百分比
             * @param i1     屏幕上滑动的像素
             */
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            /**
             * 当某个页面被选中了的时候，回调
             * @param i  被选中的页面的位置
             */
            @Override
            public void onPageSelected(int i) {
                //以下是实现无限循环
                int realPosition = i%imageViews.size() ;//无限循环，position的数量变大了，取余，获取第几张
                //设置对应的文本
                tv_title.setText(imageDescriptions[realPosition]);
                //把上一个高亮设置为灰色
                ll_point_group.getChildAt(prePosition).setEnabled(false);
                //  当前的位置设置高亮
                ll_point_group.getChildAt(realPosition).setEnabled(true);
                //当前位置赋予
                prePosition = realPosition;



//                //设置对应的文本
//                tv_title.setText(imageDescriptions[i]);
//                //把上一个高亮设置为灰色
//                ll_point_group.getChildAt(prePosition).setEnabled(false);
//               //  当前的位置设置高亮
//                ll_point_group.getChildAt(i).setEnabled(true);
//                //当前位置赋予
//                prePosition = i;
            }

            /**
             * 状态的变化回调
             * 静止-滑动
             * 滑动-静止
             * 静止到拖拽
             * @param i
             */
            @Override
            public void onPageScrollStateChanged(int i) {
                if (i==ViewPager.SCROLL_STATE_DRAGGING){//正在拖动，滑动状态
                    isDragging = true;
                    handler.removeCallbacksAndMessages(null);

                }else if (i==ViewPager.SCROLL_STATE_SETTLING){//松手后，自动滑动，归于停止

                }else if (i==ViewPager.SCROLL_STATE_IDLE&&isDragging){//滑动后的静止状态
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0,2500);
                }

            }
        });

        //中间的位置，同时保证是imageViews的整数倍(这样初始的时候，可以从0开始取)
        int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size();
        //初始化就设置为中间位置
        viewPager.setCurrentItem(item);
        //第一次进来设置0位置的文本
        tv_title.setText(imageDescriptions[prePosition]);
        //第一次发消息
        handler.sendEmptyMessageDelayed(0,2000);
    }


    class MypagerAdapter extends PagerAdapter{


        /**
         * 得到展示页的总数
         * @return
         */
        @Override
        public int getCount() {
        //    return imageViews.size();
            return Integer.MAX_VALUE;
        }

        /**
         * 比较View和Object是否是统一实例
         * @param view 页面
         * @param o instantiateItem返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        /**
         *
         * @param container viewpager自身
         * @param position  当前实例化页面的位置
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int realPositon = position%imageViews.size() ;

            ImageView imageView = imageViews.get(realPositon);

           // ImageView imageView = imageViews.get(position);
            //添加到Viewpager中
            container.addView(imageView);

            //设置触摸事件，解决点击的时候，还在自动播放下一个
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN://手指按下
                            Log.e("songbl","onTouch==手指按下");
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_CANCEL://手指在这个控件上移动
                            Log.e("songbl","onTouch==事件取消");
                            break;
                        case MotionEvent.ACTION_UP://手指离开
                            Log.e("songbl","onTouch==手指离开");
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                    }
                    //如果返回false，系统将不再通知手势的其它事件了
                    return true;
                }
            });
            return imageView;
        }

        /**
         * 最多创建三个，创建第四个的时候，先销毁再创建
         * 释放资源
         * @param container viewpager
         * @param position  要释放的位置
         * @param object   要释放的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
