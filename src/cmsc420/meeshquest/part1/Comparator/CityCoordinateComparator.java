package cmsc420.meeshquest.part1.Comparator;

import java.util.Comparator;

import cmsc420.meeshquest.part1.structure.City;

public class CityCoordinateComparator implements Comparator<City> {
	@Override
	public int compare(City c1, City c2) {
		if (c1.getY() > c2.getY()) {
			return 1;
		} else if (c1.getY() < c2.getY()) {
			return -1;
		} else {
			if (c1.getX() > c2.getX()) {
				return 1;
			} else if (c1.getX() < c2.getX()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
