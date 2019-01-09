package cn.sjtu.meetingroom.meetingroomcore.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static List<String> parseList(String s){
        String[] utils = (s.substring(1, s.length() - 1)).split(",");
        return Arrays.asList(utils);
    }

    public static PageRequest createPageRequest(int pageNumber, int pageSize) {

        return new PageRequest(pageNumber-1, pageSize, new Sort(Sort.Direction.DESC, "id"));
    }
}
