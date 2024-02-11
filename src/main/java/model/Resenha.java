package model;

public class Resenha {
  private int ID_Resenha;
  private String Conteudo;
  private Boolean Opiniao;
  private String Data;

  public Resenha() {
    this.ID_Resenha = -1;
    this.Conteudo = "";
    this.Opiniao = false;
    this.Data = "";
  }
  
  public Resenha(String Conteudo, Boolean Opiniao, String Data) {
	    this.Conteudo = Conteudo;
	    this.Opiniao = Opiniao;
	    this.Data = Data;
	  }

  public Resenha(int ID_Resenha, String Conteudo, Boolean Opiniao, String Data) {
    this.ID_Resenha = ID_Resenha;
    this.Conteudo = Conteudo;
    this.Opiniao = Opiniao;
    this.Data = Data;
  }

  public int getID_Resenha() {
    return ID_Resenha;
  }

  public void setID_Resenha(int ID_Resenha) {
    this.ID_Resenha = ID_Resenha;
  }

  public String getConteudo() {
    return Conteudo;
  }

  public void setConteudo(String Conteudo) {
    this.Conteudo = Conteudo;
  }

  public Boolean getOpiniao() {
    return Opiniao;
  }

  public void setOpiniao(Boolean Opiniao) {
    this.Opiniao = Opiniao;
  }

  public String getData() {
    return Data;
  }

  public void setData(String Data) {
    this.Data = Data;
  }

  @Override

  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }
}