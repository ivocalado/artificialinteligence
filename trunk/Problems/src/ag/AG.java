package ag;

import java.util.*;

public class AG {
   protected Vector populacao;
   protected double somaAvaliacoes;
   protected double chance_mutacao;
   protected Vector nova_populacao;
   protected int numero_geracoes,tamanho_populacao;
   
   

   private double calculaSomaAvaliacoes() {
	int i;
	this.somaAvaliacoes=0;
	for(i=0;i<populacao.size();++i) {
		this.somaAvaliacoes+=((Cromossomo) populacao.get(i)).getAvaliacao();
	}
	return(this.somaAvaliacoes);
   }

   public int roleta() {
	int i;
	double aux=0;
	calculaSomaAvaliacoes();
	double limite=Math.random()*this.somaAvaliacoes;	
	for(i=0;( (i<this.populacao.size())&&(aux<limite) );++i) {
	   aux+=((Cromossomo) populacao.get(i)).getAvaliacao();
	}
	/*Como somamos antes de testar, então tiramos 1 de i pois
	  o anterior ao valor final consiste no elemento escolhido*/
	i--;	
	//System.out.println("Escolhi o elemento de indice "+i);
	return(i);
   }

   public void inicializaPopulacao() {
   /*Esta funcao tem que ser substituida por uma que inicialize a populacao
     com a subclasse apropriada de elementoGA*/
	int i;
	this.populacao=new Vector();	
	for(i=0;i<this.tamanho_populacao;++i) {
	   this.populacao.add(new Cromossomo()); 
	}
   }
   public void geracao() {
	nova_populacao=new Vector();
        Cromossomo pai1,pai2, filho;
	int i;
	//System.out.println("Calculando nova geracao...\n");
	for(i=0;i<this.populacao.size();++i) {
		pai1 = (Cromossomo)populacao.get(this.roleta());
		pai2 = (Cromossomo) populacao.get(this.roleta());		
	        filho= pai1.crossoverUmPonto(pai2);
		filho.mutacao(chance_mutacao);
		//System.out.println("Vou adicionar...");
		nova_populacao.add(filho);
	}
   }

   public void moduloPopulacao() {
        populacao.removeAllElements();
        populacao.addAll(nova_populacao);	
   }

   protected int determinaMelhor() {
	int i,ind_melhor=0;
	Cromossomo aux;
	this.avaliaTodos();
	double aval_melhor=((Cromossomo)this.populacao.get(0)).getAvaliacao();
	for(i=1;i<this.populacao.size();++i) {
		aux=(Cromossomo)this.populacao.get(i);		
		if (aux.getAvaliacao()>aval_melhor) {
		   aval_melhor=aux.getAvaliacao();
		   ind_melhor=i;
		}
	}
	return(ind_melhor);
   }

   private void avaliaTodos() {
	int i;
	Cromossomo aux;
	//System.out.println("Avaliando todos...\n");
	for(i=0;i<this.populacao.size();++i) {
		aux=(Cromossomo)this.populacao.get(i);
		aux.calculaAvaliacao();
	}
	this.somaAvaliacoes=calculaSomaAvaliacoes();
	//System.out.println("A soma das avaliacoes eh "+this.somaAvaliacoes);
   }
   
   public void executa() {
	int i, j;	
	this.inicializaPopulacao();
	for (i=0;i<this.numero_geracoes;++i) {
		System.out.println("\n================================\n\nGERAÇÃO "+i+"\n");
		this.avaliaTodos();
		this.geracao();
		this.moduloPopulacao();
	
                //System.out.println("saiu da geracao "+i);
                j=this.determinaMelhor();
                System.out.println("Melhor indivíduo da geração: \n"+(Cromossomo) this.populacao.get(j));
        }
   }

   /****************/
   /* Construtores */
   /****************/

   public AG(int num_geracoes,int tam_populacao, double prob_mut) {
   	this.chance_mutacao=prob_mut;
   	this.numero_geracoes=num_geracoes;
   	this.tamanho_populacao=tam_populacao;
   }

   public AG(int tam_populacao, double prob_mut) {
   	this(60,tam_populacao,prob_mut);
   }
   
   public AG(double prob_mut) {
   	this(60,100,prob_mut);
   }
   
   public AG() {
   	this(60,100,0.001);
   }
   
 
  
}