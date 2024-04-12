package com.houcloud.example.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Houcloud
 */
@Data
public class TreeNode implements Serializable {

    protected Long id;
    protected Long parentId;
    protected List<TreeNode> children = new ArrayList<>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
