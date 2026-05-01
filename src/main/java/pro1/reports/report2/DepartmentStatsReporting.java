package pro1.reports.report2;

import pro1.apiDataModel.Action;
import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.ActionsList;
import pro1.reports.report2.reportDataModel.DepartmentStats;
import java.util.ArrayList;
import java.util.List;

public class DepartmentStatsReporting {
    public static DepartmentStats GetReport(DataSource dataSource, String rok, String katedra) {
        var actionsListJson = dataSource.getRozvrhByKatedra(rok, katedra);
        var actionsList = new Gson().fromJson(actionsListJson, ActionsList.class);
        return new DepartmentStats(
                maxActionStudentsCount(actionsList),
                emptyActionsCount(actionsList),
                maxTeacherScore(actionsList)
        );
    }

    private static long maxActionStudentsCount(ActionsList actionsList) {
        if (actionsList == null || actionsList.items == null) {
            return 0;
        }
        long max = 0;
        for (Action a : actionsList.items) {
            if (a.studentsCount > max) {
                max = a.studentsCount;
            }
        }
        return max;
    }

    private static long emptyActionsCount(ActionsList actionsList) {
        if (actionsList == null || actionsList.items == null) {
            return 0;
        }
        long count = 0;
        for (int i = 0; i < actionsList.items.size(); i++) {
            Action a = actionsList.items.get(i);
            if (a.studentsCount == 0) {
                count++;
            }
        }
        return count;
    }


    private static long maxTeacherScore(ActionsList actionsList) {
        if (actionsList == null || actionsList.items == null) {
            return 0;
        }

        List<Long> teacherIds = new ArrayList<>();
        for (int i = 0; i < actionsList.items.size(); i++) {
            Action a = actionsList.items.get(i);
            if (a.teacherId != null && !teacherIds.contains(a.teacherId)) {
                teacherIds.add(a.teacherId);
            }
        }

        long maxScore = 0;
        for (int i = 0; i < teacherIds.size(); i++) {
            Long tId = teacherIds.get(i);
            long score = teacherScore(tId, actionsList);
            if (score > maxScore) {
                maxScore = score;
            }
        }
        return maxScore;
    }

    private static long teacherScore(long teacherId, ActionsList actionsList) {
        if (actionsList == null || actionsList.items == null) {
            return 0;
        }
        long score = 0;
        for (int i = 0; i < actionsList.items.size(); i++) {
            Action a = actionsList.items.get(i);
            if (a.teacherId != null && a.teacherId == teacherId) {
                score += a.studentsCount;
            }
        }
        return score;
    }
}
