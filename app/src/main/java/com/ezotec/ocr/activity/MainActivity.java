package com.ezotec.ocr.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

import com.ezotec.ocr.fragment.BarcodeFragment;
import com.ezotec.ocr.fragment.OCRFragment;
import com.ezotec.ocr.R;

/**
 *現在未使用(2020/9/17)
 *削除予定
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 画面生成時の処理。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OCRFragment OCRFragment =new OCRFragment();
        BarcodeFragment barcodeFragment=new BarcodeFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.google_fragment, OCRFragment);
        transaction.add(R.id.barcode_fragment,barcodeFragment);
        transaction.commit();

    }
}