package com.kh.myapp.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLObject {
	
	public NodeList nodeList;
	public Node currentNode;
	
	XMLObject(NodeList nNode) {
		this.nodeList = nNode;
	}
	
	public void setItem(int Index) {
		currentNode = nodeList.item(Index);
	}

	private String _getTagValue(String tag, Element eElement) {
      NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
      Node nValue = (Node)nlList.item(0);
      if(nValue == null) {
         return null;
      }
      
      return nValue.getNodeValue();
      
	}
	
	public boolean isEquals(String prefix, String str) {
		if (getTagValue(prefix).equals(str)) {
			return true;
		}
		
		return false;
	}
	
	public String getTagValue(String prefix) {
		Element eElement = (Element) currentNode;
		return _getTagValue(prefix, eElement);
	}
	
	public boolean isNodeElement() {
		if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
			return true;
		}
		
		return false;
	}
	
}
