import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParserSettings;
import model.Crime;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author alexescg
 * @version 1.0
 * @since 9/13/2016
 */
public class CsvParser {

    private CsvParserSettings settings;
    private BeanListProcessor<Crime> processor;
    final private String filePath;

    public CsvParser(String filePath) {
        this.filePath = filePath;
        configureParserSettings();
    }

    private void configureParserSettings() {
        settings = new CsvParserSettings();
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setMaxCharsPerColumn(200);

        processor = new BeanListProcessor<>(Crime.class);
        settings.setProcessor(processor);
    }

    public List<Crime> parseCsv() {
        com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(settings);
        parser.parseAll(getReader(filePath));
        List<Crime> records = processor.getBeans();
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
