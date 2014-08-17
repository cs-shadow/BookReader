package com.example.textcontrols;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class XMLParser {

	public XMLWords[] getWords(InputStream xmlFileStream) {

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			dbf.setValidating(false);
			dbf.setIgnoringComments(false);
			dbf.setIgnoringElementContentWhitespace(true);
			dbf.setNamespaceAware(true);

			DocumentBuilder db = null;
			db = dbf.newDocumentBuilder();
			db.setEntityResolver(new NullResolver());

			Document doc = db.parse(xmlFileStream);
			
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("PAIR");
			XMLWords[] words = new XMLWords[nList.getLength()];

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				
//				words[i] = eElement.getElementsByTagName("TOKEN").item(0).getTextContent();
				String token = eElement.getElementsByTagName("TOKEN").item(0).getTextContent();
				int startTime = Integer.parseInt(eElement.getElementsByTagName("START").item(0).getTextContent());
				int endTime = Integer.parseInt(eElement.getElementsByTagName("END").item(0).getTextContent());
				
				words[i] = new XMLWords(token, startTime, endTime);
			}

			return words;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new XMLWords[0];
	}

}


class NullResolver implements EntityResolver {
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
      IOException {
    return new InputSource(new StringReader(""));
  }
}