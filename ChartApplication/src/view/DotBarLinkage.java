package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Tab;
import model.TabList;

public class DotBarLinkage implements ActionListener {

	private Icon icon;
	private Point point;
	private boolean isInput;

	public DotBarLinkage(Icon icon, Point point, boolean isInput) {
		this.icon = icon;
		this.point = point;
		this.isInput = isInput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = TabList.getPresentInstance().getTab();
		if (!isInput || tab.isClicked()) {
			if (!tab.isClicked()) {
				tab.setIsClicked(true);
				tab.setOriginIcon(icon);
				tab.setOriginPoint(point);
				tab.setOriginInput(isInput);
				tab.setOrigin((JButton) e.getSource());
			} else if (icon != tab.getOriginIcon()) {
				tab.setDestinationInput(isInput); 
				tab.setDestIcon(icon);
				tab.setDestination((JButton) e.getSource());
				tab.setDestPoint(point, "Drawline");
			}
		}
	}
}
