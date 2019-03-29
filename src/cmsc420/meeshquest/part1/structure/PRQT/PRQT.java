package cmsc420.meeshquest.part1.structure.PRQT;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.CityCoordinates;

/*
 * Interface for nodes
 */
public interface PRQT {	
	public int getLeft();

	public void setLeft(int left);

	public int getRight();

	public void setRight(int right);

	public int getTop();

	public void setTop(int top);

	public int getBottom();
	
	public CityCoordinates getMidPoint();
	
	public int getX();
	
	public int getY();
	
	public int getRadius();
	
	/* 
	 * Return city object
	 * Only valid for black node
	 */
	public City getCity();
	
	/*
	 * Return type of the node
	 */
	public String getType();
	
	public String toString();

	public void clear();
}