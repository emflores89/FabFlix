package app.Management;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	HashMap<Movie,Integer> items;
	
	public Cart() {
		super();
		this.items = new HashMap<Movie,Integer>();
	}
	
	public void addItem(Movie movie){
		if(!items.containsKey(movie)) {
			items.put(movie, 1);
		}
		
		else {
			Integer quantity = items.get(movie);
			items.put(movie, quantity + 1);
		}
	}
	
	public void setQuantity(Movie movie, Integer quantity){
		if(items.containsKey(movie))
		{
			if(quantity == 0)
			{
				items.remove(movie);
			}
			else
			{
				items.put(movie,quantity);
			}
		}
	}
	
	
	public void setItems(HashMap<Movie,Integer> items)
	{
		this.items = items;
	}
	
	public HashMap<Movie,Integer> getItems()
	{
		return items;
	}
	
	public void emptyCart()
	{
		items.clear();
	}
	public void removeItem(Movie movie)
	{
		items.remove(movie);
	
	}
	
	public double calculate_total()
	{
		double total = 0;
		
		for (Map.Entry<Movie, Integer> entry : items.entrySet()) {
		    Movie key = entry.getKey();
		    Integer value = entry.getValue();
		    
		    total += key.getPrice() * value;
		}
		
		
		return Math.round(total * 100.0) / 100.0;
	}
	

}
