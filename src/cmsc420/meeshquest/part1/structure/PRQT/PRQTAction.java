package cmsc420.meeshquest.part1.structure.PRQT;

import java.awt.Color;
import java.io.IOException;
import java.util.PriorityQueue;

import cmsc420.drawing.CanvasPlus;
import cmsc420.meeshquest.part1.CityResult;
import cmsc420.meeshquest.part1.Comparator.PRQTComparator;
import cmsc420.meeshquest.part1.structure.City;

public class PRQTAction {
	public PRQT head;
	private int width;
	private int height;
	public CanvasPlus canvas;

	public PRQTAction(int width, int height) {
		head = new WhiteNode(width, 0, height, 0);
		setWidth(width);
		setHeight(height);
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

	public PRQT mapCity(City city) {
		return mapCity(city, head);
	}
	
	private PRQT mapCity(City city, PRQT head) {
		if (head.getType().equals("white")) {
			head = new BlackNode(head, city);
		} else if (head.getType().equals("black")) {
			City oldCity = head.getCity();
			if (sameCity(oldCity, city)) {
				return head;
			} else {
				GrayNode gray = new GrayNode(head);
				head = mapCity(city, mapCity(oldCity, gray));
			}
		} else {
			int direction = getDirection(head, city);
			GrayNode gray = (GrayNode) head;
			gray.child[direction] = mapCity(city, gray.child[direction]);
			head = gray;
		}
		return head;
	}
	
	public boolean exist(City city) {
		return exist(city, head);
	}
	
	private boolean exist(City city, PRQT head) {
		if (head.getType().equals("white")) {
			return false;
		} else if (head.getType().equals("black")) {
			if (sameCity(head.getCity(), city)) {
				return true;
			} else {
				return false;
			}
		} else {
			GrayNode gray = (GrayNode) head;
			int direction = getDirection(gray, city);
			return exist(city, gray.child[direction]);
		}
	}
	
	public void unmapCity(City city) {
		head = unmapCity(city, head);
	}

	private PRQT unmapCity(City city, PRQT head) {
		if (head.getType().equals("white")) {
			return head;
		} else if (head.getType().equals("black")) {
			return new WhiteNode(head);
		} else {
			int direction = getDirection(head, city);
			GrayNode gray = (GrayNode) head;
			gray.child[direction] = unmapCity(city, gray.child[direction]);
			int child = -1;
			for (int i = 0; i < 4; i++) {
				if (gray.child[i].getType().equals("black")) {
					if (child == -1) {
						child = i;
					} else {
						child = -2;
					}
				} else if (gray.child[i].getType().equals("gray")) {
					child = -2;
				}
			}
			if (child > -1) {
				head = new BlackNode(gray, gray.child[child].getCity());
			} else {
				head = gray;
			}
			return head;
		}
	}
	
	public void printPRQT() {		
		if (head.getType().equals("white")) {
			CityResult.printPRQTResult("mapIsEmpty", null);
		} else {
			CityResult.printPRQTResult("success", head);
		}
	}
	
	public void rangeCities(int x, int y, int radius, String name) throws IOException {
		rangeCities(x, y, radius);
		if (name != "") {
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
		PRQTComparator c = new PRQTComparator(x, y);
		PriorityQueue<PRQT> pq = new PriorityQueue<PRQT>(100, c);
		if (head.getType() != "white") {
			pq.add(head);
			while (keepLooping && pq.size() > 0) {
				if (pq.peek().getType().equals("black")) {
					if (inRange(pq.peek().getCity(), x, y, radius)) {
						CityResult.addCityList(pq.poll().getCity());
					} else {
						keepLooping = false;
					}
				} else if (pq.peek().getType().equals("gray")) {
					GrayNode gray = (GrayNode)pq.poll();
					for (int i = 0; i < 4; i++) {
						pq.add(gray.child[i]);
					}
				}
			}
		}
	}
	
	private boolean inRange(City city, int x, int y, int radius) {
		double distance = Math.sqrt(Math.pow(Math.abs(city.getX() - x), 2) + Math.pow(Math.abs(city.getY() - y), 2));
		return distance <= radius ? true : false;
	}
	
	public void saveMap(String name) throws IOException {
		addPoint(head, canvas);
		canvas.save(name);
		CityResult.saveMapResult("success", name);
	}
	
	private void addPoint(PRQT head, CanvasPlus canvas) {
		if (head.getType().equals("black")) {
			City city = head.getCity();
			canvas.addPoint(city.getName(), city.getX(), city.getY(), Color.BLACK);
		} else if (head.getType().equals("gray")) {
			GrayNode gray = (GrayNode) head;
			canvas.addCross(gray.getX(), gray.getY(), gray.getRadius(), Color.BLACK);
			for (int i = 0; i < 4; i++) {
				addPoint(gray.child[i], canvas);
			}
		}
	}
	
	public void nearestCity(int x, int y) {
		if (head.getType().equals("white")) {
			CityResult.nearestCityResult("mapIsEmpty", x, y, null);
		} else {
			boolean keepLooping = true;
			PRQTComparator c = new PRQTComparator(x, y);
			PriorityQueue<PRQT> pq = new PriorityQueue<PRQT>(100, c);
			pq.add(head);
			while (keepLooping) {
				if (pq.peek().getType().equals("black")) {
					CityResult.nearestCityResult("success", x, y, pq.poll().getCity());
					keepLooping = false;
				} else if (pq.peek().getType().equals("gray")) {
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
	
	private boolean sameCity(City c1, City c2) {
		if (c1.getName().equals(c2.getName()) && c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
			return true;
		} else {
			return false;
		}
	}

	public void end() {
		canvas.dispose();
	}
}
