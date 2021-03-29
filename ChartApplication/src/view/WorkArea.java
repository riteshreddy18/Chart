package view;


import java.awt.Point;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import java.util.ArrayList;
import java.io.Serializable;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import model.Tab;
import model.TabList;
import model.Connections;

public class WorkArea extends JPanel implements MouseListener, MouseMotionListener, Serializable {

	private static final long serialVersionUID = 1L;

	public WorkArea() {
		this.setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY());
		TabList.getPresentInstance().getTab().setPoint(point, "Dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (TabList.getPresentInstance().getTab().isClicked()) {
			Point point = new Point(e.getX(), e.getY());
			TabList.getPresentInstance().getTab().setDestPoint(point, "DrawTempLine");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY());
		Tab tab = TabList.getPresentInstance().getTab();
		if (e.getClickCount() == 2) {
			tab.setPoint(point, "DoubleClicked");
		} else {
			tab.setPoint(point, "Clicked");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point point = new Point(e.getX(), e.getY());
		TabList.getPresentInstance().getTab().setPoint(point, "Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		TabList.getPresentInstance().getTab().setSelectedOption(null);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Referenced from https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java/27461352#27461352
	 */
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xpoints, ypoints, 3);

	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Tab tab = TabList.getPresentInstance().getTab();
		ArrayList<Icon> iconList = tab.getIconList();
		ListIterator<Icon> i = iconList.listIterator();

		while (i.hasNext()) {
			Icon nextIcon = i.next();
			nextIcon.drawShape(graphics);
		}

		for (Connections connection : tab.getConnectionsList()) {
			drawArrowLine(graphics, (int) connection.getOriginPoint().getX() + 5,
					(int) connection.getOriginPoint().getY() + 5, (int) connection.getDestPoint().getX() + 5,
					(int) connection.getDestPoint().getY() + 5, 10, 5);
		}

		if (tab.isMoving()) {
			drawArrowLine(graphics, (int) tab.getOriginPoint().getX() + 5, (int) tab.getOriginPoint().getY() + 5,
					(int) tab.getDestPoint().getX(), (int) tab.getDestPoint().getY(), 10, 5);
		}
	}
	

	public void setCrossHairCursor() {
		setCursorMethod(Cursor.CROSSHAIR_CURSOR);
	}

	public void setDefaultCursor() {
		setCursorMethod(Cursor.DEFAULT_CURSOR);
	}

	public void setMovingCursor() {
		setCursorMethod(Cursor.MOVE_CURSOR);
	}

	private void setCursorMethod(int cursorType) {
		Cursor cursor = new Cursor(cursorType);
		this.setCursor(cursor);
	}

	public String getInputString(String setMessage) {
		return (String) JOptionPane.showInputDialog("Enter the description", setMessage);
	}

	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public boolean prompt(String message) {
		int showConfirmDialog = JOptionPane.showConfirmDialog(this, message);
		if (showConfirmDialog == 0) {
			return true;
		}
		return false;
	}
}