package com.stxr.teacher_test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.fragments.home.AccountFragment;
import com.stxr.teacher_test.fragments.home.ExamFragment;
import com.stxr.teacher_test.fragments.home.PracticeFragment;
import com.stxr.teacher_test.utils.ShareUtil;
import com.stxr.teacher_test.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private BottomNavigationView navigation;
    public static long FIRST_TIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initFragments();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //滑动的时候选中导航栏下面的按键
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 缓存账户
     */


    private void initFragments() {
        fragments.add(new PracticeFragment());
        fragments.add(new ExamFragment());
        fragments.add(new AccountFragment());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //点击BottomNavigationView的Item项，切换ViewPager页面
            //menu/navigation.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }

    };


    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, StudentActivity.class);
        return intent;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - FIRST_TIME < 2000) {
            super.onBackPressed();
        } else {
            FIRST_TIME = System.currentTimeMillis();
            ToastUtil.show(this, "再按一次返回键退出");
        }
    }

}
