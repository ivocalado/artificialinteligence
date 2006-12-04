package br.ufal.ic.se.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.ufal.ic.se.database.Banco;

import br.ufal.ic.se.models.KB;

public class MainFrame extends JFrame {

	private JPanel telaPrincipal;

	private JMenuBar menu;

	private JPanel rodape;

	private JPanel panel;

	private JTabbedPane tabbedPane;

	public MainFrame() {
		super("S.E::Edtior de regras");

		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		JPanel tmp1 = new JPanel();
		tmp1.setLayout(new BorderLayout());
		JLabel tmp = new JLabel();
		tmp1.add(tmp, BorderLayout.SOUTH);
		tmp1.add(PanelEditorFatos.getInstance(), BorderLayout.CENTER);
		// tmp.setVisible(false);

		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Editor de Fatos", tmp1);
		tabbedPane.addTab("Editor de Regras", new PanelEditor());
		tabbedPane.addTab("Motor de Inferência", MotorInferencia.getInstance());

		menu = new MainMenu(this);

		setJMenuBar(menu);
		container.add(tabbedPane, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(601, 501);
		setResizable(false);
		setVisible(true);
}

	public JMenuBar getMenu() {
		return menu;
	}

	public void setMenu(JMenuBar menu) {
		this.menu = menu;
	}

	

}
