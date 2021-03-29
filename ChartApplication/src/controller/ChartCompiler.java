package controller;


import java.util.Stack;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.LinkedHashMap;

import model.Connections;
import model.Tab;
import model.TabList;
import view.AtTheRate;
import view.CloseBracket;
import view.BarToBarLinkage;
import view.GreaterThan;
import view.Icon;
import view.LessThan;
import view.OpenBracket;

public class ChartCompiler {

	private String errorMessage = "";
	public void performCompile() {
		Tab tab = TabList.getPresentInstance().getTab();
		LinkedHashMap<Icon, LinkedList<Icon>> adjList = createAdjacencyList(tab);
		Stack<Icon> stack = new Stack<Icon>();
		Icon start = getStartIcon(adjList);
		if (start == null) {
			errorMessage = "No Connections in present tab";
		} else
			traverse(adjList, start, stack, null, 0);

		for (Tab tabAll : TabList.getPresentInstance().getTabList()) {
			for (Icon icon : tabAll.getIconList()) {
				icon.setFirstConnection(false);
			}
		}

		if (!stack.isEmpty() || start == null) {
			if (start == null) {
				errorMessage = "One or more connections is/are missing";
			}
			tab.getWorkArea().displayMessage("Compilation Error - " + errorMessage);
		} else {
			tab.getWorkArea().displayMessage("Compilation Succeeded");
			errorMessage = "";
		}
	}

	public LinkedHashMap<Icon, LinkedList<Icon>> createAdjacencyList(Tab tab) {
		if (!checkIconCount()) {
			tab.getWorkArea().displayMessage("Compilation Error : " + errorMessage);
			return null;
		}
		LinkedHashMap<Icon, LinkedList<Icon>> adjList = new LinkedHashMap<Icon, LinkedList<Icon>>();
		List<Connections> connectionList = tab.getConnectionsList();
		HashMap<Icon, Double> atTheRateLoc = new HashMap<Icon, Double>();
		for (Connections connection : connectionList) {
			Icon originIcon = connection.getOriginIcon();
			Icon destIcon = connection.getDestIcon();
			if (!adjList.containsKey(originIcon)) {
				LinkedList<Icon> list = new LinkedList<Icon>();
				list.add(destIcon);
				adjList.put(originIcon, list);
				if (originIcon instanceof AtTheRate)
					atTheRateLoc.put(originIcon, connection.getOriginPoint().getY());
			} else {
				LinkedList<Icon> list = adjList.get(originIcon);
				if (originIcon instanceof AtTheRate) {
					double getprevY = atTheRateLoc.get(originIcon);
					double getnewY = connection.getOriginPoint().getY();
					if (getnewY > getprevY)
						list.addFirst(destIcon);
					else
						list.add(destIcon);
				} else {
					list.add(destIcon);
				}
			}
		}

		for (Map.Entry<Icon, LinkedList<Icon>> map : adjList.entrySet()) {
			Icon key = map.getKey();
			if (key instanceof AtTheRate) {
				List<Icon> icon = map.getValue();
				if (icon.size() != 2) {
					tab.getWorkArea().displayMessage("Connections missing at @");
					return null;
				}
			}
		}

		return adjList;
	}
	
	private boolean checkIconCount() {
		int countOpenBracket = 0, countCloseBracket = 0;
		Tab tab = TabList.getPresentInstance().getTab();
		if (tab.getIconList().size() == 0) {
			errorMessage = "Empty";
			return false;
		}
		for (Icon icon : tab.getIconList()) {
			if (icon instanceof OpenBracket) {
				countOpenBracket++;
			} else if (icon instanceof CloseBracket) {
				countCloseBracket++;
			}
		}
		if (countCloseBracket > 1 || countOpenBracket > 1) {
			errorMessage = "There are more than one open or close brackets present";
			return false;
		}
		return true;
	}
	
	private Icon getStartIcon(LinkedHashMap<Icon, LinkedList<Icon>> adjList) {
		Icon start = null;
		int countStartIcons = 0;
		for (Entry<Icon, LinkedList<Icon>> map : adjList.entrySet()) {
			Icon key = map.getKey();
			if (key instanceof OpenBracket) {
				start = key;
				countStartIcons++;
			}
		}
		if (countStartIcons >= 2) {
			errorMessage = "There are more than one open bracket or close bracket";
			return null;
		}
		return start;
	}

	private void traverse(LinkedHashMap<Icon, LinkedList<Icon>> adjList, Icon start, Stack<Icon> stack,
			Icon previous, int tabId) {

		if (previous != null) {
		if (!stack.isEmpty() && start instanceof CloseBracket && stack.peek() instanceof OpenBracket) {
			stack.pop();
			return;
		}
		previous = start;
		if (start instanceof CloseBracket) {
			return;
		}

		List<Icon> list = adjList.get(start);
		if (list == null && !(start instanceof CloseBracket)) {
			errorMessage = "Connection from " + start.getClass().getSimpleName() + " is missing";
		}
		if (start instanceof LessThan && list.size() != 2) {
			errorMessage = "Connection from " + start.getClass().getSimpleName() + " is missing";
		}
		if (start instanceof AtTheRate && list.size() != 2) {
			errorMessage = "Connection from " + start.getClass().getSimpleName() + " is missing";
		}
		if (list == null)
			return;

		if (start instanceof OpenBracket || start instanceof LessThan)
			stack.push(start);

		if (!stack.isEmpty() && start.isFirstConnection() && start instanceof GreaterThan
				&& stack.peek() instanceof LessThan) {
			stack.pop();
		}
		if (!stack.isEmpty() && start.isFirstConnection() && start instanceof AtTheRate
				&& stack.peek() instanceof AtTheRate) {
			stack.pop();
			return;
		}

		if (start.isFirstConnection() && start instanceof BarToBarLinkage) {
			return;
		}

		if (start instanceof AtTheRate && !start.isFirstConnection()) {
			stack.push(start);
			start.setFirstConnection(true);
		}

		if (start instanceof BarToBarLinkage && !start.isFirstConnection()) {
			start.setFirstConnection(true);
		}
		if (start instanceof GreaterThan && !start.isFirstConnection()) {
			start.setFirstConnection(true);
			return;
		}

		for (Icon icon : list) {

			traverse(adjList, icon, stack, previous, tabId);
			}
		}
	}


	
}