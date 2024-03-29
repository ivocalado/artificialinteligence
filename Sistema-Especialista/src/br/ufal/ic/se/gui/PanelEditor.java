package br.ufal.ic.se.gui;

import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.ufal.ic.se.lexer.LexerException;
import br.ufal.ic.se.parser.ParserException;

import br.ufal.ic.se.util.SentenceCreator;

import br.ufal.ic.se.database.Banco;

public class PanelEditor extends JPanel  {
	/*
	 * EditorRegras.java
	 *
	 * Created on 29 de Outubro de 2006, 23:39
	 */

	

	/**
	 *
	 * @author  Fritz
	 */
	
	    
	    /** Creates new form EditorRegras */
	    public PanelEditor() {
	        initComponents();
	    }
	    
	    /** This method is called from within the constructor to
	     * initialize the form.
	     * WARNING: Do NOT modify this code. The content of this method is
	     * always regenerated by the Form Editor.
	     */
	    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
	    
	    private void initComponents() {
	        jPanelConstrutor = new javax.swing.JPanel();
	        jLabel1 = new javax.swing.JLabel();
	        jComboBox1 = new javax.swing.JComboBox();
	        jLabel2 = new javax.swing.JLabel();
	        jComboBox2 = new javax.swing.JComboBox();
	        jLabel4 = new javax.swing.JLabel();
	        jComboBox3 = new javax.swing.JComboBox();
	        jLabel6 = new javax.swing.JLabel();
	        jComboBox4 = new javax.swing.JComboBox();
	        jLabel8 = new javax.swing.JLabel();
	        jComboBox5 = new javax.swing.JComboBox();
	        jLabel10 = new javax.swing.JLabel();
	        jComboBox6 = new javax.swing.JComboBox();
	        jSeparator1 = new javax.swing.JSeparator();
	        jComboBox7 = new javax.swing.JComboBox();
	        jLabel3 = new javax.swing.JLabel();
	        jPanel1 = new javax.swing.JPanel();
	        jPanelBotoes = new javax.swing.JPanel();
	        jButton1 = new javax.swing.JButton();
	        Remover = new javax.swing.JButton();
	        jButton3 = new javax.swing.JButton();
	        jPanelBotoes1 = new javax.swing.JPanel();

	        setLayout(new java.awt.BorderLayout());

	        jPanelConstrutor.setLayout(null);

	        jPanelConstrutor.setBorder(new javax.swing.border.TitledBorder("Construtor de Regras"));
	        jLabel1.setText("Se");
	        jPanelConstrutor.add(jLabel1);
	        jLabel1.setBounds(10, 30, 20, 20);

	        jPanelConstrutor.add(jComboBox1);
	        jComboBox1.setBounds(30, 30, 260, 22);

	        jLabel2.setText("e");
	        jPanelConstrutor.add(jLabel2);
	        jLabel2.setBounds(300, 30, 20, 20);

	        jPanelConstrutor.add(jComboBox2);
	        jComboBox2.setBounds(30, 60, 260, 22);

	        jLabel4.setText("e");
	        jPanelConstrutor.add(jLabel4);
	        jLabel4.setBounds(300, 60, 20, 20);

	        jPanelConstrutor.add(jComboBox3);
	        jComboBox3.setBounds(30, 90, 260, 22);

	        jLabel6.setText("e");
	        jPanelConstrutor.add(jLabel6);
	        jLabel6.setBounds(300, 90, 20, 20);

	        jPanelConstrutor.add(jComboBox4);
	        jComboBox4.setBounds(30, 120, 260, 22);

	        jLabel8.setText("e");
	        jPanelConstrutor.add(jLabel8);
	        jLabel8.setBounds(300, 120, 20, 20);

	        jPanelConstrutor.add(jComboBox5);
	        jComboBox5.setBounds(30, 150, 260, 22);

	        jLabel10.setText("e");
	        jPanelConstrutor.add(jLabel10);
	        jLabel10.setBounds(300, 150, 20, 20);

	        jPanelConstrutor.add(jComboBox6);
	        jComboBox6.setBounds(30, 180, 260, 22);

	        jPanelConstrutor.add(jSeparator1);
	        jSeparator1.setBounds(10, 230, 310, 10);

	        jPanelConstrutor.add(jComboBox7);
	        jComboBox7.setBounds(50, 250, 240, 22);

	        jLabel3.setText("Ent\u00e3o");
	        jPanelConstrutor.add(jLabel3);
	        jLabel3.setBounds(10, 250, 40, 20);

	        jPanelConstrutor.add(jPanel1);
	        jPanel1.setBounds(0, 0, 10, 10);

	        add(jPanelConstrutor, java.awt.BorderLayout.CENTER);

	        jPanelBotoes.setLayout(new java.awt.GridLayout(3, 1, 0, 45));

	        jPanelBotoes.setBorder(new javax.swing.border.TitledBorder("Editar regras"));
	        jButton1.setText("Adicionar");
	        jPanelBotoes.add(jButton1);

	        Remover.setText("Remover");
	        jPanelBotoes.add(Remover);

	        jButton3.setText("Editar");
	        jPanelBotoes.add(jButton3);

	        add(jPanelBotoes, java.awt.BorderLayout.EAST);

	        jPanelBotoes1.setLayout(new java.awt.GridLayout(3, 1, 0, 45));

	        jPanelBotoes1.setBorder(new javax.swing.border.TitledBorder("Express\u00e3o correspondente"));
	        add(jPanelBotoes1, java.awt.BorderLayout.SOUTH);

	    }
	    // </editor-fold>
	    
	    
	    // Variables declaration - do not modify
	    private javax.swing.JButton Remover;
	    private javax.swing.JButton jButton1;
	    private javax.swing.JButton jButton3;
	    private javax.swing.JComboBox jComboBox1;
	    private javax.swing.JComboBox jComboBox2;
	    private javax.swing.JComboBox jComboBox3;
	    private javax.swing.JComboBox jComboBox4;
	    private javax.swing.JComboBox jComboBox5;
	    private javax.swing.JComboBox jComboBox6;
	    private javax.swing.JComboBox jComboBox7;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel10;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel6;
	    private javax.swing.JLabel jLabel8;
	    private javax.swing.JPanel jPanel1;
	    private javax.swing.JPanel jPanelBotoes;
	    private javax.swing.JPanel jPanelBotoes1;
	    private javax.swing.JPanel jPanelConstrutor;
	    private javax.swing.JSeparator jSeparator1;
	    // End of variables declaration
	    
	}
