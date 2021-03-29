package model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import view.WorkArea;


public class TabList implements Serializable {
	private static final long serialVersionUID = 1L;
	private int currentTabIndex;
	private List<Tab> tabList;
	private static TabList tabListInstance;

	private TabList() {
		currentTabIndex = 0;
		tabList = new ArrayList<Tab>();
	}

	public static TabList getPresentInstance() {
		if (tabListInstance == null) {
			tabListInstance = new TabList();
		}
		return tabListInstance;
	}

	public Tab getTab() {
		return tabList.get(currentTabIndex);
	}

	public Tab getTab(int i) {
		return tabList.get(i);
	}

	public Tab getRecentTab() {
		return tabList.get(getSize() - 1);
	}

	public void addTab(WorkArea workArea) {
		tabList.add(new Tab(workArea));
	}

	public int getCurrentTabIndex() {
		return currentTabIndex;
	}

	public void setCurrentTabIndex(int currentTabIndex) {
		this.currentTabIndex = currentTabIndex;
	}

	public int getSize() {
		return tabList.size();
	}

	public List<Tab> getTabList() {
		return tabList;
	}

	public void setTabList(List<Tab> tabList) {
		this.tabList = tabList;
	}

	public final String OPENBRACKET = "OPEN_BRACKET";

	public final String CLOSEBRACKET = "CLOSE_BRACKET";

	public final String LESSTHAN = "LESS_THAN";

	public final String GREATERTHAN = "GREATER_THAN";

	public final String ATTHERATE = "AT_THE_RATE";

	public final String HYPHEN = "HYPHEN";

	public final String BARS = "BARS";

	public String getOpenBracket() {
		return OPENBRACKET;
	}

	public String getCloseBracket() {
		return CLOSEBRACKET;
	}

	public String getGreaterThan() {
		return GREATERTHAN;
	}

	public String getLessThan() {
		return LESSTHAN;
	}

	public String getAtTheRate() {
		return ATTHERATE;
	}

	public String getHyphen() {
		return HYPHEN;
	}

	public String getBars() {
		return BARS;
	}

}
