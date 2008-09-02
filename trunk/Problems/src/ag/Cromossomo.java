package ag;

public class Cromossomo implements Comparable {
	protected String valor;
	protected double avaliacao;
        
        
        protected float converteBooleano(int inicio,int fim) {
           int i;
           float aux=0;
           String s=this.getValor();
           for(i=inicio;i<=fim;++i) {
                aux*=2;
                if (s.substring(i,i+1).equals("1")) {
                   aux+=1;
                }
           }
           return(aux);
        }

        protected static int nBitsPorGene(int numero){
            for (int i=1; i<=numero;i++){
                if (Math.pow(2,i) >= numero)
                    return i;
            }
            return 0;
        }
        
        public boolean equals(Cromossomo outro) {
            //Usada para testar se dois indivíduos são iguais - importante
            //quando formos ver os módulos de população mais avançados
            return(valor.equals(outro.getValor()));
        }
        
        public int compareTo(Object outro) {
            //Será usada na hora de inserir os elementos em ordem em uma população
            //para o GA com steady state (capítulo 7)
            int retorno=-1;
            Cromossomo aux=(Cromossomo) outro;
            if (avaliacao>aux.getAvaliacao()) {retorno=1;}
            if (avaliacao==aux.getAvaliacao()) {retorno=0;}
            return(retorno);
        }
	
	public double calculaAvaliacao() {
	/*Esta função deve ser sobreescrita por uma função dentro da sub-classe
	  a ser usada como definidora dos cromossomos do GA*/
	   this.avaliacao=0;
	   return(this.avaliacao);
	}

	public double getAvaliacao() {
	   return(this.avaliacao);
	}

	public String getValor() {
 	   return(this.valor);
	}

	public void setValor(String aux) {
 	   this.valor=aux;
	}


	protected void inicializaElemento(int tamanho) {
	   int i;
	   this.valor="";
	   for(i=0;i<tamanho;++i) {
	      if (Math.random()<0.5) {
	         this.valor=this.valor+"0";
	      } else {
	         this.valor=this.valor+"1";
	      }
	   }
	}

	/****************/
	/* Construtores */
	/****************/
	
	public Cromossomo(String novoValor) {
	   this.valor=novoValor;
	}	

	public Cromossomo(int tamanho) {
	   inicializaElemento(tamanho);
	}

	public Cromossomo() {
  	   this(100);
	}

	

	/************************/
	/* Operadores Geneticos */
	/************************/

	public void mutacao(double chance) {
	   int i;
	   int tamanho=this.valor.length();
	   String aux,inicio,fim;
	   for(i=0;i<tamanho;i++) {		
		if (java.lang.Math.random()<chance) {		   
		   aux=this.valor.substring(i,i+1);
  	         if (aux.equals("1")) {
			aux="0";
		   } else {
			aux="1";
		   }		   
		   inicio=this.valor.substring(0,i);
		   fim=this.valor.substring(i+1,tamanho);		   
		   this.valor=inicio+aux+fim;
		}
	   }
	}

	public void mutacao() {
		this.mutacao(0.005);
	}

	public Cromossomo crossoverUmPonto(Cromossomo outroPai)  {
	   String aux1;	   
	   Cromossomo retorno=null;
	   int pontoCorte=(new Double(java.lang.Math.random()*this.valor.length())).intValue();;	   
	   if (java.lang.Math.random()<0.5) {		
	   	aux1=this.valor.substring(0,pontoCorte)+outroPai.getValor().substring(pontoCorte,outroPai.getValor().length());
	   } else {		
		aux1=outroPai.getValor().substring(0,pontoCorte)+this.valor.substring(pontoCorte,this.valor.length());
	   }
	   try {
	      retorno=(Cromossomo) outroPai.getClass().newInstance();
	      retorno.setValor(aux1);
	   } catch (Exception e) {
	   }	   	   
	   return(retorno);
	} 

	/********************************/
	/* Metodos Basicos de Classe    */
	/********************************/

	public String toString() {
		return(" String:"+this.valor+"\n Avaliacao:"+this.avaliacao);
	}

	
}