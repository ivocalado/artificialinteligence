package br.ufal.ic.se.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.ufal.ic.se.database.Banco;

import br.ufal.ic.se.models.BackwardChainingEngine;
import br.ufal.ic.se.models.ForwardChainingEngine;
import br.ufal.ic.se.models.KB;

public class MotorInferencia extends JPanel implements ActionListener {
	private static MotorInferencia singleton = null;

	private JLabel lblInsert;

	private JTextField txtFato;

	private JTextArea txtArea;

	private JLabel lblSelect;

	private JRadioButton radioForward;

	private JRadioButton radioBacking;

	private JButton btnIniciar;

	String metodoSelected;

	private JComboBox comboBox;

	private KB kb;

	private ButtonGroup buttonGroup;

	private MotorInferencia() {
		setLayout(new BorderLayout());
		lblInsert = new JLabel("Provar o seguinte fato:");
		comboBox = new JComboBox();

		comboBox.setToolTipText("Selecione o Fato a ser provado");

		lblSelect = new JLabel("Selecione o tipo de encadeamento");

		radioBacking = new JRadioButton("BackingChaining");
		radioBacking.setActionCommand("Backing");
		radioBacking.setMnemonic(KeyEvent.VK_B);
		radioBacking.addActionListener(this);
		radioBacking.setToolTipText("Encadeamento para trás.");

		radioForward = new JRadioButton("ForwardChaining");
		radioForward.setActionCommand("Forward");
		radioForward.setMnemonic(KeyEvent.VK_F);
		radioForward.addActionListener(this);
		radioForward.setToolTipText("Encadeamento para frente.");

		radioForward.setSelected(true);
		metodoSelected = radioBacking.getActionCommand();

		buttonGroup = new ButtonGroup();
		buttonGroup.add(radioBacking);
		buttonGroup.add(radioForward);

		Box box = new Box(BoxLayout.Y_AXIS);

		box.add(radioBacking);
		box.add(radioForward);
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setToolTipText("Iniciar o motor de inferencia com "
				+ "com o metodo selecionado");
		btnIniciar.setMnemonic(KeyEvent.VK_ENTER);
		btnIniciar.addActionListener(this);

		JPanel north = new JPanel();
		north.add(lblInsert);
		north.add(comboBox);
		north.add(btnIniciar);
		add(north, BorderLayout.NORTH);

		JPanel west = new JPanel();
		west.add(lblSelect);
		west.add(box);
		add(west, BorderLayout.CENTER);
		updateEngine();

	}

	public static MotorInferencia getInstance() {
		if (singleton == null) {
			singleton = new MotorInferencia();
		}
		return singleton;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == btnIniciar) {
			String value = (String) comboBox.getSelectedItem();
			if (value == null) {
				JOptionPane.showMessageDialog(this,
						"Nenhuma busca pode ser efetuado pois "
								+ "nenhuma variável está definida",
						"Motor de inferência", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String var = Banco.getInstance().findFactbyValue(value);
			JOptionPane.showMessageDialog(null, "buscando "+ var);
			if (buttonGroup.getSelection() == radioForward.getModel()) {
				JOptionPane.showMessageDialog(this, "A variável '" + value
						+ "' foi provada e tem valor: "
						+ (new ForwardChainingEngine(kb).query(var))
						+ " usando o encadeamento para frente",
						"Motor de inferância", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "A variável '" + value
						+ "' foi provada e tem valor: "
						+ new BackwardChainingEngine(kb).query(var)
						+ " usando o encadeamento para trás",
						"Motor de inferância", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	public void updateEngine() {
		String tmp[][] = Banco.getInstance().getFacts();
		comboBox.removeAllItems();
		if (tmp == null)
			return;
		for (String[] strings : tmp)
			comboBox.addItem(strings[1]);
		startKB();
	}

	private void startKB() {
		kb = new KB();
		try {
			String tmp[] = Banco.getInstance().getRoles();
			String facts[][] = Banco.getInstance().getFacts();
			
			for (String string : tmp) {
				System.out.println("telling: " + string);
				kb.tell(string, "");
			}
			
			for (String[]  string : facts) {
				System.out.println("telling: " + string[0]);
				kb.tell(string[0],"");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
