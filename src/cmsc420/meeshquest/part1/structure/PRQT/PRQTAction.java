package cmsc420.meeshquest.part1.structure.PRQT;

import java.awt.Color;
import java.io.IOException;
import java.util.PriorityQueue;

import cmsc420.drawing.CanvasPlus;
import cmsc420.meeshquest.part1.CityResult;
import cmsc420.meeshquest.part1.Comparator.PRQTComparator;
import cmsc420.meeshquest.part1.structure.City;

/*
 * Handle PR quadtree commands
 * PR quadtree has three kinds of nodes
 * White: empty child
 * Black: child with city
 * Gray: separate into four directions by middle of width and height each children is one kind of node
 */
public class PRQTAction {
	public PRQT head;
	private int width;
	private int height;
	public CanvasPlus canvas;

	public PRQTAction(int width, int height) {
		// Set head to white node
		head = new WhiteNode(width, 0, height, 0);
		setWidth(width);
		setHeight(height);
		// Initialize canvas map for saveMap
		canvas = new CanvasPlus("MeeshQuest");
		canvas.setFrameSize(width, height);
    	canvas.addRectangle(0, 0, width, height, Color.WHITE, true);
    	canvas.addRectangle(0, 0, width, height, Color.BLACK, false);
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/*
	 * Map city to PR quadtree
	 * Return modified head
	 */
	public PRQT mapCity(City city) {
		return mapCity(city, head);
	}
	
	private PRQT mapCity(City city, PRQT head) {
		if (head.getType().equals("white")) {
			// Change white to black node with city
			head = new BlackNode(head, city);
		} else if (head.getType().equals("black")) {
			// Change black node to gray node and add both original city from black node and new city
			City oldCity = head.getCity();
			// New city cannot exist in PR quadtree
			if (sameCity(oldCity, city)) {
				return head;
			} else {
				GrayNode gray = new GrayNode(head);
				// Map both cities
				head = mapCity(city, mapCity(oldCity, gray));
			}
		} else {
			// Recursive call to the corresponding direction based on midpoint
			int direction = getDirection(head, city);
			GrayNode gray = (GrayNode) head;
			gray.child[direction] = mapCity(city, gray.child[direction]);
			head = gray;
		}
		return head;
	}
	
	/*
	 * Return true if city exists in PR quadtree
	 * Else return false
	 */
	public boolean exist(City city) {
		return exist(city, head);
	}
	
	private boolean exist(City city, PRQT head) {
		if (head.getType().equals("white")) {
			// Base case. No city can exist in white
			return false;
		} else if (head.getType().equals("black")) {
			// Check if city is the target
			if (sameCity(head.getCity(), city)) {
				return true;
			} else {
				return false;
			}
		} else {
			// Recursive call to the corresponding direction
			GrayNode gray = (GrayNode) head;
			int direction = getDirection(gray, city);
			return exist(city, gray.child[direction]);
		}
	}
	
	/* 
	 * Unmap city from PR quadtree
	 */
	public void unmapCity(City city) {
		head = unmapCity(city, head);
	}

	private PRQT unmapCity(City city, PRQT head) {
		if (head.getType().equals("white")) {
			// City does not exist
			return head;
		} else if (head.getType().equals("black")) {
			// Change black node to white node
			return new WhiteNode(head);
		} else {
			// Recursive call to the direction
			int direction = getDirection(head, city);
			GrayNode gray = (GrayNode) head;
			gray.child[direction] = unmapCity(city, gray.child[direction]);
			// Modify this node according to deletion
			int child = -1;
			for (int i = 0; i < 4; i++) {
				if (gray.child[i].getType().equals("black")) {
					if (child == -1) {
						// First black node
						child = i;
					} else {
						// More than one black node
						child = -2;
					}
				} else if (gray.child[i].getType().equals("gray")) {
					child = -2;
				}
			}
			if (child > -1) {
				// One black node and three white nodes
				head = new BlackNode(gray, gray.child[child].getCity());
			} else if (child == -1) {
				// All children are white nodes
				head = new WhiteNode(head);
			} else {
				// Stays gray
				head = gray;
			}
			return head;
		}
	}
	
	/*
	 * Print PR quadtree
	 * PR quadtree cannot be empty
	 */
	public void printPRQT() {		
		if (head.getType().equals("white")) {
			CityResult.printPRQTResult("mapIsEmpty", null);
		} else {
			CityResult.printPRQTResult("success", head);
		}
	}
	
	/*
	 * Find cities within range defined by a point and a radius
	 * Use a PriorityQueue to keep track of nearest node to the point
	 * If the nearest node is black,
	 * 	If it's city is in range, add it's city to list and move to next node
	 * 	Else the closest city is out of bounds, stop looping
	 * If the nearest node is gray, Add all of it's children to PriorityQueue
	 * If the nearest node is white, no cities left, stop looping
	 * If name is not empty, save to a map with that name
	 * Throws IOException
	 */
	public void rangeCities(int x, int y, int radius, String name) throws IOException {
		rangeCities(x, y, radius);
		if (name != "") {
			// Construct map and add the circle
			CanvasPlus canvas1 = new CanvasPlus("MeeshQuest");
			canvas1.setFrameSize(width, height);
			canvas1.addRectangle(0, 0, width, height, Color.WHITE, true);
			canvas1.addRectangle(0, 0, width, height, Color.BLACK, false);
			canvas1.addCircle(x, y, radius, Color.BLUE, false);
			addPoint(head, canvas1);
			canvas1.save(name);
		}
		CityResult.rangeCitiesResult(x, y, radius, name);
	}
	
	private void rangeCities(int x, int y, int radius) {
		boolean keepLooping = true;
		// Use PRQTComparator to compare distance from point to each node (See PRQTComparator.java)
		PRQTComparator c = new PRQTComparator(x, y);
		// Use PriorityQueue to keep track of the closest node
		PriorityQueue<PRQT> pq = new PriorityQueue<PRQT>(100, c);
		if (head.getType() != "white") {
			pq.add(head);
			while (keepLooping && pq.size() > 0) {
				if (pq.peek().getType().equals("black")) {
					// If the closest node is black, then the city is in range
					if (inRange(pq.peek().getCity(), x, y, radius)) {
						CityResult.addCityList(pq.poll().getCity());
					} else {
						// Stop looping if the closest city is out of range
						keepLooping = false;
					}
				} else if (pq.peek().getType().equals("gray")) {
					// Add all children of this node to priority queue and poll this node
					GrayNode gray = (GrayNode)pq.poll();
					for (int i = 0; i < 4; i++) {
						pq.add(gray.child[i]);
					}
				} else {
					// No cities left, stop looping
					keepLooping = false;
				}
			}
		}
	}
	
	/*
	 * Helper method for checking if the city is in range
	 */
	private boolean inRange(City city, int x, int y, int radius) {
		double distance = Math.sqrt(Math.pow(Math.abs(city.getX() - x), 2) + Math.pow(Math.abs(city.getY() - y), 2));
		return distance <= radius ? true : false;
	}
	
	/*
	 * Save the current PR quadtree to map
	 */
	public void saveMap(String name) throws IOException {
		addPoint(head, canvas);
		canvas.save(name);
		CityResult.saveMapResult("success", name);
	}
	
	/*
	 * Helper method for save map, add points to the map
	 */
	private void addPoint(PRQT head, CanvasPlus canvas) {
		if (head.getType().equals("black")) {
			City city = head.getCity();
			canvas.addPoint(city.getName(), city.getX(), city.getY(), Color.BLACK);
		} else if (head.getType().equals("gray")) {
			// Add each of the children to map
			GrayNode gray = (GrayNode) head;
			canvas.addCross(gray.getX(), gray.getY(), gray.getRadius(), Color.BLACK);
			for (int i = 0; i < 4; i++) {
				addPoint(gray.child[i], canvas);
			}
		}
	}
	
	/*
	 * Almost same algorithm as rangeCities
	 * Stop when find the closest city
	 */
	public void nearestCity(int x, int y) {
		if (head.getType().equals("white")) {
			// No city in PR quadtree
			CityResult.nearestCityResult("mapIsEmpty", x, y, null);
		} else {
			boolean keepLooping = true;
			PRQTComparator c = new PRQTComparator(x, y);
			PriorityQueue<PRQT> pq = new PriorityQueue<PRQT>(100, c);
			pq.add(head);
			while (keepLooping) {
				if (pq.peek().getType().equals("black")) {
					// City found
					CityResult.nearestCityResult("success", x, y, pq.poll().getCity());
					keepLooping = false;
				} else if (pq.peek().getType().equals("gray")) {
					// Add each child to priority queue
					GrayNode gray = (GrayNode)pq.poll();
					for (int i = 0; i < 4; i++) {
						pq.add(gray.child[i]);
					}
				} else {
					keepLooping = false;
				}
			}
		}
	}
	
	/*
	 * Delete all nodes and cities
	 */
	public void clearAll() {
		clearAll(head);
		head = new WhiteNode(width, 0, height, 0);
	}
	
	private void clearAll(PRQT head) {
		if (head.getType().equals("gray")) {
			GrayNode gray = (GrayNode) head;
			for (int i = 0; i < 4; i++) {
				clearAll(gray.child[i]);
			}
		}
		head.clear();
		head = null;
	}
	
	/*
	 * Get direction of city based on midpoint of node
	 */
	private int getDirection(PRQT prqt, City city) {
		if (prqt.getY() > city.getY()) {
			if (prqt.getX() > city.getX()) {
				return 2;
			} else {
				return 3;
			}
		} else {
			if (prqt.getX() > city.getX()) {
				return 0;
			} else {
				return 1;
			}
		}
	}
	
	/*
	 * Helper method to check if two cities are the same
	 */
	private boolean sameCity(City c1, City c2) {
		if (c1.getName().equals(c2.getName()) && c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
			return true;
		} else {
			return false;
		}
	}

	/* 
	 * Terminate canvas
	 */
	public void end() {
		canvas.dispose();
	}
}
