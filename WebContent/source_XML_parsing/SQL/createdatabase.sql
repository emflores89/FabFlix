DROP database moviedb;

CREATE database moviedb;

USE moviedb;

CREATE TABLE movies (
      id int NOT NULL AUTO_INCREMENT,
      title VARCHAR(100) NOT NULL,
      year INT NOT NULL,
      director VARCHAR(100) NOT NULL,
      banner_url VARCHAR(200) DEFAULT '',
      trailer_url VARCHAR(200) DEFAULT '',
      primary key (id)
);


CREATE TABLE stars (
	id int NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE NULL,
    photo_url VARCHAR(200) DEFAULT '',
    primary key (id)
);

CREATE TABLE stars_in_movies(
	star_id INT NOT NULL,
    FOREIGN KEY (star_id) references stars(id),
    movie_id INT NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id)
   
);

CREATE TABLE genres(
	id int NOT NULL AUTO_INCREMENT,
    name varchar(32) NOT NULL,
    primary key (id)
);

CREATE TABLE genres_in_movies(
	genre_id INT NOT NULL, 
    FOREIGN  KEY (genre_id) references genres(id),
    movie_id INT NOT NULL,
    FOREIGN KEY (movie_id) references movies(id)
);

CREATE TABLE creditcards(
	id VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    expiration DATE NOT NULL,
    primary key (id)
);

CREATE TABLE customers(
	id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    cc_id VARCHAR(20) NOT NULL,
    FOREIGN KEY (cc_id) references creditcards(id),
    address VARCHAR(200) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    primary key (id)
);

CREATE TABLE sales(
	id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) references customers(id) ON DELETE CASCADE ON UPDATE CASCADE,
    movie_id INT NOT NULL,
    FOREIGN KEY (movie_id) references movies(id),
    sale_date DATE NOT NULL,
    primary key (id)
);

CREATE TABLE employees (
	email varchar(50) primary key,
    password varchar(20) not null,
	fullname varchar(100)
);

INSERT INTO employees VALUES ('classta@course.edu', 'classta','TA CS122B');

DELIMITER $$

CREATE 
PROCEDURE add_movie (
	IN movie_title VARCHAR(100),
	IN movie_year INTEGER,
	IN movie_director VARCHAR(100),
	IN genre_name VARCHAR(32),
	IN star_first_name VARCHAR(50),
	IN star_last_name VARCHAR(50),
	IN star_dob DATE,
	IN star_photo_url VARCHAR(200),
    OUT current_movie_id INTEGER,
    OUT current_genre_id INTEGER,
    OUT current_star_id INTEGER,
    OUT movie_count INTEGER,
    OUT star_count INTEGER,
    OUT genre_count INTEGER,
    OUT stars_in_movies_count INTEGER,
	OUT genres_in_movie_count INTEGER

)
BEGIN
     
	SET movie_count = (SELECT COUNT(*) FROM movies WHERE title = movie_title AND year = movie_year AND director = movie_director);
   
    SET genre_count = (SELECT COUNT(*) FROM genres WHERE name = genre_name);
   
	IF star_dob IS NULL THEN
		SET star_count = (SELECT COUNT(*) FROM stars WHERE first_name = star_first_name AND last_name = star_last_name AND dob IS NULL);
	ELSE
		SET star_count = (SELECT COUNT(*) FROM stars WHERE first_name = star_first_name AND last_name = star_last_name AND dob = star_dob);
	END IF;
    
    IF movie_count = 0 THEN
		INSERT INTO movies (title, year, director) 
			VALUES (movie_title, movie_year, movie_director);
    END IF;
    
	IF genre_count = 0 THEN
		INSERT INTO genres (name) VALUES (genre_name);
    END IF;
    
    IF star_count  = 0 THEN
		IF star_dob IS NULL THEN
			INSERT INTO stars (first_name, last_name, photo_url) 
				VALUES (star_first_name, star_last_name, star_photo_url);
        ELSE
			INSERT INTO stars (first_name, last_name, dob, photo_url) 
				VALUES (star_first_name, star_last_name, star_dob, star_photo_url);
		END IF;
    END IF;
    
	SET current_movie_id = (SELECT id FROM movies WHERE title = movie_title AND year = movie_year AND director = movie_director);
    SET current_genre_id = (SELECT id FROM genres WHERE name = genre_name);
    
	IF star_dob IS NULL THEN
		SET current_star_id =  (SELECT id FROM stars WHERE first_name = star_first_name AND last_name = star_last_name AND dob IS NULL LIMIT 1);
	ELSE
		SET current_star_id =  (SELECT id FROM stars WHERE first_name = star_first_name AND last_name = star_last_name AND dob = star_dob);
	END IF;
    
    SET genres_in_movie_count = (SELECT COUNT(*) FROM genres_in_movies WHERE genre_id = current_genre_id AND movie_id = current_movie_id);
    
    IF genres_in_movie_count = 0 THEN
		INSERT INTO genres_in_movies (genre_id, movie_id) VALUES (current_genre_id, current_movie_id);
    END IF;
    
    SET stars_in_movies_count = (SELECT COUNT(*) FROM stars_in_movies WHERE star_id = current_star_id AND movie_id = current_movie_id);
	
    IF stars_in_movies_count = 0 THEN 
		INSERT INTO stars_in_movies (star_id, movie_id) VALUES (current_star_id, current_movie_id);
	END IF;

END$$

DELIMITER ;



