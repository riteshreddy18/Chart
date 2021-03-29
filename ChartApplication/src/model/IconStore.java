package model;

import java.awt.Point;

import view.AtTheRate;
import view.CloseBracket;
import view.BarToBarLinkage;
import view.GreaterThan;
import view.Hyphen;
import view.Icon;
import view.LessThan;
import view.OpenBracket;

public class IconStore {

	public Icon getIconObject(Point point, String icon) {
		
		TabList tabList=TabList.getPresentInstance();
		if (tabList.OPENBRACKET.equals(icon) && !tabList.getTab().isOpenBracketPresent()) {
			tabList.getTab().setOpenBracketPresent(true);
			return new OpenBracket(point);
		} else if (tabList.CLOSEBRACKET.equals(icon) && !tabList.getTab().isCloseBracketAdded()) {
			tabList.getTab().setCloseBracketPresent(true);
			return new CloseBracket(point);
		} else if (tabList.ATTHERATE.equals(icon)) {
			return new AtTheRate(point);
		} else if (tabList.HYPHEN.equals(icon)) {
			return new Hyphen(point);
		} else if (tabList.BARS.equals(icon)) {
			return new BarToBarLinkage(point);
		} else if (tabList.LESSTHAN.equals(icon)) {
			return new LessThan(point);
		} else if (tabList.GREATERTHAN.equals(icon)) {
			return new GreaterThan(point);
		} 
		
		return null;
	}
}
