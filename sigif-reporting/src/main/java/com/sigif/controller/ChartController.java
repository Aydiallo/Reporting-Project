package com.sigif.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.sigif.modele.DemandeAchat;
import com.sigif.service.DemandeAchatService;
import com.sigif.views.CalendarView;

@ManagedBean(name="chartController")
@SessionScoped
public class ChartController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2930838327986792875L;
    private PieChartModel pieModel;
    private CartesianChartModel categoryModel ;
    @ManagedProperty(value="#{demandeAchatService}")
	DemandeAchatService demandeAchatService;
    
    @ManagedProperty(value="#{controllerBean}")
   	ControllerBean controllerBean;
    
    
	public ControllerBean getControllerBean() {
		return controllerBean;
	}
	public void setControllerBean(ControllerBean controllerBean) {
		this.controllerBean = controllerBean;
	}
	public PieChartModel getPieModel() {
		return pieModel;
	}
	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}
	public void setCategoryModel(CartesianChartModel categoryModel) {
		this.categoryModel = categoryModel;
	}
	public DemandeAchatService getDemandeAchatService() {
		return demandeAchatService;
	}
	public void setDemandeAchatService(DemandeAchatService demandeAchatService) {
		this.demandeAchatService = demandeAchatService;
	}
	/** create a new instance of ChartController **/
    public ChartController()
    {
    	this.pieModel = new PieChartModel();
    	this.pieModel.set("", 0);
    	this.categoryModel = new CartesianChartModel() ;
    	ChartSeries fakultas = new ChartSeries() ;
    	fakultas.set("", 0);
    	this.categoryModel.addSeries(fakultas);
    }
    public void chartJumlahMahasiswaPerFakultas()
    {
    	this.pieModel = new PieChartModel();
    	this.categoryModel = new CartesianChartModel();
    	ChartSeries fakultas = new ChartSeries() ;
    	fakultas.setLabel("Fakultas");
    	List<Object[]> pieList = this.controllerBean.diagrammes();
    			if(pieList != null)
    	{
    		for(Object[] pie : pieList)
    		{
    			String mois = this.moiss((int)pie[0]) ;
    			String datemy = mois+"-"+((int)pie[1]) ;
    			this.pieModel.set(datemy, (int)pie[2]);
    			fakultas.set(datemy, (int)pie[2]);
    		}
    		this.categoryModel.addSeries(fakultas);
    	}
    }
    
    private String moiss(final int n)
    {
    	switch (n) {
		case 1: return "janvier" ;
		case 2: return "fevrier" ;
		case 3: return "mars" ;
		case 4: return "avril" ;
		case 5: return "mai" ;
		case 6: return "juin" ;
		case 7: return "juillet" ;
		case 8: return "aout" ;
		case 9: return "septembre" ;
		case 10: return "octobre" ;
		case 11: return "novembre" ;
		case 12: return "decembre" ;
		default:
			break;
		}
		return null;
    }
    
}
