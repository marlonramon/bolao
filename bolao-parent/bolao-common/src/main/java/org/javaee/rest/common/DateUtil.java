package org.javaee.rest.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

	private static Calendar calendar = new GregorianCalendar();

	private DateUtil() {
	}

	public static int compareDDMMYYYY(Date date1, Date date2) {

		calendar.setTime(date1);

		Integer year1 = calendar.get(Calendar.YEAR);
		Integer month1 = calendar.get(Calendar.MONTH);
		Integer day1 = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(date2);
		
		Integer year2 = calendar.get(Calendar.YEAR);
		Integer month2 = calendar.get(Calendar.MONTH);
		Integer day2 = calendar.get(Calendar.DAY_OF_MONTH);

		if (year1.compareTo(year2) != 0) {
			return year1.compareTo(year2);
		}

		if (month1.compareTo(month2) != 0) {
			return month1.compareTo(month2);
		}

		return day1.compareTo(day2);

	}

	public static int compareHHMM(Date date1, Date date2) {

		calendar.setTime(date1);

		Integer hour1 = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute1 = calendar.get(Calendar.MINUTE);

		calendar.setTime(date2);
		
		Integer hour2 = calendar.get(Calendar.HOUR_OF_DAY);
		Integer minute2 = calendar.get(Calendar.MINUTE);

		if (hour1.compareTo(hour2) != 0) {
			return hour1.compareTo(hour2);
		}

		return minute1.compareTo(minute2);
	}

	public static Date addDayToDate(Date date, int day) {
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date addMonthToDate(Date date, int month) {
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date addYearToDate(Date date, int year) {
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date setDay(Date date, int day) {
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date setYear(Date date, int year) {
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date setHourToData(Date date, int hour, int minute, int second) {
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, second);
		return calendar.getTime();
	}
	
	public static Date copyHour(Date date, Date time) {
		calendar.setTime(time);
		
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		calendar.setTime(date);
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		
		return calendar.getTime();
	}

	public static Date setCurrentTimeToDate(Date date) {
		calendar.setTime(date);
		Calendar currentTime = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, currentTime.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, currentTime.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, currentTime.get(Calendar.SECOND));
		return calendar.getTime();
	}

	public static Date getDateToDayOfMonth(int day) {
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date getDateToDayOfMonthAndMonth(int day, int month) {
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getTime();
	}

	public static Date addMaximumHour(Date date) {
		if (date == null)
			return null;
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	public static Date addMinimunHour(Date date) {
		if (date == null)
			return null;
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/*
	 * public static static String getDayOfWeek(Date date){ calendar.setTime(date);
	 * return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG,
	 * Locale.getDefault()); }
	 */

	public static Date getFirstDayOffMonth(Date date) {
		calendar.setTime(addMinimunHour(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	public static Date getFirstDayOffMonth() {
		return getFirstDayOffMonth(new Date());
	}

	public static Date getLastDayOffMonth() {
		return getLastDayOffMonth(new Date());
	}

	public static Date getLastDayOffMonth(Date date) {
		calendar.setTime(addMaximumHour(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/**
	 * Metodo que compara 2 datas sendo uma delas um DateTime, ou seja, ignora a
	 * hora das datas
	 * 
	 * @param data1
	 * @param data2
	 * @return * 0 se as datas forem iguais <br>
	 *         * Um valor menor que 0 se a data1 for menor que a data2 <br>
	 *         * um valor maior que 0 se a data1 for maior que a data2.
	 */
	public static int comparaDatas(Date data1, Date data2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(data1);
		resetTime(c1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(data2);
		resetTime(c2);

		return c1.getTime().compareTo(c2.getTime());
	}

	public static Long dayDifference(Date data1, Date data2) {
		calendar.setTime(data1);
		resetTime(calendar);

		long millis1 = calendar.getTimeInMillis();
		
		calendar.setTime(data2);
		resetTime(calendar);

		long millis2 = calendar.getTimeInMillis();
		
		long diferenca = millis1 - millis2;

		return diferenca / (1000 * 60 * 60 * 24);
	}

	public static void resetTime(Calendar c) {
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}

	public static Date addMinutes(Date date, int qtde) {
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + qtde);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int qtde) {
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + qtde);
		return calendar.getTime();
	}

	public static Date addUtilDay(Date data) {
		calendar.setTime(data);
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		if (day == Calendar.SATURDAY) {
			data = addDay(data, 2);
		} else {
			if (day == Calendar.SUNDAY) {
				data = addDay(data, 1);
			}
		}
		return data;

	}

	public static Date addDay(Date date, int days) {
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	public static boolean isInConflict(Date dataInicio1, Date dataFim1, Date dataInicio2, Date dataFim2) {
		if (inIn(dataInicio2, dataInicio1, dataFim1, true)) {
			return true;
		}
		if (inIn(dataFim2, dataInicio1, dataFim1, true)) {
			return true;
		}
		if (inIn(dataInicio1, dataInicio2, dataFim2, true)) {
			return true;
		}
		if (inIn(dataFim1, dataInicio2, dataFim2, true)) {
			return true;
		}
		return false;
	}

	/**
	 * @param data
	 *          data para comparar com as outras duas
	 * @param dataInicio
	 * @param dataFim
	 * @param inclusivo
	 *          se true usa >= e <=, se false usa apenas > e <
	 * @return true se a data for maior ou igual a inicial e menor ou igual a
	 *         final, caso a dataFim e/ou a data seja(m) nulla, atrinbui a maior
	 *         data possivel (31/12/9999)
	 */
	public static boolean inIn(Date data, Date dataInicio, Date dataFim, boolean inclusivo) {

		if (data == null && (dataFim == null || dataInicio == null))
			return true;

		// substitui as vigencias em aberto (dataFim == null)
		// jah que uma vigencia em aberto seria a maior data possivel
		if (dataFim == null) {
			Calendar calendar = new GregorianCalendar(9999, 12, 31);
			dataFim = new java.sql.Date(calendar.getTimeInMillis());
		}

		if (data == null) {
			Calendar calendar = new GregorianCalendar(9999, 12, 31);
			data = new java.sql.Date(calendar.getTimeInMillis());
		}

		if (inclusivo) {
			boolean maiorOuIgual = data.compareTo(dataInicio) >= 0;
			boolean menorOuIgual = data.compareTo(dataFim) <= 0;

			return maiorOuIgual && menorOuIgual;
		} else {
			boolean maior = data.compareTo(dataInicio) > 0;
			boolean menor = data.compareTo(dataFim) < 0;

			return maior && menor;
		}

	}

	public static boolean isSaturdayOrSunday(Date data) {
		calendar.setTime(data);
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		return (dia == Calendar.SATURDAY || dia == Calendar.SUNDAY);
	}

	public static String getMonth(Date data) {
		calendar.setTime(data);
		return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
	}

	public static Date setActualHourToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Calendar calendarAux = Calendar.getInstance();
		calendarAux.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, calendarAux.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendarAux.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendarAux.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendarAux.get(Calendar.MILLISECOND));
		return calendar.getTime();
	}
	
	public static DayIterator iterator(Date dateStart, Date dateEnd){
		return new DayIterator(dateStart, dateEnd);
	}
	
	public static class DayIterator{
		
		private Date dataEnd;
		
		Calendar calendar = new GregorianCalendar();
		
		private DayIterator(Date dataStart, Date dataEnd) {
	    this.dataEnd = dataEnd;
	    calendar.setTime(dataStart);
	    //inclusive
	    calendar.add(Calendar.DAY_OF_MONTH, -1);
    }
		
		public boolean hasNext(){
			return compareDDMMYYYY(calendar.getTime(), dataEnd) < 0;
		}
		
		public Date nextDay(){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			return calendar.getTime();
		}
		
	}
	
	public static void main(String[] args) throws Exception{
	  
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date dataStart = sdf.parse("31/05/2012");
		
		Date dataEnd = sdf.parse("07/06/2012");
		
		DayIterator dayIterator = DateUtil.iterator(dataStart, dataEnd);
		
		while(dayIterator.hasNext()){
			System.out.println(dayIterator.nextDay());
		}
		
		
		
  }
	
	
}