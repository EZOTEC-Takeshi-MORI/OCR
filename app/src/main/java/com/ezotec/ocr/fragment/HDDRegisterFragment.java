package com.ezotec.ocr.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezotec.ocr.R;
import com.ezotec.ocr.entity.HDDInfoEntity;
import com.ezotec.ocr.repository.HDDInfoRepository;

/**
 * HDD情報登録画面
 */
public class HDDRegisterFragment extends Fragment {

    EditText hddNumber;
    EditText hddManagedNumber;
    EditText makerName;
    Button register_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hdd_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        hddNumber=view.findViewById(R.id.hdd_number);
        hddManagedNumber=view.findViewById(R.id.hdd_manage_number);
        makerName=view.findViewById(R.id.maker_name);
        register_button=view.findViewById(R.id.hdd_info_register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //なんかまとめたい
                HDDInfoEntity hddInfoEntity=new HDDInfoEntity();
                hddInfoEntity.setHddNumber(hddNumber.getText().toString());
                hddInfoEntity.setManagedNumber(hddManagedNumber.getText().toString());
                hddInfoEntity.setMakerName(makerName.getText().toString());
                HDDInfoRepository repository=new HDDInfoRepository(getActivity().getApplication());
                repository.insert(hddInfoEntity);
            }
        });
    }
}