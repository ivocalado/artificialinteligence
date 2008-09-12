package br.edu.ufcg.ia.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import br.edu.ufcg.ia.algorithms.examples.EstadoCaixeiroViajante;
import br.edu.ufcg.ia.algorithms.examples.EstadoRainhas;
import br.edu.ufcg.ia.algorithms.search.AEstrela;
import br.edu.ufcg.ia.algorithms.search.Busca;
import br.edu.ufcg.ia.algorithms.search.BuscaGulosa;
import br.edu.ufcg.ia.algorithms.search.BuscaLargura;
import br.edu.ufcg.ia.algorithms.search.BuscaProfundidade;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.MostraStatusConsole;
import br.edu.ufcg.ia.algorithms.search.Nodo;
import br.edu.ufcg.ia.algorithms.search.SubidaMontanha;
import br.edu.ufcg.ia.algorithms.search.TSP_AG;
import br.edu.ufcg.ia.gui.MainFrameOld.Problem;
import br.edu.ufcg.ia.util.TextAreaOutputStream;

/**
 *
 * @author  thiagobrunoms
 */
public class MainGUI extends javax.swing.JFrame {

    private javax.swing.JComboBox comboVertex2;
    private javax.swing.JButton buttonAddEdge;
    private javax.swing.JButton buttonClearTextArea;
    private javax.swing.JButton buttonCreateVertice;
    private javax.swing.JButton buttonExec;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JButton buttonRemoveEdge;
    private javax.swing.JComboBox comboAlgoritms;
    private javax.swing.JComboBox comboVertex1;
    private javax.swing.JInternalFrame jInternalFrame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField textFieldRateOfDeath;
    private javax.swing.JList listEdges;
    private javax.swing.JList listVertex;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextArea textAreaResult;
    private javax.swing.JTextField textFieldEvolution;
    private javax.swing.JTextField textFieldNumberOfQueens;
    private javax.swing.JTextField textFieldVertexName;
    private javax.swing.JTextField textFieldWeight;
	
    enum Problem { NQUEENS, TRAVELLING_SALESMAN }
    private SimpleWeightedGraph<String, DefaultWeightedEdge> g;
    private HashMap<String, Integer> hashAlgoritms;
    private Problem selectedProblem;
    private MostraStatusConsole statusConsole;
    private TextAreaOutputStream textAreaOut;
	
    /** Creates new form MainFrame */
    public MainGUI() {
        initComponents();
        initComponentesValue();
    }
    
    private void initComponentesValue() {
    	this.g = new SimpleWeightedGraph<String, DefaultWeightedEdge>(new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(
						DefaultWeightedEdge.class));
    	
    	this.hashAlgoritms = new HashMap<String, Integer>();
    	this.statusConsole = new MostraStatusConsole();
    	
    	this.textAreaOut = new TextAreaOutputStream(this.textAreaResult);
    	System.setOut(new PrintStream(this.textAreaOut));
    	System.setErr(new PrintStream(this.textAreaOut));
    	
    	Properties p = new Properties();
		try {
			p.load(new FileInputStream("algorithms.properties"));
			String algorithm = "";
			for (Object algorithmKey : p.keySet()) {
				algorithm = p.getProperty((String) algorithmKey);
				this.comboAlgoritms.addItem(algorithm);
				this.hashAlgoritms.put(algorithm, Integer.valueOf((String)algorithmKey));				
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
    

    private void initComponents() {
        jInternalFrame = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaResult = new javax.swing.JTextArea();
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textFieldNumberOfQueens = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        textFieldVertexName = new javax.swing.JTextField();
        buttonCreateVertice = new javax.swing.JButton();
        comboVertex1 = new javax.swing.JComboBox();
        comboVertex2 = new javax.swing.JComboBox();
        textFieldWeight = new javax.swing.JTextField();
        buttonAddEdge = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listEdges = new javax.swing.JList();
        buttonRemoveEdge = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listVertex = new javax.swing.JList();
        buttonRemove = new javax.swing.JButton();
        comboAlgoritms = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        buttonExec = new javax.swing.JButton();
        buttonClearTextArea = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        textFieldEvolution = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        textFieldRateOfDeath = new javax.swing.JTextField(); 

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jInternalFrame.setTitle("Resultado da Busca");
        jInternalFrame.setName("internalFrame");
        jInternalFrame.setVisible(true);
        textAreaResult.setColumns(20);
        textAreaResult.setEditable(false);
        textAreaResult.setRows(5);
        jScrollPane1.setViewportView(textAreaResult);

        org.jdesktop.layout.GroupLayout jInternalFrameLayout = new org.jdesktop.layout.GroupLayout(jInternalFrame.getContentPane());
        jInternalFrame.getContentPane().setLayout(jInternalFrameLayout);
        jInternalFrameLayout.setHorizontalGroup(
            jInternalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        );
        jInternalFrameLayout.setVerticalGroup(
            jInternalFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        jLabel1.setText("N\u00famero de Rainhas");

        textFieldNumberOfQueens.setName("textFieldNumberOfQueens");
        textFieldNumberOfQueens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNumberOfQueensActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, textFieldNumberOfQueens)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(textFieldNumberOfQueens, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        tabbedPane.addTab("N Rainhas", jPanel1);

        buttonCreateVertice.setText("Criar");
        buttonCreateVertice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateVerticeActionPerformed(evt);
            }
        });

        buttonAddEdge.setText("Adicionar");
        buttonAddEdge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddEdgeActionPerformed(evt);
            }
        });

        jLabel3.setText("Criar V\u00e9rtices");

        jLabel4.setText("Criar Arestas");

        jScrollPane2.setViewportView(listEdges);

        buttonRemoveEdge.setText("Remover");
        buttonRemoveEdge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveEdgeActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(listVertex);

        buttonRemove.setText("Remover");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRemoveActionPerformed(evt);
            }
        });
        
        this.listVertex.setModel(new DefaultListModel());
        this.listEdges.setModel(new DefaultListModel());
        
        this.comboAlgoritms.setModel(new DefaultComboBoxModel());
        this.comboVertex1.setModel(new DefaultComboBoxModel());
        this.comboVertex2.setModel(new DefaultComboBoxModel());

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel3))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(261, Short.MAX_VALUE)
                        .add(buttonRemoveEdge))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(112, 112, 112)
                        .add(textFieldWeight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonAddEdge))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel4))
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(textFieldVertexName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(comboVertex1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(buttonCreateVertice, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(buttonRemove)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                                    .add(jPanel2Layout.createSequentialGroup()
                                        .add(16, 16, 16)
                                        .add(comboVertex2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 154, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                        .add(5, 5, 5)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(textFieldVertexName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(buttonCreateVertice)
                    .add(buttonRemove))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(comboVertex2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(comboVertex1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonAddEdge)
                    .add(textFieldWeight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 106, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(buttonRemoveEdge)
                .add(14, 14, 14))
        );
        tabbedPane.addTab("Caixeiro Viajante", jPanel2);

        comboAlgoritms.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));
        comboAlgoritms.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboAlgoritmsItemStateChanged(evt);
            }
        });

        jLabel2.setText("Algoritmos de Busca");

        buttonExec.setText("Executar");
        buttonExec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExecActionPerformed(evt);
            }
        });

        buttonClearTextArea.setText("Limpar");
        buttonClearTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearTextAreaActionPerformed(evt);
            }
        });

        jLabel6.setText("N\u00famero de Evolu\u00e7\u00f5es");

        jLabel7.setText("Taxa de Mortalidade");
        
        this.jLabel6.setVisible(false);
        this.jLabel7.setVisible(false);
        this.textFieldEvolution.setVisible(false);
        this.textFieldRateOfDeath.setVisible(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, comboAlgoritms, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                        .add(buttonExec)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jInternalFrame)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel6)
                            .add(jLabel7))
                        .add(5, 5, 5)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, textFieldRateOfDeath)
                            .add(textFieldEvolution, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)))
                    .add(buttonClearTextArea))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(21, 21, 21)
                        .add(tabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 397, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jInternalFrame)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(buttonClearTextArea))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(comboAlgoritms, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(textFieldEvolution, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonExec)
                    .add(jLabel7)
                    .add(textFieldRateOfDeath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pack();
    }

    private void comboAlgoritmsItemStateChanged(java.awt.event.ItemEvent evt) {
        if(((DefaultComboBoxModel)this.comboAlgoritms.getModel()).getSelectedItem().equals("Algoritmo Genético")) {
        	this.jLabel6.setVisible(true);
        	this.jLabel7.setVisible(true);
        	this.textFieldEvolution.setVisible(true);
        	this.textFieldRateOfDeath.setVisible(true);
       } else {
       		this.jLabel6.setVisible(false);
       		this.jLabel7.setVisible(false);
       		this.textFieldEvolution.setVisible(false);
       		this.textFieldRateOfDeath.setVisible(false);
       }
    }

    private void buttonClearTextAreaActionPerformed(java.awt.event.ActionEvent evt) {
    	this.textAreaResult.setText("");
    }

    private void buttonExecActionPerformed(java.awt.event.ActionEvent evt) {
    	Nodo n 		   = null;    	
    	Busca busca    = null;
    	Estado inicial = null;
    	
    	if(this.selectedProblem.equals(Problem.NQUEENS)) {
    		try {
        		short numberOfQueens = Short.valueOf(this.textFieldNumberOfQueens.getText());
                EstadoRainhas.tam    = numberOfQueens;
                
                inicial 		 	 = new EstadoRainhas();
                System.out.println("Estado inicial:"+inicial+"\n");

        	} catch (NumberFormatException e) {
        		JOptionPane.showMessageDialog(this, "Entrada Inválida!");
    		}
        	
    	} else {
    		inicial = new EstadoCaixeiroViajante("Pernambuco", this.g);
    		this.textAreaResult.append("Estado inicial: " + inicial);   		
    	}
    	
    	String selectedAlgorithm = (String)this.comboAlgoritms.getModel().getSelectedItem();
    	int selectedAlgorithmId  = this.hashAlgoritms.get(selectedAlgorithm);
    	
    	switch (selectedAlgorithmId) {
			case 1:
				//extensão
				busca = new BuscaLargura(this.statusConsole);
				break;
			case 2:
				//Busca em Profundidade
				busca = new BuscaProfundidade(this.statusConsole);
				break;
			case 3:
				//Satisfação de Restrinção (Subida de Montanha)
				busca = new SubidaMontanha(this.statusConsole);
				break;
			case 4:
				//Busca A Estrela
				busca = new AEstrela(this.statusConsole);
				break;
			case 5:
				//Busca Gulosa
				busca = new BuscaGulosa(this.statusConsole);
				break;
		}
		
    	if(selectedAlgorithmId != 6) {
    		busca.usarFechados(this.selectedProblem.equals(Problem.NQUEENS));
    		try {
    			n = busca.busca(inicial);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		System.out.println(n);
    		if (n != null) {
    			System.out.println("Solução:\n" + n.getEstado() + "\n\n");
    			System.out.println("solucao = " + n.montaCaminho());
    			System.out.println("\toperacoes = " + n.getProfundidade());
    			System.out.println("\tcusto = " + n.g());
    		}	
    	} else {
    		//algoritmo genético
    		if(this.selectedProblem.equals(Problem.TRAVELLING_SALESMAN)) {
    			double rateOfDeath = 0.5;
    			int evolutions     = 3000;
    			
    			try {
        			rateOfDeath = Double.valueOf(this.textFieldRateOfDeath.getText()).doubleValue();
        			evolutions  = Double.valueOf(this.textFieldEvolution.getText()).intValue();
        			new TSP_AG(this.g, true, rateOfDeath, evolutions).start();
    			} catch (NumberFormatException e) {
    				JOptionPane.showMessageDialog(this, "Parâmetro(s) inválido(s)! Taxa de Mortalidade ou Número de Evolução");
    			}

    		}
    	}
    }

    private void buttonRemoveEdgeActionPerformed(java.awt.event.ActionEvent evt) {
    	((DefaultListModel)this.listEdges.getModel()).removeElement(this.listEdges.getSelectedValue());
    	//remover do this.g
    }

    private void buttonAddEdgeActionPerformed(java.awt.event.ActionEvent evt) {
    	String selectedVertex1 = (String)((DefaultComboBoxModel)this.comboVertex1.getModel()).getSelectedItem();
    	String selectedVertex2 = (String)((DefaultComboBoxModel)this.comboVertex2.getModel()).getSelectedItem();
    	
    	if(selectedVertex1.equals(selectedVertex2)) {
    		JOptionPane.showMessageDialog(this, "Selecione dois vértices diferentes!");
    	} else {    		
    		try {
    			double weight = (Double.valueOf(this.textFieldWeight.getText())).doubleValue();
        		this.g.setEdgeWeight(this.g.addEdge(selectedVertex1, selectedVertex2), weight);        		
        		((DefaultListModel)this.listEdges.getModel()).addElement(selectedVertex1 + "<-" + weight + "->" + selectedVertex2);
        		this.textFieldWeight.setText("");
    		} catch (NumberFormatException e) {
    			JOptionPane.showMessageDialog(this, "Entrada inválida!");
    		}    		
    	} 
    }

    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt) {
    	String selectedVertex = (String) this.listVertex.getSelectedValue();
    	((DefaultListModel)this.listVertex.getModel()).removeElement(selectedVertex);
    	((DefaultComboBoxModel)this.comboVertex1.getModel()).removeElement(selectedVertex);
    	((DefaultComboBoxModel)this.comboVertex2.getModel()).removeElement(selectedVertex);
    	
    	Set<DefaultWeightedEdge> edges 		   = this.g.edgesOf(selectedVertex);
    	Iterator<DefaultWeightedEdge> iterator = edges.iterator();
    	
    	while(iterator.hasNext()) {
    		DefaultWeightedEdge e = iterator.next();
    		String vertex 		  = this.g.getEdgeSource(e);
    		
    		if(vertex.equals(selectedVertex)) {
    			vertex = this.g.getEdgeTarget(e);       		
    			((DefaultListModel)this.listEdges.getModel()).removeElement(selectedVertex + "<-" + this.g.getEdgeWeight(e) + "->" + vertex);
    		} else {    			
    			((DefaultListModel)this.listEdges.getModel()).removeElement(vertex + "<-" + this.g.getEdgeWeight(e) + "->" + selectedVertex);
    		}
    	}
    	
    	this.g.removeVertex(selectedVertex);
    }

    private void buttonCreateVerticeActionPerformed(java.awt.event.ActionEvent evt) {
    	String vertexName = this.textFieldVertexName.getText();
    	
    	this.g.addVertex(vertexName);    	
    	
    	((DefaultListModel)this.listVertex.getModel()).addElement(vertexName);
    	((DefaultComboBoxModel)this.comboVertex1.getModel()).addElement(vertexName);
    	((DefaultComboBoxModel)this.comboVertex2.getModel()).addElement(vertexName);
    	
    	this.textFieldVertexName.setText("");
    }

    private void textFieldNumberOfQueensActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {
    	if(this.selectedProblem == null) {
    		this.selectedProblem = Problem.NQUEENS;
    	} else {
        	if(this.selectedProblem.equals(Problem.NQUEENS)) {
        		this.selectedProblem = Problem.TRAVELLING_SALESMAN;
        	} else {
        		this.selectedProblem = Problem.NQUEENS;
        	}	
    	}
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }
    
}
