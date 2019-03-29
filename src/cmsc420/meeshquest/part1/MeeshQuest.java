package cmsc420.meeshquest.part1;
import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cmsc420.xml.XmlUtility;

/*
 * Main class
 * Takes file and parse command
 */
public class MeeshQuest {
	private Document results;
    public static void main(String[] args) throws ParserConfigurationException {    	
    	MeeshQuest mq = new MeeshQuest();
    	mq.processInput();
    }
    
    public void processInput() {
        try {
//        	Document doc = XmlUtility.validateNoNamespace(System.in);
//        	Document doc = XmlUtility.validateNoNamespace(new File("Test/part1.createCity1.input.xml"));
        	Document doc = XmlUtility.validateNoNamespace(new File("Test/part1.test.input3.xml"));
//        	Document doc = XmlUtility.validateNoNamespace(new File("Test/part1.test.rangeCities1.xml"));

        	results = XmlUtility.getDocumentBuilder().newDocument();
        	Element commandNode = doc.getDocumentElement();
        	
        	int width = Integer.parseInt(commandNode.getAttribute("spatialWidth"));
        	int height = Integer.parseInt(commandNode.getAttribute("spatialHeight"));
        	// Set up cityAction
        	CityAction cityAction = new CityAction(width, height);
        	
        	// Set up results
        	new CityResult(results);
        	
        	// Parse each command, and sends to cityAction.java
        	final NodeList nl = commandNode.getChildNodes();
        	for (int i = 0; i < nl.getLength(); i++) {
        		if (nl.item(i).getNodeType() == Document.ELEMENT_NODE) {
        			commandNode = (Element) nl.item(i);
        			String action = commandNode.getNodeName();
        			if (action.equals("createCity")) {
        				cityAction.createCity(commandNode);
        			} else if (action.equals("listCities")) {
        				cityAction.listCities(commandNode);
        			} else if (action.equals("deleteCity")) {
        				cityAction.deleteCity(commandNode);
        			} else if (action.equals("clearAll")) {
        				cityAction.clearAll();
        			} else if (action.equals("mapCity")) {
        				cityAction.mapCity(commandNode);
        			} else if (action.equals("unmapCity")) {
        				cityAction.unmapCity(commandNode);
        			} else if (action.equals("printPRQuadtree")) {
        				cityAction.printPRQT();
        			} else if (action.equals("saveMap")) {
        				cityAction.saveMap(commandNode);
        			} else if (action.equals("rangeCities")) {
        				cityAction.rangeCities(commandNode);
        			} else if (action.equals("nearestCity")) {
        				cityAction.nearestCity(commandNode);
        			} else {
        				CityResult.error("fatalError");
        			}
        		}
        	}
        	cityAction.end();
        } catch (SAXException e) {
            addFatalError();
        } catch (ParserConfigurationException e) {
            addFatalError();
        } catch (IOException e) {
            addFatalError();
        } finally {
			try {
				XmlUtility.print(results);
			} catch (TransformerException e) {
				System.exit(-1);
			}
        }
    }
    
    // Fatal error
    private void addFatalError() {
        try {
            results = XmlUtility.getDocumentBuilder().newDocument();
            final Element fatalError = results.createElement("fatalError");
            results.appendChild(fatalError);
        } catch (ParserConfigurationException e) {
            System.exit(-1);
        }
    }
}
