package app.Search;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import app.Management.Movie;
import app.Management.Star;
import app.Management.User;
import app.Management.Genre;
import app.Management.MetaData;


import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

public class Search {
	//Set true for connection pooling. False otherwise.
	public final static boolean CONNECTION_POOLING = false;
	
	public Search() {
		super();
	}
	
	public Connection getConnection() throws Exception 
	{
	    
    	// the following few lines are for connection pooling
	    // Obtain our environment naming context
		if(CONNECTION_POOLING)
		{
	    	Context initCtx = new InitialContext();
	        if (initCtx == null) System.out.println ("initCtx is NULL");
		    
	        Context envCtx = (Context) initCtx.lookup("java:comp/env");
	        if (envCtx == null) System.out.println ("envCtx is NULL");
			
		    // Look up our data source
		    DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
			
			if (ds == null)
				System.out.println ("ds is null.");
				Connection connection = ds.getConnection();
			
			if (connection == null)
	           System.out.println ("dbcon is null.");
			
	    	return connection;
			
		}
		
		else
		{
			// Incorporate mySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        String USER = "root";
	        String PASSWORD = "35152766";
	        
	    	// Connect to the test database
	    	Connection connection = DriverManager.getConnection("jdbc:mysql:///moviedb?useSSL=false",USER, PASSWORD);	
			return connection;
		}
	}
	
	public ArrayList<String> getGenres(Connection connection) throws SQLException
	{
		ArrayList<String> genres = new ArrayList<String>();
		String query = "SELECT * FROM genres";
		
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		
		while(results.next())
		{
			genres.add(results.getString(2));
		}
		
		results.close();
		statement.close();
		
		return genres;
		
	}
	
	
	public HashSet<Movie> getBestSellingMovies(Connection connection) throws SQLException
	{
		// Prepare Query
		 //String query = "SELECT * FROM customers WHERE email = ? AND password = ?";
		String query = 
				"SELECT "
					+"movies.id,"
					+"movies.title,"
					+"movies.year,"
					+"movies.banner_url,"
					+"COUNT(movies.id) as count"
				+" FROM movies"
				+" INNER JOIN sales"
					+" ON movies.id = sales.movie_id"
				+" GROUP BY movies.title"
				+" ORDER BY count DESC LIMIT 5";
		
		Statement statement = connection.createStatement();
		
		//Execute Query
		ResultSet result = statement.executeQuery(query);
		
		HashSet<Movie> movies = new HashSet<Movie>();
		while(result.next())
		{
			Movie movie = new Movie();
			movie.setMovieId(result.getString(1));
			movie.setTitle(result.getString(2));
			movie.setYear(result.getString(3));
			movie.setBanner_url(result.getString(4));
			
			movies.add(movie);
			
		}
		
		result.close();
		statement.close();

		return movies;
		
	}
	
	public User searchUser(String email, String password, Connection connection) throws SQLException
	{
		// Prepare Query
		String query = 
				"SELECT " 
					+ "customers.id," 
					+ "customers.first_name," 
					+ "customers.last_name," 
					+ "customers.email"
				+" FROM customers WHERE email = ? AND md5(password) = md5(?)";
		
		PreparedStatement statement = connection.prepareStatement(query);
		 
		//Bind values into the parameters
		statement.setString(1, email);
		statement.setString(2, password);
		 
		//Execute Query
		ResultSet result = statement.executeQuery();
		
		//If User Exists then return User Object otherwise null
		User user = null;
		if(result.next())
		{
			user = new User();
			user.setid(result.getString(1));
		   	user.setfirst_name(result.getString(2));
		   	user.setlast_name(result.getString(3));
		   	user.setEmail(result.getString(4));
		}
		
		
		result.close();
		statement.close();
		connection.close();
		
		return user;
		
	}
	
	public int recordSale(String customer_id, String movie_id, Connection connection) throws SQLException
	{
		
        String insertTableSQL = "INSERT INTO sales"
				+ "(customer_id, movie_id, sale_date) VALUES"
				+ "(?,?,?)";
		
        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
		
		preparedStatement.setString(1, customer_id);
		preparedStatement.setString(2, movie_id);
		java.util.Date today = new java.util.Date();
		preparedStatement.setDate(3, new java.sql.Date(today.getTime()));

		int status = preparedStatement.executeUpdate();
		
		preparedStatement.close();
		connection.close();
		
		return status;
		
	}
	
	public Integer checkOutAuthentication(String cc_id, String first_name, String last_name, String expiration
			, Connection connection) throws SQLException {
		
		String query = "SELECT COUNT(*) FROM creditcards WHERE id =? AND first_name = ? AND last_name =? AND expiration =?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setString(1,cc_id);	
		statement.setString(2, first_name);	
		statement.setString(3, last_name);
		statement.setString(4, expiration);
		
		ResultSet result = statement.executeQuery();
		
		//Get Result
		result.next();
		int checkout_status = result.getInt(1);
		
		//Close Connections
		statement.close();
		result.close();
		connection.close();
		
		return checkout_status;
	}
	
	public List<String> autoCompleteMovies(String[] tokens, Connection connection) throws SQLException
	{
		String query = "SELECT title FROM movies WHERE";
		Set<String> setTitle = new HashSet<String>();
		
		for(int index = 0; index < tokens.length; index++)
		{
			String token_ = tokens[index];
			String token = token_.replace("'", "");
			
			if(tokens.length == 1)
			{
				query += " match(movies.title) against('" + token + "*' IN BOOLEAN MODE)";
			
			}
			
			else if(index == 0 && tokens.length != 1)
			{
				query += " match(title) against('"+token+"' IN BOOLEAN MODE)";
			}
	
			else if(index == tokens.length-1)
			{
				query += "AND match(movies.title) against('" + token + "*' IN BOOLEAN MODE)";
			}
			
			else
			{
				query += " AND match(title) against('"+token+"' IN BOOLEAN MODE) ";
			}
		}
		
		query += " LIMIT 10";
		//System.out.println(query);
		
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(query);
		
		while(results.next())
		{
			setTitle.add(results.getString(1));
		}
		
		statement.close();
		results.close();
		connection.close();
		return new ArrayList<String>(setTitle);
	}
	
	
	public Star searchStar(String first_name, String last_name, String starid, Connection connection) throws SQLException
	{
		String query = 
			"SELECT "
				+"stars.first_name,"
			    +"stars.last_name,"
			    +"stars.dob,"
			    +"stars.photo_url,"
			    +"movies.id,"
			    +"movies.title"
			+" FROM stars"
				+" INNER JOIN stars_in_movies" 
					+" ON stars.id = stars_in_movies.star_id"
				+" INNER JOIN movies" 
					+" ON movies.id =  stars_in_movies.movie_id"
			+" WHERE stars.id =? AND stars.first_name =? AND stars.last_name=?";
		
		PreparedStatement statement = connection.prepareStatement(query);
		
		statement.setString(1,starid);	
		statement.setString(2, first_name);	
		statement.setString(3, last_name);
	
		//Execute Query
		ResultSet result = statement.executeQuery();
			
		//Create Star Class 
		Star star = new Star();
		star.setId(starid);
		
		while(result.next())
		{			 					
			star.setfirst_name(result.getString(1));
			star.setlast_name(result.getString(2));
			star.setdob(result.getString(3));

			star.setPhoto_url(result.getString(4));
			
			//Add Movie No Need to Worry about duplicates
			//Since it is a set

			Movie movie = new Movie();
			movie.setMovieId(result.getString(5));
			movie.setTitle(result.getString(6));
			star.addMovie(movie);
			
		}
		
		result.close();	     
		connection.close();
		statement.close();
		
		return star;
	}
	
	
	public LinkedHashMap<Integer, Movie> searchMovie(String movieID, String title, Connection connection) throws SQLException
	{
		String query =
				"SELECT "
						+"movies.id as film_id,"
						+"movies.title,"
						+"movies.year,"
						+"movies.director,"
						+"movies.banner_url,"
						+"movies.trailer_url,"
						+"movies.price,"
					    +"stars.id as actor_id,"
						+"stars.first_name,"
						+"stars.last_name,"
						+"genres.id as type_id,"
						+"genres.name"
						+" FROM movies"
							+" INNER JOIN stars_in_movies"
								+" ON movies.id = stars_in_movies.movie_id"
							+" INNER JOIN stars"
								+" ON stars_in_movies.star_id = stars.id"
							+" INNER JOIN genres_in_movies" 
								+" ON movies.id = genres_in_movies.movie_id"
							+" INNER JOIN genres"
								+" ON genres.id = genres_in_movies.genre_id"
						+" WHERE movies.id =?";
	
		
		PreparedStatement statement;
		if(title.isEmpty())
		{
			statement = connection.prepareStatement(query);
			statement.setString(1, movieID);
		}
		
		else
		{
			query += " AND movies.title =?";
			
			statement = connection.prepareStatement(query);
			
			statement.setString(1, movieID);
			statement.setString(2, title);
			
		}
		
		//Execute Query
		ResultSet result = statement.executeQuery();
		
		//Linked HashMap Keeps Order inserted by ResultSet
		//Can return multiple movies but we only need one
		//Can Change later
		
		LinkedHashMap<Integer, Movie> results = new LinkedHashMap<Integer, Movie>();
		int movie_id = Integer.parseInt(movieID);
		
		while(result.next())
		{			 		

			if(!results.containsKey(movie_id))
			{	
				//Create Movie Class
				 Movie movie = new Movie();
				 movie.setMovieId(result.getString(1));
				 movie.setTitle(result.getString(2));
				 movie.setYear(result.getString(3));
				 movie.setDirector(result.getString(4));
				 movie.setBanner_url(result.getString(5));
				 movie.setTrailer_url(result.getString(6));
				 movie.setPrice(result.getFloat(7));
				 
				 //Create Star Class
				 Star star = new Star();
				 star.setId(result.getString(8));
				 star.setfirst_name(result.getString(9));
				 star.setlast_name(result.getString(10));
				 movie.addStar(star);
				 
				 //Create Genre Class
				 Genre genre = new Genre();
				 genre.setid(result.getString(11));
				 genre.setgenre(result.getString(12));				 
				 
				 movie.addGenre(genre);
				 movie.addStar(star);
				 				 
				 results.put(movie_id, movie);	
			 }
			 
			 else
			 {
				//Get movie from HashMap
				 Movie movie = results.get(movie_id);
					 
				//Add star (Duplicates are removed)
				Star star = new Star();
				star.setId(result.getString(8));
				star.setfirst_name(result.getString(9));
				star.setlast_name(result.getString(10));
				 
				//Add Genre (Duplicates are removed)
				Genre genre = new Genre();
				genre.setid(result.getString(11));
				genre.setgenre(result.getString(12));
					 
				movie.addGenre(genre);
				movie.addStar(star);
					 
				results.put(movie_id, movie);
				 
			 }
					 
		 }
		
		result.close();	     
		connection.close();
		statement.close();
		
		return results;
				
	}
	
	public LinkedHashMap<Integer, Movie> searchMovies(String params, ArrayList<String> values, Connection connection) throws SQLException
	{
		//Prepare Query
		String query =
				"SELECT "
						+"movies.id as film_id,"
						+"movies.title,"
						+"movies.year,"
						+"movies.director,"
						+"movies.banner_url,"
						+"movies.trailer_url,"
						+"movies.price,"
					    +"stars.id as actor_id,"
						+"stars.first_name,"
						+"stars.last_name,"
						+"genres.id as type_id,"
						+"genres.name"
						+" FROM movies"
							+" INNER JOIN stars_in_movies"
								+" ON movies.id = stars_in_movies.movie_id"
							+" INNER JOIN stars"
								+" ON stars_in_movies.star_id = stars.id"
							+" INNER JOIN genres_in_movies" 
								+" ON movies.id = genres_in_movies.movie_id"
							+" INNER JOIN genres"
								+" ON genres.id = genres_in_movies.genre_id"
							+" WHERE" + params;
		
		ResultSet result;
		
		if(values == null)
		{
			Statement statement = connection.createStatement();
			result = statement.executeQuery(query);
			
		}
		
		else
		{
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			int pos = 1;
			for(String value: values)
			{
				statement.setString(pos, value);
				pos++;
			}
			
			//Execute Query
			result = statement.executeQuery();
		
		}
		
		
	
		//Get Results
		//Linked HashMap Keeps Order inserted by ResultSet
		LinkedHashMap<Integer, Movie> results = new LinkedHashMap<Integer, Movie>();

		while(result.next())
		{			 
			
			String movieid = result.getString(1);
			int movie_id = Integer.parseInt(movieid);
			 
			//If movie already in list
			if(!results.containsKey(movie_id))
			{	
				//Create Movie Class
				 Movie movie = new Movie();
				 movie.setMovieId(movieid);
				 movie.setTitle(result.getString(2));
				 movie.setYear(result.getString(3));
				 movie.setDirector(result.getString(4));
				 movie.setBanner_url(result.getString(5));
				 movie.setTrailer_url(result.getString(6));
				 movie.setPrice((result.getFloat(7)));
				 
				 //Create Star Class
				 Star star = new Star();
				 star.setId(result.getString(8));
				 star.setfirst_name(result.getString(9));
				 star.setlast_name(result.getString(10));
				 movie.addStar(star);
				 
				 //Create Genre Class
				 Genre genre = new Genre();
				 genre.setid(result.getString(11));
				 genre.setgenre(result.getString(12));				 
				 
				 movie.addGenre(genre);
				 movie.addStar(star);
				 				 
				 results.put(movie_id, movie);	
			 }
			 
			 else
			 {
				//Get movie from HashMap
					Movie movie = results.get(movie_id);
					 
					//Add star (Duplicates are removed)
					Star star = new Star();
					star.setId(result.getString(8));
					star.setfirst_name(result.getString(9));
					star.setlast_name(result.getString(10));
					 
					//Add Genre (Duplicates are removed)
					Genre genre = new Genre();
					genre.setid(result.getString(11));
					genre.setgenre(result.getString(12));
					 
					movie.addGenre(genre);
					movie.addStar(star);
					 
					results.put(movie_id, movie);			 
			 }
					 
		 }
			 
		 result.close();	     
		 connection.close();
	
		 return results;
	}
	
	
	public ArrayList<Movie> sortResults(ArrayList<Movie> movies, String ORDER, String SORT)
	{
		if(ORDER.compareTo("title") == 0 && SORT.compareTo("asc") == 0)
		{
			movies =  movies.stream()
			        .sorted(Comparator.comparing(Movie::getTitle))
			        .collect(Collectors.toCollection(ArrayList::new));
			
		}
		
		else if(ORDER.compareTo("title") == 0 && SORT.compareTo("desc") == 0)
		{	
			movies =  movies.stream()
			        .sorted(Comparator.comparing(Movie::getTitle))
			        .collect(Collectors.toCollection(ArrayList::new));
			        
			Collections.reverse(movies);
		}
		
		else if(ORDER.compareTo("year") == 0 && SORT.compareTo("asc") == 0)
		{
			movies =  movies.stream()
			        .sorted(Comparator.comparing(Movie::getYear))
			        .collect(Collectors.toCollection(ArrayList::new));
			        
		}
		
		else if(ORDER.compareTo("year") == 0 && SORT.compareTo("desc") == 0)
		{
			movies =  movies.stream()
			        .sorted(Comparator.comparing(Movie::getYear))
			        .collect(Collectors.toCollection(ArrayList::new));
			
			Collections.reverse(movies);
			        
		}
		
		return movies;
	}
	
	public ArrayList<Movie> movieSubList(ArrayList<Movie> movies, int fromIndex, int toIndex )
	{
		//fromIndex - OFFSET
		//toIndex - LIMIT
		
		//System.out.println(fromIndex);
		//System.out.println(movies.size());
		//System.out.println(toIndex);
		
		int size = movies.size();

		//If limit was greater than the size of the list
		//While offset was less than array size
		if(toIndex > size && fromIndex < size)
		{
			return new ArrayList<Movie>(movies.subList(fromIndex, size));
		}
		
		//Return empty list if OFFSET greater than list (No More Results)
		else if(fromIndex > size)
		{
			return new ArrayList<Movie>();
		}
		
		//If the limit was greater than the size of the list
		//return all movies
		else if (toIndex > size)
		{
			return movies;
		}
		
		else if(fromIndex == 0)
		{
			return new ArrayList<Movie>(movies.subList(0, toIndex));
		}
		
		else
		{
			return new ArrayList<Movie>(movies.subList(fromIndex, toIndex));
		}
	}
	
	
	//Employee Methods
	public User searchEmployee(String email, String password, Connection connection) throws SQLException
	{
		// Prepare Query
		String query = 
				"SELECT * FROM employees WHERE email = ? AND md5(password) = md5(?)";
		
		PreparedStatement statement = connection.prepareStatement(query);
		 
		//Bind values into the parameters
		statement.setString(1, email);
		statement.setString(2, password);
		 
		//Execute Query
		ResultSet result = statement.executeQuery();
		
		//If User Exists then return User Object otherwise null
		User user = null;
		if(result.next())
		{
			user = new User();
			user.setEmail(result.getString(1));
			user.setpassword(result.getString(2));
			user.setlast_name(result.getString(3));
			
		}
		
		result.close();
		statement.close();
		connection.close();
		
		return user;
		
	}
	
	public Map<String,List<MetaData>> addMovie(String title, String year, String director, String banner_url, String trailer_url,
			String genre, String first_name, String last_name, String dob, String photo_url, Connection connection) throws SQLException
	{
		
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
				
	    java.sql.Date javaSqlDate = null;	
	    try
	    {
	    	javaSqlDate = java.sql.Date.valueOf(dob);
	    }

	    catch(Exception e)
	    {
	    	//No or Invalid Date 
	    	//Catch exception and pass it on
	    }
	    
		CallableStatement callStmt = connection.prepareCall("{call add_movie(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		callStmt.setString(1, title);
		callStmt.setInt(2, Integer.parseInt(year));
		callStmt.setString(3, director);
		callStmt.setString(4, genre);
		callStmt.setString(5, first_name);
		callStmt.setString(6, last_name);
		callStmt.setDate(7, javaSqlDate);
		callStmt.setString(8, photo_url);
		callStmt.registerOutParameter(9, Types.INTEGER); //  current_movie_id 
		callStmt.registerOutParameter(10, Types.INTEGER); // current_genre_id 
		callStmt.registerOutParameter(11, Types.INTEGER); // current_star_id 
		callStmt.registerOutParameter(12, Types.INTEGER); // movie_count
		callStmt.registerOutParameter(13, Types.INTEGER); // star_count
		callStmt.registerOutParameter(14, Types.INTEGER); // genre_count
		callStmt.registerOutParameter(15, Types.INTEGER); // genres_in_movie_count
		callStmt.registerOutParameter(16, Types.INTEGER); // stars_in_movies_count
			
		callStmt.execute();
		
		String current_movie_id = callStmt.getString(9);
		String current_genre_id = callStmt.getString(10);
		String current_star_id = callStmt.getString(11);
		
		int movie_count = callStmt.getInt(12);
		int star_count= callStmt.getInt(13);
		int genre_count = callStmt.getInt(14);
		int stars_in_movies_count = callStmt.getInt(15);
		int genres_in_movie_count = callStmt.getInt(16);
		
		
		///MOVIES
		MetaData field_names = new MetaData();
		
		field_names.setVar1("Status");
		field_names.setVar2("ID");
		field_names.setVar3("Message");
		
		MetaData movie_data = new MetaData();
		List<MetaData> data = new ArrayList<MetaData>();
		
		movie_data.setVar2(current_movie_id);
		
		if(movie_count > 0)
		{
			movie_data.setVar1("UPDATE");
			movie_data.setVar3("Movie Exists. Movie Table Not Changed.");
		}
		
		else
		{
			movie_data.setVar1("INSERT");
			movie_data.setVar3("Movie Table Updated. Movie Inserted.");		
		}
		
		data.add(field_names);
		data.add(movie_data);
		metadata.put("movies", data);
		
		///////STARS
		MetaData star_data = new MetaData();
		star_data.setVar2(current_star_id);
			
		if(star_count > 0)
		{
			star_data.setVar1("DO NOTHING");
			star_data.setVar3("Star Exists. Cannot add Star to Star Table");
		}
		
		else
		{
			star_data.setVar1("INSERT");
			star_data.setVar3("Star Table Updated. Star has been added to Table.");
			
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(star_data);
		metadata.put("stars", data);
		
		
		/////GENRE
		MetaData genre_data = new MetaData();
		genre_data.setVar2(current_genre_id);;
		
		if(genre_count > 0)
		{
			genre_data.setVar1("DO NOTHING");
			genre_data.setVar3("Genre Exists. Cannot add Genre to Movie Database");		
		}
		else
		{
			genre_data.setVar1("INSERT");
			genre_data.setVar3("Genre Table Has been Updated");
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(genre_data);
		metadata.put("genres", data);
		
		
		/////////////STAR MOVIE LINK
		MetaData star_movie = new MetaData();
		star_movie.setVar2("N/A");
		
		if(stars_in_movies_count > 0)
		{
			star_movie.setVar1("DO NOTHING");
			star_movie.setVar3("Link Between Movie and Star Exists.");		
		}
		
		else
		{
			star_movie.setVar1("INSERT");
			star_movie.setVar3("Link Created Between Movie and Star");
		}
		
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(star_movie);
		metadata.put("stars_in_movies", data);				
		
		MetaData genre_movie = new MetaData();
		genre_movie.setVar2("N/A");

		if(genres_in_movie_count > 0)
		{
			genre_movie.setVar1("DO NOTHING");
			genre_movie.setVar3("Linke Between Movie and Genre Exists.");		
		}
		
		else
		{
			genre_movie.setVar1("INSERT");
			genre_movie.setVar3("Link Created Between Movie and Genre");
		}
			
		data = new ArrayList<MetaData>();
		data.add(field_names);
		data.add(genre_movie);
		metadata.put("genres_in_movies", data);		
		
		callStmt.close();
		connection.close();
		
		return metadata;
	}
	
	public int starExists(String[] star,Connection connection) throws SQLException
	{
		String firstName,lastName,dob;
		
		firstName = star[0];
		lastName = star[1];
		dob = star[2];
		    
		ResultSet results;
		PreparedStatement preparedStatement;
		
		if(dob.trim().isEmpty())
		{
			 
			String queryCount = "SELECT COUNT(*) FROM stars WHERE first_name = ? AND last_name = ? AND dob IS NULL";
			preparedStatement = connection.prepareStatement(queryCount);
			
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
				
			results = preparedStatement.executeQuery();
			
		}
		
		else
		{
			String queryCount = "SELECT COUNT(*) FROM stars WHERE first_name = ? AND last_name = ? AND dob = ?";
			preparedStatement = connection.prepareStatement(queryCount);
			
			java.sql.Date date = java.sql.Date.valueOf(dob);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			
			results = preparedStatement.executeQuery();
		    
			
		}

		
	    int count = 0;
	    if(results.next())
	    {
	    	count= results.getInt(1); 	
	    }
		
	    preparedStatement.close();
	    results.close();
	    
		return count;
	}
	
	public Map<String,List<MetaData>> addStar(String[] star, Connection connection) throws SQLException
	{
		String firstName,lastName,dob,photoURL;
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
		
		firstName = star[0];
		lastName = star[1];
		dob = star[2];
		photoURL = star[3];
		    
		String insertTableSQL;
		PreparedStatement preparedStatement;
		
		java.sql.Date date = null;
		if(dob.trim().isEmpty())
		{
			insertTableSQL = "INSERT INTO stars"
					+ "(first_name, last_name,dob,photo_url) VALUES"
					+ "(?,?,?,?)";
			
			preparedStatement = 
					connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			preparedStatement.setString(4, photoURL);
		}
		
		else
		{
			insertTableSQL = "INSERT INTO stars"
					+ "(first_name, last_name, dob, photo_url) VALUES"
					+ "(?,?,?,?)";
			
			preparedStatement = 
					connection.prepareStatement(insertTableSQL,PreparedStatement.RETURN_GENERATED_KEYS);
			
			date = java.sql.Date.valueOf(dob);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setDate(3, date);
			preparedStatement.setString(4, photoURL);
			
		}
		
		// execute insert SQL statement
		int rows = preparedStatement.executeUpdate();
		ResultSet id = preparedStatement.getGeneratedKeys();
		
		if(rows > 0 && id.next())
		{
			String query = "Select * FROM stars WHERE id = '"+ id.getInt(1)+"'";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			
			List<MetaData> data = new ArrayList<MetaData>();
			MetaData meta = new MetaData();
			meta.setVar1("ID");
			meta.setVar2("First Name");
			meta.setVar3("Last Name");
			meta.setVar4("Date of Birth");
			meta.setVar5("Photo URL");
			
			data.add(meta);
			while(rs.next())
			{
				meta = new MetaData();
				
				meta.setVar1(rs.getString(1));
				
				String rs_fname = rs.getString(2);
				if(rs_fname == null || rs_fname.trim().isEmpty())
				{
					rs_fname = "N/A";
				}
				meta.setVar2(rs_fname);
				
				meta.setVar3(rs.getString(3));
				
				String rs_dob = rs.getString(4);
				if(rs_dob == null || rs_dob.trim().isEmpty())
				{
					rs_dob = "N/A";
				}
				
				String rs_photo = rs.getString(5);
				if(rs_photo == null || rs_photo.trim().isEmpty())
				{
					rs_photo = "N/A";
				}
				
				meta.setVar4(rs_dob);
				meta.setVar5(rs_photo);
				
				data.add(meta);
			}
			
			rs.close();
			id.close();
			statement.close();
			
			metadata.put("Stars (Rows Affected: " + rows + ")", data);
		}
	

		preparedStatement.close();
		connection.close();
		
		return metadata;
	}
	
	public Map<String,List<MetaData>> getMetaData(Connection connection) throws SQLException
	{
		DatabaseMetaData md = connection.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		Map<String,List<MetaData>> metadata = new HashMap<String,List<MetaData>>();
				
		while (rs.next()) 
		{
			List<MetaData> data = new ArrayList<MetaData>();
			
			String tableName = rs.getString(3);
			
			Statement select = connection.createStatement();
			ResultSet tableData = select.executeQuery("DESCRIBE " + tableName);
		
			MetaData table_names = new MetaData();
			table_names.setVar1("Field");
			table_names.setVar2("Type");
			table_names.setVar3("NULL");
			table_names.setVar4("Key");
			
			data.add(table_names);
			
			while(tableData.next())
			{
				String field = tableData.getString(1);
				String type = tableData.getString(2);
				String NULL = tableData.getString(3);
				String key = tableData.getString(4);
	    	  
				MetaData fields = new MetaData();
				fields.setVar1(field);
				fields.setVar2(type);
				fields.setVar3(NULL);
				fields.setVar4(key);
				
				data.add(fields);
			}
	      
			metadata.put(tableName, data);
			
			select.close();
			tableData.close();      
		}	
		
		rs.close();
		connection.close();		
		
		return metadata;
	}
}
			