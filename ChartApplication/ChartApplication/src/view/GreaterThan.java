package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GreaterThan extends Icon {
	private static final long serialVersionUID = 1L;
	private Point point;
	private Shape icon;
	private Point inputPoint1, inputPoint2, outputPoint;

	public GreaterThan(Point point) {
		dots = new ArrayList<DotConnector>();
		this.point = point;
		inputPoint1 = new Point((int) point.getX() + 10, (int) point.getY() + 10);
		inputPoint2 = new Point((int) point.getX() + 10, (int) point.getY() + 30);
		outputPoint = new Point((int) point.getX() + 80, (int) point.getY() + 20);
		dots.add(new DotConnector(inputPoint1, true, this));
		dots.add(new DotConnector(inputPoint2, true, this));
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
		inputPoint1.setLocation(point.getX() + 10, point.getY() + 10);
		inputPoint2.setLocation(point.getX() + 10, point.getY() + 30);
		outputPoint.setLocation(point.getX() + 80, point.getY() + 20);
		for (DotConnector dot : dots) {
			dot.drawShape();
		}
		graphics2.setFont(new Font("Monospaced", Font.BOLD, 32));
		graphics2.drawString(">", (int) point.getX() + 40, (int) point.getY() + 35);
		icon = new Ellipse2D.Double(this.point.getX(), this.point.getY(), 100, 50);
		graphics2.draw(icon);
	}

	@Override
	public boolean containsIcon(Point point) {
		return icon.contains(point);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


}
