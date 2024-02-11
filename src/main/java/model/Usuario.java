package model;

public class Usuario {
    private int ID_Usuario;
    private String Nome;
    private String Email;
    private String Senha;
    private String[] Generos;
    private String Data_Nascimento;
    private String Data_Ingresso;
    private int Quantidade_Criticas;

    public Usuario() {
        this.ID_Usuario = -1;
        this.Nome = "";
        this.Email = "";
        this.Senha = "";
        this.Generos = new String[19];
        this.Data_Nascimento = "";
        this.Data_Ingresso = "";
        this.Quantidade_Criticas = 0;
    }
    
    public Usuario(String Nome, String Email, String Senha, String[] Generos, String Data_Nascimento,
            String Data_Ingresso, int Quantidade_Criticas) {
        this.Nome = Nome;
        this.Email = Email;
        this.Senha = Senha;
        this.Generos = Generos;
        this.Data_Nascimento = Data_Nascimento;
        this.Data_Ingresso = Data_Ingresso;
        this.Quantidade_Criticas = Quantidade_Criticas;
    }

    public Usuario(int ID_Usuario, String Nome, String Email, String Senha, String[] Generos, String Data_Nascimento,
            String Data_Ingresso, int Quantidade_Criticas) {
        this.ID_Usuario = ID_Usuario;
        this.Nome = Nome;
        this.Email = Email;
        this.Senha = Senha;
        this.Generos = Generos;
        this.Data_Nascimento = Data_Nascimento;
        this.Data_Ingresso = Data_Ingresso;
        this.Quantidade_Criticas = Quantidade_Criticas;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String[] getGeneros() {
        return Generos;
    }

    public void setGeneros(String[] Generos) {
        this.Generos = Generos;
    }

    public String getData_Nascimento() {
        return Data_Nascimento;
    }

    public void setData_Nascimento(String Data_Nascimento) {
        this.Data_Nascimento = Data_Nascimento;
    }

    public String getData_Ingresso() {
        return Data_Ingresso;
    }

    public void setData_Ingresso(String Data_Ingresso) {
        this.Data_Ingresso = Data_Ingresso;
    }

    public int getQuantidade_Criticas() {
        return Quantidade_Criticas;
    }

    public void setQuantidade_Criticas(int Quantidade_Criticas) {
        this.Quantidade_Criticas = Quantidade_Criticas;
    }

    @Override
    public String toString() {
        return "[Usuario=" + ID_Usuario + ", Nome=" + Nome + ", Email=" + Email + ", Senha=" + Senha + ", Generos=" + Generos
                + ", Data_Nascimento=" + Data_Nascimento + ", Data_Ingresso=" + Data_Ingresso + ", Quantidade_Criticas="
                + Quantidade_Criticas + "]";
    }
}