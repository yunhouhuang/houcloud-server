package com.houcloud.example.model.dto;

import com.houcloud.example.model.entity.Menu;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构构建工具
 *
 * @author Houcloud
 */
@Slf4j
@UtilityClass
public class TreeUtil {
    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {

        List<T> trees = new ArrayList<>();

        for (T treeNode : treeNodes) {

            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 通过sysMenu创建树形节点
     *
     * @param menus
     * @param root
     * @return
     */
    public List<MenuTree> buildTree(List<Menu> menus, long root) {
        List<MenuTree> trees = new ArrayList<>();
        MenuTree node;
        for (Menu menu : menus) {
            node = new MenuTree();
            node.setId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setPath(menu.getPath());
            node.setType(menu.getType());
            node.setPermission(menu.getPermission());
            node.setTitle(menu.getTitle());
            node.setIcon(menu.getIcon());
            node.setWeight(menu.getWeight());
            node.setHidden(menu.getHidden());
            node.setComponent(menu.getComponent());
            node.setRedirect(menu.getRedirect());
            node.setSingle(menu.getSingle());
            node.setPermission(menu.getPermission());
            node.setWhite(menu.getWhite());
            node.setKeepAlive(menu.getKeepAlive());
            node.setValue(menu.getId());
            node.setLabel(menu.getTitle());
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }
}
