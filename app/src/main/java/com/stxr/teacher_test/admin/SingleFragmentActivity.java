package com.stxr.teacher_test.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.stxr.teacher_test.R;

/**
 * Created by stxr on 2018/5/3.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    private FragmentManager manager= getSupportFragmentManager();

    protected abstract Fragment createFragment();

    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        createFragment(createFragment());
    }

    /**
     * 添加fragment
     * @param f
     */
    public void createFragment(Fragment f) {
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = f;
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void addFragment(Fragment fragment) {
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void finishFragment(Fragment fragment) {
        manager.beginTransaction()
                .remove(fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragment(Fragment fragment) {
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void popBackStack() {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            finish();
        }
    }
}
