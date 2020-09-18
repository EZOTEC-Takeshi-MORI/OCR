package com.ezotec.ocr.repository;

import android.app.Application;

import com.ezotec.ocr.dao.HDDInfoDAO;
import com.ezotec.ocr.database.MachineInfoDatabase;
import com.ezotec.ocr.entity.HDDInfoEntity;

/**
 * hdd_infoテーブルのリポジトリー
 * ここでhdd_infoテーブルを操作する
 */
public class HDDInfoRepository {
    private HDDInfoDAO infoDAO;

    public HDDInfoRepository(Application application){
        MachineInfoDatabase db=MachineInfoDatabase.getDatabase(application);
        infoDAO=db.hddInfoDAO();
    }

    public void insert(final HDDInfoEntity hddInfoEntity){
        MachineInfoDatabase.databaseWriteExecutor.execute(() -> {
            infoDAO.insert(hddInfoEntity);
        });
    }
}
