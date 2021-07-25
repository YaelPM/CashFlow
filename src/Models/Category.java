package Models;

public class Category{

    private int idCategoria;
    private String categoria;
    private String subcategoria;
	public Category(int idCategoria, String categoria, String subcategoria) {
		super();
		this.idCategoria = idCategoria;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

}
