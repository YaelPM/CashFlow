package Models;

public class User{

    private int idUser;
    private String name;
    private String rol;
    private String password;
    
    public User() {
	}
    
    public User(String name, String rol, String password) {
		super();
		this.name = name;
		this.rol = rol;
		this.password = password;
	}
    
	public User(int idUser, String name, String rol, String password) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.rol = rol;
		this.password = password;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    

}