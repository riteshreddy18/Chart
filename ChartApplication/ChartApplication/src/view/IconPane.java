package view;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Tab;
import model.TabList;


public class IconPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean isMoving = false;

	private List<JButton> shapes = new ArrayList<>();

	public IconPane() {
		initializeButtons();
		addActionListenersToButtons();
	}

	private void initializeButtons() {		
		JButton greaterThan = new JButton(new ImageIcon("Resources//greaterThan.png"));
		greaterThan.setName(TabList.getPresentInstance().getGreaterThan());
		greaterThan.setOpaque(false);
		greaterThan.setContentAreaFilled(false);
		greaterThan.setBorderPainted(false);
		shapes.add(greaterThan);
		

		JButton lessThan = new JButton(new ImageIcon("Resources//lessThan.png"));
		lessThan.setName(TabList.getPresentInstance().getLessThan());
		lessThan.setOpaque(false);
		lessThan.setContentAreaFilled(false);
		lessThan.setBorderPainted(false);
		shapes.add(lessThan);
		
		JButton bars = new JButton(new ImageIcon("Resources//bars.png"));
		bars.setName(TabList.getPresentInstance().getBars());
		bars.setOpaque(false);
		bars.setContentAreaFilled(false);
		bars.setBorderPainted(false);
		shapes.add(bars);
		

		JButton atTheRate = new JButton(new ImageIcon("Resources//atTheRate.png"));
		atTheRate.setName(TabList.getPresentInstance().getAtTheRate());
		atTheRate.setOpaque(false);
		atTheRate.setContentAreaFilled(false);
		atTheRate.setBorderPainted(false);
		shapes.add(atTheRate);

		JButton hyphen = new JButton(new ImageIcon("Resources//hyphen.png"));
		hyphen.setName(TabList.getPresentInstance().getHyphen());
		hyphen.setOpaque(false);
		hyphen.setContentAreaFilled(false);
		hyphen.setBorderPainted(false);
		shapes.add(hyphen);
	}
	
    public void setOpenBracket() {
    	TabList.getPresentInstance().getTab().setSelectedOption(TabList.getPresentInstance().getOpenBracket());
		Point convertPoint = new Point(50,50);
		TabList.getPresentInstance().getTab().setPoint(convertPoint, "Clicked");
		TabList.getPresentInstance().getTab().setSelectedOption(null);
    }
    
    public void setCloseBracket() {
		TabList.getPresentInstance().getTab().setSelectedOption(TabList.getPresentInstance().getCloseBracket());
		Point convertPoint = new Point(1000,600);
		TabList.getPresentInstance().getTab().setPoint(convertPoint, "Clicked");
		TabList.getPresentInstance().getTab().setSelectedOption(null);
    }

	private void addActionListenersToButtons() {
		ListIterator<JButton> listIterator = shapes.listIterator();
		Box box = Box.createVerticalBox();
		while (listIterator.hasNext()) {
			JButton button = listIterator.next();
			box.add(Box.createVerticalStrut(30));
			button.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					if (!isMoving) {
						TabList.getPresentInstance().getTab().setSelectedOption(button.getName());
						TabList.getPresentInstance().getTab().getWorkArea().setMovingCursor();
						Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
						setCursor(cursor);
					}
					isMoving = true;
				}
			});
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.getLocationOnScreen().getX() > TabList.getPresentInstance().getTab().getWorkArea().getParent()
							.getLocationOnScreen().getX()) {
						Point convertPoint = SwingUtilities.convertPoint(button, e.getPoint(),
								TabList.getPresentInstance().getTab().getWorkArea());
						TabList.getPresentInstance().getTab().setPoint(convertPoint, "Clicked");
					}
					TabList.getPresentInstance().getTab().setSelectedOption(null);
					isMoving = false;
					TabList.getPresentInstance().getTab().getWorkArea().setDefaultCursor();
					Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
					setCursor(cursor);
				}
			});
			box.add(button);
		}
		this.add(box);
	}
}
