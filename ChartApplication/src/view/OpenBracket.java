package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class OpenBracket extends Icon {
	private static final long serialVersionUID = 1L;
	private Point point;
	private Shape icon;
	private Point outputPoint;

	public OpenBracket(Point point) {
		dots = new ArrayList<DotConnector>();
		this.point = point;
		outputPoint = new Point((int) point.getX() + 80, (int) point.getY() + 20);
		dots.add(new DotConnector(outputPoint, false, this));
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
		Graphics2D graphics2 = (Graphics2D) graphic;
		outputPoint.setLocation(point.getX() + 80, point.getY() + 20);
		for (DotConnector dot : dots) {
			dot.drawShape();
		}
		graphics2.setFont(new Font("Monospaced", Font.BOLD, 32));
		graphics2.drawString("(", (int) point.getX() + 35, (int) point.getY() + 35);
		icon = new Ellipse2D.Double(this.point.getX(), this.point.getY(), 100, 50);
		graphics2.draw(icon);
	}

	@Override
	public boolean containsIcon(Point point) {
		return icon.contains(point);
	}

}
