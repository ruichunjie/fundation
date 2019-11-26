package cn.own.boot.fundation.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class TreeBuilder<T extends TreeNode<T>> {

	/**
	 * 将平级list根据id、parentid生成树型结构数据
	 * @param dirs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> buildListToTree(List<T> dirs) {
		List<T> roots = findRoots(dirs);
		List<T> notRoots = (List<T>)subtract(dirs, roots);
		for (T root : roots) {
			root.setChildren(findChildren(root, notRoots));
		}
		return roots;
	}

	/**
	 * 查找树的多个根节点
	 * @param allNodes
	 * @return
	 */
	private List<T> findRoots(List<T> allNodes) {
		List<T> results = new ArrayList<>();
		for (T node : allNodes) {
			boolean isRoot = true;
			for (T comparedOne : allNodes) {
				if (StringUtils.isNotBlank(node.getParentId()) && node.getParentId().equals(comparedOne.getId())) {
					isRoot = false;
					break;
				}
			}
			if (isRoot) {
				node.setLevel(1);
				results.add(node);
				node.setRootId(node.getId());
			}
		}
		return results;
	}

	/**
	 * 查询父节点的子节点
	 * @param root
	 * @param allNodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<T> findChildren(T root, List<T> allNodes) {
		List<T> children = new ArrayList<>();

		for (T comparedOne : allNodes) {
			if (StringUtils.isNotBlank(comparedOne.getParentId()) && comparedOne.getParentId().equals(root.getId())) {
				comparedOne.setParent(root);
				comparedOne.setLevel(root.getLevel() + 1);
				children.add(comparedOne);
			}
		}
		List<T> notChildren = (List<T>)subtract(allNodes, children);
		for (T child : children) {
			List<T> tmpChildren = findChildren(child, notChildren);
			if (tmpChildren == null || tmpChildren.isEmpty()) {
				child.setLeaf(true);
			} else {
				child.setLeaf(false);
			}
			child.setChildren(tmpChildren);
		}
		return children;
	}

	/**
	 * 返回多个树的所有叶子节点
	 * @param tree
	 * @return
	 */
	public List<T> getLeafChildren(List<T> tree) {
		List<T> resultList = new ArrayList<>();
		for (T node : tree) {
			if (node.isLeaf()) {
				node.setParent(null);
				node.setChildren(null);
				resultList.add(node);
			} else {
				if(node.getChildren() != null){
					resultList.addAll(getLeafChildren(node.getChildren()));
				}
			}
		}
		return resultList;
	}

	/**
	 * 获取最大层级数
	 * @param tree
	 * @return
	 */
	public int maxLevel(T tree){
		int levelTemp = 0;
		List<T> children = tree.getChildren();
		levelTemp = tree.getLevel();
		for(T t : children){
			// 取最大level为最大层级数
			levelTemp = levelTemp >= t.getLevel() ? levelTemp : t.getLevel();
			if(t.getChildren() != null && !t.getChildren().isEmpty()){
				int clildlevel = maxLevel(t);
				levelTemp = levelTemp >= clildlevel ? levelTemp : clildlevel;
			}
		}
		return levelTemp;
	}

	/**
	 * 树型结构数据拍平为list数据
	 * @param
	 * @param tree
	 * @return
	 */
	public List<T> treeToList(T tree){
		List<T> treeNodeList = new ArrayList<>();
		treeNodeList.add(tree);
		List<T> treeNodeListChild = tree.getChildren();
		if(null != treeNodeListChild){
			treeNodeListChild.forEach(t -> {
				// 循环增加每个子节点
				treeNodeList.addAll(treeToList(t));
			});
		}
		tree.setChildren(null);
		tree.setParent(null);

		return treeNodeList;
	}
	private List<T> subtract(List<T> allNodes, List<T> sonNodes){
        for (Iterator<T> it = allNodes.iterator(); it.hasNext();){
            T temp = it.next();
            for (int i = 0; i < sonNodes.size(); i++){
                if (temp.equals(sonNodes.get(i))){
                    it.remove();
                }
            }
        }
        return allNodes;
    }

}
