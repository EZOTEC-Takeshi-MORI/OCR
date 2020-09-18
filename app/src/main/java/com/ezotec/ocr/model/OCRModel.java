package com.ezotec.ocr.model;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * OCR用の処理
 * 実質firebaseの機能を実装
 */
public class OCRModel {

    //非同期処理用
    private Listener listener;
    public interface Listener{
        void onSuccess(ArrayList<String> result);
    }
    public void setListener(Listener listener){
        this.listener=listener;
    }

    //OCR処理時に使用する画像
    private FirebaseVisionImage image;
    private static final SparseIntArray ORIENTATIONS=new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    /**
     * imageをcloudAPI用の画像に変換
     * @param mediaImage　変換したいimage
     * @param degrees 回転度合い
     */
    public void mediaImageToFirebaseVisionImage(Image mediaImage, int degrees){
        image=FirebaseVisionImage.fromMediaImage(mediaImage,degrees);
    }

    /**
     * bitmapをcloudAPI用の画像に変換。
     * @param bitmap 変換したいBitmap
     */
    public void bitmapToFirebaseVisionImage(Bitmap bitmap){
        image= FirebaseVisionImage.fromBitmap(bitmap);
    }

    /**
     * フォルダから選択した画像をcloudAPI用の画像に変換。
     * @param context フラグメントのあるActivityのcontext
     * @param uri　変換したいローカル画像のURI
     */
    public void choiceImageToVisionImage(Context context, Uri uri){
        try{
            image=FirebaseVisionImage.fromFilePath(context,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**
    public ArrayList<String> documentToOCR(){
        final ArrayList<String> ocrText=new ArrayList<>();
        FirebaseVisionCloudDocumentRecognizerOptions options =new FirebaseVisionCloudDocumentRecognizerOptions.Builder().setLanguageHints(Arrays.asList(textLanguage,"hi")).build();
        FirebaseVisionDocumentTextRecognizer detector=FirebaseVision.getInstance().getCloudDocumentTextRecognizer(options);
        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionDocumentText>() {
            @Override
            public void onSuccess(FirebaseVisionDocumentText result) {
                for(FirebaseVisionDocumentText.Block block:result.getBlocks()){
                    ocrText.add(block.getText());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FAILURE");
            }
        });
        return ocrText;
    }
 **/


    /**
     * 画像から文字列を取得。
     * @param mode　1　クラウド版でOCR処理
     *            　2　デバイス版でOCR処理
     */
    public void imageToOCR(int mode){
        final ArrayList<String> ocrText=new ArrayList<>();
        FirebaseVisionTextRecognizer textRecognizer;
        if (mode==1){
            String textLanguage = "en";
            FirebaseVisionCloudTextRecognizerOptions options = new FirebaseVisionCloudTextRecognizerOptions.Builder().setLanguageHints(Arrays.asList(textLanguage, "hi")).build();
            textRecognizer = FirebaseVision.getInstance().getCloudTextRecognizer(options);
        }else{
            textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        }
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText result) {
                for(FirebaseVisionText.TextBlock block: result.getTextBlocks()){
                    ocrText.add(block.getText());
                }
                listener.onSuccess(ocrText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    /**
     * 撮影した画像の回転に対応するメソッド
     * Image型をFirebaseVisionImageにするときにしか使わない
     * Image型の取得の仕方がわからないからまだ使ってない
     * @param cameraId　デバイスのカメラID
     * @param activity　画面のActivity
     * @param context　画面のcontext
     * @return 回転度合い
     * @throws CameraAccessException
     */
    public int getRotationCompensation(String cameraId, Activity activity,Context context)throws CameraAccessException{
        int deviceRotation =activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation=ORIENTATIONS.get(deviceRotation);

        CameraManager cameraManager=(CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        int sensorOrientation=cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation=(rotationCompensation+sensorOrientation+270)%360;
        int result;
        switch (rotationCompensation){
            case 0:
                result=FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result=FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result=FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result=FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result=FirebaseVisionImageMetadata.ROTATION_0;
                Log.e("ERROR","Bad rotation value:"+rotationCompensation);
        }
        return result;
    }


}
