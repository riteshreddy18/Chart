package controller;

import java.util.List;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JFileChooser;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import model.Connections;
import model.TabList;
import view.WorkArea;
import view.Icon;
import view.BarConnector;
import view.DotConnector;

@SuppressWarnings("deprecation")
public class FileLoader {
	private final String FILE_EXTENSION = ".sa";
	private final String ERROR_MSG_LOAD = "Error occured during loading operation";

	public void loadFile(JTabbedPane jTabbedPane) {
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		String fileName = null;
		
		try {
			int showOpenDialog = JFileChooser.APPROVE_OPTION;;
			JFileChooser chosenFile = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(FILE_EXTENSION, FILE_EXTENSION.replace(".", ""));
			chosenFile.setFileFilter(filter);
			showOpenDialog = chosenFile.showOpenDialog(null);

			
			if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
				fileName = chosenFile.getSelectedFile().getAbsolutePath();
				fileIn = new FileInputStream(fileName);
				in = new ObjectInputStream(fileIn);
				int numberOfTabs = in.readInt();
				TabList tabList = TabList.getPresentInstance();
				int currentTabIndex = tabList.getCurrentTabIndex();
				for (int i = 0; i < numberOfTabs; i++) {
					WorkArea workArea = (WorkArea) in.readObject();
					jTabbedPane.add("Tab " + (tabList.getSize() + 1), workArea);
					tabList.setCurrentTabIndex(tabList.getSize());
					tabList.addTab(workArea);
					tabList.getTab().setWorkArea(workArea);
					WorkAreaController workAreaController = new WorkAreaController();
					workAreaController.setTabbedPane(jTabbedPane);
					tabList.getTab().addObserver(workAreaController);
					tabList.getTab().setIconList((ArrayList<Icon>) in.readObject());
					tabList.getTab().setConnectionsList((List<Connections>) in.readObject());
					addActionListeners(tabList, workAreaController);
				}
				tabList.setCurrentTabIndex(currentTabIndex);
			}
		} catch (IOException i) {
			JOptionPane.showMessageDialog(null, ERROR_MSG_LOAD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void addActionListeners(TabList tabList, WorkAreaController workAreaController) {
		for (Icon icon : tabList.getTab().getIconList()) {
			if (icon.getBars() != null) {
				for (BarConnector bar : icon.getBars()) {
					bar.addActionListener(icon);
				}
			}
			else if (icon.getDots() != null) {
				for (DotConnector dot : icon.getDots()) {
					dot.addActionListener(icon);
				}
			}
		}
	}
}
