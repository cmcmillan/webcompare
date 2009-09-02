package com.chris.utils.text;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.junit.Test;

public class XPathTest
{
    @Test
    public void parseCommons()
    {
	try
	{
	    SAXBuilder builder = new SAXBuilder();
	    Document d = builder.build("commons.html");
	    XPath xpath = XPath.newInstance("/table/tr/td/a");
	    List nodes = xpath.selectNodes(d);
	    for (int i = 0; i < nodes.size(); i++)
	    {
		Element elem = (Element) nodes.get(i);
		System.out.print("Commons " + elem.getValue());
		System.out.print("\t");
		System.out.println(elem.getAttributeValue("href"));
	    }
	}
	catch (JDOMException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    fail(e.getMessage());
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    fail(e.getMessage());
	}

    }
}
