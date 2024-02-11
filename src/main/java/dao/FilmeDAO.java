package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Filme;

public class FilmeDAO extends DAO {

	public FilmeDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Filme filme) {
	    boolean status = false;
	    try {
	        String descricao = filme.getDescricao().replace("\"", "\\\"");
	        
	        String sql = "INSERT INTO filme (\"Generos\", \"Titulo\", \"Linguagem\", \"Classificacao_Indicativa\", \"Descricao\", \"Data_Lancamento\")"
	                + "VALUES (?, ?, ?, ?, ?, ?)";
	        
	        PreparedStatement st = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        st.setArray(1, conexao.createArrayOf("VARCHAR", filme.getGeneros()));
	        st.setString(2, filme.getTitulo());
	        st.setString(3, filme.getLinguagem());
	        st.setBoolean(4, filme.getClassificacao_Indicativa());
	        st.setString(5, descricao);
	        st.setString(6, filme.getData_Lancamento());
	        
	        Date dataLancamento = Date.valueOf(filme.getData_Lancamento());
	        st.setDate(6, dataLancamento);

	        int affectedRows = st.executeUpdate();

	        if (affectedRows > 0) {
	            ResultSet generatedKeys = st.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int generatedID = generatedKeys.getInt(1);
	                filme.setID_Filme(generatedID);
	            }
	            status = true;
	        }

	        st.close();
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    return status;
	}


	public Filme get(int ID_Filme) {
		Filme filme = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM filme WHERE 'ID_Filme'=" + filme;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				Array generosArray = rs.getArray("Generos");
	            String[] generos = (String[]) generosArray.getArray();
	            
				filme = new Filme(rs.getInt("ID_Filme"), generos, rs.getString("Linguagem"),
						rs.getString("Titulo"), rs.getBoolean("Classificacao_Indicativa"), rs.getString("Descricao"),
						rs.getString("Data_Lancamento"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return filme;
	}

	public List<Filme> get() {
		return get("");
	}

	public List<Filme> getOrderByID() {
		return get("ID_Filme");
	}

	public List<Filme> getOrderByGeneros() {
		return get("Generos");
	}

	public List<Filme> getOrderByLinguagem() {
		return get("Linguagem");
	}

	public List<Filme> getOrderByTitulo() {
		return get("Titulo");
	}

	public List<Filme> getOrderByClassificacao_Indicativa() {
		return get("Classificacao_Indicativa");

	}

	public List<Filme> getOrderByDescricao() {
		return get("Descricao");
	}

	public List<Filme> getOrderByData_Lancamento() {
		return get("Data_Lancamento");
	}

	private List<Filme> get(String orderBy) {

		List<Filme> filmes = new ArrayList<Filme>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM filme" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Array generosArray = rs.getArray("Generos");
	            String[] generos = (String[]) generosArray.getArray();
	            
				Filme a = new Filme(rs.getInt("ID_Filme"), generos, rs.getString("Linguagem"),
						rs.getString("Titulo"), rs.getBoolean("Classificacao_Indicativa"), rs.getString("Descricao"),
						rs.getString("Data_Lancamento"));
				filmes.add(a);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return filmes;
	}

	public boolean update(Filme filme) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE filme SET ID_Filme = '" + filme.getID_Filme() + "', Generos = '"
					+ filme.getGeneros() + "', Linguagem = '" + filme.getLinguagem() + "', Titulo = '" + filme.getTitulo()
					+ "', Classificacao_Indicativa = '"
					+ filme.getClassificacao_Indicativa() + "', Descricao = '"
					+ filme.getDescricao() + "', Data_Lancamento = '"
					+ filme.getData_Lancamento() + "'"
					+ " WHERE ID_Filme = " + filme.getID_Filme();

			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean delete(int ID_Filme) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM filme WHERE ID_Filme = " + ID_Filme;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}