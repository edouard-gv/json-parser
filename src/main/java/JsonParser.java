import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonParser {
    public static Document parse(String fileName) throws IOException {
        Path path = Paths.get("src/main/resources/"+fileName);
        String jsonString = Files.readString(path);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, Document.class);
    }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public record Document(List<Page> pages) { }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public record Page(String name, List<Element> elements, Translation title, Translation description) { }

    @JsonIgnoreProperties(ignoreUnknown=true)
    public record Element(String name, Translation title) { }

    public record Translation(@JsonProperty("default")String en, String fr) { }

    public static void main(String[] args) throws IOException {
        Document doc = JsonParser.parse("survey-enfr.json");
        System.out.printf("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\""+System.lineSeparator(),
                "section", "section title (en)", "section title (fr)", "page desc. (en)", "page desc. (fr)",
                "question", "question title (en)", "question title (fr)");
        for (Page page: doc.pages()) {
            String pageData = String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\"", page.name(), page.title().en(), page.title().fr(), page.description().en(), page.description().fr());
            for (Element e: page.elements()) {
                if (e.title() != null)
                    System.out.printf("%s;\"%s\";\"%s\";\"%s\""+System.lineSeparator(), pageData, e.name, e.title().en(), e.title().fr());
            }
        }
    }
}
