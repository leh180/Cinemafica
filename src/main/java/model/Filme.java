package model;

public class Filme {
  private int ID_Filme;
  private String[] Generos;
  private String Linguagem;
  private String Titulo;
  private Boolean Classificacao_Indicativa;
  private String Descricao;
  private String Data_Lancamento;

  public Filme() {
    this.ID_Filme = -1;
    this.Generos = new String[19];
    this.Linguagem = "";
    this.Titulo = "";
    this.Classificacao_Indicativa = false;
    this.Descricao = "";
    this.Data_Lancamento = "";
  }

  public Filme(int ID_Filme, String[] Generos, String Linguagem, String Titulo, Boolean Classificacao_Indicativa,
      String Descricao, String Data_Lancamento) {
    this.ID_Filme = ID_Filme;
    this.Generos = Generos;
    this.Linguagem = Linguagem;
    this.Titulo = Titulo;
    this.Classificacao_Indicativa = Classificacao_Indicativa;
    this.Descricao = Descricao;
    this.Data_Lancamento = Data_Lancamento;
  }

  public int getID_Filme() {
    return ID_Filme;
  }

  public void setID_Filme(int ID_Filme) {
    this.ID_Filme = ID_Filme;
  }

  public String[] getGeneros() {
    return Generos;
  }

  public void setGeneros(String[] Generos) {
    this.Generos = Generos;
  }

  public String getLinguagem() {
    return Linguagem;
  }

  public void setLinguagem(String Linguagem) {
    this.Linguagem = Linguagem;
  }

  public String getTitulo() {
    return Titulo;
  }

  public void setTitulo(String Titulo) {
    this.Titulo = Titulo;
  }

  public Boolean getClassificacao_Indicativa() {
    return Classificacao_Indicativa;
  }

  public void setClassificacao_Indicativa(Boolean Classificacao_Indicativa) {
    this.Classificacao_Indicativa = Classificacao_Indicativa;
  }

  public String getDescricao() {
    return Descricao;
  }

  public void setDescricao(String Descricao) {
    this.Descricao = Descricao;
  }

  public String getData_Lancamento() {
    return Data_Lancamento;
  }

  public void setData_Lancamento(String Data_Lancamento) {
    this.Data_Lancamento = Data_Lancamento;
  }

  @Override

  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }
}