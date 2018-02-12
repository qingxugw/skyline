package com.skyline.pub.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Date Utility Class
 * This is used to convert Strings to Dates and Timestamps
 *
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 *   to correct time pattern. Minutes should be mm not MM
 * 	(MM is month).
 * @version $Revision: 1.5 $ $Date: 2004/10/05 07:20:13 $
 *
 *
 * liuzy modify 2010-5-22 增加计算加班时间和其他考勤类型的时间
 *
 */
public class DateUtil {
    //~ Static fields/initializers =============================================

    private static Log log = LogFactory.getLog(DateUtil.class);
    public static String datePattern = "yyyy-MM-dd";
    public static String italicDatePattern = "yyyy/MM/dd";
    public static String simpledDatePattern = "yyyyMMdd";
    public static String timePattern = "HH:mm";
    public static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
    public static String xmlDatePattern = "dd/MM/yyyy";
    public static String extDatePattern = "MM-dd-yyyy";

    //~ Methods ================================================================

    /**
     * Return default datePattern (MM/dd/yyyy)
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * This method attempts to convert an Oracle-formatted date
     * in the form dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 返回当时的时间 格式是"yyyy-MM-dd 'at' HH:mm:ss"
     * @param aDate
     * @return      当时的时间的字符串
     */
    public static final String getDateTime(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(dateTimePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
    /**
     * 返回当前时间 格式是"yyyy-MM-dd"
     * @return      当前时间的字符串
     */
    public static final String getDate() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat df = null;
        String returnValue = "";
        cd.setTime(new Date());
        Date aDate =cd.getTime();
        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 返回响应格式的日期 ，格式为 YYYY-MM-DD ,如果参数为NULL ，则返回当前日期
     * @param aDate
     * @return
     *
    public static final Date getCurrentDate(String aDate){
    SimpleDateFormat df = null;
    Date returnValue = null;
    Date d = null;
    df = new SimpleDateFormat(datePattern);
    if(aDate == null||aDate.equals(""))
    {
    d = new Date();

    returnValue = df.
    }
    else
    {
    returnValue = df.parse(aDate);
    }
    return (returnValue);
    }
     */
    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see java.text.SimpleDateFormat
     * @throws java.text.ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format:
     * MM/dd/yyyy HH:MM a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws java.text.ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     *
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }

    /**
     * used for Gantt
     * @param aDate
     * @return  a string
     */
    public static final String convertDateToXmlString(Date aDate){
        return getDateTime(xmlDatePattern,aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     *
     * @throws java.text.ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + datePattern);
            }

            if(strDate.indexOf("/") != -1){
                aDate = convertStringToDate(italicDatePattern, strDate);
            }else if(strDate.indexOf("-") != -1){
                aDate = convertStringToDate(datePattern, strDate);
            }else{
                aDate = convertStringToDate(simpledDatePattern, strDate);
            }

        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(),
                    pe.getErrorOffset());

        }

        return aDate;
    }
    /**
     * 得到当前时间
     * @return    当前时间
     */
    public static Timestamp getCurrentTime(){
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());

        return now;
    }
    /**
     * 得到当前日期的前一天  任务模块需要
     * */

    public static Timestamp getCurrentTimebefore(){
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime()-86400000);
        return now;
    }

    /**
     * 得到当前时间
     * @return    字符串型的当前时间
     */
    public static String getCurrentDateTime(){
        Date date = new Date();
        return getDateTime(date);
    }


    /**
     * 得到一天的最早的时间
     * @param date
     * @return
     */
    public static Timestamp getDateFirstTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp getDateLastTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTimeInMillis());
    }

    /**
     * 得到一天的最晚的时间
     * @param date
     * @return
     */
    public static Timestamp getDateEndTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        Timestamp end = new Timestamp(cal.getTimeInMillis());
        return end;
    }

    /**
     * 得到一日的特定几天前的日期
     * @param endpoint    一日
     * @param pastdays    从该日起的几天
     * @return            几天前的日期
     */
    public static Date getPastDay(Date endpoint, int pastdays){
        int daysInYear = 365;
        Calendar cal = Calendar.getInstance();
        cal.setTime(endpoint);
        //得到月份
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DATE);
        if(currentMonth == 0){//如果是一月
            if(currentDay <= pastdays){//如果要翻年
                cal.roll(Calendar.YEAR,-1 - pastdays/daysInYear);
            }
            cal.roll(Calendar.DAY_OF_YEAR,(-pastdays));
        }else{
            cal.roll(Calendar.DAY_OF_YEAR,(-pastdays));
        }

        return cal.getTime();
    }

    /**
     * 得到一日的特定几日后的日期
     * @param startpoint 某一日
     * @param afterdays  从该日起的几天
     * @return           几天后的日期
     * modified by bianjsh 20101125
     */
    public static Date getAfterDay(Date startpoint, int afterdays){
        //int daysInYear = 365;
        Calendar cal = Calendar.getInstance();
        cal.setTime(startpoint);
        cal.add(Calendar.DATE,afterdays);

        /*
          //得到月份
          int currentMonth = cal.get(Calendar.MONTH);
          int currentDay = cal.get(Calendar.DATE);
          if(currentMonth == 11){//如果是12月
              if((currentDay + afterdays)>31){//如果要翻年
                  cal.roll(Calendar.DAY_OF_YEAR,afterdays);
                  cal.roll(Calendar.YEAR,1+afterdays/daysInYear);
              }else{
                  cal.roll(Calendar.DAY_OF_YEAR,afterdays);
              }
          }else{
              cal.roll(Calendar.DAY_OF_YEAR,afterdays);
          }*/

        return cal.getTime();
    }

    public static String getAfterDayString(String startpoint,int afterdays){
        Date date = null;
        try {
            date = convertStringToDate(startpoint);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        date = getAfterDay(date,afterdays);
        return convertDateToString(date);

    }

    /**
     * 获取日期
     * @param point 目标日期
     * @param field roll类型: Calendar.YEAR or Calendar.Month... Calendar.DATE
     * @param numbers numbers
     * @return date
     */
    public static Date rollDate(Date point,int field,int numbers){
        Calendar cal = Calendar.getInstance();
        cal.setTime(point);
        cal.add(field,numbers);
        return cal.getTime();
    }
    /**
     * 得到两天之间相隔的天数
     *
     * 四舍五入
     *
     * @param startDate		开始时间（小的）
     * @param endDate		结束时间（大的）
     * @return				天数
     */
    public static int getFate(Timestamp startDate,Timestamp endDate){

        long endTime = endDate.getTime();

        long startTime = startDate.getTime();

        int intNum = (int) Math.round((double)(endTime - startTime)/(double)( 1000 * 60 * 60 * 24 )) ;

        return intNum;
    }

    /**
     * 得到当月的第一天
     * @param date    日期
     * @return        当月的第一天
     */
    public static Date getFirstDayOfCurrentMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始
        int day = 1;

        cal.set(year,month,day);

        return cal.getTime();
    }

    /**
     * 得到输入的日期当月的最后一天
     * @param date    日期
     * @return        当月的最后一天
     */
    public static Date getLastDayOfCurrentMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始
//    	int day = cal.get(Calendar.DAY_OF_MONTH);
        int lastDay = 0;

        //如果月份是1,3,5,7,8,10,12的情况,每月的最后一天是31号
        int[] bigMonth = {1,3,5,7,8,10,12};
        //如果月份是4,6,9,11的情况,每月的最后一天是30号
        int[] smallMonth = {4,6,9,11};
        if((month+1) == 2){//如果是2月,则最后一天是28号
            lastDay = 28;
        }else{
            for(int i=0;i<bigMonth.length;i++){
                if((month+1) == bigMonth[i]){
                    lastDay = 31;
                    break;
                }
            }
            for(int i=0;i<smallMonth.length;i++){
                if((month+1) == smallMonth[i]){
                    lastDay = 30;
                    break;
                }
            }
        }
        cal.set(year,month,lastDay);
        return cal.getTime();
    }

    /**
     * 得到下个月的某一天
     * @param date        输入的日期
     * @param nextDay     下月的某一天(int型的)
     * @return            下月的一天
     */
    public static Date getNextMonthDay(Date date, int nextDay){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始

        //如果月份为12月,则转入下一年
        if((month+1) == 12){
            month = 0;
            year++;
        }else{
            month++;
        }
        cal.set(year,month,nextDay);

        return cal.getTime();
    }
    /**
     * 得到下个月的某一天
     * @param date        输入的日期
     * @param
     * @return            下月的一天
     */
    public static Date getNextMonthDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始
        int day = cal.get(Calendar.DATE);//月份是从0开始

        //如果月份为12月,则转入下一年
        if((month+1) == 12){
            month = 0;
            year++;
        }else{
            month++;
        }
        cal.set(year,month,day);

        return cal.getTime();
    }
    /**
     * 得到上个月的某一天
     * @param date        输入的日期
     * @param lastDay     上月的某一天(int型的)
     * @return            上月的一天
     */
    public static Date getLastMonthDay(Date date, int lastDay){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始

        //如果月份为-1月,则转入上一年
        if((month) == -1){
            month = 12;
            year--;
        }else{
            month--;
        }
        cal.set(year,month,lastDay);

        return cal.getTime();
    }

    /**
     * 得到上个月的第一天
     * @param date        输入的日期
     * @param
     * @return            上月的第一天
     */
    public static Date getLastMonthFirstDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);//月份是从0开始
        int day = 1;

        //如果月份为-1月,则转入上一年
        if((month-1) == -1){
            month = 12;
            year--;
        }else{
            month--;
        }
        cal.set(year,month,day);

        return cal.getTime();
    }





    /**
     * 得到输入年月当月的第一天
     * @param
     * @param
     * @return        当月的第一天
     */
    public static Date getFirstDayOfMonth(int year,int month){
        if(month < 1 || month > 12){
            month = 1;
        }
        month = month -1;

        Calendar cal = Calendar.getInstance();
        int day = 1;

        cal.set(year,month,day);

        return cal.getTime();
    }
    /**
     * 得到输入年月当月的最后一天
     * @param
     * @param
     * @return        当月的最后一天
     */
    public static Date getLastDayOfMonth(int year,int month){
        if(month < 1 || month > 12){
            month = 1;
        }
        month = month -1;

        Calendar cal = Calendar.getInstance();
        int lastDay = 0;

        //如果月份是1,3,5,7,8,10,12的情况,每月的最后一天是31号
        int[] bigMonth = {1,3,5,7,8,10,12};
        //如果月份是4,6,9,11的情况,每月的最后一天是30号
        int[] smallMonth = {4,6,9,11};
        if((month+1) == 2){//如果是2月,则最后一天是28号
            if(isLeapYear(year) == false){
                lastDay = 28;
            }else{//闰年
                lastDay = 29;
            }
        }else{
            for(int i=0;i<bigMonth.length;i++){
                if((month+1) == bigMonth[i]){
                    lastDay = 31;
                    break;
                }
            }
            for(int i=0;i<smallMonth.length;i++){
                if((month+1) == smallMonth[i]){
                    lastDay = 30;
                    break;
                }
            }
        }
        cal.set(year,month,lastDay);
        return cal.getTime();
    }

    /**
     * 得到今年的前n年的组合列表
     * @param n		数字
     * @return           年份组合列表
     */
    public static List<String> getListBeforeThisYear(int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentTime());
        int year = cal.get(Calendar.YEAR);

        List<String> list = new ArrayList<String>();

        for(int i=n;i>0;i--){
            list.add(String.valueOf(year - i));
        }
        list.add(String.valueOf(year));

        return list;
    }

    /**
     * 判断是否是闰年
     * @param year	年
     * @return           boolean
     */
    public static boolean isLeapYear(int year){
        boolean result = false;
        if(year % 100 == 0){
            if(year % 400 == 0){
                result = true;
            }
        }else {
            if(year % 4 == 0){
                result = true;
            }
        }

        return result;
    }

    /**
     * 获得周一的日期
     * @param date
     * @return date
     */
    public static Date getMonday(Date date){
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        return c.getTime();
    }
    /**
     * 获得周日的日期
     * @param date
     * @return date
     */
    public static Date getSunday(Date date){
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return c.getTime();
    }
    /**
     * 获得date日期相对startdate日期所在的周的间隔
     * @param date
     * @return int
     */
    public static int getWeekNum(Timestamp startdate,Timestamp date){
        int int_date = getFate(getDateFirstTime(getMonday(startdate)),getDateFirstTime(date));
        return int_date / 7;
    }
    /**
     * 获得date日期相对startdate日期所在的月的间隔
     * @param date
     * @return int
     */
    public static int getMonthNum(Timestamp startdate,Timestamp date){

        Calendar start = Calendar.getInstance();
        start.setTime(startdate);
        int startCurrentMonth = start.get(Calendar.MONTH);

        Calendar end = Calendar.getInstance();
        end.setTime(date);
        int endCurrentMonth = end.get(Calendar.MONTH);

        return endCurrentMonth - startCurrentMonth;
    }

    public static Timestamp assembleTimestamp (String date,String hour,String minute) throws Exception{
        return Timestamp.valueOf(date + " " + hour + ":" +minute + ":00");
    }



    /**
     * 获得输入日期的周1和周日的日期
     */
    public static HashMap getWeekDate(String inDate){
        String str = "";
        HashMap weekMap = new HashMap();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String showDate ="";
        try {
            date = dateFormat.parse(inDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int mydate = cd.get(Calendar.DAY_OF_WEEK);
        cd.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        if(mydate == 1){
            cd.add(Calendar.DATE, -7);
        }
        str =  new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime());
        weekMap.put("monday", str);

        cd.add(Calendar.DATE, 6);
        str =  new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime());
        weekMap.put("sunday", str);
        return weekMap;
    }
    /**
     * 获得输入日期的周1到周日的日期列表
     */
    public static List getWeekList(HashMap weekMap){
        List weekList = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String showDate ="";
        try {
            date = dateFormat.parse((String)weekMap.get("monday"));
        } catch (Exception e){
            e.printStackTrace();
        }
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        weekList.add(new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime()));
        for(int i = 0;i <= 5;i++){
            cd.add(Calendar.DATE, 1);
            weekList.add(new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime()));
        }
        return weekList;
    }
    /*
      * 获得自开始日期至结束日期的日期列表
      */
    public static List getDateList(String startDate,String endDate){
        List dateList = new ArrayList();
        Date sDate = new Date();
        Date eDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sDate = dateFormat.parse(startDate);
            eDate = dateFormat.parse(endDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        Calendar cd = Calendar.getInstance();
        cd.setTime(sDate);
        dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime()));
        for(;cd.getTime().compareTo(eDate) < 0;){
            cd.add(Calendar.DATE, 1);
            dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(cd.getTime()));
        }
        return dateList;
    }
    /*
      * 根据String类型的日期判断该日是否周末
      */
    public static boolean isWeekend(String date) throws ParseException{
        boolean b = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dateFormat.parse(date);
        Calendar cd = Calendar.getInstance();
        cd.setTime(d);
        int weekDay = cd.get(Calendar.DAY_OF_WEEK)-1;
        if(weekDay == 0 || weekDay == 6){
            b = true;
        }
        return b;
    }

    /**
     * 已经转换成中国地区的周
     * @param date
     * @return
     */
    public static int getWeek(Date date){
        Calendar cd = Calendar.getInstance();
        int week = cd.get(Calendar.DAY_OF_WEEK);
        if(week==1) return 7;
        else
            return week-1;
    }

    /**
     * 如果是周末就包含本周，如果不是周末则查找前三个周
     * @param
     * @praram wk  当前是多少周
     * @return
     * @throws java.text.ParseException
     */
    public static String[] getLast4Weeks(String wk) throws ParseException{
        //System.out.println(wk);
        String[] weeks = new String[4];
        int year = Integer.parseInt(wk.substring(0,4));
        int week = Integer.parseInt(wk.substring(4,6));
        if(isWeekend(DateUtil.getDate())){
            weeks[0] =  String.valueOf(year+""+week);//取当前周
            weeks[1] =  getLastWeek(year,Integer.parseInt(weeks[0].substring(4,6)));
            weeks[2] =  getLastWeek(year,Integer.parseInt(weeks[1].substring(4,6)));
            weeks[3] =  getLastWeek(year,Integer.parseInt(weeks[2].substring(4,6)));
        }
        else
        {
            weeks[0] =  getLastWeek(year,week);//从上周开始
            //System.out.println(weeks[0]);
            weeks[1] =  getLastWeek(year,Integer.parseInt(weeks[0].substring(4,6)));
            weeks[2] =  getLastWeek(year,Integer.parseInt(weeks[1].substring(4,6)));
            weeks[3] =  getLastWeek(year,Integer.parseInt(weeks[2].substring(4,6)));
        }
        return weeks;
    }


    static int tempYear=0;

    /**
     * 获取上周 201110-201109
     * @param year year
     * @param week week
     * @return last YYYYIW
     */
    public static String getLastWeek(int year,int week){
        if(week<2){
            tempYear=year-1;
            return (year-1)+""+52;//一年52周,可能有出入，忽略
        }
        else {
            if(week-1<10){
                return year+""+"0"+(week-1);
            }
            else if(year>tempYear&&tempYear!=0){
                return year-1+""+(week-1);
            }
            return year+""+(week-1);
        }
    }

    /**
     * 计算两个日期之间相差的天数  
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetween(Date date1,Date date2)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }



    /**
     *   计算加班时间：加班时间的规则为：8：00-8：30 ； 12：00-13：00  ；17：30-18：00，在这个三个时间段内都不属于加班时间。
     *   @param   start   date
     *   @param   end     date
     *   @return   Date   yyyy-mm-dd HH:mm  
     *   author liuzy 
     * @throws Exception
     */
    public static  double  overTimeCount (Date start,Date end) throws Exception   {
        SimpleDateFormat sdf =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date   ycStart   =   new   Date();
        Date   ycEnd =   new   Date();
        ycStart.setTime(start.getTime());
        ycStart.setSeconds(0);
        ycEnd.setTime(end.getTime());
        ycEnd.setSeconds(0);
//		int xcts = DateUtil.daysBetween(ycStart,ycEnd);
        double fzhj = 0.0;
        SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        String day800 = df.format(ycStart);
        Date day800d = new Date();
        day800d = df.parse(day800);
        day800d.setHours(8);
        day800d.setMinutes(00);
        //8：30
        String day830 = df.format(ycStart);
        Date day830d = new Date();
        day830d = df.parse(day830);
        day830d.setHours(8);
        day830d.setMinutes(30);
        //12点
        String day1200 = df.format(ycStart);
        Date day1200d = new Date();
        day1200d = df.parse(day1200);
        day1200d.setHours(12);
        day1200d.setMinutes(00);
        //13：00
        String day1300 = df.format(ycStart);
        Date day1300d = new Date();
        day1300d = df.parse(day1300);
        day1300d.setHours(13);
        day1300d.setMinutes(00);
        //17：30
        String day1730 = df.format(ycStart);
        Date day1730d = new Date();
        day1730d = df.parse(day1730);
        day1730d.setHours(17);
        day1730d.setMinutes(30);
        //18：00
        String day1800 = df.format(ycStart);
        Date day1800d = new Date();
        day1800d = df.parse(day1800);
        day1800d.setHours(18);
        day1800d.setMinutes(00);

        // 总时间数
        double totalTime=0.0;
//			8：00-8：30 ； 12：00-13：00  ；17：30-18：00
        //确定开始时间
        if(ycStart.getTime()>=day800d.getTime() && ycStart.getTime()<=day830d.getTime()){
            ycStart.setHours(8);
            ycStart.setMinutes(30);
        }
        if(ycStart.getTime()>=day1200d.getTime() && ycStart.getTime()<=day1300d.getTime()){
            ycStart.setHours(13);
            ycStart.setMinutes(00);
        }
        if(ycStart.getTime()>=day1730d.getTime() && ycStart.getTime()<=day1800d.getTime()){
            ycStart.setHours(18);
            ycStart.setMinutes(00);
        }
        //确定结束时间
        if(ycEnd.getTime()>=day800d.getTime() && ycEnd.getTime()<=day830d.getTime()){
            ycEnd.setHours(8);
            ycEnd.setMinutes(30);
        }
        if(ycEnd.getTime()>=day1200d.getTime() && ycEnd.getTime()<=day1300d.getTime()){
            ycEnd.setHours(13);
            ycEnd.setMinutes(00);
        }
        if(ycEnd.getTime()>=day1730d.getTime() && ycEnd.getTime()<=day1800d.getTime()){
            ycEnd.setHours(18);
            ycEnd.setMinutes(00);
        }
        System.out.println("ycStart==="+df.format(ycStart));
        System.out.println("ycEnd===="+df.format(ycEnd));
        //开始时间小于等于8:00时,同时结束时间小于等于12：00时
        if(ycStart.getTime()<=day800d.getTime() && ycEnd.getTime()<=day1200d.getTime()){
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-30;
        }else if(ycStart.getTime()<=day800d.getTime() && ycEnd.getTime()>=day1300d.getTime()&& ycEnd.getTime()<=day1730d.getTime() ){//开始时间小于等于8:00时,并且结束时间大于等于13：00小于等于17：30时
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-90;
        }else if(ycStart.getTime()<=day800d.getTime() && ycEnd.getTime()>=day1800d.getTime()){ //8:00>=开始时间,并且结束时间<=18:00
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-120;
        }
        //lzy 2010-7-2	加班通宵时间计算如下：
        if(ycStart.getTime()<=day800d.getTime() && ycEnd.getTime()<=day800d.getTime()){
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60);
        }
        //开始时间>=8:30时并且开始时间小于等于12：00
        if(ycStart.getTime()>=day830d.getTime() && ycStart.getTime()<=day1200d.getTime()  && ycEnd.getTime()<=day1200d.getTime()){ //并且结束时间<=12:00时
            totalTime=(ycEnd.getTime()-ycStart.getTime())/1000/60;
        }else if(ycStart.getTime()>=day830d.getTime() && ycStart.getTime()<=day1200d.getTime() && ycEnd.getTime()<=day1730d.getTime()){//并且结束时间<=17:30时
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-60;
        }else if(ycStart.getTime()>=day830d.getTime() && ycStart.getTime()<=day1200d.getTime() && ycEnd.getTime()>=day1800d.getTime()){////并且结束时间>=18:30时
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-90;
        }
        //开始时间>=13:00 并且小于等于17：30时
        if(ycStart.getTime()>=day1300d.getTime() && ycStart.getTime()<=day1730d.getTime()  && ycEnd.getTime()<=day1730d.getTime()){ //并且结束时间<=17:30时
            totalTime=(ycEnd.getTime()-ycStart.getTime())/1000/60;
        }else if(ycStart.getTime()>=day1300d.getTime() && ycStart.getTime()<=day1730d.getTime()  && ycEnd.getTime()>=day1800d.getTime()){//并且结束时间>=18:00时
            totalTime=((ycEnd.getTime()-ycStart.getTime())/1000/60)-30;
        }
        //开始时间大于等于18：00
        if(ycStart.getTime()>=day1800d.getTime() ){
            totalTime=(ycEnd.getTime()-ycStart.getTime())/1000/60;
        }
        System.out.println("dddddd"+totalTime);
        return totalTime;

    }

    /**
     *   //其他异常类型（除加班外），计算分钟数
     *   @param   start   date
     *   @param   end     date
     *   @return   Date   yyyy-mm-dd HH:mm  
     *
     *    author liuzy 
     * @throws Exception
     */
    public static  double  otherTimeCount (Date start,Date end) throws Exception   {
        SimpleDateFormat sdf =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date   ycStart   =   new   Date();
        Date   ycEnd =   new   Date();
        ycStart.setTime(start.getTime());
        ycStart.setSeconds(0);
        ycEnd.setTime(end.getTime());
        ycEnd.setSeconds(0);
        int xcts = DateUtil.daysBetween(ycStart,ycEnd);
        double fzhj = 0.0;
        SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        //开始时间的判断=============
        //8：30
        String day830 = df.format(ycStart);
        Date day830d = new Date();
        day830d = df.parse(day830);
        day830d.setHours(8);
        day830d.setMinutes(30);
        //12点
        String day1200 = df.format(ycStart);
        Date day1200d = new Date();
        day1200d = df.parse(day1200);
        day1200d.setHours(12);
        day1200d.setMinutes(00);
        //13：00
        String day1300 = df.format(ycStart);
        Date day1300d = new Date();
        day1300d = df.parse(day1300);
        day1300d.setHours(13);
        day1300d.setMinutes(00);
        //17：30
        String day1730 = df.format(ycStart);
        Date day1730d = new Date();
        day1730d = df.parse(day1730);
        day1730d.setHours(17);
        day1730d.setMinutes(30);
        //================end
//			结束时间的判断=============start
        //8：30
        String day830e = df.format(ycEnd);
        Date day830de = new Date();
        day830de = df.parse(day830e);
        day830de.setHours(8);
        day830de.setMinutes(30);
        //12点
        String day1200e = df.format(ycEnd);
        Date day1200de = new Date();
        day1200de = df.parse(day1200e);
        day1200de.setHours(12);
        day1200de.setMinutes(00);
        //13：00
        String day1300e = df.format(ycEnd);
        Date day1300de = new Date();
        day1300de = df.parse(day1300e);
        day1300de.setHours(13);
        day1300de.setMinutes(00);
        //17：30
        String day1730e = df.format(ycEnd);
        Date day1730de = new Date();
        day1730de = df.parse(day1730e);
        day1730de.setHours(17);
        day1730de.setMinutes(30);
        //================end

        // 总时间数
        double totalTime=0.0;
        //正常上班时间规则为:8:30-12:00  13:00-17:30
        //确定开始时间
        if( ycStart.getTime()<=day830d.getTime()){
            ycStart.setHours(8);
            ycStart.setMinutes(30);
        }
        if(ycStart.getTime()>=day1200d.getTime() && ycStart.getTime()<=day1300d.getTime()){
            ycStart.setHours(13);
            ycStart.setMinutes(00);
        }
        if(ycStart.getTime()>=day1730d.getTime() ){
            ycStart.setHours(17);
            ycStart.setMinutes(30);
        }
        //确定结束时间
        if( ycEnd.getTime()<=day830de.getTime()){
            ycEnd.setHours(8);
            ycEnd.setMinutes(30);
        }
        if(ycEnd.getTime()>=day1200de.getTime() && ycEnd.getTime()<=day1300de.getTime()){
            ycEnd.setHours(13);
            ycEnd.setMinutes(00);
        }
        if(ycEnd.getTime()>=day1730de.getTime() ){
            ycEnd.setHours(17);
            ycEnd.setMinutes(30);
        }

        System.out.println("ycStart==="+df.format(ycStart));
        System.out.println("ycEnd===="+df.format(ycEnd));
        if(xcts==0){
            SimpleDateFormat   df1   =   new   SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat   df2   =   new   SimpleDateFormat("yyyy-MM-dd");
            //结束中午时间
            String zhws = df1.format(ycStart);
            zhws += " 12:00";
            Date ycEndNew = new Date();
            ycEndNew = switchStringToDate(zhws);
            ycEndNew.setHours(12);
            //开始时间小于12点时，并且结束时间大于12点
            if(ycEndNew.getTime()>ycStart.getTime() && ycEndNew.getTime()<ycEnd.getTime()){
                System.out.println("ycStart888==="+df.format(ycEndNew));
                System.out.println("ycStart444==="+df.format(ycStart));
                //开始时间小于12点
                fzhj = ((ycEnd.getTime()-ycStart.getTime())/1000/60)-60;
            }else{
                System.out.println("ycStart555==="+df.format(ycStart));
                fzhj = (ycEnd.getTime()-ycStart.getTime())/1000/60;
            }
        }else{
            SimpleDateFormat   df1   =   new   SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat   df2   =   new   SimpleDateFormat("yyyy-MM-dd");
            //结束中午时间
            String zhws = df1.format(ycEnd);
            zhws += " 12:00";
            //结束上班时间
            String xw = df1.format(ycEnd);
            xw += " 08:30";
            //开始下班时间
            String startXb = df2.format(ycStart);
            startXb += " 17:30";
            //开始中午时间
            String startZw = df2.format(ycStart);
            startZw += " 12:00";

            Date ycEndNew = new Date();
            Date ycEndNewXW = new Date();
            Date ycStartXb = new Date();
            Date ycStartZw = new Date();
            ycEndNew = switchStringToDate(zhws);
            ycEndNew.setHours(12);
            ycEndNewXW = switchStringToDate(xw);
            ycEndNewXW.setHours(8);
            ycStartXb = switchStringToDate(startXb);
            ycStartXb.setHours(17);
            ycStartZw = switchStringToDate(startZw);
            ycStartZw.setHours(12);
            //第一天的分钟数
            if(ycStart.getTime()>ycStartZw.getTime()){
                fzhj += (ycStartXb.getTime()-ycStart.getTime())/1000/60;
            }else{
                //减掉中午的一个小时
                fzhj += ((ycStartXb.getTime()-ycStart.getTime())/1000/60)-60;
            }

            //第二天的时间请假结束时间
            if(ycEnd.getTime()>ycEndNew.getTime()){
                //如果 结束时间>12点  结束时间-8:30-一个小时;
                fzhj = fzhj+((ycEnd.getTime()-ycEndNewXW.getTime())/1000/60-60);
            }else{
                //如果 结束时间<12点  结束时间-8:30;
                fzhj = fzhj+((ycEnd.getTime()-ycEndNewXW.getTime())/1000/60);
            }
            if(xcts>1)fzhj = fzhj +((xcts-1)*480);
        }
        return fzhj;

    }

    /**
     *   //去除公休日和法定节假日来计算分钟数
     *   @param   start   date
     *   @param   end     date
     *   @return   Date   yyyy-mm-dd HH:mm  
     *
     *    author liuzy 
     * @throws Exception
     */
    public static  double  otherTimeCountHoliday (int xcts,Date start,Date end) throws Exception   {
        SimpleDateFormat sdf =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date   ycStart   =   new   Date();
        Date   ycEnd =   new   Date();
        ycStart.setTime(start.getTime());
        ycStart.setSeconds(0);
        ycEnd.setTime(end.getTime());
        ycEnd.setSeconds(0);
        //int xcts = DateUtil.daysBetween(ycStart,ycEnd);
        double fzhj = 0.0;
        SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
        //开始时间的判断=============
        //8：30
        String day830 = df.format(ycStart);
        Date day830d = new Date();
        day830d = df.parse(day830);
        day830d.setHours(8);
        day830d.setMinutes(30);
        //12点
        String day1200 = df.format(ycStart);
        Date day1200d = new Date();
        day1200d = df.parse(day1200);
        day1200d.setHours(12);
        day1200d.setMinutes(00);
        //13：00
        String day1300 = df.format(ycStart);
        Date day1300d = new Date();
        day1300d = df.parse(day1300);
        day1300d.setHours(13);
        day1300d.setMinutes(00);
        //17：30
        String day1730 = df.format(ycStart);
        Date day1730d = new Date();
        day1730d = df.parse(day1730);
        day1730d.setHours(17);
        day1730d.setMinutes(30);
        //================end
//			结束时间的判断=============start
        //8：30
        String day830e = df.format(ycEnd);
        Date day830de = new Date();
        day830de = df.parse(day830e);
        day830de.setHours(8);
        day830de.setMinutes(30);
        //12点
        String day1200e = df.format(ycEnd);
        Date day1200de = new Date();
        day1200de = df.parse(day1200e);
        day1200de.setHours(12);
        day1200de.setMinutes(00);
        //13：00
        String day1300e = df.format(ycEnd);
        Date day1300de = new Date();
        day1300de = df.parse(day1300e);
        day1300de.setHours(13);
        day1300de.setMinutes(00);
        //17：30
        String day1730e = df.format(ycEnd);
        Date day1730de = new Date();
        day1730de = df.parse(day1730e);
        day1730de.setHours(17);
        day1730de.setMinutes(30);
        //================end

        // 总时间数
        double totalTime=0.0;
        //正常上班时间规则为:8:30-12:00  13:00-17:30
        //确定开始时间
        if( ycStart.getTime()<=day830d.getTime()){
            ycStart.setHours(8);
            ycStart.setMinutes(30);
        }
        if(ycStart.getTime()>=day1200d.getTime() && ycStart.getTime()<=day1300d.getTime()){
            ycStart.setHours(13);
            ycStart.setMinutes(00);
        }
        if(ycStart.getTime()>=day1730d.getTime() ){
            ycStart.setHours(17);
            ycStart.setMinutes(30);
        }
        //确定结束时间
        if( ycEnd.getTime()<=day830de.getTime()){
            ycEnd.setHours(8);
            ycEnd.setMinutes(30);
        }
        if(ycEnd.getTime()>=day1200de.getTime() && ycEnd.getTime()<=day1300de.getTime()){
            ycEnd.setHours(13);
            ycEnd.setMinutes(00);
        }
        if(ycEnd.getTime()>=day1730de.getTime() ){
            ycEnd.setHours(17);
            ycEnd.setMinutes(30);
        }

        System.out.println("ycStart==="+df.format(ycStart));
        System.out.println("ycEnd===="+df.format(ycEnd));
        if(xcts==1){
            SimpleDateFormat   df1   =   new   SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat   df2   =   new   SimpleDateFormat("yyyy-MM-dd");
            //结束中午时间
            String zhws = df1.format(ycStart);
            zhws += " 12:00";
            Date ycEndNew = new Date();
            ycEndNew = switchStringToDate(zhws);
            ycEndNew.setHours(12);
            //开始时间小于12点时，并且结束时间大于12点
            if(ycEndNew.getTime()>ycStart.getTime() && ycEndNew.getTime()<ycEnd.getTime()){
                System.out.println("ycStart888==="+df.format(ycEndNew));
                System.out.println("ycStart444==="+df.format(ycStart));
                //开始时间小于12点
                fzhj = ((ycEnd.getTime()-ycStart.getTime())/1000/60)-60;
            }else{
                System.out.println("ycStart555==="+df.format(ycStart));
                fzhj = (ycEnd.getTime()-ycStart.getTime())/1000/60;
            }
        }else{
            SimpleDateFormat   df1   =   new   SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat   df2   =   new   SimpleDateFormat("yyyy-MM-dd");
            //结束中午时间
            String zhws = df1.format(ycEnd);
            zhws += " 12:00";
            //结束上班时间
            String xw = df1.format(ycEnd);
            xw += " 08:30";
            //开始下班时间
            String startXb = df2.format(ycStart);
            startXb += " 17:30";
            //开始中午时间
            String startZw = df2.format(ycStart);
            startZw += " 12:00";

            Date ycEndNew = new Date();
            Date ycEndNewXW = new Date();
            Date ycStartXb = new Date();
            Date ycStartZw = new Date();
            ycEndNew = switchStringToDate(zhws);
            ycEndNew.setHours(12);
            ycEndNewXW = switchStringToDate(xw);
            ycEndNewXW.setHours(8);
            ycStartXb = switchStringToDate(startXb);
            ycStartXb.setHours(17);
            ycStartZw = switchStringToDate(startZw);
            ycStartZw.setHours(12);
            //第一天的分钟数
            if(ycStart.getTime()>ycStartZw.getTime()){
                fzhj += (ycStartXb.getTime()-ycStart.getTime())/1000/60;
            }else{
                //减掉中午的一个小时
                fzhj += ((ycStartXb.getTime()-ycStart.getTime())/1000/60)-60;
            }

            //第二天的时间请假结束时间
            if(ycEnd.getTime()>ycEndNew.getTime()){
                //如果 结束时间>12点  结束时间-8:30-一个小时;
                fzhj = fzhj+((ycEnd.getTime()-ycEndNewXW.getTime())/1000/60-60);
            }else{
                //如果 结束时间<12点  结束时间-8:30;
                fzhj = fzhj+((ycEnd.getTime()-ycEndNewXW.getTime())/1000/60);
            }
            if(xcts>2)fzhj = fzhj +((xcts-2)*480);
        }
        return fzhj;

    }

    /**
     *   将一个日期字符串转化成日期   
     *   @param   sDate   String   
     *   @return   Date   yyyy-mm-dd   
     */
    public  static Date   switchStringToDate(String   sDate)   {
        Date   date   =   null;
        try   {
            SimpleDateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm");
            date   =   df.parse(sDate);

        }
        catch   (Exception   e)   {
            System.out.println("日期转换失败:"   +   e.getMessage());
        }
        return   date;
    }

    /**
     * 获取传入年周的起始日期
     * @param weekNo 201101
     * @return  first date
     * @throws Exception -e
     */
    public static Date getFirstDayOfWeek(String weekNo) throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(weekNo.substring(0,4)), Calendar.JANUARY,1);
        //1月1日是一周第几天
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        if(day_of_week == Calendar.SUNDAY) day_of_week = 7;
        else day_of_week = day_of_week -1;

        cal.add(Calendar.DATE,-day_of_week);
        cal.add(Calendar.WEEK_OF_YEAR,Integer.parseInt(weekNo.substring(4,6)));
        cal.add(Calendar.DATE,1);
        return cal.getTime();
    }

    /**
     * 获取传入年周截止日期
     * @param weekNo 201101
     * @return  last date
     * @throws Exception -e
     */
    public static Date getLastDayOfWeek(String weekNo) throws Exception{
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(weekNo.substring(0,4)), Calendar.JANUARY,1);
        //1月1日是一周第几天
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        if(day_of_week == Calendar.SUNDAY) day_of_week = 7;
        else day_of_week = day_of_week -1;

        cal.add(Calendar.DATE,-day_of_week);
        cal.add(Calendar.WEEK_OF_YEAR,Integer.parseInt(weekNo.substring(4,6)));
        cal.add(Calendar.DATE,7);
        return cal.getTime();
    }

    /**
     * 获取传入日期字符串所在周起止日期和当前日期为一周第几天
     * 暂时没有用到
     * @param dateStr 日期字符串 2011-01-01 类似
     * @return map
     */
    public static Map getDateInfo(String dateStr){
        Map map = new HashMap();
        try {
            Date date = convertStringToDate(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer day_of_week = cal.get(Calendar.DAY_OF_WEEK);
            if(day_of_week == Calendar.SUNDAY) day_of_week = 7;
            else day_of_week --;
            Date startPoint = getAfterDay(date,-(day_of_week-1));
            Date endPoint = getAfterDay(startPoint,6);
            map.put("dayOfWeek",day_of_week);
            map.put("startPoint",convertDateToString(startPoint));
            map.put("endPoint",convertDateToString(endPoint));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取一天的最早时间
     * @param startDate
     * @return
     */
    public static Date getStartDate(String startDate){
        StringBuilder sb = new StringBuilder(startDate);
        sb.append(" 00:00:00");
        try {
            return convertStringToDate(dateTimePattern,sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 获取一天的最晚时间
     * @param endDate
     * @return
     */
    public static Date getEndDate(String endDate){
        StringBuilder sb = new StringBuilder(endDate);
        sb.append(" 23:59:59");
        try {
            return convertStringToDate(dateTimePattern,sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static void main(String[] args){
        try {
            System.out.println(convertStringToDate("2012/12/12"));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
