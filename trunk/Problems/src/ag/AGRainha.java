package ag;

import java.util.*;

 public class AGRainha extends AG {
 
   public void inicializaPopulacao() {   
	   int i;
	   this.populacao=new Vector();	
	   for(i=0;i<this.tamanho_populacao;++i) {
		   this.populacao.add(new CromossomoRainha()); 
	   }
   }
   
   public void executa() {
        super.executa();
        CromossomoRainha c = (CromossomoRainha)this.populacao.get(this.determinaMelhor());
        c.imprimeTabuleiro();
   }
   
   /****************/
   /* Construtores */
   /****************/

   public AGRainha(int num_geracoes,int tam_populacao, double prob_mut) {
	   super(num_geracoes,tam_populacao,prob_mut);
   }
   
   public AGRainha(int tam_populacao, double prob_mut) {
	   super(60,tam_populacao,prob_mut);
   }
   
   public AGRainha(double prob_mut) {
	   super(60,100,prob_mut);
   }
   
   public AGRainha() {
	   super(60,100,0.001);
   }
   
 }