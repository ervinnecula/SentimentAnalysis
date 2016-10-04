package main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import utilities.BuildMovies;

public class MovieTests {

	@Test
	public void generateMovieTestOpen() {
		List<Movie> movies = new ArrayList<Movie>();

		boolean fileExists = false;
		
		try {
			String[] reactions = { 
					"reactions/blink.mp4",
					"reactions/sad.mp4",
					"reactions/thumbs-up.mp4",
					"reactions/small-smile.mp4",
					"reactions/smile.mp4"
					};
			
			
			movies.add(MovieCreator.build(reactions[1]));
			movies.add(MovieCreator.build(reactions[0]));
			BuildMovies.generateMovie("test.mp4", movies);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		File f = new File("test.mp4");
		if(f.exists() && !f.isDirectory()){
			fileExists = true;
		}
		
		assertEquals(true, fileExists);
	}

}
