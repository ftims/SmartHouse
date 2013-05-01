package model;

import java.util.ArrayList;
import java.util.List;

public class WeatherSignal {

	private List<WeatherState> weatherStates;
	private Duration duration;

	public WeatherSignal() {
		this.weatherStates = new ArrayList<WeatherState>();
		this.duration = new Duration();
	}

	public WeatherSignal(List<WeatherState> weatherStates, Duration duration) {
		super();
		this.weatherStates = weatherStates;
		this.duration = duration;
	}

	public List<WeatherState> getWeatherStates() {
		return weatherStates;
	}

	public void setWeatherStates(List<WeatherState> weatherStates) {
		this.weatherStates = weatherStates;
	}

	public String getDescription() {
		return "Weather Signal (" + duration.toString() + ")";
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
}
