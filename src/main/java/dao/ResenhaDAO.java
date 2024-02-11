package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Resenha;

public class ResenhaDAO extends DAO {

	public ResenhaDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Resenha resenha) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO resenha (\"Conteudo\", \"Opiniao\", \"Data\")"
					+ "VALUES ('" +  resenha.getConteudo() + "', '"
					+ resenha.getOpiniao() + "', '" + resenha.getData() + "')";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Resenha get(int ID_Resenha) {
		Resenha resenha = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM resenha WHERE 'ID_Resenha'=" + ID_Resenha;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				resenha = new Resenha(rs.getInt("ID_Resenha"), rs.getString("Conteudo"), rs.getBoolean("Opiniao"),
						rs.getString("Data"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resenha;
	}

	public List<Resenha> get() {
		return get("");
	}

	public List<Resenha> getOrderByID_Resenha() {
		return get("ID_Resenha");
	}

	public List<Resenha> getOrderByConteudo() {
		return get("Conteudo");
	}

	public List<Resenha> getOrderByOpiniao() {
		return get("Opiniao");
	}

	public List<Resenha> getOrderByData() {
		return get("Data");
	}

	public List<Resenha> getOrderByID_Usuario() {
		return get("ID_Usuario");

	}

	public List<Resenha> getOrderByID_Filme() {
		return get("ID_Filme");
	}

	private List<Resenha> get(String orderBy) {

		List<Resenha> resenhas = new ArrayList<Resenha>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM resenha" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY \"" + orderBy + "\""));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Resenha a = new Resenha(rs.getInt("ID_Resenha"), rs.getString("Conteudo"), rs.getBoolean("Opiniao"),
						rs.getString("Data"));
				resenhas.add(a);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resenhas;
	}

	public boolean update(Resenha resenha) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE resenha SET ID_Resenha = '" + resenha.getID_Resenha() + "', Conteudo = '"
					+ resenha.getConteudo() + "', Opiniao = '" + resenha.getOpiniao() + "', Data = '"
					+ resenha.getData() + "'"
					+ " WHERE ID_Resenha = " + resenha.getID_Resenha();

			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean delete(int ID_Resenha) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM resenha WHERE ID_Resenha = " + ID_Resenha;
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