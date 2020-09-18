package com.ezotec.ocr.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ezotec.ocr.R;
import com.ezotec.ocr.fragment.MachineRegisterFragment;

/**
 * 機体情報登録画面のアクティビティ
 *  * 現在未使用(2020/9/17)
 *  * 削除予定
 */
public class MachineRegisterActivity extends AppCompatActivity {

    /**
     * アクティビティ生成時の挙動
     * フラグメントを画面に貼る
     * @param savedInstanceState デフォルト
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_register);
        //機体情報登録のためのフラグメントを貼り付ける
        MachineRegisterFragment machineRegisterFragment=new MachineRegisterFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.register_fragment, machineRegisterFragment);
        transaction.commit();
    }
}