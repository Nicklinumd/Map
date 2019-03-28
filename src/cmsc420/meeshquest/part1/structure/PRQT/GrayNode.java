package cmsc420.meeshquest.part1.structure.PRQT;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;

public class GrayNode implements PRQT {
	public PRQT[] child = new PRQT[4];
	private int left;
	private int right;
	private int top;
	private int bottom;
	
	public GrayNode(int right, int left, int top, int bottom) {
		setRight(right);
		setLeft(left);
		setTop(top);
		setBottom(bottom);
		child[0] = new WhiteNode(getX(), left, top, getY());
		child[1] = new WhiteNode(right, getX(), top, getY());
		child[2] = new WhiteNode(getX(), left, getY(), bottom);
		child[3] = new WhiteNode(right, getX(), getY(), bottom);
	}
	
	public GrayNode(PRQT prqt) {
		this(prqt.getRight(), prqt.getLeft(), prqt.getTop(), prqt.getBottom());
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
	
	@Override
	public String getType() {
		return "gray";
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

	@Override
	public CityCoordinates getMidPoint() {
		CityCoordinates coord = new CityCoordinates((top - bottom) / 2, (right - left) / 2, null);
		return coord;
	}

	@Override
	public City getCity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return "Gray, left: " + left + "top: " + top + "right: " + right + "bottom: " + bottom;
	}
	
	public void clear() {
		child = null;
	}
}
