package net.easipay.cbp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.easipay.cbp.model.ResourceInfo;

public class TreeHelper {
	public TreeHelper() {

	}

	public ResourceInfo execFunction(List<ResourceInfo> nodeList, String root) {
		HashMap tempNodeMap = putNodesIntoMap(nodeList);
		HashMap tempParentNodeMap = putParentNodesIntoMap(nodeList);
		HashMap nodeMap = putChildIntoParent(tempNodeMap, tempParentNodeMap);

		ResourceInfo treeNode = (ResourceInfo) nodeMap.get(root);

		return treeNode;
	}
	@SuppressWarnings("rawtypes")
	public HashMap putNodesIntoMap(List<ResourceInfo> nodeList) {

		HashMap nodeMap = new HashMap<String, ResourceInfo>();
		Iterator it = nodeList.iterator();
		while (it.hasNext()) {
			ResourceInfo treeNode = (ResourceInfo) it.next();
			Long selfId = treeNode.getResourceId();
			String keyId = String.valueOf(selfId);
			nodeMap.put(keyId, treeNode);
		}
		return nodeMap;
	}

	@SuppressWarnings("rawtypes")
	public HashMap putParentNodesIntoMap(List<ResourceInfo> nodeList) {
		HashMap parentNodeMap = new HashMap<String, ResourceInfo>();
		Iterator it = nodeList.iterator();
		while (it.hasNext()) {
			ResourceInfo treeNode = (ResourceInfo) it.next();
			Long id = treeNode.getParentId();
			String keyId = String.valueOf(id);
			parentNodeMap.put(keyId, treeNode);
		}
		return parentNodeMap;
	}

	@SuppressWarnings("rawtypes")
	public HashMap putChildIntoParent(HashMap nodeMap, HashMap parentNodeMap) {
		List<ResourceInfo> tempNodeList = new ArrayList<ResourceInfo>();

		Iterator it = nodeMap.values().iterator();
		while (it.hasNext()) {
			ResourceInfo treeNode = (ResourceInfo) it.next();

			Long parentId = treeNode.getParentId();
			Long selfId = treeNode.getResourceId();

			String parentKeyId = String.valueOf(parentId);
			String selfKeyId = String.valueOf(selfId);

			if (!parentNodeMap.containsKey(selfKeyId)) {
				if (nodeMap.containsKey(parentKeyId)) {
					ResourceInfo parentNode = (ResourceInfo) nodeMap
							.get(parentKeyId);
					it.remove();
					parentNode.add(treeNode);
				}
			}
		}

		Iterator tempit = nodeMap.values().iterator();
		while (tempit.hasNext()) {
			ResourceInfo treeNode = (ResourceInfo) tempit.next();
			tempNodeList.add(treeNode);
		}
		HashMap tempNodeMap = putNodesIntoMap(tempNodeList);
		HashMap tempParentNodeMap = putParentNodesIntoMap(tempNodeList);
		if (tempParentNodeMap.size() > 1) {
			putChildIntoParent(tempNodeMap, tempParentNodeMap);
		} else {
			return nodeMap;
		}

		return nodeMap;
	}

}
