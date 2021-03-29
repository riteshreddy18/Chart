package model;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;

import controller.WorkAreaController;
import view.Icon;
import view.WorkArea;



@SuppressWarnings("deprecation")
public class Tab extends Observable implements Serializable {
	private static final long serialVersionUID = 1L;
	private String selectedOption = "";
	private JButton origin, destination;
	private WorkArea workArea;
	private WorkAreaController workAreaController;
	private List<Icon> icons;
	private Point point, originPoint, destPoint;
	private Icon originIcon, destIcon;
	private boolean isOriginInput, isDestinationInput;
	private List<Connections> connectionsList;
	private ArrayList<Icon> iconList;
	private boolean clicked = false, moving = false, openBracketPresent = false, closedBracketPresent = false;

	public Tab(WorkArea workArea) {
		iconList = new ArrayList<Icon>();
		setConnectionsList(new ArrayList<Connections>());
		this.workArea = workArea;
		icons = new ArrayList<Icon>();
		for (int i=0;i<3;i++) {
			icons.add(null);
		}
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	public Icon getSelectedIcon() {
		return icons.get(0);
	}
	public void setSelectedIcon(Icon selectedIcon) {
		this.icons.set(0, selectedIcon);
	}
	public void addIcon(Icon icon) {
		iconList.add(icon);
	}

	public ArrayList<Icon> getIconList() {
		return iconList;
	}

	public void setIconList(ArrayList<Icon> iconList) {
		this.iconList = iconList;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point, String operation) {
		this.point = point;
		this.notifyMethod(operation);
	}

	public void setWorkArea(WorkArea workArea) {
		this.workArea = workArea;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setIsClicked(boolean isClicked) {
		this.clicked = isClicked;
	}

	public Icon getOriginIcon() {
		return icons.get(1);
	}

	public void setOriginIcon(Icon originIcon) {
		this.icons.set(1, originIcon);
	}

	public Icon getDestIcon() {
		return icons.get(2);
	}

	public void setDestIcon(Icon destIcon) {
		this.icons.set(2, destIcon);
	}

	public Point getOriginPoint() {
		return originPoint;
	}

	public void setOriginPoint(Point originPoint) {
		this.originPoint = originPoint;
	}
	
	public Icon getOriginIcon1() {
		return originIcon;
	}

	public void setOriginIcon1(Icon originIcon) {
		this.originIcon = originIcon;
	}
	
	public JButton getOrigin() {
		return origin;
	}

	public void setOrigin(JButton origin) {
		this.origin = origin;
	}

	public Point getDestPoint() {
		return destPoint;
	}

	public void setDestPoint(Point destPoint, String operation) {
		this.destPoint = destPoint;
		this.notifyMethod(operation);
	}

	public JButton getDestination() {
		return destination;
	}

	public void setDestination(JButton destDot) {
		this.destination = destDot;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}


	public boolean isDestinationInput() {
		return isDestinationInput;
	}

	public void setDestinationInput(boolean isDestInput) {
		this.isDestinationInput = isDestInput;
	}

	public boolean isOriginInput() {
		return isOriginInput;
	}

	public void setOriginInput(boolean isOriginInput) {
		this.isOriginInput = isOriginInput;
	}
	
	public boolean isOpenBracketPresent() {
		return openBracketPresent;
	}

	public void setOpenBracketPresent(boolean openBracketAdded) {
		this.openBracketPresent = openBracketAdded;
	}

	public boolean isCloseBracketAdded() {
		return closedBracketPresent;
	}

	public void setCloseBracketPresent(boolean closedBracketAdded) {
		this.closedBracketPresent = closedBracketAdded;
	}
	

	public List<Connections> getConnectionsList() {
		return connectionsList;
	}

	public void setConnectionsList(List<Connections> connectionsList) {
		this.connectionsList = connectionsList;
	}
	
	public WorkAreaController getWorkAreaController() {
		return workAreaController;
	}

	public void setWorkAreaController(WorkAreaController workAreaController) {
		this.workAreaController = workAreaController;
	}
	public WorkArea getWorkArea() {
		return workArea;
	}
	

	public void setWorkspace(WorkArea workArea) {
		this.workArea = workArea;
	}
	public void notifyMethod(String operation) {
		setChanged();
		notifyObservers(operation);
	}

}

