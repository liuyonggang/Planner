package com.lyg.planner.model;

/**
 * Created by Administrator on 2016/3/5.
 */
public class Plan extends BaseModel{
    private int projectID = -1;
    private String name;
    private String goal;
    private int status;
    private int progress = 0;
    private int planType = 0;
    private long startDate = -1;
    private long endDate = -1;
    private String memo;

    public void setProjectID(int projectID) { this.projectID = projectID; }
    public int getProjectID() { return projectID; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setGoal(String goalDes) { this.goal = goalDes; }
    public String getGoal() { return goal; }

    public void setStatus(int status) { this.status = status; }
    public int getStatus() { return status; }

    public void setPlanType(int planType) { this.planType = planType; }
    public int getPlanType() { return planType; }

    public void setProgress(int progress) { this.progress = progress; }
    public int getProgress() { return progress; }

    public void setStartDate(long startDate) { this.startDate = startDate; }
    public long getStartDate() { return startDate; }

    public void setEndDate(long endDate) { this.endDate = endDate; }
    public long getEndDate() { return endDate; }

    public void setMemo(String memo) { this.memo = memo; }
    public String getMemo() { return memo; }

    //validation验证
    public boolean validate(){
        if (name.length() > 21){return false;}
        if (goal.length() > 80){return false;}
       // if (memo.length() > 150){return false;}

        if (startDate < 0){return false;}
        if (endDate < 0){return  false;}

        if (startDate > endDate){return false;}

        return true;
    }
}
