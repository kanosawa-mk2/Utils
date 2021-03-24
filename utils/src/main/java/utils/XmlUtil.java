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

/**
 * XMLパーサ
 */
public final class XmlUtil {

	/**
	 * XMLのInputStreamからDocumentのElementを取得
	 *
	 * @param xml XMLのInputStream
	 * @return DocumentのElement
	 */
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

	/**
	 *  Elementから指定のタグのElementを取得.
	 *
	 * @param element Element
	 * @param name タグ名
	 * @return Element
	 */
	public static Element getElementByTagName(Element element, String name) {
		NodeList nodeList = element.getElementsByTagName(name);
		if (nodeList.getLength() == 0) {
			return null;
		} else if (nodeList.getLength() == 1) {
			return (Element) nodeList.item(0);
		} else {
			throw new RuntimeException(String.format("Tag name element not single!! tagname=[%s] length=[%d]",name,nodeList.getLength()));
		}
	}

	/**
	 * Elementから指定のタグのElementリストを取得.
	 *
	 * @param element Element
	 * @param name タグ名
	 * @return Elementリスト
	 */
	public static List<Element> getElementsByTagName(Element element, String name) {
		NodeList nodeList = element.getElementsByTagName(name);
		List<Element> elments = new ArrayList<Element>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			elments.add((Element) nodeList.item(i));
		}
		return elments;
	}

	/**
	 * Elementからコンテンツを取得.
	 * @param element Element
	 * @return コンテンツの文字列
	 */
	public static String getElementContent(Element element) {
		return element.getTextContent();
	}

}
