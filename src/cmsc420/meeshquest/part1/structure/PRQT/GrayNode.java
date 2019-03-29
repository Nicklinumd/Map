package cmsc420.meeshquest.part1.structure.PRQT;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;

/*
 * Gray Node for PR quadtree
 * Contains four edges and four children
 * child 0: top left
 * child 1: top right
 * child 2: bottom left
 * child 3: bottom right
 */
public class GrayNode implements PRQT {
	public PRQT[] child = new PRQT[4];
	private int left;
	private int right;
	private int top;
	private int bottom;
	
	/*
	 * Basic constructor
	 */
	public GrayNode(int right, int left, int top, int bottom) {
		setRight(right);
		setLeft(left);
		setTop(top);
		setBottom(bottom);
		// Initialize four white node as children
		child[0] = new WhiteNode(getX(), left, top, getY());
		child[1] = new WhiteNode(right, getX(), top, getY());
		child[2] = new WhiteNode(getX(), left, getY(), bottom);
		child[3] = new WhiteNode(right, getX(), getY(), bottom);
	}
	
	/*
	 * Constructor for same edges
	 */
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
	
	/*
	 * Helper method for getting half of width(non-Javadoc)
	 * @see cmsc420.meeshquest.part1.structure.PRQT.PRQT#getX()
	 */
	@Override
	public int getX() {
		return (right - left) / 2 + left;
	}

	/*
	 * Helper method for getting half of height(non-Javadoc)
	 * @see cmsc420.meeshquest.part1.structure.PRQT.PRQT#getY()
	 */
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

	/*
	 * Get city
	 * No city in gray node(non-Javadoc)
	 * @see cmsc420.meeshquest.part1.structure.PRQT.PRQT#getCity()
	 */
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
