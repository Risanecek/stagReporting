package pro1.reports.report3.reportDataModel;

import com.google.gson.annotations.SerializedName;

public class WeekdayStat {
    @SerializedName("weekday")
    public String day;
    @SerializedName("actionsCount")
    public long count;

    public WeekdayStat(String day, long count) {
        this.day = day;
        this.count = count;
    }
}