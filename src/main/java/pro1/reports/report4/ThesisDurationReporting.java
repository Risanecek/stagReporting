package pro1.reports.report4;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.Thesis;
import pro1.apiDataModel.ThesisList;
import pro1.reports.report4.reportDataModel.ThesisYearStat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ThesisDurationReporting {

    public static List<ThesisYearStat> GetReport(DataSource dataSource, String katedra, String[] years) {
        List<ThesisYearStat> result = new ArrayList<>();
        Gson gson = new Gson();

        for (int y = 0; y < years.length; y++) {
            String year = years[y];
            var json = dataSource.getKvalifikacniPrace(year, katedra);
            ThesisList list = gson.fromJson(json, ThesisList.class);
            long totalDays = 0;
            long validCount = 0;

            if (list != null && list.items != null) {
                for (int i = 0; i < list.items.size(); i++) {
                    Thesis t = list.items.get(i);
                    if (t.assignmentDate != null && t.assignmentDate.isValid()) {
                        if (t.submissionDate != null && t.submissionDate.isValid()) {
                            LocalDate start = t.assignmentDate.toLocalDate();
                            LocalDate end = t.submissionDate.toLocalDate();

                            long days = ChronoUnit.DAYS.between(start, end);
                            totalDays += days;
                            validCount++;
                        }
                    }
                }
            }

            long avg = 0;
            if (validCount > 0) {
                avg = Math.round((double) totalDays / validCount);
            }
            result.add(new ThesisYearStat(year, avg));
        }

        return result;
    }
}