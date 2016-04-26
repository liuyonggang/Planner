package com.lyg.planner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lyg.planner.model.Plan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/5.
 */
public class PlanDao extends BaseDao{
    public static final String TABLE_NAME = "plan";

    //DB列名
    public static final String COLUMN_PlanID= "planID";
    public static final String COLUMN_PlanName= "planName";
    public static final String COLUMN_PlanGoal= "planGoal";
    public static final String COLUMN_PlanStartDate= "planStartDate";
    public static final String COLUMN_PlanEndDate= "planEndDate";
    public static final String COLUMN_PlanStatus= "planStatus";
    public static final String COLUMN_PlanProgress= "planProgress";
    public static final String COLUMN_PlanMemo= "planMemo";
    public static final String COLUMN_SubPlans= "subPlans";//子计划个数

    static final String[] COLUMNS = {
       COLUMN_PlanID,COLUMN_PlanName,COLUMN_PlanGoal,COLUMN_PlanStartDate, COLUMN_PlanEndDate,COLUMN_PlanStatus,COLUMN_PlanProgress,COLUMN_PlanMemo
    ,COLUMN_SubPlans};

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_PlanID + " INTEGER PRIMARY KEY,"
            + COLUMN_PlanName + " TEXT,"
            + COLUMN_PlanGoal + " TEXT,"
            + COLUMN_PlanStartDate + " INTEGER,"
            + COLUMN_PlanEndDate + " INTEGER,"
            + COLUMN_PlanStatus + " INTEGER,"
            + COLUMN_PlanProgress + " INTEGER,"
            + COLUMN_PlanMemo + " TEXT,"
            + COLUMN_SubPlans + " INTEGER)";

    SQLiteDatabase db;

    public PlanDao(SQLiteDatabase db){
        this.db = db;
        //db.execSQL(CREATE_SQL);
    }

    public List<Plan> findAll(){
        List<Plan> list = new ArrayList<>();

        Cursor c = db.query(TABLE_NAME,COLUMNS,null,null,null,null,COLUMN_PlanID +" desc");
        while (c.moveToNext()){
            Plan pj = new Plan();
            pj.setProjectID(c.getInt(c.getColumnIndex(COLUMN_PlanID)));
            pj.setName(c.getString(c.getColumnIndex(COLUMN_PlanName)));
            pj.setGoal(c.getString(c.getColumnIndex(COLUMN_PlanGoal)));
            pj.setStartDate(c.getLong(c.getColumnIndex(COLUMN_PlanStartDate)));
            pj.setEndDate(c.getLong(c.getColumnIndex(COLUMN_PlanEndDate)));
            pj.setMemo(c.getString(c.getColumnIndex(COLUMN_PlanMemo)));
            pj.setStatus(c.getInt(c.getColumnIndex(COLUMN_PlanStatus)));
            pj.setProgress(c.getInt(c.getColumnIndex(COLUMN_PlanProgress)));
            pj.setSubPlans(c.getInt(c.getColumnIndex(COLUMN_SubPlans)));
            list.add(pj);
        }
        c.close();
        return list;
    }
/*    public int getSubPlansSize(int planID){
        Cursor c = db.query(TABLE_NAME,COLUMNS,COLUMN_PlanID + " = ?",
                new String[]{String.valueOf(planID)},null,null,COLUMN_PlanID);
        Plan pj = null;
        while (c.moveToNext()){
            pj = new Plan();
            return pj.getSubPlans();
        }
        return 0;
    }*/
    public Plan findByProjectID(int projectID){
        Cursor c = db.query(TABLE_NAME,COLUMNS,COLUMN_PlanID + " = ?",
                new String[]{String.valueOf(projectID)},null,null,COLUMN_PlanID);
        Plan pj = null;

        if (c.moveToNext()){
            pj = new Plan();
            pj.setProjectID(c.getInt(c.getColumnIndex(COLUMN_PlanID)));
            pj.setName(c.getString(c.getColumnIndex(COLUMN_PlanName)));
            pj.setGoal(c.getString(c.getColumnIndex(COLUMN_PlanGoal)));
            pj.setStartDate(c.getLong(c.getColumnIndex(COLUMN_PlanStartDate)));
            pj.setEndDate(c.getLong(c.getColumnIndex(COLUMN_PlanEndDate)));
            pj.setMemo(c.getString(c.getColumnIndex(COLUMN_PlanMemo)));
            pj.setStatus(c.getInt(c.getColumnIndex(COLUMN_PlanStatus)));
            pj.setProgress(c.getInt(c.getColumnIndex(COLUMN_PlanProgress)));
            pj.setSubPlans(c.getInt(c.getColumnIndex(COLUMN_SubPlans)));
        }
        c.close();
        return pj;
    }

    public long save(Plan pj){
        if (!pj.validate()){
            return -1;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_PlanName,pj.getName());
        values.put(COLUMN_PlanGoal,pj.getGoal());
        values.put(COLUMN_PlanStartDate,pj.getStartDate());
        values.put(COLUMN_PlanEndDate,pj.getEndDate());
        values.put(COLUMN_PlanMemo,pj.getMemo());
        values.put(COLUMN_PlanStatus, pj.getStatus());
        values.put(COLUMN_PlanProgress,pj.getProgress());
        values.put(COLUMN_SubPlans,pj.getSubPlans());

        if (exists(pj.getProjectID())){
            String where = COLUMN_PlanID + " = ?";
            String[] arg = {String.valueOf(pj.getProjectID())};
            return db.update(TABLE_NAME,values,where,arg);
        }else {
            values.put(COLUMN_PlanID,pj.getProjectID());
            return db.insert(TABLE_NAME,null,values);
        }
    }

    public long deleteByProjectID(int projectID){
        String where = COLUMN_PlanID + " = ?";
        String[] arg = {String.valueOf(projectID)};
        return db.delete(TABLE_NAME,where,arg);
    }

    public int getLastID(){
        int lastID = 0;

        String[] column = {"MAX(" + COLUMN_PlanID + ")"};

        Cursor c = db.query(TABLE_NAME,column,null,null,null,null,null);
        if (c.moveToFirst()){
            lastID = c.getInt(c.getColumnIndex(column[0]));
        }
        c.close();
        return lastID;
    }

    public boolean exists(int projectID){
        return findByProjectID(projectID) != null;
    }


}
