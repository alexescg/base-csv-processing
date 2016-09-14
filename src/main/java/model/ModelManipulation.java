package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by alexescg on 9/11/16.
 */
public class ModelManipulation {
    private List<Crime> records;


    public List<Crime> getRecords() {
        return records;
    }

    public ModelManipulation(List<Crime> records) {
        this.records = records;
    }

    /**
     * Filter rows that contain a null value in a column
     *
     * @param field column to filter value from
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public void ignoreRowsWithNullField(String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {

        Method getterMethod = Crime.class.getDeclaredMethod("get" + capitalize(field));
        records = records.stream().filter(fieldFilter(getterMethod)).collect(Collectors.toList());
    }

    /**
     * Obtain mode from field within listing collection.
     *
     * @param field to obtain mode from
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String getModeFromField(String field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method getterMethod = Crime.class.getDeclaredMethod("get" + capitalize(field));

        HashMap<String, Integer> ocurrences = new HashMap<>();

        for (Crime crime : records) {
            Object methodOutput = getterMethod.invoke(crime);
            if (methodOutput != null) {
                if (methodOutput.toString() != "null") {
                    if (!ocurrences.containsKey(methodOutput)) {
                        ocurrences.put(methodOutput.toString(), 1);
                    } else {
                        ocurrences.replace(methodOutput.toString(), ocurrences.get(methodOutput.toString()) + 1);
                    }
                }
            }
        }

        return ocurrences.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    private Predicate<Crime> fieldFilter(Method method) {
        return record -> {
            try {
                return method.invoke(record) != null;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    /**
     * Make first letter of a word uppercase.
     *
     * @param toCapitalize word to capitalize
     * @return capitalized word
     */
    private String capitalize(String toCapitalize) {
        return Character.toUpperCase(toCapitalize.charAt(0)) + toCapitalize.substring(1);
    }

    public void fixShiftValues() {
        this.getRecords().forEach(crime -> {
            shouldBe(crime, "shift", "DAY", "D", "day");
            shouldBe(crime, "shift", "EVENING", "E", "evening");
            shouldBe(crime, "shift", "MIDNIGHT", "M", "midnight");
            if (crime.getShift().equals("?")) {
                setShiftAccordingToHour(crime);
            }
        });
    }

    private void shouldBe(Crime crime, String field, String expected, String... available) {
        try {
            Method getter = crime.getClass().getMethod("get" + capitalize(field));
            if (Arrays.asList(available).contains(getter.invoke(crime))) {
                Method setter = crime.getClass().getMethod("set" + capitalize(field), String.class);
                setter.invoke(crime, expected);
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void setShiftAccordingToHour(Crime crime) {
        if (crime.getHour() > 6 && crime.getHour() <= 12) {
            crime.setShift("DAY");
        } else if (crime.getHour() < 18) {
            crime.setShift("EVENING");
        } else {
            crime.setShift("MIDNIGHT");
        }
    }

    public void fixOffenseValues() {
        this.getRecords().stream().forEach(crime -> {
            shouldBe(crime, "offense", "HOMICIDE", "H", "homicide");
            shouldBe(crime, "offense", "SEX ABUSE", "S", "sex abuse");
            if (crime.getOffense().equals("?")) {
                setOffenseAccordingToMethod(crime);
            }
        });
    }

    public void setOffenseAccordingToMethod(Crime crime) {
        if (crime.getMethod().equals("OTHERS")) {
            crime.setOffense("SEX ABUSE");
        } else {
            crime.setOffense("HOMICIDE");
        }
    }

    public void fixMethodValues() {
        this.getRecords().stream().forEach(crime -> {
            shouldBe(crime, "method", "KNIFE", "K");
            shouldBe(crime, "method", "GUN", "G");
            shouldBe(crime, "method", "OTHERS", "O");
        });
    }



    public void fixMonthValues() {
        this.getRecords().stream().forEach(crime -> {
            monthNameToNumber(crime);
            monthNotInYearToReportDate(crime);
            yearNotRecentFromReportDate(crime);
            formulateValidDate(crime);
        });

    }

    private void monthNameToNumber(Crime crime) {
        switch (crime.getMonth()) {
            case "ENE": {
                crime.setMonth("1");
                break;
            }
            case "FEB": {
                crime.setMonth("2");
                break;
            }
            case "MAR": {
                crime.setMonth("3");
                break;
            }
            case "ABR": {
                crime.setMonth("4");
                break;
            }
            case "MAY": {
                crime.setMonth("5");
                break;
            }
            case "JUN": {
                crime.setMonth("6");
                break;
            }
            case "JUL": {
                crime.setMonth("7");
                break;
            }
            case "AGO": {
                crime.setMonth("8");
                break;
            }
            case "SEP": {
                crime.setMonth("9");
                break;
            }
            case "OCT": {
                crime.setMonth("10");
                break;
            }
            case "NOV": {
                crime.setMonth("11");
                break;
            }
            case "DIC": {
                crime.setMonth("12");
                break;
            }
            default:
                break;

        }
    }

    private void monthNotInYearToReportDate(Crime crime) {
        if (Integer.parseInt(crime.getMonth()) > 12) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date reportDate = (Date) sdf.parseObject(crime.getReport_date());
                crime.setMonth("" + reportDate.getMonth());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void yearNotRecentFromReportDate(Crime crime) {
        if (isCrimeRecent(crime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date reportDate = (Date) sdf.parseObject(crime.getReport_date());
                crime.setYear(reportDate.getYear() + 1900);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean isCrimeRecent(Crime crime) {
        return Integer.parseInt(crime.getMonth()) > 2017 || Integer.parseInt(crime.getMonth()) < 2000;
    }

    private void formulateValidDate(Crime crime) {
        String[] mdy = crime.getReport_date().split("/");

        Integer month = Integer.parseInt(mdy[0]);
        Integer day = Integer.parseInt(mdy[1]);
        Integer year = Integer.parseInt(mdy[2]);

        crime.setReport_date(new StringBuilder()
                .append(month).append("/")
                .append(day).append("/")
                .append(year).toString());
    }

}
