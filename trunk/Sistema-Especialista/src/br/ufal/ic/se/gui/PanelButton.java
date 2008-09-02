package br.ufal.ic.se.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelButton extends JPanel {
	MainFrame mainframe;
	public PanelButton(JFrame frame){
		super();
		mainframe = (MainFrame)frame;
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		
		JButton btnAbrirArquivo = new JButton("Abrir Arquivo");
		JButton btnSair = new JButton("Sair");
		JButton btnAddRegra = new JButton("Add regra");
		JButton btnAddFato= new JButton("Add variavel");
		JButton btnExibeRegras = new JButton("Mostrar regras");
		
		
		btnAbrirArquivo.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						int result = fileChooser.showOpenDialog(PanelButton.this);
						if(result == JFileChooser.CANCEL_OPTION)
							return;
						File file = fileChooser.getSelectedFile();
						
						if((file == null) || (file.getName().equals("")))
							JOptionPane.showMessageDialog(PanelButton.this, "Invalide File name",
									"Invalide File name!!",JOptionPane.ERROR_MESSAGE );
						else{
							
						}
						
					}
					
				}
		);
		btnAddRegra.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						//PanelButton.this.mainframe.setTelaPrincipal(PanelEditor.getInstance());
						mainframe.add(PanelEditorFatos.getInstance());
						//PanelButton.this.mainframe.add();
						//PanelButton.this.mainframe.telaPrincipal.();
					}
					
				}
		);
		btnAddFato.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						JPanel pane = PanelButton.this.mainframe.getTelaPrincipal();
						PanelButton.this.mainframe.remove(pane);
						pane = PanelEditorFatos.getInstance();
						PanelButton.this.mainframe.setTelaPrincipal(pane);
						PanelButton.this.mainframe.repaint();
					}
					
				}
		);
		btnExibeRegras.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				}
		);
		btnSair.addActionListener(
				new ActionListener(){

					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
						
					}
					
				}
		);
		this.add(btnAbrirArquivo);
		this.add(btnAddRegra);
		this.add(btnAddFato);
		this.add(btnExibeRegras);
		this.add(btnSair);
		
	}
}
