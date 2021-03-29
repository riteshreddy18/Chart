package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import model.Tab;
import model.TabList;


public class FileSaver {
	
	private final String FILE_EXTENSION = ".sa";
	private final String SUCCESS_MSG_SAVE = "Saved successfully";
	private final String ERROR_MSG_SAVE = "Error occured during saving operation";

		public void saveFile() {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		String fileName = null;
		try {
			JFileChooser chosenFile = new JFileChooser();
			int showSaveDialog = chosenFile.showSaveDialog(null);
			if (showSaveDialog == JFileChooser.APPROVE_OPTION) {
				fileName = chosenFile.getSelectedFile().getAbsolutePath().toString() + FILE_EXTENSION;
			}
			if (fileName != null) {
				fileOut = new FileOutputStream(new File(fileName));
				out = new ObjectOutputStream(fileOut);
				List<Tab> tabList = TabList.getPresentInstance().getTabList();
				out.writeInt(tabList.size());
				for (Tab tab : tabList) {
					out.writeObject(tab.getWorkArea());
					out.writeObject(tab.getIconList());
					out.writeObject(tab.getConnectionsList());
				}
				fileOut.flush();
				JOptionPane.showMessageDialog(null, SUCCESS_MSG_SAVE);
			}
		} catch (Exception i) {
			JOptionPane.showMessageDialog(null, ERROR_MSG_SAVE);
			i.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
