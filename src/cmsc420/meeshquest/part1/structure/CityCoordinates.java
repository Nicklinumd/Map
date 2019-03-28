package cmsc420.meeshquest.part1.structure;

import java.awt.geom.Point2D;

public class CityCoordinates extends Point2D.Float {
	private City city;
	
	public CityCoordinates(float x, float y, City city) {
		super(x, y);
		this.setCity(city);
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
