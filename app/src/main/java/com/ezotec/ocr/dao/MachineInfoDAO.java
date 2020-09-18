package com.ezotec.ocr.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ezotec.ocr.entity.MachineInfoAndHDDEntity;
import com.ezotec.ocr.entity.MachineInfoEntity;

import java.util.List;

/**
 * machine_infoテーブルのインターフェース
 */
@Dao
public interface MachineInfoDAO {

    /**
     * 全件取得
     * @return　machine_infoテーブルのデータすべて
     */
    @Query("SELECT * FROM machine_info")
    LiveData<List<MachineInfoEntity>> getALL();

    /**
     * 主キーで検索
     * @param ids　主キー
     * @return　検索結果(1件)
     */
    @Query("SELECT * FROM machine_info WHERE id = :ids")
    LiveData<MachineInfoEntity> findById(int ids);

    /**
     * データの追加
     * @param machineInfoEntity 追加したいデータ
     */
    @Insert
    void insert(MachineInfoEntity machineInfoEntity);

    /**
     * データの削除
     * @param machineInfoEntity 削除したいデータ
     */
    @Delete
    void delete(MachineInfoEntity machineInfoEntity);

    @Transaction
    @Query("SELECT * FROM machine_info")
    List<MachineInfoAndHDDEntity> getMachineInfoAndHDD();


}
