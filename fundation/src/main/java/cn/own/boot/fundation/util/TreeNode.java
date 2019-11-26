package cn.own.boot.fundation.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TreeNode<T> {

    /**
     *
     */
	private String id;
    private String parentId;
    private T parent;
    private List<T> children;
    private int level;
    private String rootId;
    private boolean leaf;

    public TreeNode(){}
    public TreeNode(String id, String parentId){
        this.setId(id);
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    @JsonIgnore
    public T getParent() {
        return parent;
    }
    public void setParent(T parent) {
        this.parent = parent;
    }
    public List<T> getChildren() {
        return children;
    }
    public void setChildren(List<T> children) {
        this.children = children;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getRootId() {
        return rootId;
    }
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }
    public boolean isLeaf() {
        return leaf;
    }
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
