package ansteph.cct.healthscreener.entity;

import java.util.Date;



public class School {

	
	
	private String id;
	private String schoolname;
	private String principalname;
	private String principalContact;
	private String personName;
	private String personContact;
	private String addline1 ;
	private String addline2;
	private String gpsCoordinate;
	
	
	public School() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public School(String id,String scname,String prinname,String prinContact,String personname, String personcont,
			String addline1 , String addline2, String gps) {
		// TODO Auto-generated constructor stub
		this.id= id; this.schoolname= scname; this.principalname= prinname ; this.principalContact = prinContact; 
		this.personName= personname;
		this.personContact=personcont;
		this.addline1= addline1;
		this.addline2= addline2;
		this.gpsCoordinate= gps; 
	}
	
	
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	
	public String getSchoolName(){
		return schoolname;
			}
	public void setSchoolName(String scname)
	{
		this.schoolname= scname;
	}
	
	
	public String getPrincipalName(){
		return principalname;
			}
	public void setPrincipalName(String pn)
	{
		this.principalname= pn;
	}
	
	public String getPrincipalContact(){
		return principalContact;
			}
	
	public void setPrincipalContact(String pCn)
	{
		this.principalContact= pCn;
	}
	
	public String getPersonName(){
		return personName;
			}
	public void setPersonName(String persN)
	{
		this.personName= persN;
	}
	
	public String getPersonContact(){
		return personContact;
			}
	
	public void setPersonContact(String persC)
	{
		this.personContact= persC;
	}
	
	
	
	public String getAddline1(){
		return addline1;
			}
	public void setAddline1(String add)
	{
		this.addline1= add;
	}
	
	public String getAddline2(){
		return addline2;
			}
	public void setAddline2(String add)
	{
		this.addline2= add;
	}
	
	
	public String getGPSCoordinate(){
		return gpsCoordinate;
			}
	
	public void setGPSCoordinate(String gps)
	{
		this.gpsCoordinate= gps;
	}
	
	

}
