package cmsc420.meeshquest.part1.structure.PRQT;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;

public class WhiteNode implements PRQT {
	private int left;
	private int right;
	private int top;
	private int bottom;
	
	public WhiteNode(int right, int left, int top, int bottom) {
		setRight(right);
		setLeft(left);
		setTop(top);
		setBottom(bottom);
	}
	
	public WhiteNode(PRQT prqt) {
		setRight(prqt.getRight());
		setLeft(prqt.getLeft());
		setTop(prqt.getTop());
		setBottom(prqt.getBottom());
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
	public String getType() {
		return "white";
	}

	@Override
	public City getCity() {
		return null;
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
	
	public void clear() {}
	
	public String toString() {
		return "White, left: " + left + "top: " + top + "right: " + right + "bottom: " + bottom;
	}
}
