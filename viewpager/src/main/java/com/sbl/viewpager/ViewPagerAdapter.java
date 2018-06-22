package com.sbl.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by songbl on 2018/6/13.
 */

public class ViewPagerAdapter extends PagerAdapter {

    String [] titletop={"小美女","小帅哥","美铝，帅锅"};
    String [] titlebottom={"小美铝你好","小帅哥你好","帅锅美铝好啊"};
    private int[] mGuideLists = new int[] { R.drawable.guide_one, R.drawable.guide_two,
            R.drawable.guide_three };

    Context context ;
    TextView textViewTop;
    TextView textViewBottom;
    ImageView imageView;

    public ViewPagerAdapter(Context context ) {
        this.context= context;
    }


    @Override
    public int getCount() {
        return mGuideLists.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.work_test,null);
        imageView = view.findViewById(R.id.iv);
        imageView.setImageResource(mGuideLists[position]);

        textViewTop = view.findViewById(R.id.tv_content_top);
        textViewTop.setText(titletop[position]);

        textViewBottom = view.findViewById(R.id.tv_content_bottom);
        textViewBottom.setText(titlebottom[position]);


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
