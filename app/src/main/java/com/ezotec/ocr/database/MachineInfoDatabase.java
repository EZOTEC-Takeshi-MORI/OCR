package com.ezotec.ocr.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ezotec.ocr.dao.HDDInfoDAO;
import com.ezotec.ocr.dao.MachineInfoDAO;
import com.ezotec.ocr.entity.HDDInfoEntity;
import com.ezotec.ocr.entity.MachineInfoEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DBクラス
 */
@Database(entities = {MachineInfoEntity.class, HDDInfoEntity.class},version = 3,exportSchema = false)
public abstract class MachineInfoDatabase extends RoomDatabase {
    public abstract MachineInfoDAO machineInfoDAO();
    public abstract HDDInfoDAO hddInfoDAO();

    private static volatile MachineInfoDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS=4;
    public static final ExecutorService databaseWriteExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MachineInfoDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (MachineInfoDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),MachineInfoDatabase.class,"machine_info_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
