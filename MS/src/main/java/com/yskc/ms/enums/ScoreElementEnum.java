package com.yskc.ms.enums;

import com.yskc.common.exception.OwnerException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @DateTime: 2022/6/14 13:49
 * @Description:评分要素枚举
 * @author: hsx
 */
public enum ScoreElementEnum {

    RD_REPORT(5, "立项报告" , Arrays.asList(10,10,10,50,10,10)),
    RD_DOC(6,"过程文件",Arrays.asList(10,20,20,20,20,10)),
    RD_BACKUP_FILE(10,"留存备查资料",Arrays.asList(100)),
    RD_NOVELTY_SEARCH(8,"查新报告",Arrays.asList(100)),
    RD_INNOVATION_SYS(9,"创新体系",Arrays.asList(100));

    private Integer type;

    private String name;

    private List<Integer> element;

    ScoreElementEnum(Integer type, String name, List<Integer> element) {
        this.type = type;
        this.name = name;
        this.element = element;
    }

    //检验得分要素
    public static void ScoreCheck(List<Integer> scores, Integer type) throws OwnerException{
        for (ScoreElementEnum s : ScoreElementEnum.values()) {
            if (Objects.equals(type,s.getType())) {
                List<Integer> elements = s.getElement();
                for (int i = 0; i < scores.size(); i++) {
                    Integer score = scores.get(i);
                    if (score < 0 || score > elements.get(i)) {
                        throw new OwnerException("分数信息有误，请检查无误后重新评分！");
                    }
                }
                return;
            }
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getElement() {
        return element;
    }

    public void setElement(List<Integer> element) {
        this.element = element;
    }
}
