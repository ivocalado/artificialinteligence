package ag;


public class Rainha {

  private int linha;
  private int Coluna;

  public Rainha(int linha, int Coluna) {
    this.linha = linha;
    this.Coluna = Coluna;
  }

  public int getLinha() {
    return linha;
  }

  public int getColuna() {
    return Coluna;
  }

  public boolean ataca(Rainha q) {
    if (q.getLinha() == this.linha || q.getColuna() == this.Coluna)
      return true;
    int x = Math.abs(this.linha - q.getLinha());
    int y = Math.abs(this.Coluna - q.getColuna());
    return (x == y);
  }

  public String toString(){
      return "\nLinha: " + linha + " Coluna: " + Coluna;
      
  }

}