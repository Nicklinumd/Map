package cmsc420.meeshquest.part1;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import cmsc420.meeshquest.part1.Comparator.CityCoordinateComparator;
import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;
import cmsc420.meeshquest.part1.structure.CityNode;

public class Dictionary {
	public Set<City> coordinatesSet;
	public CityNode head;
	
	public Dictionary() {
		head = null;
		coordinatesSet = new TreeSet<City>(new CityCoordinateComparator());
	}
	
	public CityNode getHead() {
		return head;
	}

	public void setHead(CityNode head) {
		this.head = head;
	}
	
	public CityNode insert(City city, Comparator<City> comparator) {
		return insert(city, head, comparator);
	}

	private CityNode insert(City city, CityNode head, Comparator<City> comparator) {
		if (head == null) {
			CityNode newNode = new CityNode();
			newNode.setCity(city);
			CityResult.createCityResult("success", city);
			return newNode;
		} else {
			City city2 = head.getCity();
			if (comparator.compare(city, city2) == 0) {
				// Can't be equal
				CityResult.createCityResult("duplicateCityName", city);
				coordinatesSet.remove(city);
				return head;
			} else if (comparator.compare(city, city2) < 0) {
				head.left = insert(city, head.left, comparator);
			} else if (comparator.compare(city, city2) > 0) {
				head.right = insert(city, head.right, comparator);
			}
			return head;
		}
	}
	
	public void remove(String name) {
		head = remove(name, head);
	}
	
	private CityNode remove(String name, CityNode head) {
		if (head.getCity().getName().compareTo(name) > 0) {
			head.left = remove(name, head.left);
		} else if (head.getCity().getName().compareTo(name) < 0) 
			head.right = remove(name, head.right);
		else { 
			if (head.left == null) {
				return head.right;
			} else if (head.right == null) {
				return head.left;
			} else {
				head.setCity(minCity(head.right));
				head.right = remove(head.getName(), head.right);
			}
        } 
        return head; 
	}
	
	private City minCity(CityNode head) {
		City min = head.getCity(); 
        while (head.left != null) { 
        	min = head.left.getCity(); 
            head = head.left; 
        }
        return min;
	}
	
	public City get(String name) {
		return get(head, name);
	}
	
	private City get(CityNode head, String name) {
		if (head == null) {
			return null;
		} else {
			if (head.getCity().getName().equals(name)) {
				return head.getCity();
			} else if (head.getCity().getName().compareTo(name) > 0){
				return get(head.left, name);
			} else {
				return get(head.right, name);
			}
		}
	}

	
	public void printByName() {
		printByName(head);
	}
	
	private void printByName(CityNode head) {
		if (head == null) {
			return;
		}
		printByName(head.right);
		CityResult.addCityList(head.getCity());
		printByName(head.left);
	}
		
	public void printByCoordinate() {
		Iterator<City> it = coordinatesSet.iterator(); 
		while (it.hasNext()) {
			CityResult.addCityList(it.next()); 
		}
	}
	
	public CityNode clearTree(CityNode head) {
		if (head != null) {
			clearTree(head.left);
			clearTree(head.right);
			head.clear();
			head=null;
			return head;
		}
		return null;
	}
}
