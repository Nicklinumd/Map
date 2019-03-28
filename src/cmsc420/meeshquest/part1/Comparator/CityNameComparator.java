package cmsc420.meeshquest.part1.Comparator;

import java.util.Comparator;

import cmsc420.meeshquest.part1.structure.City;

public class CityNameComparator implements Comparator<City> {

	@Override
	public int compare(City c1, City c2) {
		return c1.getName().compareTo(c2.getName());
	}
}
