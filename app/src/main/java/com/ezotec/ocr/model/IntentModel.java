package com.ezotec.ocr.model;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.ezotec.ocr.activity.MachineInfoListActivity;
import com.ezotec.ocr.activity.MachineRegisterActivity;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.File;
import java.util.Date;
import java.util.Locale;

/**
 * 各Intentを集約したモデル
 */
public class IntentModel {

    private static final int REQUEST_CAMERA_CODE=1001;
    private static final int REQUEST_FOLDER_CODE=42;
    private Activity activity;
    private Fragment fragment;

    //コンストラクタは3パターンとりあえず用意
    public IntentModel(Activity activity){
        this.activity=activity;
    }

    public IntentModel(Fragment fragment){
        this.fragment=fragment;
    }

    public IntentModel(Activity activity,Fragment fragment){
        this.activity=activity;
        this.fragment=fragment;
    }

    /**
     * カメラを起動し、撮影した写真を一時フォルダに保存。画像のURIを変数に保存する。
     */
    public Uri cameraIntent(){
        Uri cameraUri;
        //一時フォルダのパスを生成
        File cFolder = activity.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        //一時保存するファイルの命名用日付(文字列)
        String fileDate = new SimpleDateFormat("ddHHmmss", Locale.US).format(new Date());
        //ファイル名
        String fileName = String.format("CameraIntent_%s.jpg", fileDate);
        //ファイルを生成
        File cameraFile = new File(cFolder, fileName);
        //URIを記憶
        cameraUri = FileProvider.getUriForFile(
                activity,
                "com.ezotec.ocr.fileprovider",
                cameraFile);
        //インテント生成
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        //カメラ起動
        fragment.startActivityForResult(intent, REQUEST_CAMERA_CODE);
        return cameraUri;
    }

    /**
     * ローカルフォルダ内の画像を一覧表示する。
     */
    public void folderIntent(){
        //インテント生成
        Intent intent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        //ファイル一覧起動
        fragment.startActivityForResult(intent,REQUEST_FOLDER_CODE);
    }


    /**
     * バーコードスキャナーを起動
     */
    public void barcodeIntent(){
        IntentIntegrator intentIntegrator=IntentIntegrator.forSupportFragment(fragment);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan Barcode");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.initiateScan();
    }
}
