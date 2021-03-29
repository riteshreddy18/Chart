package view;

import java.io.Serializable;

import javax.swing.JButton;

import java.awt.Point;
import java.awt.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.TabList;


public class DotConnector extends JButton implements Serializable, MouseListener {

	private Point point;
	private Shape shape;
	private boolean isInput;
	private static final long serialVersionUID = 1L;

	public DotConnector(Point point, boolean isInput, Icon icon) {
		this.point = point;
		this.isInput = isInput;
		drawShape();
		setContentAreaFilled(false);
		TabList.getPresentInstance().getTab().getWorkArea().add(this);
		addActionListener(icon);
	}
	
	public Point getLocation() {
		return point;
	}

	public void setLocation(Point point) {
		this.point = point;
	}

	public boolean isInput() {
		return isInput;
	}

	public void setInput(boolean isInput) {
		this.isInput = isInput;
	}

	public void drawShape() {
		this.setBounds((int) point.getX(), (int) point.getY(), 10, 10);
	}


	public void addActionListener(Icon icon) {
		this.addActionListener(new DotBarLinkage(icon, point, isInput));
		this.addMouseListener(this);
	}
	
	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}

		return shape.contains(x, y);
	}

	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(getBackground());
		} else {
			g.setColor(Color.BLACK);
		}

		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		super.paintComponent(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
