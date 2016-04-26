package com.lyg.planner.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lyg.planner.dao.PlanDao;
import com.lyg.planner.dao.SubPlanDao;
import com.lyg.planner.model.Plan;

/**
 * Created by Administrator on 2016/3/5.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "plandata";
    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlanDao.CREATE_SQL);
        db.execSQL(SubPlanDao.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( oldVersion == 1 && newVersion == 2 ){
            db.execSQL(PlanDao.CREATE_SQL);
        }
        if( oldVersion == 2 && newVersion == 3) {
            //db.execSQL("alter table " + ProjectDao.TABLE_NAME + " add " + ProjectDao.COLUMN_imagePath + " TEXT");
            db.execSQL(PlanDao.CREATE_SQL);
        }
    }
}
