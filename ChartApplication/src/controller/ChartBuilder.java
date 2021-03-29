package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.TabList;
import view.IconPane;
import view.WorkArea;


public class ChartBuilder extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private final String APPLICATION_TITLE = "ChartApplication";
    private IconPane iconPanel;
    private JTabbedPane tabbedPane;

    private WorkAreaController workAreaController;
    private ChartCompiler chartCompiler;
    private Rectangle panelSize;
    private JMenuBar menuBar;
    private JMenuItem save,load,compile,newSpace;
    
    FileSaver fileSaver;
    FileLoader fileLoader;

    public ChartBuilder() {
        menuBar = new JMenuBar();
        chartCompiler = new ChartCompiler();
        this.setMinimumSize(new Dimension(400, 400));
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        panelSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setLayout(null);
        setTitle(APPLICATION_TITLE);
        setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        if(tabbedPane==null) {
	        tabbedPane = new JTabbedPane();
	        tabbedPane.setVisible(true);
	        tabbedPane.setBounds(panelSize.width / 4, panelSize.height / 10, 11 * panelSize.width / 15 + 10,
	            4 * panelSize.height / 5);
	        this.add(tabbedPane);
	        tabbedPane.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	                TabList.getPresentInstance().setCurrentTabIndex(tabbedPane.getSelectedIndex());
	                TabList.getPresentInstance().getTab().getWorkArea().repaint();
	            }
	        });  
    	}
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                tabbedPane.setBounds(e.getComponent().getWidth() / 5, 0,11 * e.getComponent().getWidth() / 15,e.getComponent().getHeight());
            }
        });
    }
    
    private void createMenuBar() {
        save = new JMenuItem("Save");
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.addActionListener(this);
        menuBar.add(save);
        menuBar.add(load);
        compile = new JMenuItem("Compile");
        compile.addActionListener(this);
        newSpace = new JMenuItem("New Space");
        newSpace.addActionListener(this);
        menuBar.add(compile);
        menuBar.add(newSpace);
        this.setJMenuBar(menuBar);
    }
    
    private void createTabs() {
        createWorkspace();
        TabList tabList = (TabList.getPresentInstance());
        tabbedPane.setSelectedIndex(tabList.getSize()-1);
        iconPanel.setOpenBracket();
        iconPanel.setCloseBracket();
     }
    
    @SuppressWarnings("deprecation")
	private void createWorkspace() {
        TabList tabList = TabList.getPresentInstance();
        WorkArea workArea = new WorkArea();
        tabList.addTab(workArea);
        workAreaController = new WorkAreaController();
        workAreaController.setTabbedPane(tabbedPane);
        tabList.getRecentTab().addObserver(workAreaController);
        tabbedPane.add("Tab " + (tabList.getSize()), workArea);
    }
    
    private void createOptionsPanel() {

        iconPanel = new IconPane();
        iconPanel.setBounds(0, 0, panelSize.width / 6, panelSize.height);
        iconPanel.setVisible(true);
        this.getContentPane().add(iconPanel);
    }


    public static void main(String[] args) {
        ChartBuilder frame = new ChartBuilder();
        frame.createMenuBar();
        frame.createOptionsPanel();
        frame.setVisible(true);
        frame.createTabs();
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
	    
		if(ae.getSource().equals(compile)) {
			chartCompiler.performCompile();
		}
		else if(ae.getSource().equals(newSpace)) {
			createTabs();
		}
		else if(ae.getSource().equals(save)) {
			fileSaver = new FileSaver();
			fileSaver.saveFile();
		}
		else if(ae.getSource().equals(load)) {
			fileLoader = new FileLoader();
			fileLoader.loadFile(tabbedPane);
		}		
	}

}