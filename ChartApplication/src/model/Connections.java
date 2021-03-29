package model;


import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.io.Serializable;


import view.Icon;

public class Connections implements Serializable {

	private static final long serialVersionUID = 1L;
	private Point originPoint, destPoint;
	private List<Icon> icons;

	public Connections() {
		icons = new ArrayList<Icon>();
		icons.add(null);
		icons.add(null);
	}

	public Icon getOriginIcon() {
		return icons.get(0);
	}

	public void setOriginIcon(Icon originShape) {
		this.icons.set(0, originShape);
	}
	
	public Point getDestPoint() {
		return destPoint;
	}

	public void setDestPoint(Point destPoint) {
		this.destPoint = destPoint;
	}

	public Icon getDestIcon() {
		return icons.get(1);
	}
	
	public void setDestIcon(Icon destShape) {
		this.icons.set(1, destShape);
	}

	public Point getOriginPoint() {
		return originPoint;
	}

	public void setOriginPoint(Point originPoint) {
		this.originPoint = originPoint;
	}

}
