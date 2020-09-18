package com.ezotec.ocr.fragment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.ezotec.ocr.R;
import com.ezotec.ocr.dao.MachineInfoDAO;
import com.ezotec.ocr.database.MachineInfoDatabase;
import com.ezotec.ocr.entity.MachineInfoEntity;
import com.ezotec.ocr.repository.MachineInfoRepository;

import java.util.List;

/**
 * 機体情報登録用フラグメント(画面)
 */
public class MachineRegisterFragment extends Fragment {

    //各項目入力用テキストエリア
    EditText managedNumber;
    EditText machineNumber;
    EditText machineName;
    EditText storeNumber;
    Button register_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_machine_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        managedNumber=view.findViewById(R.id.managed_number);
        machineNumber=view.findViewById(R.id.machine_number);
        machineName=view.findViewById(R.id.machine_name);
        storeNumber=view.findViewById(R.id.store_number);
        register_button=view.findViewById(R.id.machine_info_register_button);

        //登録ボタン押下時
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineInfoEntity machineInfoEntity=new MachineInfoEntity();
                //各項目をエンティティにセット(なんかまとめたい)
                machineInfoEntity.setManagedNumber(managedNumber.getText().toString());
                machineInfoEntity.setMachineNumber(machineNumber.getText().toString());
                machineInfoEntity.setMachineName(machineName.getText().toString());
                machineInfoEntity.setStoreNumber(storeNumber.getText().toString());
                //リポジトリを介してinsertする
                MachineInfoRepository repository=new MachineInfoRepository(getActivity().getApplication());
                repository.insert(machineInfoEntity);
            }
        });


    }
}