package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JButton;
import model.TabList;

public class BarConnector extends JButton implements Serializable, MouseListener {
	private static final long serialVersionUID = 1L;
	private Point point;
	private boolean isInput;
	
	public boolean isInput() {
		return isInput;
	}

	public void setInput(boolean isInput) {
		this.isInput = isInput;
	}

	public BarConnector(Point point, boolean isInput, Icon icon) {
		this.point = point;
		this.isInput = isInput;
		this.setBorderPainted(false);
		this.setBackground(Color.BLACK);
		this.setOpaque(true);
		TabList.getPresentInstance().getTab().getWorkArea().add(this);
		addActionListener(icon);
	}

	public Point getLocation() {
		return point;
	}

	public void setLocation(Point point) {
		this.point = point;
	}

	public void drawShape() {
		this.setBounds((int) point.getX(), (int) point.getY(), 10, 28);
	}

	public boolean containsPoint(Point point) {
		return this.contains(point);
	}

	public void addActionListener(Icon icon) {
		this.addActionListener(new DotBarLinkage(icon, point, isInput));
		this.addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//// TODO Auto-generated method stub
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
