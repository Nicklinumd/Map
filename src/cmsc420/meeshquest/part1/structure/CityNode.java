package cmsc420.meeshquest.part1.structure;

/*
 * City node for bst
 */
public class CityNode {
	private City city;
	public CityNode left;
	public CityNode right;
	
	public String getName() {
		return city.getName();
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	public void clear() {
		city = null;
	}
}