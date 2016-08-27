package sax;

import java.sql.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SQL {

	public Connection getConnection() throws Exception 
	{
		// Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String USER = "root";
        String PASSWORD = "35152766";
        
    	// Connect to the test database
    	Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb?useSSL=false",USER, PASSWORD);	
	
    	return connection;
	}
	
	
	public void addMovies(Map<String,Movie> movies, Connection connection) throws SQLException
	{
		String existsQuery = "SELECT * FROM movies WHERE title = ? AND director = ?";
		String insertTableSQL = "INSERT INTO movies (title, year, director) VALUES (?,?,?)";

		PreparedStatement existsStatement = connection.prepareStatement(existsQuery);
		PreparedStatement preparedStatement = 
	    		connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);

		for(Entry<String, Movie> movie: movies.entrySet())
		{	    
			existsStatement.setString(1, movie.getValue().getTitle());
			existsStatement.setString(2, movie.getValue().getDirector());
			ResultSet rs = existsStatement.executeQuery();
			
			if(!rs.next())
			{	    
			    preparedStatement.setString(1, movie.getValue().getTitle());
			    preparedStatement.setInt(2, movie.getValue().getYear());
			    preparedStatement.setString(3, movie.getValue().getDirector());
			     						    
			    int status = preparedStatement.executeUpdate();		    
			    ResultSet id = preparedStatement.getGeneratedKeys();
			    
			    if (id.next()) 
			    {
			    	int value = id.getInt(1);
			    	movie.getValue().setid(value);   
		        }		 	
			}		
			//Duplicate Film Found with Different Film ID
			//Keep for Star -> Movie Mapping
			else
			{
				int id = rs.getInt(1);
				movie.getValue().setid(id);			
			}
		 }
		
		existsStatement.close();
		preparedStatement.close();			
	}
	
	public void addStars(Map<String,Star> stars, Connection connection) throws SQLException
	{
		String existsQuery = "SELECT * FROM stars WHERE first_name = ? AND last_name = ?";
		String insertTableSQL = "INSERT INTO stars (first_name, last_name, dob) VALUES (?,?,?)";
		
		PreparedStatement existsStatement = connection.prepareStatement(existsQuery);
		PreparedStatement preparedStatement = 
				connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);
		
		for(Entry<String, Star> star: stars.entrySet())
		{		
			existsStatement.setString(1, star.getValue().getfirst_name());
			existsStatement.setString(2, star.getValue().getlast_name());
			
			ResultSet rs = existsStatement.executeQuery();
			
			if(!rs.next())
			{
				preparedStatement.setString(1, star.getValue().getfirst_name());
			    preparedStatement.setString(2, star.getValue().getlast_name());
			    preparedStatement.setString(3, star.getValue().getdob());
			          
			    preparedStatement.executeUpdate();
			    
			    ResultSet id = preparedStatement.getGeneratedKeys();
			     
			     if (id.next()) 
			     {
			    	 int value = id.getInt(1);
		             star.getValue().setId(value);  
		         }	
			     
			     id.close();
			}
			
			else
			{
				int id = rs.getInt(1);
				star.getValue().setId(id);
			}
		}
		
		existsStatement.close();
		preparedStatement.close();
	}
	
	public void addStarredIn(Map<String,Star> stars, Connection connection) throws SQLException
	{
		String insertStars_in_movies = "INSERT INTO stars_in_movies (star_id,movie_id) VALUES (?,?)";
		
		PreparedStatement statement = connection.prepareStatement(insertStars_in_movies);
		for(Entry<String, Star> star: stars.entrySet())
		{
			Set<Movie> movies = star.getValue().getStarredIn();
			for(Movie movie: movies)
			{
				//System.out.println(star.getValue().getName());
				//System.out.println("Star ID "  + star.getValue().getid());
				///System.out.println("MovieID " + movie.getId());
				//System.out.println("Movie Name: " + movie.getTitle());

				statement.setInt(1, star.getValue().getid());
				statement.setInt(2, movie.getId());
				
				int status = statement.executeUpdate();			
			}
		}
		
		statement.close();	
	}
	
	public void addGenres(Map<String,Movie> movies, Connection connection) throws SQLException
	{	
		String genre_exists = "SELECT id FROM genres WHERE name = ?";
		PreparedStatement existStatement = connection.prepareStatement(genre_exists);
		//String genre_movieExists = "SELECT * FROM genres_in_movies"
		
		String genre_query = "INSERT INTO genres (name) VALUES (?)";
		PreparedStatement insertGenreStatement = 
	    		 connection.prepareStatement(genre_query,PreparedStatement.RETURN_GENERATED_KEYS);
		
		String genreInMovies_query = "INSERT INTO genres_in_movies (genre_id,movie_id) VALUES (?,?)";
		PreparedStatement insertGenre_MovieStatement = connection.prepareStatement(genreInMovies_query);

	
		for(Entry<String, Movie> movie: movies.entrySet())
		{
			for(String genre: movie.getValue().getGenres())
			{
	
				existStatement.setString(1, genre);
				ResultSet result = existStatement.executeQuery();
			
				//If Genre does not exist
				//Insert new genre as well 
				//Into genres movie table
				if(!result.next())
				{
					insertGenreStatement.setString(1, genre);
					int status = insertGenreStatement.executeUpdate();
				
					ResultSet genre_id = insertGenreStatement.getGeneratedKeys();
					
					if (genre_id.next()) 
					{
						insertGenre_MovieStatement.setInt(1, genre_id.getInt(1));
						insertGenre_MovieStatement.setInt(2, movie.getValue().getId());
						
						int status2 = insertGenre_MovieStatement.executeUpdate();			
					}
					
					genre_id.close();			
				}
				
				//If Genres exists get id
				//and insert into genre moves table
				else
				{
					int genre_id = result.getInt(1);
					
					insertGenre_MovieStatement.setInt(1, genre_id);
					insertGenre_MovieStatement.setInt(2, movie.getValue().getId());
					
					int status = insertGenre_MovieStatement.executeUpdate();		
					     
				}			
			}
		}
		
		insertGenre_MovieStatement.close();
		existStatement.close();
		insertGenreStatement.close();
		
	}
}
