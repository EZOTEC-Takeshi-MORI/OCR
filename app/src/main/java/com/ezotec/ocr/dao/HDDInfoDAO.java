package com.ezotec.ocr.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ezotec.ocr.entity.HDDInfoEntity;

import java.util.List;

/**
 * hdd_infoテーブルのインターフェース
 */
@Dao
public interface HDDInfoDAO {
    /**
     * select文
     * @return　検索結果
     */
    @Query("SELECT * FROM hdd_info")
    List<HDDInfoEntity> getALL();

    /**
     * 条件付きselect文
     * @param number 管理番号
     * @return　検索結果
     */
    @Query("SELECT * FROM hdd_info WHERE managed_number=:number")
    List<HDDInfoEntity> findByManagedNumber(String number);

    /**
     * insert文
     * @param hddInfoEntity　insertする内容
     */
    @Insert
    void insert(HDDInfoEntity hddInfoEntity);
}
