package model;

import com.univocity.parsers.annotations.NullString;
import com.univocity.parsers.annotations.Parsed;

/**
 * Created by alexescg on 13/09/2016.
 */
public class Crime {


    @Parsed
    private Integer year;

    @Parsed
    private String month;

    @Parsed
    private Integer week;

    @NullString(nulls = { "?" })
    @Parsed(defaultNullRead = "0")
    private Integer hour;

    @Parsed
    private String report_date;

    @Parsed
    private String shift;

    @Parsed
    private String offense;

    @Parsed
    private String method;

    @Parsed
    private String district;

    public Crime() {
    }

    public Crime(Integer year, String month, Integer week, Integer hour, String report_date, String shift, String offense, String method, String district) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.hour = hour;
        this.report_date = report_date;
        this.shift = shift;
        this.offense = offense;
        this.method = method;
        this.district = district;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getOffense() {
        return offense;
    }

    public void setOffense(String offense) {
        this.offense = offense;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "Crime{" +
                "year=" + year +
                ", month='" + month + '\'' +
                ", week=" + week +
                ", hour=" + hour +
                ", report_date='" + report_date + '\'' +
                ", shift='" + shift + '\'' +
                ", offense='" + offense + '\'' +
                ", method='" + method + '\'' +
                ", district='" + district + '\'' +
                '}';
    }
}

