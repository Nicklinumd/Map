package cmsc420.meeshquest.part1;

import java.util.ArrayList;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cmsc420.meeshquest.part1.Comparator.ElementNameComparator;
import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.PRQT.GrayNode;
import cmsc420.meeshquest.part1.structure.PRQT.PRQT;

public class CityResult {
	private static Document results;
	private static Element resElt;
	private static ArrayList<Element> list;
	
	public CityResult(Document r) {
		results = r;
		resElt = results.createElement("results");
		results.appendChild(resElt);
		list = new ArrayList<Element>();
	}

	public static void error(String message) {
		Element elt = results.createElement(message);
		resElt.appendChild(elt);
	}
	
	private static Element setHead(String status) {
		Element elt;
		if (status.equals("success")) {
			elt = results.createElement(status);
		} else {
			elt = results.createElement("error");
			elt.setAttribute("type", status);
		}
		return elt;
	}
	
	public static void createCityResult(String status, City city) {
		Element elt1 = setHead(status);
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "createCity");
		
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("name");
		elt4.setAttribute("value", city.getName());
		Element elt5 = results.createElement("x");
		elt5.setAttribute("value", String.valueOf(Math.round(city.getX())));
		Element elt6 = results.createElement("y");
		elt6.setAttribute("value", String.valueOf(Math.round(city.getY())));
		Element elt7 = results.createElement("radius");
		elt7.setAttribute("value", String.valueOf(Math.round(city.getRadius())));
		Element elt8 = results.createElement("color");
		elt8.setAttribute("value", city.getColor());
		
		elt3.appendChild(elt4);
		elt3.appendChild(elt5);
		elt3.appendChild(elt6);
		elt3.appendChild(elt7);
		elt3.appendChild(elt8);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt9 = results.createElement("output");
			elt1.appendChild(elt9);
		}
		resElt.appendChild(elt1);
	}
	

	public static void deleteCityResult(String status, String name, boolean unmap, City city) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "deleteCity");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("name");
		elt4.setAttribute("value", name);
		
		elt3.appendChild(elt4);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt5 = results.createElement("output");
			if (unmap) {
				Element elt6 = results.createElement("cityUnmapped");
				elt6.setAttribute("name", name);
				elt6.setAttribute("x", String.valueOf(Math.round(city.getX())));
				elt6.setAttribute("y", String.valueOf(Math.round(city.getY())));
				elt6.setAttribute("color", city.getColor());
				elt6.setAttribute("radius", String.valueOf(Math.round(city.getRadius())));
				elt5.appendChild(elt6);
			}
			elt1.appendChild(elt5);
		}
		resElt.appendChild(elt1);
	}
	
	public static void listCitiesResult(String status, String sortBy) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "listCities");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("sortBy");
		elt4.setAttribute("value", sortBy);
		
		elt3.appendChild(elt4);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt5 = results.createElement("output");
			Element elt6 = results.createElement("cityList");
			for (int i = 0; i < list.size(); i++) {
				elt6.appendChild(list.get(i));
			}
			
			elt5.appendChild(elt6);
			elt1.appendChild(elt5);
		}
		resElt.appendChild(elt1);
		list.clear();
	}
	
	public static void clearAllResult() {
		Element elt1 = results.createElement("success");
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "clearAll");
		Element elt3 = results.createElement("parameters");
		Element elt5 = results.createElement("output");
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		elt1.appendChild(elt5);
		resElt.appendChild(elt1);
	}
	
	public static void mapCityResult(String status, String name) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "mapCity");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("name");
		elt4.setAttribute("value", name);
		
		elt3.appendChild(elt4);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt5 = results.createElement("output");
			elt1.appendChild(elt5);
		}
		resElt.appendChild(elt1);
	}
	
	public static void unmapCityResult(String status, String name) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "unmapCity");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("name");
		elt4.setAttribute("value", name);
		
		elt3.appendChild(elt4);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt5 = results.createElement("output");
			elt1.appendChild(elt5);
		}
		resElt.appendChild(elt1);
		list.clear();
	}
	
	public static void printPRQTResult(String status, PRQT prqt) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "printPRQuadtree");
		Element elt3 = results.createElement("parameters");
		
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt4 = results.createElement("output");
			Element elt5 = results.createElement("quadtree");
			elt5.appendChild(addNode(prqt));
			elt4.appendChild(elt5);
			elt1.appendChild(elt4);
		}
		resElt.appendChild(elt1);
	}

	public static void rangeCitiesResult(int x, int y, int radius, String name) {
		Element elt1;
		String status;
		if (list.size() == 0) {
			elt1 = setHead("noCitiesExistInRange");
			status = "error";
		} else {
			elt1 = setHead("success");
			status = "success";
		}
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "rangeCities");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("x");
		elt4.setAttribute("value", String.valueOf(x));
		Element elt5 = results.createElement("y");
		elt5.setAttribute("value", String.valueOf(y));
		Element elt6 = results.createElement("radius");
		elt6.setAttribute("value", String.valueOf(radius));

		elt3.appendChild(elt4);
		elt3.appendChild(elt5);
		elt3.appendChild(elt6);
		if (name != "") {
			Element elt9 = results.createElement("saveMap");
			elt9.setAttribute("value", name);
			elt3.appendChild(elt9);
		}
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt7 = results.createElement("output");
			Element elt8 = results.createElement("cityList");
			Collections.sort(list, new ElementNameComparator());
			Collections.reverse(list);
			elt7.appendChild(elt8);
			for (int i = 0; i < list.size(); i++) {
				elt8.appendChild(list.get(i));
			}
			elt1.appendChild(elt7);
		}
		resElt.appendChild(elt1);
		list.clear();
	}
	
	public static void saveMapResult(String status, String name) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "saveMap");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("name");
		elt4.setAttribute("value", name);
		
		elt3.appendChild(elt4);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt5 = results.createElement("output");
			elt1.appendChild(elt5);
		}
		resElt.appendChild(elt1);
	}

	public static void nearestCityResult(String status, int x, int y, City city) {
		Element elt1 = setHead(status);
		
		Element elt2 = results.createElement("command");
		elt2.setAttribute("name", "nearestCity");
		Element elt3 = results.createElement("parameters");
		Element elt4 = results.createElement("x");
		elt4.setAttribute("value", String.valueOf(x));
		Element elt5 = results.createElement("y");
		elt5.setAttribute("value", String.valueOf(y));
		
		elt3.appendChild(elt4);
		elt3.appendChild(elt5);
		elt1.appendChild(elt2);
		elt1.appendChild(elt3);
		if (status.equals("success")) {
			Element elt6 = results.createElement("output");
			Element elt7 = results.createElement("city");
			elt7.setAttribute("color", city.getColor());
			elt7.setAttribute("name", city.getName());
			elt7.setAttribute("radius", String.valueOf(Math.round(city.getRadius())));
			elt7.setAttribute("x", String.valueOf(Math.round(city.getX())));
			elt7.setAttribute("y", String.valueOf(Math.round(city.getY())));
			
			elt6.appendChild(elt7);
			elt1.appendChild(elt6);
		}
		resElt.appendChild(elt1);
	}
	
	public static void addCityList(City city) {
		Element elt1 = results.createElement("city");
		elt1.setAttribute("color", city.getColor());
		elt1.setAttribute("name", city.getName());
		elt1.setAttribute("radius", String.valueOf(Math.round(city.getRadius())));
		elt1.setAttribute("x", String.valueOf(Math.round(city.getX())));
		elt1.setAttribute("y", String.valueOf(Math.round(city.getY())));
		list.add(elt1);
	}
	
	public static Element addNode(PRQT prqt) {
		Element elt1;
		String type = prqt.getType();
		elt1 = results.createElement(type);
		if (type.equals("black")) {
			elt1.setAttribute("name", prqt.getCity().getName());
			elt1.setAttribute("x", String.valueOf(Math.round(prqt.getCity().getX())));
			elt1.setAttribute("y", String.valueOf(Math.round(prqt.getCity().getY())));
		} else if (type.equals("gray")) {
			elt1.setAttribute("x", String.valueOf(prqt.getX()));
			elt1.setAttribute("y", String.valueOf(prqt.getY()));
			GrayNode gray = (GrayNode)prqt;
			for (int i = 0; i < 4; i++) {
				Element elt2 = addNode(gray.child[i]);
				elt1.appendChild(elt2);
			}
		}
		return elt1;
	}
}
