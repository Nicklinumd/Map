package cmsc420.meeshquest.part1.structure.PRQT;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;

public class BlackNode implements PRQT {
	private City city;
	private int left;
	private int right;
	private int top;
	private int bottom;
	
	public BlackNode(int right, int left, int top, int bottom, City city) {
		setRight(right);
		setLeft(left);
		setTop(top);
		setBottom(bottom);
		setCity(city);
	}
	
	public BlackNode(PRQT prqt) {
		this(prqt.getRight(), prqt.getLeft(), prqt.getTop(), prqt.getBottom(), prqt.getCity());
	}
	
	public BlackNode(PRQT prqt, City city) {
		this(prqt);
		setCity(city);
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	
	public CityCoordinates getMidPoint() {
		CityCoordinates coord = new CityCoordinates((top - bottom) / 2, (right - left) / 2, null);
		return coord;
	}

	@Override
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	@Override
	public String getType() {
		return "black";
	}
	
	@Override
	public int getX() {
		return (right - left) / 2 + left;
	}

	@Override
	public int getY() {
		return (top - bottom) / 2 + bottom;
	}
	
	public int getRadius() {
		return (top - bottom) / 2;
	}
	
	public String toString() {
		return "Black, left: " + left + "top: " + top + "right: " + right + "bottom: " + bottom;
	}
	
	public void clear() {
		this.city = null;
	}
}
