package br.ufal.ic.se.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JDesktopPane;

public class teste extends JFrame {

	private static final long serialVersionUID = 1L;
	private JDesktopPane jDesktopPane = null;

	/**
	 * This is the default constructor
	 */
	public teste() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(680, 479);
		this.setContentPane(getJDesktopPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jDesktopPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jDesktopPane = new JDesktopPane();
		}
		return jDesktopPane;
	}

	public static void main(String[] args) {
		new teste().setVisible(true);
		
	}

}  //  @jve:decl-index=0:visual-constraint="20,9"
