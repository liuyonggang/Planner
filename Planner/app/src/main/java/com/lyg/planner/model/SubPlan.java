package com.lyg.planner.model;

/**
 * Created by Administrator on 2016/4/19.
 */
public class SubPlan extends BaseModel{

    int id;
    int parentId;
    String content;//计划内容
    long startDateMilli;//开始时间戳
    long endDateMilli;//结束时间戳
    int progress;//计划进度
    int weight;//占总任务比

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStartDateMilli() {
        return startDateMilli;
    }

    public void setStartDateMilli(long startDateMilli) {
        this.startDateMilli = startDateMilli;
    }

    public long getEndDateMilli() {
        return endDateMilli;
    }

    public void setEndDateMilli(long endDateMilli) {
        this.endDateMilli = endDateMilli;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    //validation验证
    public boolean validate(){
       // if (name.length() > 21){return false;}
       // if (goal.length() > 80){return false;}
        // if (memo.length() > 150){return false;}

        if (startDateMilli <= 0){return false;}
        if (endDateMilli <= 0){return  false;}

        if (startDateMilli > endDateMilli){return false;}

        return true;
    }
}
