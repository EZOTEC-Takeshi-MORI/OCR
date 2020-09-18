package com.ezotec.ocr.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * 外部結合用のクラス
 */
public class MachineInfoAndHDDEntity {
    @Embedded public MachineInfoEntity machineInfoEntity;
    @Relation(parentColumn = "managed_number",entityColumn = "managed_number")
    public List<HDDInfoEntity> HDDList;
}
