package model;

import com.univocity.parsers.annotations.BooleanString;
import com.univocity.parsers.annotations.Parsed;

/**
 * Created by alexescg on 9/11/16.
 */
public class Model {


    @Parsed(field = "Whether_of_not_the_TA_is_a_native_English_speaker")
    @BooleanString(falseStrings = { "1" }, trueStrings = { "2" })
    private Boolean isTANativeEnglishSpeaker;

    @Parsed(field = "Course_instructor")
    private Integer courseInstructor;

    @Parsed(field = "Course")
    private Integer course;

    @Parsed(field = "Summer_or_regular_semester")
    private String summerOrRegularSemester;

    @Parsed(field = "Class_Size")
    private Integer classSize;

    @Parsed(field = "Class_attribute")
    private Integer classAttribute;

    public Model() {
    }

    public Model(Boolean isTANativeEnglishSpeaker, Integer courseInstructor, Integer course, String summerOrRegularSemester, Integer classSize, Integer classAttribute) {
        this.isTANativeEnglishSpeaker = isTANativeEnglishSpeaker;
        this.courseInstructor = courseInstructor;
        this.course = course;
        this.summerOrRegularSemester = summerOrRegularSemester;
        this.classSize = classSize;
        this.classAttribute = classAttribute;
    }

    public Boolean getTANativeEnglishSpeaker() {
        return isTANativeEnglishSpeaker;
    }

    public void setTANativeEnglishSpeaker(Boolean TANativeEnglishSpeaker) {
        isTANativeEnglishSpeaker = TANativeEnglishSpeaker;
    }

    public Integer getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(Integer courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getSummerOrRegularSemester() {
        return summerOrRegularSemester;
    }

    public void setSummerOrRegularSemester(String summerOrRegularSemester) {
        this.summerOrRegularSemester = summerOrRegularSemester;
    }

    public Integer getClassSize() {
        return classSize;
    }

    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    public Integer getClassAttribute() {
        return classAttribute;
    }

    public void setClassAttribute(Integer classAttribute) {
        this.classAttribute = classAttribute;
    }
}
