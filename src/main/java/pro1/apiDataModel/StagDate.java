package pro1.apiDataModel;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StagDate {
    @SerializedName("value")
    public String value;

    public boolean isValid() {
        if (this.value == null || this.value.trim().isEmpty()) {
            return false;
        }
        try {
            this.toLocalDate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LocalDate toLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        String cleanDate = this.value.split(" ")[0];
        return LocalDate.parse(cleanDate, formatter);
    }
}