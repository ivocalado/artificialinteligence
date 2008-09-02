package br.ufal.ic.se.database;

import java.sql.DriverManager;

public class testebanco {
	public testebanco() {
		try {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		DriverManager.getConnection("jdbc:hsqldb:file:sistema_especialista", "sa", "");
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		System.out.println("passou aqui");
	}
	}
	public static void main(String[] args) {
		new testebanco();
	}
	

}

