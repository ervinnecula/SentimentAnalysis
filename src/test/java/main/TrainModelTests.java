package main;

import static org.junit.Assert.assertEquals;

import java.io.File;
import org.junit.Test;
import training.TrainModel;

public class TrainModelTests {

	@Test
	public void trainModelTestExist() {
		TrainModel.trainModel("finaltraining.csv");
		boolean file = false;
		File f = new File("dictionary.xml");
		if (f.exists() && !f.isDirectory()) {
			file = true;
		}
		assertEquals(true, file);
	}

}
