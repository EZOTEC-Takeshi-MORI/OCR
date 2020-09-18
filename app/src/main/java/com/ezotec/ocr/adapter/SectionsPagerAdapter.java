package com.ezotec.ocr.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ezotec.ocr.R;
import com.ezotec.ocr.fragment.BarcodeFragment;
import com.ezotec.ocr.fragment.GoogleDriveFragment;
import com.ezotec.ocr.fragment.HDDRegisterFragment;
import com.ezotec.ocr.fragment.MachineInfoListFragment;
import com.ezotec.ocr.fragment.MachineRegisterFragment;
import com.ezotec.ocr.fragment.OCRFragment;

/**
 * TopActivityに張り付けるフラグメントを選ぶAdapter。
 * タブの機能はここで担っている。
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{R.string.tab_text_1 ,
                    R.string.tab_text_2,
                    R.string.tab_text_3,
                    R.string.tab_text_4,
                    R.string.tab_text_5,
                    R.string.tab_text_6};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position) {
            case 0:
                fragment = new MachineRegisterFragment();
                break;
            case 1:
                fragment = new HDDRegisterFragment();
                break;
            case 2:
                fragment = new MachineInfoListFragment();
                break;
            case 3:
                fragment = new OCRFragment();
                break;
            case 4:
                fragment= new BarcodeFragment();
                break;
            case 5:
                fragment=new GoogleDriveFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}