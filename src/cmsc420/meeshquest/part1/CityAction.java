package cmsc420.meeshquest.part1;

import java.io.IOException;

import org.w3c.dom.Element;

import cmsc420.meeshquest.part1.Comparator.CityNameComparator;
import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;
import cmsc420.meeshquest.part1.structure.PRQT.PRQTAction;

public class CityAction {
	private Dictionary dictionary;
	private PRQTAction prqt;
	private int width;
	private int height;
	
	public CityAction(int width, int height) {
		dictionary = new Dictionary();
		prqt = new PRQTAction(width, height);

		setWidth(width);
		setHeight(height);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void createCity(Element element) {
		String name = element.getAttribute("name");
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		String color = element.getAttribute("color");
		float radius = Float.parseFloat(element.getAttribute("radius"));
		
		City city = new City(x, y, name, color, radius);
		
		if (dictionary.coordinatesSet.add(city) == false) { 
			// City coordinate already exists 
			CityResult.createCityResult("duplicateCityCoordinates", city); 
		} else { 
			// Adding city to coordinate success
			dictionary.head = dictionary.insert(city, new CityNameComparator());
		}
	}
	

	public void deleteCity(Element element) {
		Boolean unmap = false;
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			CityResult.deleteCityResult("cityDoesNotExist", name, unmap, city);
		} else {
			if (prqt.exist(city)) {
				prqt.unmapCity(city);
				unmap = true;
			}
			dictionary.remove(name);
			dictionary.coordinatesSet.remove(city);
			CityResult.deleteCityResult("success", name, unmap, city);
		}
	}
	
	public void listCities(Element element) {
		if (dictionary.head == null) {
			CityResult.listCitiesResult("noCitiesToList", element.getAttribute("sortBy"));
		} else {
			if(element.getAttribute("sortBy").equals("name")) {
				dictionary.printByName();
				CityResult.listCitiesResult("success", "name");
			} else if(element.getAttribute("sortBy").equals("coordinate")) {
				dictionary.printByCoordinate();
				CityResult.listCitiesResult("success", "coordinate");
			} else {
				CityResult.error("fatalError");
			}
		}		
	}
	
	public void clearAll() {
		dictionary.setHead(dictionary.clearTree(dictionary.head));
		dictionary.coordinatesSet.clear();
		prqt.clearAll();
		CityResult.clearAllResult();
	}

	public void mapCity(Element element) {
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			CityResult.mapCityResult("nameNotInDictionary", name);
		} else if (city.getX() >= width || city.getY() >= height) {
			CityResult.mapCityResult("cityOutOfBounds", name);
		} else if (prqt.exist(city)) {
			CityResult.mapCityResult("cityAlreadyMapped", name);
		} else {
			prqt.head = prqt.mapCity(city);
			CityResult.mapCityResult("success", name);
		}
	}
	
	public void unmapCity(Element element) {
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			CityResult.unmapCityResult("nameNotInDictionary", name);
		} else if (!prqt.exist(city)) {
			CityResult.unmapCityResult("cityNotMapped", name);
		} else {
			prqt.unmapCity(city);
			CityResult.unmapCityResult("success", name);
		}
	}
	
	public void printPRQT() {
		prqt.printPRQT();
	}
	
	public void rangeCities(Element element) throws IOException {
		int x = Integer.parseInt(element.getAttribute("x"));
		int y = Integer.parseInt(element.getAttribute("y"));
		int radius = Integer.parseInt(element.getAttribute("radius"));
		String name = element.getAttribute("saveMap");
		prqt.rangeCities(x, y, radius, name);
	}

	public void saveMap(Element element) throws IOException {
		String name = element.getAttribute("name");
		prqt.saveMap(name);
	}

	public void nearestCity(Element element) {
		// TODO Auto-generated method stub
		int x = Integer.parseInt(element.getAttribute("x"));
		int y = Integer.parseInt(element.getAttribute("y"));
		prqt.nearestCity(x, y);
	}

	public void end() {
		prqt.end();
	}
}
