package pro1.reports.report3;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.Action;
import pro1.apiDataModel.ActionsList;
import pro1.reports.report3.reportDataModel.WeekdayStat;

import java.util.ArrayList;
import java.util.List;

public class DepartmentWeekdaysReporting {

    public static List<WeekdayStat> GetReport(DataSource dataSource, String rok, String katedra, String[] days) {
        var actionsJson = dataSource.getRozvrhByKatedra(rok, katedra);
        var actionsList = new Gson().fromJson(actionsJson, ActionsList.class);

        List<WeekdayStat> result = new ArrayList<>();

        for (int d = 0; d < days.length; d++) {
            String day = days[d];
            long count = 0;

            if (actionsList != null && actionsList.items != null) {
                for (int i = 0; i < actionsList.items.size(); i++) {
                    Action a = actionsList.items.get(i);
                    if (a.day != null && a.day.equals(day)) {
                        count++;
                    }
                }
            }
            result.add(new WeekdayStat(day, count));
        }
        return result;
    }
}