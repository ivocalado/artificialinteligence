package br.ufal.ic.se.database;

import java.sql.*;


import br.ufal.ic.se.models.*;

public class Banco {
	private static Banco banco;

	private static Connection connection = null;

	private static Statement statement = null;

	private ResultSet resultSet;

	private Banco() {
		try {
			//Class.forName("org.hsqldb.jdbcDriver").newInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sistema_especialista", "root",
					"presto");
			//connection = DriverManager.getConnection("jdbc:hsqldb:file:sistema_especialista", "sa", "");
			statement = connection.createStatement();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		try {
		getInstance();
		String[][] q = getInstance().getFacts();
		//for (String[] a : q) System.out.println(a[0]);
		System.out.println("conectou");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Banco getInstance() {
		if (banco == null) {
			banco = new Banco();
		}
		return banco;
	}

	public void insertFacts(String variavel, String valor) {
		try {
			statement.executeUpdate("INSERT INTO fatos(variavel, valor)"
					+ " VALUES('" + variavel + "','" + valor + "')");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public void updateFacts(String variavel, String valor) {
		try {
			statement.executeUpdate("UPDATE fatos SET valor ='" +
					valor+"' WHERE variavel='"+variavel+"'");					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public String[][] getFacts() {
		String[][] rows = null;
		int numLinhas;
		try {
			resultSet = statement.executeQuery("SELECT * FROM fatos");
			resultSet.last();
			numLinhas = resultSet.getRow();
			resultSet.beforeFirst();

			rows = new String[numLinhas][2];

			for (int i = 0; resultSet.next(); i++) {
				rows[i][0] = resultSet.getString("variavel");
				rows[i][1] = resultSet.getString("valor");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resultSet.close();
			} catch (Exception ex) {
				// TODO: handle exception
			}

		}
		return rows;
	}

	public String[] getRoles() {
		String[] rows = null;
		int numLinhas;
		try {
			resultSet = statement.executeQuery("SELECT * FROM regras");
			resultSet.last();
			numLinhas = resultSet.getRow();
			resultSet.beforeFirst();
			rows = new String[numLinhas];
			for (int i = 0; resultSet.next(); i++) {
				rows[i] = resultSet.getString("regra");
			}	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resultSet.close();
			} catch (Exception ex) {
				// TODO: handle exception
			}

		}
		return rows;
		
	}
	
	public void insertRegra(String regra) {
		try {
			statement.executeUpdate("INSERT INTO regras(regra) VALUES('"+ regra +"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteRoles(Integer key) {
		try {
			return (statement.executeUpdate("DELETE FROM regras WHERE chave="
					+ key) != 0);
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return false;
	}
	/*
	 * public void insertFato(Fato fato){ try { statement.executeUpdate("INSERT
	 * INTO fatos VALUES () } catch (SQLException e) { // TODO: handle exception }
	 *  }
	 */
	public void closeConnection() {
		try {
			statement.close();
			connection.close();
			banco = null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.exit(1);
		}
	}
	// Efetua a busca por variável e retorna seu valor
	public String findFactbyVariable(String fact) {
		try {
			ResultSet r = statement
					.executeQuery("SELECT * FROM fatos WHERE variavel='" + fact
							+ "'");
			if (r.next())
				return r.getString("valor");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	//Efetua a busca por valor e retorna sua variável
	public String findFactbyValue(String fact) {
		try {
			ResultSet r = statement
					.executeQuery("SELECT * FROM fatos WHERE valor='" + fact
							+ "'");
			if (r.next())
				return r.getString("variavel");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
