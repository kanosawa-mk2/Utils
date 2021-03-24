package utils;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class XmlUtilTest {

	@Test
	void test() {
		Element element = XmlUtil.getDocumentElement(XmlUtilTest.class.getResourceAsStream("/log4j2.xml"));
		Element propertyElement = XmlUtil.getElementByTagName(element, "Property");
		System.out.println(XmlUtil.getElementContent(propertyElement));
	}

	@Test
	void test2() {
		Element element = XmlUtil.getDocumentElement(XmlUtilTest.class.getResourceAsStream("/log4j2.xml"));
		Element appendersElement = XmlUtil.getElementByTagName(element, "Appenders");
		List<Element> fileElements = XmlUtil.getElementsByTagName(appendersElement, "File");
		System.out.println(fileElements.size());
		for (Element element2 : fileElements) {
			Element patternLayoutElement = XmlUtil.getElementByTagName(element2, "PatternLayout");
			System.out.println(XmlUtil.getElementContent(patternLayoutElement));
		}

		XmlUtil.getElementByTagName(appendersElement, "File");

	}

}
