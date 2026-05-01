package pro1.reports.report4.reportDataModel;

import com.google.gson.annotations.SerializedName;

public class ThesisYearStat {
    public String year;

    @SerializedName("averageDuration")
    public long averageDurationDays;

    public ThesisYearStat(String year, long avg) {
        this.year = year;
        this.averageDurationDays = avg;
    }
}
