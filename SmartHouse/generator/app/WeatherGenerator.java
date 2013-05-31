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
import org.joda.time.DateTimeZone;

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

		int year = 2010;
		int monthOfYear = getRandomNumberFrom(1, 12);
		int dayOfMonth = getRandomNumberFrom(1, 27);
		int hourOfDay = getRandomNumberFrom(0, 23);
		int minuteOfHour = getRandomNumberFrom(0, 59);
		int secondOfMinute = getRandomNumberFrom(0, 59);

		DateTime generatedTimeStamp = new DateTime(year, monthOfYear,
				dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute,
				DateTimeZone.forID(System.getProperty("user.timezone")));

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
			Collections.sort(weatherData.getWeatherStates());
			weatherData.setDuration(new Duration());
			weatherData.getDuration().setBeginTime(
					weatherData.getWeatherStates().get(0).getTimeStamp());
			weatherData.getDuration().setEndTime(
					weatherData.getWeatherStates()
							.get(weatherData.getWeatherStates().size() - 1)
							.getTimeStamp());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return weatherData;
	}

	public static void saveToFileWeatherSignal(WeatherSignal weatherSignal,
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

	public static WeatherSignal getWeatherStatesByTime(
			WeatherSignal weatherData, DateTime dateFrom, DateTime dateTo) {

		WeatherSignal result = new WeatherSignal();

		for (WeatherState ws : weatherData.getWeatherStates()) {

			if ((ws.getTimeStamp().isAfter(dateFrom) && ws.getTimeStamp()
					.isBefore(dateTo))
					|| ws.getTimeStamp().isEqual(dateTo)
					|| ws.getTimeStamp().isEqual(dateFrom)) {

				result.getWeatherStates().add(ws);
			}
		}

		result.setDuration(new Duration());
		result.getDuration().setBeginTime(dateFrom);
		result.getDuration().setEndTime(dateTo);

		Collections.sort(result.getWeatherStates());
		return result;
	}

	public static WeatherState meanByDay(WeatherSignal weatherSignal,
			DateTime dateTime) {
		WeatherState result = new WeatherState();

		int counter = 0;

		double pressure = 0;

		double windDirection = 0;

		double windSpeed = 0;

		double insolation = 0;

		double humidity = 0;

		double temperature = 0;

		for (int i = 0; i < weatherSignal.getWeatherStates().size(); ++i) {
			if (dateTime.getYear() == weatherSignal.getWeatherStates().get(i)
					.getTimeStamp().getYear()
					&& dateTime.getMonthOfYear() == weatherSignal
							.getWeatherStates().get(i).getTimeStamp()
							.getMonthOfYear()
					&& dateTime.getDayOfMonth() == weatherSignal
							.getWeatherStates().get(i).getTimeStamp()
							.getDayOfMonth()) {
				++counter;
				pressure = pressure
						+ weatherSignal.getWeatherStates().get(i).getPressure();
				windDirection = windDirection
						+ weatherSignal.getWeatherStates().get(i)
								.getWindDirection();
				windSpeed = windSpeed
						+ weatherSignal.getWeatherStates().get(i)
								.getWindSpeed();
				insolation = insolation
						+ weatherSignal.getWeatherStates().get(i)
								.getInsolation();
				humidity = humidity
						+ weatherSignal.getWeatherStates().get(i).getHumidity();
				temperature = temperature
						+ weatherSignal.getWeatherStates().get(i)
								.getTemperature();
			}
		}

		result.setTimeStamp(dateTime);
		result.setPressure(pressure / counter);
		result.setWindDirection(windDirection / counter);
		result.setWindSpeed(windSpeed / counter);
		result.setInsolation(insolation / counter);
		result.setHumidity(humidity / counter);
		result.setTemperature(temperature / counter);

		return result;
	}

	public static WeatherState meanByHour(WeatherSignal weatherSignal,
			DateTime dateTime) {
		WeatherState result = new WeatherState();

		int counter = 0;

		double pressure = 0;

		double windDirection = 0;

		double windSpeed = 0;

		double insolation = 0;

		double humidity = 0;

		double temperature = 0;

		for (int i = 0; i < weatherSignal.getWeatherStates().size(); ++i) {
			if (dateTime.getYear() == weatherSignal.getWeatherStates().get(i)
					.getTimeStamp().getYear()
					&& dateTime.getMonthOfYear() == weatherSignal
							.getWeatherStates().get(i).getTimeStamp()
							.getMonthOfYear()
					&& dateTime.getDayOfMonth() == weatherSignal
							.getWeatherStates().get(i).getTimeStamp()
							.getDayOfMonth()
					&& dateTime.getHourOfDay() == weatherSignal
							.getWeatherStates().get(i).getTimeStamp()
							.getHourOfDay()) {
				++counter;
				pressure = pressure
						+ weatherSignal.getWeatherStates().get(i).getPressure();
				windDirection = windDirection
						+ weatherSignal.getWeatherStates().get(i)
								.getWindDirection();
				windSpeed = windSpeed
						+ weatherSignal.getWeatherStates().get(i)
								.getWindSpeed();
				insolation = insolation
						+ weatherSignal.getWeatherStates().get(i)
								.getInsolation();
				humidity = humidity
						+ weatherSignal.getWeatherStates().get(i).getHumidity();
				temperature = temperature
						+ weatherSignal.getWeatherStates().get(i)
								.getTemperature();
			}
		}

		result.setTimeStamp(dateTime);
		result.setPressure(pressure / counter);
		result.setWindDirection(windDirection / counter);
		result.setWindSpeed(windSpeed / counter);
		result.setInsolation(insolation / counter);
		result.setHumidity(humidity / counter);
		result.setTemperature(temperature / counter);

		return result;
	}

	public static WeatherSignal getWeatherStatesByPeriod(
			WeatherSignal weatherData, long period) {

		Collections.sort(weatherData.getWeatherStates());

		WeatherSignal result = new WeatherSignal();

		result.getWeatherStates().add(weatherData.getWeatherStates().get(0));
		DateTime start = weatherData.getWeatherStates().get(0).getTimeStamp();

		for (int i = 1; i < weatherData.getWeatherStates().size(); ++i) {
			WeatherState weather = weatherData.getWeatherStates().get(i);
			long numberOfSecond = (new org.joda.time.Duration(start,
					weather.getTimeStamp())).getStandardSeconds();

			if (numberOfSecond >= period) {
				result.getWeatherStates().add(weather);
				start = weather.getTimeStamp();
			}

		}

		result.setDuration(new Duration());
		result.getDuration().setBeginTime(
				weatherData.getDuration().getBeginTime());
		result.getDuration().setEndTime(weatherData.getDuration().getEndTime());

		System.out.println(result.getWeatherStates().get(0));
		System.out.println(result.getWeatherStates().get(1));

		return result;
	}

}
