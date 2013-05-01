package app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

import model.Duration;
import model.WeatherSignal;
import model.WeatherState;

import org.joda.time.DateTime;

import com.csvreader.CsvReader;

public class WeatherGenerator {

	private static Random randomGenerator = new Random();

	private static int getRandomNumberFrom(int min, int max) {
		int randomNumber = randomGenerator.nextInt((max + 1) - min) + min;
		return randomNumber;
	}

	public static WeatherState generateRandomWeatherState() {

		WeatherState state = new WeatherState();
		double pressure = getRandomNumberFrom(900, 1200);
		double windDirection = getRandomNumberFrom(0, 360);
		double windSpeed = getRandomNumberFrom(0, 100);
		double insolation = getRandomNumberFrom(0, 10);
		double humidity = getRandomNumberFrom(0, 100);
		double temperature = getRandomNumberFrom(-50, 50);

		int year = getRandomNumberFrom(1990, 2012);
		int monthOfYear = getRandomNumberFrom(1, 12);
		int dayOfMonth = getRandomNumberFrom(1, 29);
		int hourOfDay = getRandomNumberFrom(0, 23);
		int minuteOfHour = getRandomNumberFrom(0, 59);
		int secondOfMinute = getRandomNumberFrom(0, 59);

		DateTime generatedTimeStamp = new DateTime(year, monthOfYear,
				dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute);

		state.setTimeStamp(generatedTimeStamp);
		state.setTemperature(temperature);
		state.setPressure(pressure);
		state.setWindDirection(windDirection);
		state.setWindSpeed(windSpeed);
		state.setInsolation(insolation);
		state.setHumidity(humidity);

		return state;
	}

	public static WeatherSignal loadWeatherDataFromCSVFile(String path,
			char separator) {

		WeatherSignal weatherData = new WeatherSignal();
		try {
			CsvReader csvReader = new CsvReader(new FileReader(new File(path)),
					separator);

			csvReader.readHeaders();

			while (csvReader.readRecord()) {
				try {
					WeatherState state = new WeatherState();
					state.setTimeStamp(new DateTime(csvReader
							.get("unix_timestamp")));
					state.setTemperature(Double.parseDouble(csvReader
							.get("temperature")));
					state.setPressure(Double.parseDouble(csvReader
							.get("pressure")));
					state.setWindDirection(Double.parseDouble(csvReader
							.get("wind_direction")));
					state.setWindSpeed(Double.parseDouble(csvReader
							.get("wind_speed")));
					state.setInsolation(Double.parseDouble(csvReader
							.get("insolation")));
					state.setHumidity(Double.parseDouble(csvReader
							.get("humidity")));
					weatherData.getWeatherStates().add(state);
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		Collections.sort(weatherData.getWeatherStates());

		weatherData.setDuration(new Duration());
		weatherData.getDuration().setBeginTime(
				weatherData.getWeatherStates().get(0).getTimeStamp());
		weatherData.getDuration().setEndTime(
				weatherData.getWeatherStates()
						.get(weatherData.getWeatherStates().size() - 1)
						.getTimeStamp());

		return weatherData;
	}

	public static void saveToFileGeneratedData(WeatherSignal weatherSignal,
			String path, char separator) {
		Collections.sort(weatherSignal.getWeatherStates());
		try {
			FileWriter writer = new FileWriter(path);
			writer.append("unix_timestamp");
			writer.append(separator);
			writer.append("temperature");
			writer.append(separator);
			writer.append("pressure");
			writer.append(separator);
			writer.append("wind_direction");
			writer.append(separator);
			writer.append("wind_speed");
			writer.append(separator);
			writer.append("insolation");
			writer.append(separator);
			writer.append("humidity");
			writer.append('\n');

			for (WeatherState state : weatherSignal.getWeatherStates()) {
				writer.append(state.getTimeStamp().toString());
				writer.append(separator);
				writer.append(Double.toString(state.getTemperature()));
				writer.append(separator);
				writer.append(Double.toString(state.getPressure()));
				writer.append(separator);
				writer.append(Double.toString(state.getWindDirection()));
				writer.append(separator);
				writer.append(Double.toString(state.getWindSpeed()));
				writer.append(separator);
				writer.append(Double.toString(state.getInsolation()));
				writer.append(separator);
				writer.append(Double.toString(state.getHumidity()));
				writer.append('\n');
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
