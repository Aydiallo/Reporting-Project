package com.sigif.converter;


import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.sigif.dto.MinistereDTO;
import com.sigif.service.MinistereService;

@FacesConverter("ministereCoverter")
public class MinistereConverter   implements Converter {
	@ManagedProperty(value = "#{MinistereService}")
	MinistereService ministereService;

	
	
	public MinistereService getMinistereService() {
		return ministereService;
	}

	public void setMinistereService(MinistereService ministereService) {
		this.ministereService = ministereService;
	}

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		//Cas p = getCasService().getCasById(new Long(value));
		MinistereDTO m = ministereService.getById(Integer.parseInt(value));
		return m;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object value) {
		String res = "";
		MinistereDTO p = (MinistereDTO) value;
		if (p != null && p.getCode() != null) {
			res = p.getCode().toString();
		}
		return res;

	}

}