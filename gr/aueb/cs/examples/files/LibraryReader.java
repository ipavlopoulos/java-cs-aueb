package gr.aueb.cs.examples.files;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class LibraryReader {
    public static void main(String[] args) throws Exception {
        // Βήμα 1: Φόρτωση και parsing του XML
        File xmlFile = new File("gr/aueb/cs/examples/files/library.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        // Βήμα 2: Ανάκτηση όλων των <section>
        NodeList sections = doc.getElementsByTagName("section");

        for (int i = 0; i < sections.getLength(); i++) {
            Element section = (Element) sections.item(i);
            String sectionName = section.getAttribute("name");
            System.out.println("Τμήμα: " + sectionName);

            // Βήμα 3: Εύρεση <book> μέσα σε κάθε <section>
            NodeList books = section.getElementsByTagName("book");
            for (int j = 0; j < books.getLength(); j++) {
                Element book = (Element) books.item(j);
                String title = book.getAttribute("title");
                String author = book.getAttribute("author");
                String year = book.getAttribute("year");

                System.out.printf("  📖 %s (%s), %s\n", title, author, year);
            }
        }
    }
}
