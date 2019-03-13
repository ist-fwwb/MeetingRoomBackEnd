package cn.sjtu.meetingroom.meetingroomcore.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {
    public static String ROOMID = "1";
    public static String VoiceTransformURL = "http://47.106.8.44:5000";
    public static String UserRegistryURL = "http://pipipan.cn:31001";
    public static String ElasticHost = "es-cn-v0h10r5e8000jh4bi.public.elasticsearch.aliyuncs.com";
    public static List<String> parseList(String s){
        String[] utils = (s.substring(1, s.length() - 1)).split(",");
        return Arrays.asList(utils);
    }

    public static PageRequest createPageRequest(int pageNumber, int pageSize) {

        return new PageRequest(pageNumber-1, pageSize, new Sort(Sort.Direction.DESC, "id"));
    }

    public static String generateAttendantNum(int size){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i=0; i<size; ++i){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String parseIntToTime(int time){
        StringBuilder sb = new StringBuilder();
        sb.append(time / 2); sb.append(':');
        sb.append(time % 2 == 0 ? "00" : "30");
        return sb.toString();
    }

    public static boolean compare(String origin, String s){
        if (s.length() > origin.length()) return false;
        if (s.length() == 0) return true;
        int index = 0;
        for (int i=0; i<origin.length(); ++i){
            if (origin.charAt(i) == s.charAt(index)) index++;
            if (index == s.length()) return true;
        }
        return false;
    }

    public static long getTimeStamp(){
        return new Date().getTime();
    }
    public static Date getNextDay(){
        Calendar calendar = Calendar. getInstance();
        calendar.setTime( new Date());
        calendar.set(Calendar. HOUR_OF_DAY, 0);
        calendar.set(Calendar. MINUTE, 0);
        calendar.set(Calendar. SECOND, 0);
        calendar.set(Calendar. MILLISECOND, 0);
        calendar.add(Calendar. DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
