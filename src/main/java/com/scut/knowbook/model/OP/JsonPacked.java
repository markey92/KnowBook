package com.scut.knowbook.model.OP;

import java.util.ArrayList;

public class JsonPacked {

	
	private String result;
	
	private ArrayList<Object> resultSet=new ArrayList<Object>();
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ArrayList<Object> getResultSet() {
		return resultSet;
	}
	public void setResultSet(ArrayList<Object> resultSet) {
		this.resultSet = resultSet;
	}
	
}
