package main;

import static org.junit.Assert.*;

import org.junit.Test;

import training.TrainModel;

public class TrainModelTests {

	@Test
	public void trainModelTest() {
		TrainModel.trainModel("finaltraining.csv");
	}

}
