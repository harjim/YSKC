package com.yskc.ms.models.project;

import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/5/20 8:15
 */
public class StepTreeModel {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 父节点
     */
    private Integer parentId;
    /**
     * 步骤名称
     */
    private String stepName;

    private String key;

    /**
     * 子节点
     */
    private List<StepTreeModel> children;

    private Integer stepType;

    private Integer seq;

    private Integer productId;

    private Integer signId;

    private Integer seqId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSignId() {
        return signId;
    }

    public void setSignId(Integer signId) {
        this.signId = signId;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public void setSeqId(Integer seqId) {
        this.seqId = seqId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStepType() {
        return stepType;
    }

    public void setStepType(Integer stepType) {
        this.stepType = stepType;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<StepTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<StepTreeModel> children) {
        this.children = children;
    }

    /**
     * 获取步骤根节点
     *
     * @param tree
     * @return
     */
    public static List<StepTreeModel> getTree(List<StepTreeModel> tree) {
        return getTree(tree, -1);
    }

    /**
     * 递归获取层级步骤
     *
     * @param tree
     * @param parentId
     * @return
     */
    private static List<StepTreeModel> getTree(List<StepTreeModel> tree, int parentId) {
        List<StepTreeModel> dList = new ArrayList<StepTreeModel>();
        for (StepTreeModel d : tree) {
            if (d.parentId.equals(parentId)) {
                d.setChildren(getTree(tree,Integer.parseInt(d.key)));
                dList.add(d);
            }
        }
        return dList;
    }

    public static  void setTree(List<StepTreeModel> tree,StepTreeModel stepTreeModel){
        int num=stepTreeModel.getSignId()*100;
        List<StepTreeModel> list=tree;
        if(!CollectionUtils.isEmpty(stepTreeModel.getChildren())){
            for (StepTreeModel stepTreeModel1:stepTreeModel.getChildren()){
                    stepTreeModel1.setSeqId(stepTreeModel.getSignId());
                    num=num+1;
                    stepTreeModel1.setSignId(num);
                    list.add(stepTreeModel1);
                    stepTreeModel1.setTree(list,stepTreeModel1);
            }
        }
    }
}
