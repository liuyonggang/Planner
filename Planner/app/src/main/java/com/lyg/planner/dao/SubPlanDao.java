package com.lyg.planner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lyg.planner.model.SubPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class SubPlanDao extends BaseDao{
    public static final String TABLE_NAME = "subplan";
    //DB列名
    public static final String COLUMN_SubPlanID = "subPlanID";
    public static final String COLUMN_SubPlanName = "subPlanName";
    public static final String COLUMN_SubPlanParentID = "subPlanParentID";
    public static final String COLUMN_SubPlanStartDate = "subPlanStartDate";
    public static final String COLUMN_SubPlanEndDate = "subPlanEndDate";
    public static final String COLUMN_SubPlanWeight= "subPlanWeight";
    public static final String COLUMN_SubPlanProgress = "subPlanProgress";

    public static final String[] COLUMNS = {
            COLUMN_SubPlanID,COLUMN_SubPlanName,COLUMN_SubPlanParentID,COLUMN_SubPlanStartDate,COLUMN_SubPlanEndDate,COLUMN_SubPlanWeight,COLUMN_SubPlanProgress
    };

    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_SubPlanID + " INTEGER PRIMARY KEY,"
            + COLUMN_SubPlanName + " TEXT,"
            + COLUMN_SubPlanParentID + " INTEGER,"
            + COLUMN_SubPlanStartDate + " TEXT,"
            + COLUMN_SubPlanEndDate + " TEXT,"
            + COLUMN_SubPlanWeight + " INTEGER,"
            + COLUMN_SubPlanProgress + " INTEGER)";

    SQLiteDatabase db;
    public SubPlanDao(SQLiteDatabase db){
        this.db = db;
    }

    public List<SubPlan> findAllByParentID(int parentID){
        List<SubPlan> list = new ArrayList<>();
        try{
            Cursor c = db.query(TABLE_NAME,COLUMNS,COLUMN_SubPlanParentID + "= ?",
                    new String[]{String.valueOf(parentID)},null,null,COLUMN_SubPlanID + " asc");
            while (c.moveToNext()){
                SubPlan sp = new SubPlan();
                sp.setId(c.getInt(c.getColumnIndex(COLUMN_SubPlanID)));
                sp.setContent(c.getString(c.getColumnIndex(COLUMN_SubPlanName)));
                sp.setParentId(c.getInt(c.getColumnIndex(COLUMN_SubPlanParentID)));
                sp.setStartDateMilli(c.getLong(c.getColumnIndex(COLUMN_SubPlanStartDate)));
                sp.setEndDateMilli(c.getLong(c.getColumnIndex(COLUMN_SubPlanEndDate)));
                sp.setWeight(c.getInt(c.getColumnIndex(COLUMN_SubPlanWeight)));
                sp.setProgress(c.getInt(c.getColumnIndex(COLUMN_SubPlanProgress)));
                list.add(sp);
            }
            c.close();
            return list;
        }catch (Exception e){
            return null;
        }

    }
    public int getSubPlanSize(int parentID){
        try{
            Cursor c = db.query(TABLE_NAME,COLUMNS,COLUMN_SubPlanParentID + "= ?",
                    new String[]{String.valueOf(parentID)},null,null,COLUMN_SubPlanID + " desc");
            Log.e("SubPlanSize", c.getCount() + "");
            return c.getCount();
        }catch (Exception e){
            return 0;
        }

    }
    public SubPlan findBySubPlanID(int id){
        Cursor c = db.query(TABLE_NAME,COLUMNS,COLUMN_SubPlanID + " = ?",
                new String[]{String.valueOf(id)},null,null,COLUMN_SubPlanID);
        SubPlan sp = null;
        while (c.moveToNext()){
            sp = new SubPlan();
            sp.setId(c.getInt(c.getColumnIndex(COLUMN_SubPlanID)));
            sp.setContent(c.getString(c.getColumnIndex(COLUMN_SubPlanName)));
            sp.setParentId(c.getInt(c.getColumnIndex(COLUMN_SubPlanParentID)));
            sp.setStartDateMilli(c.getLong(c.getColumnIndex(COLUMN_SubPlanStartDate)));
            sp.setEndDateMilli(c.getLong(c.getColumnIndex(COLUMN_SubPlanEndDate)));
            sp.setWeight(c.getInt(c.getColumnIndex(COLUMN_SubPlanWeight)));
            sp.setProgress(c.getInt(c.getColumnIndex(COLUMN_SubPlanProgress)));
        }
        c.close();
        return sp;
    }

    public long save(SubPlan sp){
        if (!sp.validate()){
            return -1;
        }
        ContentValues values = new ContentValues();

        values.put(COLUMN_SubPlanName,sp.getContent());
        values.put(COLUMN_SubPlanParentID,sp.getParentId());
        values.put(COLUMN_SubPlanStartDate,sp.getStartDateMilli());
        values.put(COLUMN_SubPlanEndDate,sp.getEndDateMilli());
        values.put(COLUMN_SubPlanWeight, sp.getWeight());
        values.put(COLUMN_SubPlanProgress, sp.getProgress());

        if (exist(sp.getId())){
            String where = COLUMN_SubPlanID + "= ?";
            String[] arg = {String.valueOf(sp.getId())};
            return db.update(TABLE_NAME,values,where,arg);
        }else {
            values.put(COLUMN_SubPlanID,sp.getId());
            return db.insert(TABLE_NAME,null,values);
        }
    }

    public long deleteBySubPlanID(int subPlanID){
        String where = COLUMN_SubPlanID + "= ?";
        String[] arg = {String.valueOf(subPlanID)};
        return db.delete(TABLE_NAME,where,arg);
    }
    public long deleteByParentID(int parentID){
        String where = COLUMN_SubPlanParentID + "= ?";
        String[] arg = {String.valueOf(parentID)};
        return db.delete(TABLE_NAME,where,arg);
    }

/*    public int getParentProgress(int parentID){
        int sum = 0;
        String checkSQL = "select sum(subPlanWeight) from subplan where subPlanParentID="+parentID;
        Cursor c = db.rawQuery(checkSQL,null);
        Log.e("SUM-1",c.getCount()+"11111");
        if (c != null){
            while (c.moveToNext()){
                sum = c.getInt(0);

            }
        }
        Log.e("SUM",sum+"");
        return sum;
    }*/

    public boolean exist(int subplanID){
        return findBySubPlanID(subplanID) != null;
    }
    public boolean existParentID(int parentID){
        return findAllByParentID(parentID) != null;
    }

}
