package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class XmlUtil {
	public static Element getDocumentElement(InputStream xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xml);
			return document.getDocumentElement();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Element getElementByTagName(Element element, String name) {
		NodeList nodeList = element.getElementsByTagName(name);
		if (nodeList.getLength() > 0) {
			return (Element) nodeList.item(0);
		} else {
			return null;
		}
	}

	public static List<Element> getElementsByTagName(Element element, String name) {
		NodeList nodeList = element.getElementsByTagName(name);
		List<Element> elments = new ArrayList<Element>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			elments.add((Element) nodeList.item(i));
		}
		return elments;
	}

	public static String getElementContent(Element element) {
		return element.getTextContent();
	}

}
