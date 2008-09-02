package br.ufal.ic.se.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.ufal.ic.se.util.SentenceCreator;

import br.ufal.ic.se.node.AAtomSentence;
import br.ufal.ic.se.node.PSentence;

import br.ufal.ic.se.database.Banco;

public class PanelEditorFatos extends JPanel implements ActionListener {
	private JButton btnAdd;

	private JTextField txtVariavel;

	private JLabel lblNovoFato;

	private JTextField txtValor;

	private JLabel lblValor;

	private JTable table;

	private static PanelEditorFatos singleton = null;

	public static PanelEditorFatos getInstance() {
		if (singleton == null)
			singleton = new PanelEditorFatos();
		return singleton;
	}

	private PanelEditorFatos() {

		lblNovoFato = new JLabel("Nova variavel");
		txtVariavel = new JTextField(5);
		txtVariavel.setToolTipText("Inserir variavel, "
				+ "iniciado de letra maiuscula, seguida ou não de digitos.");

		lblValor = new JLabel("Valor: ");
		txtValor = new JTextField(20);

		txtValor.setToolTipText("Inserir valor, " + "descrição da variavel.");

		btnAdd = new JButton("Add variavel");
		btnAdd.addActionListener(this);
		setLayout(new FlowLayout());

		super.add(lblNovoFato);
		super.add(txtVariavel);
		super.add(lblValor);
		super.add(txtValor);
		super.add(btnAdd);
		table = new JTable();
		table.setEnabled(false);
		JScrollPane pane = new JScrollPane(table);
		super.add(pane);

		updateTable();

		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnAdd) {
			if (txtVariavel.getText().equals("")
					|| txtValor.getText().equals("")) {
				JOptionPane.showMessageDialog(this,
						"Você deve preencher os dois campos"
								+ " para que a variável seja inserida!",
						"Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String var = txtVariavel.getText();
			String val = txtValor.getText();
			try {
				if (!(SentenceCreator.getSentence(var) instanceof AAtomSentence)) {
					throw new Exception();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						"A variável deve iniciar com letra maiúscula e "
								+ "ser seguida ou não de algum dígito",
						"Adicionar variável", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String t1 = Banco.getInstance().findFactbyValue(val);
			if (t1 != null) {
				JOptionPane
						.showMessageDialog(
								this,
								"O valor para essa variável já está definida em outra!\n" +
								"Altere seu valor.",
								"Adicionar variável", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String t = Banco.getInstance().findFactbyVariable(var);
			if (t != null) {
				int i = JOptionPane.showConfirmDialog(this,
						"Deseja sobrecrever a variável?", "Adicionar variável",
						JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					Banco.getInstance().updateFacts(var, val);
					txtValor.setText("");
					txtVariavel.setText("");
					updateTable();
				}
				return;
			}
			Banco.getInstance().insertFacts(var, val);
			txtValor.setText("");
			txtVariavel.setText("");
			//updateTable();
		}
	}

	private void updateTable() {
		MotorInferencia.getInstance().updateEngine();
		Object rows[][] = Banco.getInstance().getFacts();
		String columns[] = { "Variável", "Valor" };
		TableModel model = new DefaultTableModel(rows, columns) {
			public Class getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};
		table.setModel(model);

	}

}
