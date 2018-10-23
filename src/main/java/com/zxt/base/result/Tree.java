package com.zxt.base.result;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class Tree {
	private String id;       //json id
	@JsonIgnore
    private String pid;      //
    private String text;     //json 显示文本
    @JsonInclude(Include.NON_EMPTY)
    private String state = "open";    //json 'open','closed'
    @JsonInclude(Include.NON_EMPTY)
    private List<Tree> children;       //
    
    private Map attributes;
    
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public Tree(String id, String pid, String text, String state,
			List<Tree> children) {
		super();
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.state = state;
		this.children = children;
	}
	public Tree() {
		super();
	}
	public Map getAttributes() {
		return attributes;
	}
	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}
	@Override
	public String toString() {
		return "Tree [id=" + id + ", pid=" + pid + ", text=" + text
				+ ", state=" + state + ", children=" + children
				+ ", attributes=" + attributes + "]";
	}
    
	
	
	
	
    
}
