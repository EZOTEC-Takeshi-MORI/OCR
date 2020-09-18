package com.ezotec.ocr.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ezotec.ocr.R;
import com.ezotec.ocr.fragment.MachineInfoListFragment;

/**
 * 機体情報を閲覧するためのアクティビティ
 * 現在未使用(2020/9/17)
 * 削除予定
 */
public class MachineInfoListActivity extends AppCompatActivity {

    /**
     * アクティビティ生成時の挙動
     * フラグメントを画面に貼る
     * @param savedInstanceState デフォルト
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_info_list);
        //閲覧用のフラグメントを画面に貼る
        MachineInfoListFragment machineInfoListFragment=new MachineInfoListFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.info_list_fragment, machineInfoListFragment);
        transaction.commit();
    }
}