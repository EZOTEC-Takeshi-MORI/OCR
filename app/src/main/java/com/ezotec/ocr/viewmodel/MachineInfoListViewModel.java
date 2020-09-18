package com.ezotec.ocr.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.ezotec.ocr.entity.MachineInfoEntity;
import com.ezotec.ocr.repository.MachineInfoRepository;

import java.util.List;

/**
 * MachineInfoListFragmentのViewModel
 * ここでMachineInfoListFragment画面に表示する各項目の値を保持しておく
 */
public class MachineInfoListViewModel extends AndroidViewModel {
    private MachineInfoRepository repository;
    private LiveData<List<MachineInfoEntity>> liveData;

    /**
     * コンストラクタ
     * インスタンスが生成されたときにリポジトリで全件取得するようにする
     * @param application　プロジェクトのapplication
     */
    public MachineInfoListViewModel(Application application){
        super(application);
        repository=new MachineInfoRepository(application);
        liveData=repository.getAll();
    }

    /**
     * liveDataを返す
     * @return　liveData
     */
    public LiveData<List<MachineInfoEntity>> getLiveData() {
        return liveData;
    }
}
