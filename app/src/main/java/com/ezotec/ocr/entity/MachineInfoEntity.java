package com.ezotec.ocr.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * machine_infoテーブルのエンティティ
 * getterおよびsetterはlombokで設定
 */
@Data
@Entity(tableName = "machine_info")
public class MachineInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "managed_number")
    public String managedNumber;
    @ColumnInfo(name = "machine_number")
    public String machineNumber;
    @ColumnInfo(name="machine_name")
    public String machineName;
    @ColumnInfo(name = "store_number")
    public String storeNumber;

    public void setManagedNumber(String toString) {
    }

    public void setMachineNumber(String toString) {
    }

    public void setMachineName(String toString) {
    }

    public void setStoreNumber(String toString) {
    }
}
