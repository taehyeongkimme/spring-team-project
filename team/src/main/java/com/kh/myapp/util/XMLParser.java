package com.kh.myapp.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParser {
	
	protected static Document docInstance;
	protected static String XMLURL;
	
	public static void nomalize() {
		docInstance.getDocumentElement().normalize();
	}

	public static Element getDocumentElement() {
		return docInstance.getDocumentElement();
	}
	
	public static Element getElementById(String elementId) {
		return docInstance.getElementById(elementId);
	}
	
	public static NodeList getElementsByTagName(String tagname) {
		return docInstance.getElementsByTagName(tagname);
	}
	
	public static void getInstance() throws Exception {
		if (XMLURL.isEmpty()) {
			throw new Exception("URL�� �������� �ʾҽ��ϴ�");
		}
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLURL);
		
		docInstance = doc;
	}
	
	public static void setURL(String url) throws Exception {
		XMLURL = url;
	}
}
