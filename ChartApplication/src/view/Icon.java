package view;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import model.TabList;


@SuppressWarnings("deprecation")
public abstract class Icon extends Observable implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isFirstConnection = false;
	
	protected List<DotConnector> dots;
	protected List<BarConnector> bars;
	protected String description = "";
	
	public abstract void drawShape(Graphics graphic);
	public abstract boolean containsIcon(Point point);
	public abstract Point getLocation();
	public abstract void setLocation(Point point);

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFirstConnection() {
		return isFirstConnection;
	}

	public void setFirstConnection(boolean isFirstConnection) {
		this.isFirstConnection = isFirstConnection;
	}
	
	public List<BarConnector> getBars() {
		return bars;
	}

	public void setBars(List<BarConnector> bars) {
		this.bars = bars;
	}
	

	public List<DotConnector> getDots() {
		return dots;
	}

	public void setDots(List<DotConnector> dots) {
		this.dots = dots;
	}

	public void removeDots() {
		if (dots != null) {
			for (DotConnector dot : dots) {
				TabList.getPresentInstance().getTab().getWorkArea().remove(dot);
			}
		}
		if (bars != null) {
			for (BarConnector bar : bars) {
				TabList.getPresentInstance().getTab().getWorkArea().remove(bar);
			}
		}
	}
	
}
