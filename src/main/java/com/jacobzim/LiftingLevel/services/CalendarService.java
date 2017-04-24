package com.jacobzim.LiftingLevel.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jacobzim.LiftingLevel.interfaces.CalendarDateDao;
import com.jacobzim.LiftingLevel.models.CalendarDate;
import com.jacobzim.LiftingLevel.models.User;

public class CalendarService {
	
	Calendar calendar;
	
	@Autowired
	private CalendarDateDao dateDao;

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public void calendarInitialize() {
		calendar = Calendar.getInstance();
	}
	
	//sets up the month to have the proper number of days
	public int monthDateSetup(Calendar calendar) {
		if (calendar.get(Calendar.MONTH) == 1) {
			//checks for leap year
			if (calendar.get(Calendar.YEAR) % 100 == 0 && calendar.get(Calendar.YEAR) % 400 != 0) {
				return 28;
			} else if (calendar.get(Calendar.YEAR) % 4 == 0) {
				return 29;
			} else return 28;
		}
		switch(calendar.get(Calendar.MONTH)){
		case 3:
		case 5:
		case 8:
		case 10:
			return 30;
		default:
			return 31;
		}
	}
	
	public List<Integer> dateList(int numberOfDays) {
		List<Integer> holder = new ArrayList<Integer>();
		Calendar calendarInstance = calendar;
		calendarInstance.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonthHolder = calendarInstance.getTime();
		String firstDayOfMonth = firstDayOfMonthHolder.toString().substring(0, 3);
		System.out.println(firstDayOfMonth);
		int firstDayOfMonthInt = 0;
		switch(firstDayOfMonth){
		case "Sun":
			break;
		case "Mon":
			firstDayOfMonthInt = 1;
			break;
		case "Tue":
			firstDayOfMonthInt = 2;
			break;
		case "Wed":
			firstDayOfMonthInt = 3;
			break;
		case "Thr":
			firstDayOfMonthInt = 4;
			break;
		case "Fri":
			firstDayOfMonthInt = 5;
			break;
		case "Sat":
			firstDayOfMonthInt = 6;
			break;
		}
		System.out.println(firstDayOfMonthInt);
		for (int i = 0; i < numberOfDays+firstDayOfMonthInt; i++) {
			if (firstDayOfMonthInt > 0) {
				holder.add(0);
				firstDayOfMonthInt = firstDayOfMonthInt-1;
				i--;
			}
			else holder.add(i+1);
		}
		return holder;
	}
	
	public void forwardMonth(Calendar toChange) {
		int month = toChange.get(Calendar.MONTH);
		if (month < 11) {
			month = month+1;
			calendar.set(Calendar.MONTH, month);
		} else {
			int year = toChange.get(Calendar.YEAR);
			year = year+1;
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.YEAR, year);
		}
	}
	
	public void backMonth(Calendar toChange) {
		int month = toChange.get(Calendar.MONTH);
		if (month == 0) {
			int year = toChange.get(Calendar.YEAR);
			year = year-1;
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.YEAR, year);
		} else {
			month = month-1;
			calendar.set(Calendar.MONTH, month);
		}
	}
	
	public String dateWriter(Calendar toWrite) {
		int displayMonth = toWrite.get(Calendar.MONTH) + 1;
    	int displayDay = toWrite.get(Calendar.DAY_OF_MONTH);
    	int displayYear = toWrite.get(Calendar.YEAR);
    	String fullDisplayMonth = "";
    	String fullDisplayDay = "";
    	if (displayDay < 10) {
    		fullDisplayDay = "0"+displayDay;
    	} else fullDisplayDay = ""+displayDay;
    	if (displayMonth < 10) {
    		fullDisplayMonth = "0"+displayMonth;
    	} else fullDisplayMonth = ""+displayMonth;
    	return ""+fullDisplayMonth+fullDisplayDay+displayYear;
	}
	
	//used to get the CalendarDate object from the string representation of the date
	public CalendarDate getCalendarDate() {
		String dateDisplay = this.dateWriter(calendar);
    	CalendarDate date = dateDao.findByDate(dateDisplay);
    	return date;
	}
	//returns a CalendarDate object for the current selected date if none has been provided (used only if date hasn't been selected)
	public CalendarDate useCurrentCalendarDate(User user) {
		String dateDisplay = this.dateWriter(calendar);
		CalendarDate currentDate = new CalendarDate(dateDisplay, "Workout", "", user);
		return currentDate;
	}
	
	//fix for the overlap on calendar.. logic needs fixing
	public List<Boolean> trueIfFreeSpace(List<CalendarDate> calendarDateList, List<Integer> dateIndex, int month, int year) {
		List<Boolean> holder = new ArrayList<Boolean>();
		for (int i = 0; i < calendarDateList.size(); i++) {
			for (int j = 0; j < dateIndex.size(); j++) {
			if(calendarDateList.get(i).structureDayForComparison() == dateIndex.get(j)
					&& calendarDateList.get(i).structureMonthForComparison() == month 
					&& calendarDateList.get(i).structureYearForComparison() == year) {
				holder.add(false);
				}
			}
		holder.add(true);
		}return holder;
	}
}