package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by alexescg on 9/11/16.
 */
public class ModelManipulation<T> {
    private List<T> records;
    private Class<T> modelClass;

    public List<T> getRecords() {
        return records;
    }

    public ModelManipulation(List<T> records) {
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

        Method getterMethod = modelClass.getDeclaredMethod("get" + capitalize(field));
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

        Method getterMethod = modelClass.getDeclaredMethod("get" + capitalize(field));

        HashMap<String, Integer> ocurrences = new HashMap<>();

        for (T t : records) {
            Object methodOutput = getterMethod.invoke(t);
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

    private Predicate<T> fieldFilter(Method method) {
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

//    public void filterApartmentListings() {
//        listings = listings
//                .stream()
//                .filter(listing -> listing.getProperty_type() != null)
//                .filter(listing -> listing.getProperty_type().equals("Apartment"))
//                .collect(Collectors.toList());
//    }
//
//    private BigDecimal toDollars(String amount) {
//        final NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
//        if (format instanceof DecimalFormat) {
//            ((DecimalFormat) format).setParseBigDecimal(true);
//        }
//        try {
//            return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return BigDecimal.ZERO;
//        }
//    }
//
//    /**
//     * Using the average obtained from daily and weekly prices from apartments, calculate using average estimate of a weekly price in case there is none stated.
//     */
//    public void fillMissingWeeklyPrices() {
//        BigDecimal weeklyPriceIncrease = getWeeklyPriceIncreaseAsPercent();
//        listings.stream().filter(listing -> listing.getWeekly_price() == null).forEach(listing -> {
//            listing.setWeekly_price(new StringBuilder("$").append(toDollars(listing.getPrice()).multiply(weeklyPriceIncrease).toString()).toString());
//        });
//    }
//
//    private BigDecimal getWeeklyPriceIncreaseAsPercent() {
//        return getWeeklyPriceAverage().divide(getDailyPriceAverage(), 2, RoundingMode.HALF_UP);
//    }
//
//    private BigDecimal getDailyPriceAverage() {
//        return listings.stream()
//                .map(listing -> toDollars(listing.getPrice()))
//                .reduce(BigDecimal.ZERO, BigDecimal::add)
//                .divide(new BigDecimal(listings.size()), 2, RoundingMode.HALF_UP);
//
//    }
//
//    private BigDecimal getWeeklyPriceAverage() {
//        return listings.stream()
//                .filter(listing -> listing.getWeekly_price() != null)
//                .map(listing -> toDollars(listing.getWeekly_price()))
//                .reduce(BigDecimal.ZERO, BigDecimal::add)
//                .divide(new BigDecimal(listings.stream().filter(listing -> listing.getWeekly_price() != null).count()), 2, RoundingMode.HALF_UP);
//    }
//
//    public void fetchWeeklyPrices() {
//        listings.stream().forEach(listing -> System.out.println(
//                new StringBuilder("type: ").append(listing.getProperty_type())
//                        .append(" daily: ").append(listing.getPrice())
//                        .append(" weekly: ").append(listing.getWeekly_price())));
//    }

}
