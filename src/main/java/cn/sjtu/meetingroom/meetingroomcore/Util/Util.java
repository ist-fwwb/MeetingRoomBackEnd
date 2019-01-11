package cn.sjtu.meetingroom.meetingroomcore.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Util {
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
}
