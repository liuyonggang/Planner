package com.lyg.planner.model;

/**
 * Created by Administrator on 2016/4/19.
 */
public class SubPlan extends BaseModel{
    String content;//计划内容
    long startDateMilli;//开始时间戳
    long endDateMilli;//结束时间戳
    int progress;//计划进度

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    int number;//计划编号

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


}
