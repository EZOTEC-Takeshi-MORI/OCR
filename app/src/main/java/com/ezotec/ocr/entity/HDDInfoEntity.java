package com.ezotec.ocr.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Arrays;

import lombok.Data;

/**
 * hdd_infoテーブルのエンティティ
 * getterおよびsetterはlombokで設定
 */
@Data
@Entity(tableName = "hdd_info")
public class HDDInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="managed_number")
    public String managedNumber;
    @ColumnInfo(name="hdd_number")
    public String hddNumber;
    @ColumnInfo(name="maker_name")
    public String makerName;

    public void setHddNumber(String toString) {
    }

    public void setManagedNumber(String toString) {
    }

    public void setMakerName(String toString) {
    }
}
