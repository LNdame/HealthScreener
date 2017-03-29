package ansteph.cct.healthscreener.entity;

import java.util.Date;

public class Referral {

	
	private String id;
	private boolean hasReferral;
	private String referee;
	private String catID;
	private String pupilId;
	private Date dateOfReferal;
	private Date dateOfFollowUp;
	public Referral() {
		// TODO Auto-generated constructor stub
	}

	public Referral(String id , boolean hasref, String ref, Date dref, Date dfollow,String pupId, String catid) {
		// TODO Auto-generated constructor stub
	this.id= id;
		this.hasReferral=hasref;
		this. referee=ref;
		this.catID=catid;
		this.pupilId=pupId;
		this.dateOfReferal=dref;
		this.dateOfFollowUp= dfollow;
	}

	public Referral(  boolean hasref, String ref, Date dref, Date dfollow,String pupId, String catid) {
		// TODO Auto-generated constructor stub
	
		this.hasReferral=hasref;
		this. referee=ref;
		this.catID=catid;
		this.pupilId=pupId;
		this.dateOfReferal=dref;
		this.dateOfFollowUp= dfollow;
	}
	
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	
	public boolean getHasReferral(){
		return hasReferral;
			}
	
	public void setHasReferral(boolean hr)
	{
		this.hasReferral= hr;
	}
	
	public String getReferee(){
		return referee;
			}
	public void setReferee(String n)
	{
		this.referee= n;
	}
	
	public Date getDateOfReferal(){
		return dateOfReferal;
			}
	
	public void setDateOfReferal(Date d)
	{
		this.dateOfReferal= d;
	}
	
	
	public Date getDateOfFollowUp(){
		return dateOfFollowUp;
			}
	
	public void setDateOfFollowUp(Date d)
	{
		this.dateOfFollowUp= d;
	}
	
	public String getCatID(){
		return catID;
			}
	public void setCatID(String de)
	{
		this.catID= de;
	}
	
	public String getPupilId(){
		return pupilId;
			}
	public void setPupilId(String id)
	{
		this.pupilId= id;
	}
	
}
