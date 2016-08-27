package app.Management;

public class User {
	private String first_name;
	private String last_name;
	private String cc_id;
	private String address;
	private String email;
	private String password;
	private String id;
	private Cart cart;
	
	public User() {
		this.cart = new Cart();
	}
	/**Set Methods */
	
	public void setid(String id){
		this.id = id;
	}
	public void setfirst_name(String first_name){
		this.first_name = first_name;
	}
	
	public void setlast_name(String last_name){
		this.last_name = last_name;
	}
	
	public void setcc_id(String cc_id){
		this.cc_id = cc_id;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setpassword(String password){
		this.password = password;
	}
	
	public void setCart(Cart cart){
		this.cart = cart;
	}
	
	/**Get Methods */
	public String getid(){
		return id;
	}
	
	public String getfirst_name(){
		return first_name;
	}
	
	public String getlast_name(){
		return last_name;
	}
	
	public String getcc_id(){
		return cc_id;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getpassword(){
		return password;
	}
	
	public Cart getCart(){
		return cart;
	}
	
	/** Add methods */
	public void addMovieToCart(Movie movie) {
		cart.addItem(movie);
	}

}
