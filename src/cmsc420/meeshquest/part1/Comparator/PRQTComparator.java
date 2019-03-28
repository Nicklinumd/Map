package cmsc420.meeshquest.part1.Comparator;

import java.awt.geom.Line2D;
import java.util.Comparator;

import cmsc420.meeshquest.part1.structure.City;
import cmsc420.meeshquest.part1.structure.PRQT.GrayNode;
import cmsc420.meeshquest.part1.structure.PRQT.PRQT;

public class PRQTComparator implements Comparator<PRQT> {
	private int x;
	private int y;
	
	public PRQTComparator(int x, int y) {
		setPoint(x, y);
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compare(PRQT p1, PRQT p2) {
		if (getDistance(p1) >= 0 && getDistance(p2) >= 0) {
			if (getDistance(p1) > getDistance(p2)) {
				return 1;
			} else if(getDistance(p1) < getDistance(p2)) {
				return -1;
			} else {
				return 0;
			}
		} else if (getDistance(p1) >= 0) {
			return -1;
		} else if (getDistance(p2) >= 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private double getDistance(PRQT head) {
		if (head.getType().equals("black")) {
			City city = head.getCity();
			return Math.sqrt(Math.pow(Math.abs(city.getX() - x), 2) + Math.pow(Math.abs(city.getY() - y), 2));
		} else if (head.getType().equals("gray")) {
			GrayNode gray = (GrayNode)head;
			double top = (double)gray.getTop() - 1;
			double left = (double)gray.getLeft();
			double right = (double)gray.getRight() - 1;
			double bottom = (double)gray.getBottom();
			Line2D l1 = new Line2D.Double();
			Line2D l2 = new Line2D.Double();
			Line2D l3 = new Line2D.Double();
			Line2D l4 = new Line2D.Double();
			l1.setLine(left, top, right, top);
			l2.setLine(right, top, right, bottom);
			l3.setLine(left, top, left, bottom);
			l4.setLine(left, bottom, right, bottom);
			
			double d1 = l1.ptLineDist(x, y);
			double d2 = l2.ptLineDist(x, y);
			double d3 = l3.ptLineDist(x, y);
			double d4 = l4.ptLineDist(x, y);
			double min = d1;
			if (d2 < min) {
				min = d2;
			}
			if (d3 < min) {
				min = d3;
			}
			if (d4 < min) {
				min = d4;
			}
			return min;
		}
		else {
			return -1;
		}
	}
}
