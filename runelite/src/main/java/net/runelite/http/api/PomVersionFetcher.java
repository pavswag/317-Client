package net.runelite.http.api;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PomVersionFetcher {
    public static String init() {
        try {
            String pomXmlUrl = "https://raw.githubusercontent.com/runelite/runelite/master/pom.xml";

            return "runelite-"+getVersionFromPom(pomXmlUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getVersionFromPom(String pomXmlUrl) throws IOException {
        try (InputStream inputStream = new URL(pomXmlUrl).openStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(inputStream));

            NodeList versionNodes = document.getElementsByTagName("version");
            if (versionNodes.getLength() > 0) {
                return versionNodes.item(0).getTextContent();
            } else {
                throw new IOException("Version element not found in the XML");
            }
        } catch (Exception e) {
            throw new IOException("Error parsing XML", e);
        }
    }
}