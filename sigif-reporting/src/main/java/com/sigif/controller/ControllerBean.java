package com.sigif.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import com.sigif.exception.ApplicationException;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.DemandeAchat;
import com.sigif.modele.Ministere;
import com.sigif.modele.Programme;
import com.sigif.service.DemandeAchatService;
import com.sigif.service.MinistereService;
import com.sigif.service.ProgrammeService;
import com.sigif.service.ServiceDepensierService;
import com.sigif.views.CalendarView;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.HtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;




@ManagedBean(name="controllerBean")
@SessionScoped
public class ControllerBean  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{demandeAchatService}")
	DemandeAchatService demandeAchatService;
	
	@ManagedProperty(value="#{ministereService}")
	MinistereService ministereService;
	
	@ManagedProperty(value="#{programmeService}")
	ProgrammeService programmeService;
	
	@ManagedProperty(value="#{serviceDepensierService}")
	ServiceDepensierService serviceDepensierService;
	
	@ManagedProperty(value="#{calendarView}")
    CalendarView calendarView ;
    
    
    
    public CalendarView getCalendarView() {
		return calendarView;
	}
	public void setCalendarView(CalendarView calendarView) {
		this.calendarView = calendarView;
	}
	
	private Ministere ministere;
	private Programme programme;
	private ServiceDepensierService serviceDepensier;
	
	
    
	public String getCodeMinistere() {
		if(!this.selectMinistere.isEmpty())
		{
			int n = this.listeMinistereDesignation.indexOf(this.selectMinistere) ;
			if(n>=0)
				return this.listeMinistereCode.get(n) ;
 		}
		return codeMinistere;
	}

	public void setCodeMinistere(String codeMinistere) {
		this.codeMinistere = codeMinistere;
	}

	public String getCodeServiceDepensier() {
		if(!this.selectServiceDepensier.isEmpty())
		{
			int n = this.listeServiceDepensierDescription.indexOf(this.selectServiceDepensier) ;
			if(n>=0)
				return this.listeServiceDepensierCode.get(n) ;
 		}
		return codeServiceDepensier;
	}

	public void setCodeServiceDepensier(String codeServiceDepensier) {
		this.codeServiceDepensier = codeServiceDepensier;
	}

	public String getCodeProgramme() {
		if(!this.selectProgramme.isEmpty())
		{
			int n = this.listeProgrammeDescription.indexOf(this.selectProgramme) ;
			if(n>=0)
				return this.listeProgrammeCode.get(n) ;
 		}
		return codeProgramme;
	}

	public void setCodeProgramme(String codeProgramme) {
		this.codeProgramme = codeProgramme;
	}
	private List<String> listeMinistereDesignation = new ArrayList<String>() ;
	List<String> listeMinistereCode = new ArrayList<String>() ;
	List<String> listeProgrammeDescription= new ArrayList<String>() ;
	List<String> listeProgrammeCode = new ArrayList<String>() ;
	List<String> listeServiceDepensierDescription= new ArrayList<String>() ;
	List<String> listeServiceDepensierCode = new ArrayList<String>() ;
	private String codeMinistere = "" ; 
	private String selectMinistere="";
	private String selectProgramme="";
	private String selectServiceDepensier="";
	private String codeServiceDepensier ="" ;
	private String codeProgramme ="" ;
	
	String user = "";
	String roleU = "" ;
	
	
	
	
	
	public String getSelectProgramme() {
		return selectProgramme;
	}

	public void setSelectProgramme(String selectProgramme) {
		this.selectProgramme = selectProgramme;
	}

	public String getSelectServiceDepensier() {
		return selectServiceDepensier;
	}

	public void setSelectServiceDepensier(String selectServiceDepensier) {
		this.selectServiceDepensier = selectServiceDepensier;
	}

	public String getSelectMinistere() {
		return selectMinistere;
	}

	public void setSelectMinistere(String selectMinistere) {
		this.selectMinistere = selectMinistere;
	}

	public Programme getProgramme() {
		return programme;
	}

	public void setProgramme(Programme programme) {
		this.programme = programme;
	}

	public ServiceDepensierService getServiceDepensier() {
		return serviceDepensier;
	}

	public void setServiceDepensier(ServiceDepensierService serviceDepensier) {
		this.serviceDepensier = serviceDepensier;
	}

	public List<String> getListeProgrammeDescription() {
		if(this.selectMinistere==null || this.selectMinistere.isEmpty()) return null;
		return this.listeProgramme();
	}

	public void setListeProgrammeDescription(List<String> listeProgrammeDescription) {
		this.listeProgrammeDescription = listeProgrammeDescription;
	}

	public List<String> getListeServiceDepensierDescription() {
		if(this.selectMinistere==null || this.selectMinistere.isEmpty()) return null;
		return this.listeServiceDepensier();
	}

	public void setListeServiceDepensierDescription(List<String> listeServiceDepensierDescription) {
		this.listeServiceDepensierDescription = listeServiceDepensierDescription;
	}

	public List<String> getListeMinistereDesignation() {
		
		return this.listeMinistere();
	}

	public void setListeMinistereDesignation(List<String> listeMinistereDesignation) {
		this.listeMinistereDesignation = listeMinistereDesignation;
	}

	public Ministere getMinistere() {
		return ministere;
	}

	public void setMinistere(Ministere ministere) {
		this.ministere = ministere;
	}

	public DemandeAchatService getDemandeAchatService() {
		return demandeAchatService;
	}

	public void setDemandeAchatService(DemandeAchatService demandeAchatService) {
		this.demandeAchatService = demandeAchatService;
	}
    
	
   public MinistereService getMinistereService() {
		return ministereService;
	}

	public void setMinistereService(MinistereService ministereService) {
		this.ministereService = ministereService;
	}
	
	

public ProgrammeService getProgrammeService() {
		return programmeService;
	}

	public void setProgrammeService(ProgrammeService programmeService) {
		this.programmeService = programmeService;
	}

	
	
	public ServiceDepensierService getServiceDepensierService() {
		return serviceDepensierService;
	}

	public void setServiceDepensierService(ServiceDepensierService serviceDepensierService) {
		this.serviceDepensierService = serviceDepensierService;
	}

	public void pdf()
	{

		System.out.println("***** void pdf() ***");
		List<DemandeAchat> liste = demandeAchatService.listDemandeAchats();
		System.out.println("***** return pdf() ***");
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(liste,
				false);
		HashMap parameters = new HashMap();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext
					.getExternalContext().getContext();
System.out.println("******************"+scontext.getRealPath("/resources/reports/Coffee.jasper")+"*********");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					scontext.getRealPath("/resources/reports/Coffee.jasper"), parameters, ds);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			// Creation of the HTML Jasper Reports
	          JasperExportManager.exportReportToHtmlFile(jasperPrint, scontext.getRealPath("/resources/reports/")+"/essai.html");

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			byte[] bytes = baos.toByteArray();
			if (bytes != null && bytes.length > 0) {
				HttpServletResponse response = (HttpServletResponse) facesContext
						.getExternalContext().getResponse();
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition",
						"attachement; filename=coffee.pdf");
				response.setContentLength(bytes.length);
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(bytes, 0, bytes.length);
				outputStream.flush();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
     
	public String export(final JasperPrint print) throws JRException
	{
		final  Exporter exporter;
		final ByteArrayOutputStream out = new ByteArrayOutputStream() ;
		
		
			exporter = new HtmlExporter() ;
		
		
		 try {
			exporter.setConfiguration(createHtmlConfiguration());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    exporter.setExporterOutput(new SimpleHtmlExporterOutput("employeeReport.xhtml"));
		    exporter.setExporterInput(new SimpleExporterInput(print));
		    exporter.exportReport();

		    return "employeeReport.xhtml";
		
	}
	
	public String html()
	{
		System.out.println("***** void pdf() ***");
		List<DemandeAchat> liste = demandeAchatService.listDemandeAchats();
		System.out.println("***** return pdf() ***");
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(liste,
				false);
		HashMap parameters = new HashMap();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext
					.getExternalContext().getContext();
System.out.println("******************"+scontext.getRealPath("/resources/reports/Coffee.jasper")+"*********");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					scontext.getRealPath("/resources/reports/Coffee.jasper"), parameters, ds);
			String report = this.export(jasperPrint) ;
			return report ;
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
}
	private String getHtmlHeader() {
	    StringBuffer sb = new StringBuffer();
	    sb.append("<html>");
	    sb.append("<head>");
	    sb.append("  <title></title>");
	    sb.append("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
	    sb.append("  <style type=\"text/css\">");
	    sb.append("    a {text-decoration: none}");
	    sb.append("  </style>");
	    sb.append("</head>");
	    sb.append("<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">");
	    

	    return sb.toString();
	}

	private String getHtmlFooter() {
		StringBuffer sb = new StringBuffer();
	    sb.append("</body>");
	    sb.append("</html>");
	    
	    return sb.toString();
	}
	
	private HtmlExporterConfiguration createHtmlConfiguration()
	        throws IOException {
	    SimpleHtmlExporterConfiguration shec
	            = new SimpleHtmlExporterConfiguration();

	    shec.setHtmlHeader(getHtmlHeader());
	    shec.setHtmlFooter(getHtmlFooter());

	    return shec;
	}
	
	 public List<String> getCurrentUser()
	 {
		 HttpServletRequest httpServletRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 HttpSession httpSession = httpServletRequest.getSession() ;
		 SecurityContext securityContext = (SecurityContext) 
				 httpSession.getAttribute("SPRING_SECURITY_CONTEXT") ;
		 String username = securityContext.getAuthentication().getName();
		 List<String> liste = new ArrayList<>() ;
		 liste.add(username) ;
		 for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities())
		 {
			 //roles.add(ga.getAuthority().substring(ga.getAuthority().indexOf('_')+1)) ;
			 liste.add(ga.getAuthority().substring(ga.getAuthority().indexOf('_')+1)) ;
		 }
		 //Map<String,Object> params = new HashMap<>() ;
		///.put("username", username) ;
		// params.put("roles", roles) ;
		return liste;
		 
	 }
	 public List<String> listeMinistere()
	 {
		 List<String> liste = this.getCurrentUser();
		 this.user = liste.get(0);
		 this.roleU = liste.get(1) ;
		 
		 Map<String, String> listef = null;
		 listeMinistereCode.clear() ;
			listeMinistereDesignation.clear() ;
		 
		try {
			listef = ministereService.getAllMinisteryOfUserByLoginAndRole(user, roleU, "actif");
			
			Iterator iterator = listef.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				System.out.println("*****key="+key+"*********value="+value);
				listeMinistereCode.add(key) ;
				listeMinistereDesignation.add(value) ;
			}

		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return  listeMinistereDesignation;
	 }
	 
	 public void majProgrammeListe()
	 {
		 System.out.println("********************* majProgrammeListe() ************");
//		  String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("desm");
		 Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String initialValue =String.valueOf(params.get("descriptionm"));
		  System.out.println("*********************initialValue+"+initialValue+"************");
		  if(!initialValue.isEmpty())
		   this.codeMinistere=this.listeMinistereCode.get(this.listeMinistereDesignation.indexOf(initialValue)) ;
		  else
		  {
			  this.listeProgrammeCode.clear();
			  this.listeProgrammeDescription.clear();
			  this.listeServiceDepensierCode.clear();
			  this.listeServiceDepensierDescription.clear();
			  this.codeMinistere = "" ;
		  }
	 }
	 
	 public void mcodeServiceDepensier()
	 {
//		  String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("desm");
		 Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String initialValue =String.valueOf(params.get("descriptionsd"));
		  if(!initialValue.isEmpty())
		  {
			  int n = this.listeServiceDepensierDescription.indexOf(initialValue);
			  if(n>0)
		          this.codeServiceDepensier=this.listeServiceDepensierCode.get(n) ;
		  }
		  else this.codeServiceDepensier="" ;
		  System.out.println("*******code service depensier *** "+this.codeServiceDepensier+" ************************");

	 }
	 
	 public void mcodeProgramme()
	 {
//		  String initialValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("desm");
		 Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		 String initialValue =String.valueOf(params.get("descriptionp"));
		 
		  if(!initialValue.isEmpty())
		  {
			  int n = this.listeProgrammeDescription.indexOf(initialValue);
			  if(n>0)
		          this.codeProgramme=this.listeProgrammeCode.get(n) ;
		  }
		  else this.codeProgramme="" ;
		  System.out.println("*******code programme *** "+this.codeProgramme+" ************************");
	 }
	 
	 
	 public List<String> listeProgramme()
	 {
		
		 Map<String, String> listef = null;
		 listeProgrammeCode.clear() ;
			listeProgrammeDescription.clear() ;
		 if(!this.selectMinistere.isEmpty())	
			this.codeMinistere=this.listeMinistereCode.get(this.listeMinistereDesignation.indexOf(this.selectMinistere)) ;
			if(this.codeMinistere.isEmpty()) 
			{
				List<String> l = new ArrayList<String>() ;
				l.add("");
				System.out.println("*******************taille liste programme = 0 selctMinistere = "+ this.selectMinistere+" ***************");
				return l;
			}
		try {
			
			 listef = programmeService.getAllProgrammOfUserByLoginRoleAndMinistery(user, this.codeMinistere, roleU);
			
			Iterator iterator = listef.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				listeProgrammeCode.add(key) ;
				listeProgrammeDescription.add(value) ;
			}

		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("*******************taille liste programme = "+this.listeProgrammeDescription.size()+"***************");
		return  listeProgrammeDescription;
	 }
	 
	 public List<String> listeServiceDepensier()
	 {
		 
		 Map<String, String> listef = null;
		 this.listeServiceDepensierDescription.clear() ;
			this.listeServiceDepensierCode.clear() ;
			if(this.codeMinistere.isEmpty()) 
			{
				System.out.println("*******************taille liste service depensier = 0 ***************");
				
				return null;
			}
		try {
			listef = this.serviceDepensierService.getAllSpendingServicesOfUserByLoginRoleAndMinistery(user, this.codeMinistere, roleU, "actif");
			
			Iterator iterator = listef.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				this.listeServiceDepensierCode.add(key) ;
				this.listeServiceDepensierDescription.add(value) ;
			}

		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("*******************taille liste service depensier = "+this.listeServiceDepensierDescription.size()+"***************");
		return  listeServiceDepensierDescription;
	 }
	 
	 public void onSelectMinistere(SelectEvent event)
	 {
		 this.initialisation();
		 this.selectMinistere = (String) event.getObject();
		 

	 }
	 public void initialisation()
	 {
		 this.listeProgrammeCode.clear();
		 this.listeProgrammeDescription.clear();
		 this.listeServiceDepensierCode.clear();
		 this.listeServiceDepensierDescription.clear() ;
		 this.selectMinistere="" ;
	 }
	 public List<Object[]> diagrammes()
	 {
		 return this.demandeAchatService.listeDAforPSdM(this.getCodeProgramme(), 
				 this.getCodeServiceDepensier(), this.getCodeMinistere(), this.calendarView.getDateFrom(), this.calendarView.getDateTo()) ;
	 }
	}