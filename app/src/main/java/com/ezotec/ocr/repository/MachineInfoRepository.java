package com.ezotec.ocr.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ezotec.ocr.dao.MachineInfoDAO;
import com.ezotec.ocr.database.MachineInfoDatabase;
import com.ezotec.ocr.entity.MachineInfoAndHDDEntity;
import com.ezotec.ocr.entity.MachineInfoEntity;

import java.util.List;

/**
 * machine_infoテーブルのリポジトリー
 * ここでmachine_infoテーブルを操作する
 */
public class MachineInfoRepository {
    private MachineInfoDAO infoDAO;
    private LiveData<List<MachineInfoEntity>> liveData;

    /**
     * コンストラクタ
     * @param application プロジェクトのApplication
     */
    public MachineInfoRepository(Application application){
        MachineInfoDatabase db=MachineInfoDatabase.getDatabase(application);
        infoDAO=db.machineInfoDAO();
        liveData=infoDAO.getALL();
    }

    /**
     * liveDataを取得
     * @return　liveData
     */
    public LiveData<List<MachineInfoEntity>> getAll(){
        return liveData;
    }

    /**
     * insert
     * @param machineInfoEntity insertしたいエンティティ
     */
    public void insert(final MachineInfoEntity machineInfoEntity){
        //ラムダ式で記載
        //見ずらいから直したい
        MachineInfoDatabase.databaseWriteExecutor.execute(() -> {
            infoDAO.insert(machineInfoEntity);
        });
    }

    public List<MachineInfoAndHDDEntity> getMachineInfoAndHDD(){
        return infoDAO.getMachineInfoAndHDD();
    }

}
