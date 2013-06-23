package org.controllers;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WeatherSignal;
import model.WeatherState;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.WeatherGenerator;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public String index() {
		System.out.println("index");
		return "home-tiles";
	}

	@RequestMapping(value = { "/home", "/" }, method = RequestMethod.GET)
	public ModelAndView getHomePage() {
		System.out.println("home");
		return new ModelAndView("home-tiles");
	}

	@RequestMapping(value = "/static", method = RequestMethod.GET)
	public ModelAndView getStaticPage(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("static");

		return new ModelAndView("static-tiles");
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public ModelAndView getInfoPage(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("static");

		return new ModelAndView("info-tiles");
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ModelAndView getDataPage(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("data");
		String component = request.getParameter("component");
		String type = request.getParameter("type");
		ModelAndView mav = new ModelAndView("data-tiles");
		mav.addObject("component", component);
		mav.addObject("type", type);
		return mav;
	}

	@RequestMapping(value = "/getWeatherData")
	public void getWeatherData(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {
		String component = request.getParameter("component");
		String type = request.getParameter("type");

		// aby wygenerowac dane losowe wykonujemy main z klasy Main a nastepnie
		// zawartosc wygenerowanego pliku kopiujemy do pliku random_weather_data
		// w folderze weather_data
		WeatherSignal allWeatherSignal = WeatherGenerator
				.loadWeatherDataFromCSVFile(request.getSession()
						.getServletContext().getRealPath("/")
						+ "/weather_data/real_weather_data_2010.csv", ','); /*
																			 * mozna
																			 * uzyc
																			 * pliku
																			 * random_weather_data_2010
																			 * .
																			 * csv
																			 * ale
																			 * wykresy
																			 * wtedy
																			 * sa
																			 * do
																			 * siebie
																			 * podobne
																			 * i
																			 * nie
																			 * ma
																			 * w
																			 * nich
																			 * nic
																			 * ciekawego
																			 */

		DateTime dataPoczatkowa = new DateTime(2010, 1, 1, 0, 0, 0);
		DateTime dataKoncowa = new DateTime(2010, 1, 8, 0, 0, 0);

		WeatherSignal weekWeatherSignal = WeatherGenerator
				.getWeatherStatesByTime(allWeatherSignal, dataPoczatkowa,
						dataKoncowa);

		String label = "";
		label = getNameOfComponent(component);

		JSONArray colsJSONArray = new JSONArray();
		JSONArray rowsJSONArray = new JSONArray();
		JSONObject xLblJSON = new JSONObject();
		JSONObject yLblJSON = new JSONObject();

		if ("week".equals(type)) {

			xLblJSON.put("label", "Day of week");
			xLblJSON.put("type", "string");
			colsJSONArray.put(xLblJSON);

			yLblJSON.put("label", label);
			yLblJSON.put("type", "number");
			colsJSONArray.put(yLblJSON);
			for (int i = 0; i < weekWeatherSignal.getWeatherStates().size(); i++) {
				WeatherState wst = weekWeatherSignal.getWeatherStates().get(i);
				JSONObject cRowObjJSON = new JSONObject();
				JSONArray vRowArrJSON = new JSONArray();
				JSONObject valueXRowObjJSON = new JSONObject();
				JSONObject valueYRowObjJSON = new JSONObject();
				valueXRowObjJSON.put("v", wst.getTimeStamp().getHourOfDay()
						* wst.getTimeStamp().getDayOfWeek());
				valueXRowObjJSON.put("f", getNameOfWeek(wst.getTimeStamp()
						.getDayOfWeek())
						+ " "
						+ wst.getTimeStamp().getHourOfDay() + ":00");
				valueYRowObjJSON = getValueParameter(component, wst);

				vRowArrJSON.put(valueXRowObjJSON);
				vRowArrJSON.put(valueYRowObjJSON);
				cRowObjJSON.put("c", vRowArrJSON);
				rowsJSONArray.put(cRowObjJSON);

			}
		}
		if ("day".equals(type)) {

			xLblJSON.put("label", "Hour of day");
			xLblJSON.put("type", "string");
			colsJSONArray.put(xLblJSON);

			yLblJSON.put("label", label);
			yLblJSON.put("type", "number");
			colsJSONArray.put(yLblJSON);

			dataPoczatkowa = new DateTime(2010, 1, 1, 0, 0, 0);
			dataKoncowa = new DateTime(2010, 1, 2, 0, 0, 0);

			WeatherSignal dayWeatherSignal = WeatherGenerator
					.getWeatherStatesByTime(allWeatherSignal, dataPoczatkowa,
							dataKoncowa);

			for (int i = 0; i < dayWeatherSignal.getWeatherStates().size(); i++) {
				WeatherState wst = dayWeatherSignal.getWeatherStates().get(i);

				JSONObject cRowObjJSON = new JSONObject();
				JSONArray vRowArrJSON = new JSONArray();
				JSONObject valueXRowObjJSON = new JSONObject();
				JSONObject valueYRowObjJSON = new JSONObject();

				valueXRowObjJSON.put("v", wst.getTimeStamp().getMinuteOfDay());
				valueXRowObjJSON.put("f", wst.getTimeStamp().getMinuteOfDay()
						/ 60 + " h");
				valueYRowObjJSON = getValueParameter(component, wst);

				vRowArrJSON.put(valueXRowObjJSON);
				vRowArrJSON.put(valueYRowObjJSON);
				cRowObjJSON.put("c", vRowArrJSON);
				rowsJSONArray.put(cRowObjJSON);

			}
		}

		JSONObject responseDataJSON = new JSONObject();
		responseDataJSON.put("cols", colsJSONArray);
		responseDataJSON.put("rows", rowsJSONArray);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try {
			response.getOutputStream().print(responseDataJSON.toString());
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private String getNameOfComponent(String name) {
		String label = "";
		if (name.equals("temperature"))
			label = "Temperature";
		else if (name.equals("insolation"))
			label = "Insolation";
		else if (name.equals("humidity"))
			label = "Humidity";
		else if (name.equals("wind_speed"))
			label = "Wind speed";
		else if (name.equals("wind_direction"))
			label = "Wind direction";
		else if (name.equals("pressure"))
			label = "Pressure";
		else if (name.equals(""))
			label = "";

		return label;
	}

	private String getNameOfWeek(int nrDay) {
		String nameOfDay = "";
		switch (nrDay) {
		case 1:
			nameOfDay = "Monday";
			break;
		case 2:
			nameOfDay = "Tuesday";
			break;
		case 3:
			nameOfDay = "Wednesday";
			break;
		case 4:
			nameOfDay = "Thursday";
			break;
		case 5:
			nameOfDay = "Friday";
			break;
		case 6:
			nameOfDay = "Saturday";
			break;
		case 7:
			nameOfDay = "Sunday";
			break;
		default:
			nameOfDay = "";
			break;
		}
		return nameOfDay;
	}

	private JSONObject getValueParameter(String nameParameter, WeatherState wst)
			throws JSONException {
		JSONObject jsonObj = new JSONObject();

		if (nameParameter.equals("temperature"))
			jsonObj.put("v", wst.getTemperature());
		else if (nameParameter.equals("insolation"))
			jsonObj.put("v", wst.getInsolation());
		else if (nameParameter.equals("humidity"))
			jsonObj.put("v", wst.getHumidity());
		else if (nameParameter.equals("wind_speed"))
			jsonObj.put("v", wst.getWindSpeed());
		else if (nameParameter.equals("wind_direction"))
			jsonObj.put("v", wst.getWindDirection());
		else if (nameParameter.equals("pressure"))
			jsonObj.put("v", wst.getPressure());
		else if (nameParameter.equals(""))
			jsonObj.put("v", "");

		return jsonObj;
	}
}
