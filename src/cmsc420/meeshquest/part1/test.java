package cmsc420.meeshquest.part1;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.PRQT.GrayNode;
import cmsc420.meeshquest.part1.structure.PRQT.PRQTAction;

public class test {
	public static void main(String[] args) {
		int width = 256;
		int height = 256;
		PRQTAction p = new PRQTAction(width, height);
    	
    	float x = 1;
    	float y = 1;
    	String name = "China";
    	String color = "black";
    	float radius = 1;
    	
    	City city = new City(x, y, name, color, radius);
    	City city2 = new City(200, 200, "usa", color, radius);
    	p.head = p.mapCity(city);
    	p.head = p.mapCity(city2);
		
		System.out.println(p.head.getType());
		
		GrayNode gray = (GrayNode)p.head;
		for (int i = 0; i < 4; i++) {
			System.out.println(gray.child[i].getType());
		}
//		System.out.println(p.head.getCity());
		
	}
}
