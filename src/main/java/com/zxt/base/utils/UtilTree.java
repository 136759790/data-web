package com.zxt.base.utils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.util.StringUtils;

import com.zxt.base.enums.TreeSwitchEnum;
import com.zxt.base.result.Tree;

public class UtilTree {
	 /**
	    * @Title: getfatherNode
	    * @Description 方法描述: 父节点
	    * @param 设定文件： @param Tree
	    * @param 设定文件： @return    
	    * @return 返回类型：List<JsonTreeData>    
	    * @throws
	    */
	    public final static List<? extends Tree> getfatherNode(List<? extends Tree> tree) {
	        List<Tree> newTreeDataList = new ArrayList<Tree>();
	        for (Tree t : tree) {
	            if(StringUtils.isEmpty(t.getPid())) {
	                //获取父节点下的子节点
	                t.setChildren(getChildrenNode(t.getId(),tree));
	                t.setState("open");
	                newTreeDataList.add(t);
	            }
	        }
	        return newTreeDataList;
	    }
	    public final static Tree oneTree(List<Tree> tree) {
	    	List<Tree> newTreeDataList = new ArrayList<Tree>();
	    	for (Tree t : tree) {
	    		if(StringUtils.isEmpty(t.getPid())) {
	    			//获取父节点下的子节点
	    			t.setChildren(getChildrenNode(t.getId(),tree));
	    			t.setState("open");
	    			newTreeDataList.add(t);
	    		}
	    	}
	    	if(newTreeDataList == null || newTreeDataList.size() == 0){
	    		throw new RuntimeException("没有获取导数据");
	    	}
	    	return newTreeDataList.get(0);
	    }
	     
	    /**
	    * @Title: getChildrenNode
	    * @Description 方法描述: 子节点
	    * @param 设定文件： @param pid
	    * @param 设定文件： @return    
	    * @return 返回类型：List<JsonTreeData>    
	    * @throws
	    */
	    private final static List<Tree> getChildrenNode(String pid , List<? extends Tree> treeDataList) {
	        List<Tree> newTreeDataList = new ArrayList<Tree>();
	        for (Tree jsonTreeData : treeDataList) {
	            if(jsonTreeData.getPid() == null)  continue;
	            //这是一个子节点
	            if(jsonTreeData.getPid().equals(pid)){
	                //递归获取子节点下的子节点
	                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId() , treeDataList));
	                newTreeDataList.add(jsonTreeData);
	            }
	        }
	        return newTreeDataList;
	    }
	    /**
	     * 将普通数据转为tree结构，需在对象上加上tree注解
	     * @see com.zxt.base.annotation.Tree
	     */
	    public static List<? extends Object> switchTree(List<? extends Object> list ){
	    	List<Tree> list_tree=new ArrayList<Tree>();
	    	if(list == null || list.size() == 0){
	    		return list_tree;
	    	}
	    	
	    	Field field_id=null;
	    	Field field_pid=null;
	    	Field field_text=null;
	    	
	    	Object object=list.get(0);
	    	Class clazz=object.getClass();
	    	Field[] fields=clazz.getDeclaredFields();
	    	for (Field f : fields) {
	    		if(f.isAnnotationPresent(com.zxt.base.annotation.Tree.class)){
	    			com.zxt.base.annotation.Tree anno=f.getAnnotation(com.zxt.base.annotation.Tree.class);
	    			switch (anno.value()) {
					case ID:
						field_id=f;
						field_id.setAccessible(true);
						break;
					case PID:
						field_pid=f;
						field_pid.setAccessible(true);
						break;
					case TEXT:
						field_text=f;
						field_text.setAccessible(true);
						break;
					default:
						throw new RuntimeException("树结构转换异常，请查看该注解类型是否正确");
					}
	    		}
	    	}
	    	if(field_id ==null|| field_text == null){
	    		throw new RuntimeException("树结构转换异常，缺少必要的注解内容");
	    	}
	    	try {
	    		for (Object obj : list) {
	    			Tree tree=new Tree();
	    			tree.setId(field_id.get(obj).toString());
	    			tree.setPid(field_pid.get(obj).toString());
	    			tree.setText(field_text.get(obj).toString());
	    			Map attributes=new HashMap();
	    			for (Field fi : obj.getClass().getDeclaredFields()) {
						fi.setAccessible(true);
						attributes.put(fi.getName(), fi.get(obj));
					}
	    			tree.setAttributes(attributes);
	    			list_tree.add(tree);
	    		}
			} catch (Exception e) {
				throw new RuntimeException("树结构转换异常，转换异常"+e.getMessage());
			}
	    	return list_tree;
	    }
	    /**
	     * 将普通数据转为tree结构，需在对象上加上tree注解
	     * @see com.zxt.base.annotation.Tree
	     */
	    public static List<? extends Object> switchTree(List<? extends Object> list ,Class<?> tar_class){
	    	List<Tree> list_tree=new ArrayList<Tree>();
	    	if(list == null || list.size() == 0){
	    		return list_tree;
	    	}
	    	
	    	Field field_id=null;
	    	Field field_pid=null;
	    	Field field_text=null;
	    	Field field_url=null;
	    	
	    	Object object=list.get(0);
	    	Class clazz=object.getClass();
	    	Field[] fields=clazz.getDeclaredFields();
	    	for (Field f : fields) {
	    		if(f.isAnnotationPresent(com.zxt.base.annotation.Tree.class)){
	    			com.zxt.base.annotation.Tree anno=f.getAnnotation(com.zxt.base.annotation.Tree.class);
	    			switch (anno.value()) {
	    			case ID:
	    				field_id=f;
	    				field_id.setAccessible(true);
	    				break;
	    			case PID:
	    				field_pid=f;
	    				field_pid.setAccessible(true);
	    				break;
	    			case TEXT:
	    				field_text=f;
	    				field_text.setAccessible(true);
	    				break;
	    			default:
	    				throw new RuntimeException("树结构转换异常，请查看该注解类型是否正确");
	    			}
	    		}
	    	}
	    	if(field_id ==null|| field_text == null){
	    		throw new RuntimeException("树结构转换异常，缺少必要的注解内容");
	    	}
	    	try {
	    		for (Object obj : list) {
	    			Tree tree=new Tree();
	    			tree.setId(field_id.get(obj).toString());
	    			tree.setPid(field_pid.get(obj).toString());
	    			tree.setText(field_text.get(obj).toString());
	    			list_tree.add(tree);
	    		}
	    	} catch (Exception e) {
	    		throw new RuntimeException("树结构转换异常，转换异常"+e.getMessage());
	    	}
	    	return list_tree;
	    }
	    
	    
}
