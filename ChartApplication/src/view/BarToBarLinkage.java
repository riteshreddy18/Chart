package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BarToBarLinkage extends Icon {
	private static final long serialVersionUID = 1L;
	private Shape icon;
	private Point point, inputPoint, outputPoint;

	public BarToBarLinkage(Point point) {
		bars = new ArrayList<BarConnector>();
		this.point = point;
		inputPoint = new Point((int) point.getX() + 10, (int) point.getY() + 10);
		outputPoint = new Point((int) point.getX() + 80, (int) point.getY() + 10);
		bars.add(new BarConnector(inputPoint, true, this));
		bars.add(new BarConnector(outputPoint, false, this));
	}
	
	@Override
	public Point getLocation() {
		return point;
	}

	@Override
	public void setLocation(Point point) {
		this.point = point;
	}

	@Override
	public void drawShape(Graphics graphic) {
		Graphics2D graphics2d = (Graphics2D) graphic;
		inputPoint.setLocation(point.getX() + 10, point.getY() + 10);
		outputPoint.setLocation(point.getX() + 80, point.getY() + 10);
		for (BarConnector bar : bars) {
			bar.drawShape();
		}
		graphics2d.setFont(new Font("Monospaced", Font.BOLD, 32));
		graphics2d.drawString("||", (int) point.getX() + 31, (int) point.getY() + 32);
		icon = new Ellipse2D.Double(this.point.getX(), this.point.getY(), 100, 50);
		graphics2d.draw(icon);
	}

	@Override
	public boolean containsIcon(Point point) {
		return icon.contains(point);
	}

}
