package model;

import org.joda.time.DateTime;

public class WeatherState implements Comparable<WeatherState> {

	private DateTime timeStamp;

	private double pressure;

	private double windDirection;

	private double windSpeed;

	private double insolation;

	private double humidity;

	private double temperature;

	public DateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(DateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(double windDirection) {
		this.windDirection = windDirection;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getInsolation() {
		return insolation;
	}

	public void setInsolation(double insolation) {
		this.insolation = insolation;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		String s = "WeatherState [ date: " + timeStamp + " wind_direction: "
				+ windDirection + " wind_speed: " + windSpeed + " pressure: "
				+ pressure + " temperature: " + temperature + " insolation: "
				+ insolation + " humidity: " + humidity + " ]";
		return s;
	}

	public Double getParamByName(String paramName) {

		Double param = null;

		if (paramName.equals("temperature"))
			param = getTemperature();
		if (paramName.equals("insolation"))
			param = getInsolation();
		if (paramName.equals("pressure"))
			param = getPressure();
		if (paramName.equals("wind_direction"))
			param = getWindDirection();
		if (paramName.equals("wind_speed"))
			param = getWindSpeed();
		if (paramName.equals("humidity"))
			param = getHumidity();

		return param;
	}

	@Override
	public boolean equals(Object obj) {
		WeatherState ws = (WeatherState) obj;
		return this.timeStamp.equals(ws.getTimeStamp());
	}

	public int compareTo(WeatherState o) {

		int result = 0;

		if (this.getTimeStamp().isBefore(o.getTimeStamp()) == true)
			result = -1;
		else if (this.getTimeStamp().isBefore(o.getTimeStamp()) == false)
			result = 1;
		else if (this.getTimeStamp().equals(o.getTimeStamp()) == true)
			result = 0;

		return result;
	}

}
