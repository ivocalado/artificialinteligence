package br.ufal.ic.se.database;


import java.sql.*;
/**
 *
 * @author Anderson Brandão
 */
public class DataBase {
    private static Connection con = null;	
    protected  String urlConnectionString;
    protected  String userName;
    protected  String password;


    /** Creates a new instance of JDBCPanel */

    public DataBase(String urlConnectionString, String userName, String password) {
	this.urlConnectionString = urlConnectionString;
	this.userName = userName;
	this.password = password;
    }	

    public  synchronized Connection getConnection() throws SQLException {
	if (con == null)
	    return conecta();
	return con;				
    }

    public  synchronized Connection getConnection(String urlConnectionString, String userName, String password) throws Exception {
	try {
	    desconecta(); 
	    if (con == null) {
		this.urlConnectionString = urlConnectionString;
		this.userName = userName;
		this.password = password;
		return conecta();
	    }	
	}
	catch (IllegalStateException e) {
	    System.err.println("Couldn't switch database." + e);							
	}		
	return con;
    }

    private Connection conecta() throws IllegalStateException {
	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    con = DriverManager.getConnection(urlConnectionString, userName, password);			
	    return con;
	}
	catch (SQLException e) {
	    throw new IllegalStateException("Failed to connect to the database. \n" +
	    "Please check the database connection's parameters ");
	} 
	catch(Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    public void desconecta() {
	if (con != null) 
	    try {
		con.createStatement().execute("SHUTDOWN SQL");
		con.close();			
		con = null;
	    } 
	catch (SQLException shutdownFailure) {
	    System.err.println("Failed to shutdown database " + shutdownFailure);
	}		
    }

    private void close() {
	if (con != null)
	    try {
		con.close();		
	    } catch (SQLException sqlCloseFailedException) {
		System.err.println("Failed to close JDBC connection. Now trying to release a 'connection' reference" + sqlCloseFailedException);		
	    } finally {
		con = null;
	    }
    }    

    public static void main(String[] args) {
	try {
	    DataBase db = new DataBase("jdbc:mysql://localhost/sistema_especialista", "root", "presto");
	    if (db.getConnection()==null) System.err.println("erro. connection nulo");
	    Statement rs = db.getConnection().createStatement();
	    ResultSet rset = rs.executeQuery("SELECT * FROM fatos");
	    while (rset.next()) {

		System.out.println(rset.getString("VARIAVEL"));
	    }		

	}catch(Exception e) {
	    e.printStackTrace();
	}
    }
}
