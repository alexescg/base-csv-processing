import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by alexescg on 9/11/16.
 */
public class Parser<T>{

    private Class<T> modelClass;

    private CsvParserSettings settings;
    private BeanListProcessor<T> processor;
    final private String filePath;

    public Parser(String filePath, Class<T> modelClass) {
        this.filePath = filePath;
        configureParserSettings();
        this.modelClass = modelClass;
    }

    private void configureParserSettings() {
        settings = new CsvParserSettings();
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setMaxCharsPerColumn(200000);

        processor = new BeanListProcessor<>(modelClass);
        settings.setProcessor(processor);
    }

    public List<T> parseCsv() {
        CsvParser parser = new CsvParser(settings);
        parser.parseAll(getReader(filePath));
        List<T> records = processor.getBeans();
        return records;

    }

    private Reader getReader(String relativePath) {
        try {
            return new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to read input", e);
        }
    }

}
