package com.ezotec.ocr.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.ezotec.ocr.R;
import com.ezotec.ocr.model.IntentModel;
import com.ezotec.ocr.model.OCRModel;


import java.io.IOException;
import java.util.ArrayList;

/**
 * OCR機能用の画面
 */
public class OCRFragment extends ListFragment{

    //ローカル画像読み取り時のリクエストコード
    private static final int READ_REQUEST_CODE=42;
    //カメラ使用時のリクエストコード
    private final static int RESULT_CAMERA = 1001;
    //画像のURI
    private Uri cameraUri;
    //OCRのモード選択用の変数
    private int selectedId;
    OCRModel ocrModel=new OCRModel();
    HandlerThread mBackgroundThread;
    Handler mBackgroundHandler;

    /**
     * フラグメントをActivityに貼り付ける処理。
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return フラグメントを画面に貼る
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_google,container,false);
    }

    /**
     * onCreateView()でビューが作られた後の処理。
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        startBackgroundThread();
        //ローカル画像をOCR処理するボタンのインスタンス
        Button imageToOCRButton = view.findViewById(R.id.google_file_ocr);
        //写真をOCR処理するボタンのインスタンス
        Button cameraToOCRButton = view.findViewById(R.id.google_camera_ocr);
        //OCRのモードを表示するラジオボタンのインスタンス
        RadioGroup cloudOrDeviceGroup = view.findViewById(R.id.cloud_device);

        IntentModel intentModel=new IntentModel(getActivity(),this);

        //ローカル画像をOCRへ
        imageToOCRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //ボタンクリック時処理
            public void onClick(View view) {
                //ローカル画像一覧表示
                intentModel.folderIntent();
            }
        });

        //カメラで写真した画像をOCRへ
        cameraToOCRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //カメラ起動
                cameraUri=intentModel.cameraIntent();
            }
        });

        //OCRのモードをonCloudかonDeviceか切り替える
        cloudOrDeviceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.cloud_button:
                        selectedId =1;
                    case R.id.device_button:
                        selectedId =2;
                }
            }
        });
    }

    /**
     * 各インテントの動作終了時に始動。
     * 画像のOCR処理をここで行う。
     * @param requestCode
     * @param resultCode
     * @param resultData
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent resultData){
        super.onActivityResult(requestCode,resultCode,resultData);
        //画像から選択した場合
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri ;
            if (resultData != null) {
                uri = resultData.getData();
                ocrModel.choiceImageToVisionImage(getActivity(),uri);
                ocrModel.imageToOCR(selectedId);
                ocrModel.setListener(createListener());
            }
            //写真を撮った場合
        }else if (requestCode == RESULT_CAMERA) {
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),cameraUri);
                ocrModel.bitmapToFirebaseVisionImage(bitmap);
                ocrModel.imageToOCR(selectedId);
                ocrModel.setListener(createListener());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //非同期処理用
    private OCRModel.Listener createListener(){
        return new OCRModel.Listener() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                setAdapter(result);
            }
        };
    }

    /**
     * 画面のリストに値を表示する処理。
     * @param list　画面に表示するArrayList<String>
     */
    public void setAdapter(ArrayList<String> list){
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    public void startBackgroundThread(){
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

}
