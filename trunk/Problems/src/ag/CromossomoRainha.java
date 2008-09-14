package ag;


public class CromossomoRainha extends Cromossomo {
    private static final int nRainhas = 8;
    
    private void setCromossomoToArray(Rainha[] r){
        for (int i=0; i<nRainhas; i++){
            r[i] = new Rainha(i, (int)converteBooleano(i*nBitsPorGene(nRainhas),(i+1)*nBitsPorGene(nRainhas)-1));
        } 
    }

    public void imprimeTabuleiro(){
        boolean[][] aux = new boolean[nRainhas][nRainhas]; 
        Rainha r[] = new Rainha[nRainhas];
        setCromossomoToArray(r);
        
        for (int i=0;i<nRainhas;i++){
            aux[r[i].getLinha()][r[i].getColuna()] = true;
        }

        System.out.print("/");
        for (int i=0;i<nRainhas*4-1;i++)
            System.out.print("-");
        System.out.println("\\");
        
        
        for (int i = 0; i < nRainhas; i++) {
          for (int j = 0; j < nRainhas; j++) {
            if (aux[i][j]) {
              System.out.print("| o ");
            } else {
              System.out.print("|   ");
            }
          }
          System.out.println("|");
          if (i < nRainhas-1) {
           
            System.out.print("|");
            for (int k=0;k<nRainhas;k++)
                System.out.print("---|");
            System.out.println("");
            
          } else {
            System.out.print("\\");
            for (int m=0;m<nRainhas*4-1;m++)
                System.out.print("-");
            System.out.println("/");
          }
        }        
        
    }


    public double calculaAvaliacao(){
        Rainha[] rainhas = new Rainha[nRainhas];           
        int resultado=0;
        
        setCromossomoToArray(rainhas);
        
        for (int i=0; i<nRainhas; i++){
            for (int j=0; j<nRainhas; j++){
                if (i!=j && !(rainhas[i].ataca(rainhas[j])) )
                    resultado++;
            }
        }
        this.avaliacao = resultado;
        return resultado;
    }



    /****************/
    /* Construtores */
    /****************/
    
    public CromossomoRainha() {
        super(nBitsPorGene(nRainhas)*nRainhas);   //para 4 rainhas
    }
    
    public CromossomoRainha(int tamanho){
        super(tamanho);
    }
    
    
    public static void main(String args[]){
        CromossomoRainha c = new CromossomoRainha();
        c.calculaAvaliacao();
        c.imprimeTabuleiro();
        System.out.println(c.toString() + " ");        
        
    }
    
}
