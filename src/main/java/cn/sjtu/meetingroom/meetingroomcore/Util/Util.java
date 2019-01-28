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

    public static String parseIntToTime(int time){
        StringBuilder sb = new StringBuilder();
        sb.append(time / 2); sb.append(':');
        sb.append(time % 2 == 0 ? "00" : "30");
        return sb.toString();
    }

    public static boolean compare(String origin, String s){
        if (s.length() > origin.length()) return false;
        boolean dp[][] = new boolean[s.length()+1][origin.length()+1];
        for (int i=0; i<=s.length(); ++i){
            for (int j=0; j<=origin.length(); ++j) dp[i][j] = true;
        }
        for (int i=1; i<=s.length(); ++i) dp[i][0] = false;
        for (int i=1; i<=s.length(); ++i) {
            char c = s.charAt(i-1);
            for (int j=i; j<=origin.length(); ++j){
                if (origin.charAt(j-1) == c && dp[i-1][j-1] ) break;
                else dp[i][j] = false;
            }
        }
        return dp[s.length()][origin.length()];
    }
}
