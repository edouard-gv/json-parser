import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ParserTest {

    @Test
    public void readTest() throws IOException {

        JsonParser.Document doc = JsonParser.parse("survey-enfr.json");
        Assertions.assertEquals("section_eleven", doc.pages().get(0).name());
    }
}
