package controller;

import java.awt.Component;
import java.awt.Point;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;
import view.BarToBarLinkage;
import view.Icon;
import view.WorkArea;
import model.Connections;
import model.IconStore;
import model.Tab;
import model.TabList;


@SuppressWarnings("deprecation")
public class WorkAreaController implements Observer {

	private IconStore iconStore;
	private JTabbedPane tabbedPane;

	public WorkAreaController() {
		iconStore = new IconStore();
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	private void createWorkspace() {
		TabList tabList = TabList.getPresentInstance();
		WorkArea workArea = new WorkArea();
		tabList.addTab(workArea);
		WorkAreaController workAreaController = new WorkAreaController();
		workAreaController.setTabbedPane(tabbedPane);
		tabList.getRecentTab().setWorkAreaController(workAreaController);
		tabList.getRecentTab().addObserver(workAreaController);
		tabbedPane.add("Tab " + tabList.getSize(), workArea);
	}

	@Override
	public void update(Observable o, Object arg) {

			if (arg == "Clicked") {
				newShape();
			}else if (arg == "DoubleClicked") {
				doubleClick();
			}else if (arg == "Pressed") {
				setSelectedIcon();
			} else if (arg == "Dragged") {
				iconDragged();
			} else if (arg == "Drawline") {
				drawLine(true);
			} else if (arg == "DrawTempLine") {
				drawLine(false);
			}else if (arg == "BarClicked") {
				deleteAllConnections();
			}
		repaint();
	}


	private void deleteAllConnections() {
		Tab tab = TabList.getPresentInstance().getTab();
		ListIterator<Connections> iterator = tab.getConnectionsList().listIterator();
			while (iterator.hasNext()) {
				Connections conn = iterator.next();
				if ((conn.getDestPoint().distance(tab.getPoint()) <= 30
						|| conn.getOriginPoint().distance(tab.getPoint()) <= 30)) {
					enableButtons(conn.getDestPoint());
					enableButtons(conn.getOriginPoint());
					iterator.remove();
				}
			}
	}

	private void enableButtons(Point connection) {
		Component[] componentAt = TabList.getPresentInstance().getTab().getWorkArea().getComponents();
		for (Component c : componentAt) {
			if (c.getX() == connection.getX() && c.getY() == connection.getY()) {
				c.setEnabled(true);
			}
		}
	}

	private void setSelectedIcon() {
		Tab tab = TabList.getPresentInstance().getTab();
		Icon icon = searchIcons(tab);
		if (icon != null) {
			tab.setSelectedIcon(icon);
		}
	}

	private void repaint() {
		TabList.getPresentInstance().getTab().getWorkArea().repaint();
	}

	private void iconDragged() {
		Tab tab = TabList.getPresentInstance().getTab();
		Icon selected = tab.getSelectedIcon();
		if (selected != null) {
			selected.setLocation(tab.getPoint());
		}
	}

	private void newShape() {
		Tab tab = TabList.getPresentInstance().getTab();
		if (searchIcons(tab) != null) {
			return;
		}
		if (!tab.isMoving()) {
			Icon drawnIcon = iconStore.getIconObject(tab.getPoint(), tab.getSelectedOption());
			if (drawnIcon != null) {
				drawnIcon.drawShape(tab.getWorkArea().getGraphics());
				tab.addIcon(drawnIcon);
			}
		} else {
			tab.setIsClicked(false);
			tab.setMoving(false);
			tab.getWorkArea().setDefaultCursor();
		}

	}

	private void drawLine(boolean isFinalLine) {
		Tab tab = TabList.getPresentInstance().getTab();
		Connections connection = new Connections();
		connection.setOriginIcon(tab.getOriginIcon());
		connection.setDestIcon(tab.getDestIcon());
		connection.setOriginPoint(tab.getOriginPoint());
		connection.setDestPoint(tab.getDestPoint());
		if (isFinalLine) {
			setLine(tab, connection);
			tab.setMoving(false);
			tab.getWorkArea().setDefaultCursor();
		} else {
			tab.setMoving(true);
			tab.getWorkArea().setCrossHairCursor();
		}
	}

	private void setLine(Tab tab, Connections connection) {
		if ((tab.isOriginInput() && !tab.isDestinationInput()) || (!tab.isOriginInput() && tab.isDestinationInput())) {
			tab.getConnectionsList().add(connection);
			tab.setIsClicked(false);
			if (!(tab.getOriginIcon() instanceof BarToBarLinkage)) {
				tab.getOrigin().setEnabled(false);
			}
			if (!(tab.getDestIcon() instanceof BarToBarLinkage)) {
				tab.getDestination().setEnabled(false);
			}
		}
	}

	private void doubleClick() {
		Tab tab = TabList.getPresentInstance().getTab();
		Icon icon = searchIcons(tab);
		if (icon != null) {
			String description = tab.getWorkArea().getInputString(icon.getDescription());
			icon.setDescription(description);
		}
	}

	private Icon searchIcons(Tab tab) {
		ListIterator<Icon> listIterator = tab.getIconList().listIterator();
		while (listIterator.hasNext()) {
			Icon icon = listIterator.next();
			if (icon.containsIcon(tab.getPoint())) {
				return icon;
			}
		}
		return null;
	}

}