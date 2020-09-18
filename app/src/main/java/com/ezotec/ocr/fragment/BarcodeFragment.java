package com.ezotec.ocr.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezotec.ocr.R;
import com.ezotec.ocr.model.IntentModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * バーコード機能のフラグメント(画面)
 */
public class BarcodeFragment extends Fragment {

    //バーコードリーダー起動ボタン
    Button barcode;
    //読み取り結果表示用ビュー
    TextView barcode_result;
    //バーコードリーダー機能を搭載したインスタンス
    IntentModel intentModel;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,Bundle savedInstanceState){
         //引数をgetActivity()にするとこのフラグメントを乗せたactivityのonActivityResultが起動するため
        //引数はfragmentを使用
         intentModel=new IntentModel(this);
         barcode=view.findViewById(R.id.barcode);
         barcode_result=view.findViewById(R.id.barcode_result);

         //ボタン押下時
         barcode.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //リーダー起動
                 intentModel.barcodeIntent();
             }
         });
     }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        //スキャン結果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,resultData);
        if(result!=null){
            //ビューに値を表示
            barcode_result.setText(result.getContents());
        }
    }

}