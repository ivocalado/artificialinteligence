package br.ufal.ic.se.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainMenu extends JMenuBar {
	public MainMenu(JFrame frame){
		super();
		JMenu menuFile =  new JMenu("Arquivo");
		JMenu menuEditor =  new JMenu("Editor");
		JMenu menuHelp = new JMenu("Help");
		
		JMenuItem itemFechar = new JMenuItem("Sair");
		JMenuItem itemAbrir = new JMenuItem("Abrir arquivo");
		JMenuItem itemAddRegra = new JMenuItem("Adicionar regra");
		JMenuItem itemBuscarRegra = new JMenuItem("Buscar regra");
		JMenuItem itemAllRegras = new JMenuItem("Exibir a base de Conhecimento");
		JMenuItem itemAjuda = new JMenuItem("Ajuda");
		JMenuItem itemAbout = new JMenuItem("About");
		JMenuItem itemAddFato = new JMenuItem("Adicionar fato");
		JMenuItem itemBuscarFato = new JMenuItem("Buscar fato");
		JMenuItem itemAllFatos = new JMenuItem("Exibir todos os fatos");
		/*Eventos dos items do menu Arquico*/
		
		
		itemBuscarFato.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAddFato.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemFechar.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAbrir.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						int result = fileChooser.showOpenDialog(MainMenu.this);
						if(result == JFileChooser.CANCEL_OPTION)
							return;
						File file = fileChooser.getSelectedFile();
						
						if((file == null) || (file.getName().equals("")))
							JOptionPane.showMessageDialog(MainMenu.this, "Invalide File name",
									"Invalide File name!!",JOptionPane.ERROR_MESSAGE );
						else{
							
						}
						
					}
					
				}
		);
		/*Eventos dos items do menu Editor*/
		itemBuscarRegra.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAddRegra.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAllRegras.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAbout.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		itemAjuda.addActionListener(
				new ActionListener(){
	
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
						
					}
					
				}
		);
		/*add ao menu Arquivo*/
		menuFile.add(itemAbrir);
		menuFile.addSeparator();
		menuFile.add(itemFechar);
		
		/*add ao menu Editor*/
		
		menuEditor.add(itemAddRegra);
		menuEditor.add(itemBuscarRegra);
		menuEditor.add(itemAllRegras);
		menuEditor.addSeparator();
		menuEditor.add(itemAddFato);
		menuEditor.add(itemBuscarFato);
		menuEditor.add(itemAllFatos);
		
		
		
		/*add ao menu HELP*/
		
		menuHelp.add(itemAjuda);
		menuHelp.add(itemAbout);
		
		
		this.add(menuFile);
		this.add(menuEditor);
		this.add(menuHelp);
	}
}
