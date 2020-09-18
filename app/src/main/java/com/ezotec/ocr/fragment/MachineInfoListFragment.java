package com.ezotec.ocr.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ezotec.ocr.R;
import com.ezotec.ocr.entity.MachineInfoEntity;
import com.ezotec.ocr.adapter.MachineInfoListAdapter;
import com.ezotec.ocr.viewmodel.MachineInfoListViewModel;

import java.util.List;

/**
 * 機体情報表示用フラグメント(画面)
 */
public class MachineInfoListFragment extends ListFragment {

    private MachineInfoListViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_machine_info_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        //ViewModelクラスを宣言
        model=new ViewModelProvider(this).get(MachineInfoListViewModel.class);
        //liveDataを監視する(liveDataはmachine_infoテーブルのデータ全件のlist)
        model.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<MachineInfoEntity>>() {
            //liveDataに変更があったら(machine_infoテーブルにinsert or update or delete　があったら)
            @Override
            public void onChanged(List<MachineInfoEntity> machineInfoEntity) {
                //画面に表示する
                setAdapter(machineInfoEntity);
            }
        });
    }

    /**
     * 画面にDBから検索した結果を表示する
     * @param entity　検索結果のエンティティ
     */
    public void setAdapter(List<MachineInfoEntity> entity){
        MachineInfoListAdapter adapter =new MachineInfoListAdapter(getActivity());
        adapter.setMachineInfoEntity(entity);
        setListAdapter(adapter);
    }
}
