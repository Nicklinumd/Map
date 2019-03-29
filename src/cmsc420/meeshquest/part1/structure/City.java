package cmsc420.meeshquest.part1.structure;

import java.awt.geom.*;

/* 
 * City object
 * Contains name, coordinates, color, and radius
 */
public class City extends Point2D.Float{
	private String name;
	private String color;
	private float radius;
	
	public City(float x, float y, String name, String color, float radius) {
		super(x, y);
		this.setName(name);
		this.setColor(color);
		this.setRadius(radius);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public String toString() {
		return "name: " + name + "; (x, y): (" + x + ", " + y + "); color: " + color + "; radius: " + radius;
	}
}
