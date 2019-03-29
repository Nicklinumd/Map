package cmsc420.meeshquest.part1.Comparator;

import java.util.Comparator;

import org.w3c.dom.Element;

/* 
 * Compare element by name
 */
public class ElementNameComparator implements Comparator<Element> {

	@Override
	public int compare(Element e1, Element e2) {
		return e1.getAttribute("name").compareTo(e2.getAttribute("name"));
	}

}
