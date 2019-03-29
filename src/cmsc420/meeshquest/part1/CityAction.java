package cmsc420.meeshquest.part1;

import java.io.IOException;

import org.w3c.dom.Element;

import cmsc420.meeshquest.part1.Comparator.CityNameComparator;
import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;
import cmsc420.meeshquest.part1.structure.PRQT.PRQTAction;

/**
 * @author Hongru Lin
 *
 */

/*
 * Handle Requests From MeeshQuest
 */
public class CityAction {
	// Dictionary for searching
	private Dictionary dictionary;
	// PR quadtree for mapping
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
	
	/* 
	 * Create City and add to dictionary,
	 * cannot add city with same name or coordinates
	 */
	public void createCity(Element element) {
		String name = element.getAttribute("name");
		float x = Float.parseFloat(element.getAttribute("x"));
		float y = Float.parseFloat(element.getAttribute("y"));
		String color = element.getAttribute("color");
		float radius = Float.parseFloat(element.getAttribute("radius"));
		
		// New City object
		City city = new City(x, y, name, color, radius);
		
		if (dictionary.coordinatesSet.add(city) == false) { 
			// City coordinate already exists, check coordinates
			CityResult.createCityResult("duplicateCityCoordinates", city); 
		} else { 
			// Adding city to coordinate success
			dictionary.head = dictionary.insert(city, new CityNameComparator());
		}
	}
	
	/* 
	 * Delete City
	 * Check if city exists in dictionary
	 * If city is in map, remove from map as well
	 */
	public void deleteCity(Element element) {
		Boolean unmap = false;
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			// City does not exist
			CityResult.deleteCityResult("cityDoesNotExist", name, unmap, city);
		} else {
			// If it's in the map, delete it as well
			if (prqt.exist(city)) {
				prqt.unmapCity(city);
				unmap = true;
			}
			dictionary.remove(name);
			dictionary.coordinatesSet.remove(city);
			CityResult.deleteCityResult("success", name, unmap, city);
		}
	}
	
	/* 
	 * List Cities
	 * By name or coordinates
	 */
	public void listCities(Element element) {
		if (dictionary.head == null) {
			// Dictionary is empty
			CityResult.listCitiesResult("noCitiesToList", element.getAttribute("sortBy"));
		} else {
			if(element.getAttribute("sortBy").equals("name")) {
				// Sort by name
				dictionary.printByName();
				CityResult.listCitiesResult("success", "name");
			} else if(element.getAttribute("sortBy").equals("coordinate")) {
				// Sort by coordinate
				dictionary.printByCoordinate();
				CityResult.listCitiesResult("success", "coordinate");
			} else {
				// Input error
				CityResult.error("fatalError");
			}
		}		
	}
	
	/*
	 *  Clear all cities from dictionary and map
	 */
	public void clearAll() {
		dictionary.setHead(dictionary.clearTree(dictionary.head));
		dictionary.coordinatesSet.clear();
		prqt.clearAll();
		CityResult.clearAllResult();
	}

	/*
	 * Map City to PR quadtree
	 * City must in dictionary, and cannot be out of bounds, and cannot be mapped already
	 */
	public void mapCity(Element element) {
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			// Not in dictionary
			CityResult.mapCityResult("nameNotInDictionary", name);
		} else if (city.getX() >= width || city.getY() >= height) {
			// Out of bounds
			CityResult.mapCityResult("cityOutOfBounds", name);
		} else if (prqt.exist(city)) {
			// City already mapped
			CityResult.mapCityResult("cityAlreadyMapped", name);
		} else {
			// Map city
			prqt.head = prqt.mapCity(city);
			CityResult.mapCityResult("success", name);
		}
	}
	
	/* 
	 * Unmap city from map
	 * City must be in dictionary and map
	 */
	public void unmapCity(Element element) {
		String name = element.getAttribute("name");
		City city = dictionary.get(name);
		if (city == null) {
			// City not in dictionary
			CityResult.unmapCityResult("nameNotInDictionary", name);
		} else if (!prqt.exist(city)) {
			// City not in map
			CityResult.unmapCityResult("cityNotMapped", name);
		} else {
			// Unmap City
			prqt.unmapCity(city);
			CityResult.unmapCityResult("success", name);
		}
	}
	
	/* 
	 * Print PR quadtree
	 */
	public void printPRQT() {
		prqt.printPRQT();
	}
	
	/* 
	 * Find cities in range
	 * Return list of cities
	 */
	public void rangeCities(Element element) throws IOException {
		int x = Integer.parseInt(element.getAttribute("x"));
		int y = Integer.parseInt(element.getAttribute("y"));
		int radius = Integer.parseInt(element.getAttribute("radius"));
		String name = element.getAttribute("saveMap");
		prqt.rangeCities(x, y, radius, name);
	}
	
	/* 
	 * Save map, output a map
	 */
	public void saveMap(Element element) throws IOException {
		String name = element.getAttribute("name");
		prqt.saveMap(name);
	}

	/* 
	 * Find the nearest city to a coordinate
	 */
	public void nearestCity(Element element) {
		// TODO Auto-generated method stub
		int x = Integer.parseInt(element.getAttribute("x"));
		int y = Integer.parseInt(element.getAttribute("y"));
		prqt.nearestCity(x, y);
	}

	/*
	 * Terminate canvas
	 */
	public void end() {
		prqt.end();
	}
}
