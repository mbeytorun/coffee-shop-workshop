package com.tr.kahveciefendi.app.dto;

import java.util.ArrayList;
import java.util.List;

public class ReportItemDTO {
	
	private List<String> fields;
	
	public ReportItemDTO(){
		this.fields = new ArrayList<String>();
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public void addToFields(String field){
		this.fields.add(field);
	}

}
