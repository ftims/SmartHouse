package model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Duration implements Comparable<Duration> {

	private DateTime beginTime = new DateTime();
	private DateTime endTime = new DateTime();

	private String description = "";

	public long second() {
		org.joda.time.Duration duration = new org.joda.time.Duration(beginTime,
				endTime);
		return duration.getStandardSeconds();
	}

	public DateTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(DateTime beginTime) {
		this.beginTime = beginTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public boolean before(Duration duration) {
		return this.getEndTime().isAfter(duration.getBeginTime());
	}

	public boolean after(Duration duration) {
		return this.getBeginTime().isAfter(duration.getEndTime());
	}

	public boolean includeAll(Duration duration) {
		if (this.getBeginTime().isAfter(duration.getBeginTime())
				&& this.getEndTime().isBefore(duration.getEndTime()))
			return true;
		else
			return false;
	}

	public boolean includePartly(Duration duration) {
		if (this.getBeginTime().isAfter(duration.getEndTime())
				|| this.getEndTime().isBefore(duration.getBeginTime()))
			return false;
		else
			return true;
	}

	public int compareTo(Duration duration) {
		if (this.getBeginTime().isAfter(((Duration) duration).getBeginTime()))
			return 1;
		else
			return 0;
	}

	public List<Duration> partlyIncludedList(List<Duration> durationList) {
		List<Duration> resultList = new ArrayList<Duration>();

		for (int i = 0; i < durationList.size(); i++) {
			if (durationList.get(i).includePartly(this))
				resultList.add(durationList.get(i));

		}

		return resultList;
	}

	@Override
	public String toString() {

		return description + " (" + beginTime.toString() + " - "
				+ endTime.toString() + ")";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
