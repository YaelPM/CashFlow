package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.User;
import Services.AdapterMySQL;


public class UserDAO {
	private Connection connection = null;
	private final int ACCEPT = 1;
	
	public UserDAO() {
		AdapterMySQL conector = new AdapterMySQL();
		connection = conector.getConnection();
	}
	
	public boolean insert(User user) {
		boolean resultado = false;
		if (connection != null) {
			String sql = "insert into user values(?,?,?,?)";
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, user.hashCode());
				statement.setString(2, user.getName());
				statement.setString(3, user.getRol());
				statement.setString(4, user.getPassword());
				if (statement.executeUpdate() == ACCEPT)
					resultado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public User getUser(int idUser) {
		User user = null; 
		if (connection != null) {
			String sql = "select * from user where idUser=?";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, idUser);
				ResultSet results = statement.executeQuery();
				results.next();
				if (results.getRow() == ACCEPT) {
					user = new User();
					user.setIdUser(results.getInt(1));
					user.setName(results.getString(2));
					user.setRol(results.getString(3));
					user.setPassword(results.getString(4));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public User getUser(String name) {
		User user = null;
		
		if (connection != null) {
			String sql = "select * from user where name=?";
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, name);
				ResultSet results = statement.executeQuery();
				results.next();
				if (results.getRow() == ACCEPT) {
					user= new User(results.getInt(1), results.getString(2), results.getString(3), results.getString(4));

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			user= null;
		}
		return user;
			
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		
		if (connection != null) {
			String sql = "select * from user";
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet results = statement.executeQuery();
				while (results.next()) {
					int idUser = results.getInt(1);
					String name = results.getString(2);
					String rol= results.getString(3);
					String password = results.getString(4);
					User user = new User(idUser,name,rol,password);
					users.add(user);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return users;
	}
	
	public boolean delete(int idUser) {
		boolean resultado = false;
		
		if (connection != null) {
			String sql = "delete from user where idUser=?";
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, idUser);
				if (statement.executeUpdate() == ACCEPT)
					resultado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public boolean delete(String login) {
		boolean resultado = false;
		
		if (connection != null) {
			String sql = "delete from user where nombre=?";
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, login);
				if (statement.executeUpdate() == ACCEPT)
					resultado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
	}
	
	public boolean update(User user) {
		boolean resultado = false;
		
		if (connection != null) {
			String sql = "update user set "
					+ "name=?, rol=?"
					+ "password=? where idUser=?";
			
			try {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, user.getName());
				statement.setString(2, user.getRol());
				statement.setString(3, user.getPassword());
				if (statement.executeUpdate() == ACCEPT)
					resultado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultado;
		
	}


}
