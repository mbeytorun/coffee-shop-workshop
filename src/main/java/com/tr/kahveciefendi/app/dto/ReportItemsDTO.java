package com.tr.kahveciefendi.app.dto;

import java.util.List;

public class ReportItemsDTO {
	
	private List<ReportItemDTO> reportItems;
	
	public ReportItemsDTO(){
		
	}
	
	public ReportItemsDTO(List<ReportItemDTO> reportItems){
		this.reportItems = reportItems;
	}

	public List<ReportItemDTO> getReportItems() {
		return reportItems;
	}

	public void setReportItems(List<ReportItemDTO> reportItems) {
		this.reportItems = reportItems;
	}

}
