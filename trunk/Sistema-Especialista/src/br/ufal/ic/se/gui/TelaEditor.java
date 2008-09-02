package br.ufal.ic.se.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaEditor extends JInternalFrame {
	JButton btnAddRegra;
	public TelaEditor(JFrame frame){
		setTitle("Editor de Regras");
		setClosable(true);
		setMaximizable(true);	
		setVisible(true);
		Container container = getContentPane();
		JEditorPane editorRegras  = new JEditorPane();
		
		btnAddRegra = new JButton("Armazenar Regra");
		btnAddRegra.setBounds(400, 250, 150, 30);
		btnAddRegra.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						
					}
					
				}
		);
		//editorRegras.setBounds(30, 30, 300, 200);
		//editorRegras.setSize(300, 250);
		editorRegras.setSelectedTextColor(Color.BLUE);
		editorRegras.setToolTipText("Insira regra");
		editorRegras.setAutoscrolls(true);
		JScrollPane scrollPane = new JScrollPane(editorRegras);
		scrollPane.setBounds(30, 30, 350, 250);
		//container.add(editorRegras);
		container.add(scrollPane);
		container.add(btnAddRegra);
		container.setLayout(null);
	}
 }
