package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 16:48
 * description:会议纪要
 */
@Component( "MeetingForm_Doc" )
@Scope( "prototype" )
public class MeetingFormDocument extends RDDocument {

    @Override
    public Map getDocMap() {
        Map jsonMap = this.getJsonMap();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> memberList = (List<Map<String, String>>)jsonMap.get("members");
        if (memberList != null && memberList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map<String, String> item :
                    memberList) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(item.get("ename"));
            }
            map.put("member", sb.toString());
        }
        ArrayList<Boolean> meetTypes = (ArrayList<Boolean>)jsonMap.get("checkedOptionAry");
        ArrayList<Boolean> readTypes = (ArrayList<Boolean>)jsonMap.get("readOptionAry");
        for (int i = 0; i < meetTypes.size(); i++) {
            if (i < readTypes.size()) {
                String key = MessageFormat.format("readType{0}_chk", i);
                map.put(key, readTypes.get(i));
            }
            String key = MessageFormat.format("type{0}_chk", i);
            map.put(key, meetTypes.get(i));
        }

        return map;
    }
}
