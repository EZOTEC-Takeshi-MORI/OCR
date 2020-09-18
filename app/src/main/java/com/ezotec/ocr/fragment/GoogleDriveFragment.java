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
import com.ezotec.ocr.entity.MachineInfoAndHDDEntity;
import com.ezotec.ocr.model.GoogleDriveModel;
import com.ezotec.ocr.repository.MachineInfoRepository;
import com.google.android.gms.common.SignInButton;

import java.util.List;

/**
 * GoogleDrive操作用画面のフラグメント
 *
 */
public class GoogleDriveFragment extends Fragment {

    GoogleDriveModel googleDriveModel;
    SignInButton login_button;
    TextView user_name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_drive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        login_button=(SignInButton) view.findViewById(R.id.google_login_button);
        user_name=view.findViewById(R.id.google_drive_user_name);

        googleDriveModel=new GoogleDriveModel(getActivity(),this);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleDriveModel.signIn();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        super.onActivityResult(requestCode,resultCode,resultData);
        MachineInfoRepository repository=new MachineInfoRepository(getActivity().getApplication());
        List<MachineInfoAndHDDEntity>dataList=repository.getMachineInfoAndHDD();
        String token =googleDriveModel.getIdToken(resultData);
        googleDriveModel.firebaseAuthWithGoogle(token);
        user_name.setText(googleDriveModel.getUserName());
    }
}