package app.Management;

public class Genre {
	private String id;
	private String genre;
	
	public Genre() {};
	
	/** Set Methods */
	public void setid(String id) {
		this.id = id;
	}
	
	public void setgenre(String genre) {
		this.genre = genre;
	}
	
	/** Accessor Methods */
	public String getid() {
		return id;
	}
	
	public String getname() {
		return genre;
	}
	
	/** To String Method */
	public String toString() {
		return genre;	
	}
	
	/** Comparison Method */
	public int hashCode(){
		    return  getid().hashCode();
		              
		  }
	
	public boolean equals(Object obj){
        
		return !super.equals(obj);
    }

}
