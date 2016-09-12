import model.Model;

import java.util.List;

/**
 * Created by alexescg on 9/11/16.
 */
public class ExamenMineriaApplication {

    public static void main(String[] args) {
        Parser<Model> parser = new Parser("tae.csv", Model.class);
        List<Model> csv = parser.parseCsv();
        csv.stream().forEach(System.out::println);

    }

}
