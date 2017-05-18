package com.rain.wr_weixin_60;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private List<TabFragment> mdatas;
    private FragmentPagerAdapter mAdapter;
    private String[] mFragmentTitles = new String[]{"first fragment", "second fragment", "third fragment","fourth fragment"};
    private ChangeColorIconWithText mIndicatorOne;
    private ChangeColorIconWithText mIndicatortwo;
    private ChangeColorIconWithText mIndicatorThree;
    private ChangeColorIconWithText mIndicatorFour;

    private List<ChangeColorIconWithText> mIndicators = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();

        mViewPager.setAdapter(mAdapter);
        initListener();
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(this);
    }

    private void initDatas() {
        mdatas =new ArrayList<>();
        for (String title : mFragmentTitles) {
            TabFragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE,title);
            fragment.setArguments(bundle);
            mdatas.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mdatas.get(position);
            }

            @Override
            public int getCount() {
                return mdatas.size();
            }
        };
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicatorOne = (ChangeColorIconWithText) findViewById(R.id.indicator_one);
        mIndicators.add(mIndicatorOne);
        mIndicatortwo = (ChangeColorIconWithText) findViewById(R.id.indicator_two);
        mIndicators.add(mIndicatortwo);
        mIndicatorThree = (ChangeColorIconWithText) findViewById(R.id.indicator_three);
        mIndicators.add(mIndicatorThree);
        mIndicatorFour = (ChangeColorIconWithText) findViewById(R.id.indicator_four);
        mIndicators.add(mIndicatorFour);

        mIndicatorOne.setOnClickListener(this);
        mIndicatortwo.setOnClickListener(this);
        mIndicatorThree.setOnClickListener(this);
        mIndicatorFour.setOnClickListener(this);

        mIndicatorOne.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        resetOtherTabs();
        int id = v.getId();
        switch(id){
            case R.id.indicator_one:
                mIndicatorOne.setIconAlpha(1.0f);
                //直接切换 不要viewpager的动画效果
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.indicator_two:
                mIndicatortwo.setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.indicator_three:
                mIndicatorThree.setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.indicator_four:
                mIndicatorFour.setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3,false);
                break;
            default:
                break;
        }
    }

    /**
     * 重置其他的tab颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mIndicators.size(); i++) {
            mIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d("viewpager_scroll","position="+position+",positionoffset="+positionOffset+",positionOffsetPixels"+positionOffsetPixels);
        if(positionOffset>0){
            ChangeColorIconWithText left = mIndicators.get(position);
            ChangeColorIconWithText right = mIndicators.get(position+1);
            left.setIconAlpha(1-positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
