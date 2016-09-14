import model.Crime;
import model.Model;
import model.ModelManipulation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexescg on 9/11/16.
 */
public class ExamenMineriaApplication {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        CsvParser parser = new CsvParser("crime_with_errors.csv");
        List<Crime> csv = parser.parseCsv();

        ModelManipulation crimeRecords = new ModelManipulation(csv);
        crimeRecords.fixShiftValues();
        crimeRecords.fixMethodValues();
        crimeRecords.fixOffenseValues();
        crimeRecords.fixMonthValues();
        csv.stream().forEach(System.out::println);
        System.out.println(crimeRecords.getRecords().stream().map(crime -> crime.getYear()).distinct().collect(Collectors.toList()));
        //System.out.println(crimeRecords.getModeFromField("shift"));

    }

}
