import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import model.Crime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by krystelbaca on 9/11/16.
 */
public class Output {


    public static void writeNewCsv(String filename, List<Crime> rows) {

        ByteArrayOutputStream csvResult = new ByteArrayOutputStream();
        File file = new File(filename);
        CsvWriterSettings settings = new CsvWriterSettings();

        CsvWriter writer = new CsvWriter(file, settings);

        writer.writeHeaders("year", "month", "week", "hour", "report_date", "shift", "offense", "method", "district");

        for (int i = 1; i < rows.size(); i++) {
            String crimeRow = new StringBuilder()
                    .append(rows.get(i).getYear()).append(",")
                    .append(rows.get(i).getMonth()).append(",")
                    .append(rows.get(i).getWeek()).append(",")
                    .append(rows.get(i).getHour()).append(",")
                    .append(rows.get(i).getReport_date()).append(",")
                    .append(rows.get(i).getShift()).append(",")
                    .append(rows.get(i).getOffense()).append(",")
                    .append(rows.get(i).getMethod()).append(",")
                    .append(rows.get(i).getDistrict()).toString();


            writer.writeRow(crimeRow);
        }
        writer.close();

    }


}
