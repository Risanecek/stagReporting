package pro1.reports.report5;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.Exam;
import pro1.apiDataModel.ExamsList;
import pro1.reports.report5.reportDataModel.DepartmentExamsStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepartmentExamsStatsReporting {

    public static DepartmentExamsStats GetReport(DataSource dataSource, String katedra) {
        var json = dataSource.getTerminyZkousek2(katedra);
        ExamsList list = new Gson().fromJson(json, ExamsList.class);

        long realizedCount = 0;
        List<Long> teacherIds = new ArrayList<>();

        if (list != null && list.items != null) {
            for (int i = 0; i < list.items.size(); i++) {
                Exam e = list.items.get(i);

                if (e.studentsCount > 0) {
                    realizedCount++;
                }

                if (e.teacherId != null) {
                    if (!teacherIds.contains(e.teacherId)) {
                        teacherIds.add(e.teacherId);
                    }
                }
            }
        }
        Collections.sort(teacherIds);

        return new DepartmentExamsStats(realizedCount, teacherIds);
    }
}