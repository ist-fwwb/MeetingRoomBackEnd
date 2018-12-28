package cn.sjtu.meetingroom.meetingroomcore.Util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequestFactory {
    public PageRequest create(int pageNumber, int pageSize) {

        return new PageRequest(pageNumber-1, pageSize, new Sort(Sort.Direction.DESC, "id"));
    }
}
