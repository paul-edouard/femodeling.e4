package de.femodeling.e4.util.vpmtree;

import org.w3c.dom.Element;

public abstract class VpmXmlElement {
	
	public abstract void init(Element Root);
	
	public static String getTagName()
	{
		return "tag";
	}

}
