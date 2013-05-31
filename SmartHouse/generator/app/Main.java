package app;

import java.util.Collections;

import model.WeatherSignal;
import model.WeatherState;

public class Main {
	public static void main(String[] args) {
		WeatherState randomGeneratedWeatherState;
		WeatherSignal randomGeneratedWeatherSignal = new WeatherSignal();
		for (int i = 0; i < 12*30*10; i++) { /*12*30*10 - to maksymalna ilosc pomiarow ktora mozemy ustawic dla dnia, zeby wykres byl czytelny*/
			randomGeneratedWeatherState = WeatherGenerator
					.generateRandomWeatherState();
			randomGeneratedWeatherSignal.getWeatherStates().add(
					randomGeneratedWeatherState);
		}
		Collections.sort(randomGeneratedWeatherSignal.getWeatherStates());
		WeatherGenerator.saveToFileWeatherSignal(randomGeneratedWeatherSignal,
				"random.csv", ',');

		@SuppressWarnings("unused")
		WeatherSignal ws = WeatherGenerator.loadWeatherDataFromCSVFile(
				"random.csv", ',');

		System.out.println("END OF MAIN");
	}
}
