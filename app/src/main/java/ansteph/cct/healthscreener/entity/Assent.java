package ansteph.cct.healthscreener.entity;

import java.util.Date;

public class Assent {

	
	
	private String id;
	private boolean hasConsent;
	private String pupilId;
	private String preExistingCondition;
	private Date assentDate;
	
	public Assent() {
		// TODO Auto-generated constructor stub
	}

	public Assent(String id, String pupID, String pre, Date assDate) {
		// TODO Auto-generated constructor stub
		this.id = id; this.pupilId= pupID; this.preExistingCondition = pre;
		this.assentDate = assDate;
	}
	
	public Assent( String pupID, String pre, Date assDate, boolean hsCon) {
		// TODO Auto-generated constructor stub
		this.pupilId= pupID; this.preExistingCondition = pre;
		this.assentDate = assDate; this.hasConsent=hsCon;
	}
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	
	public boolean getHasConsent(){
		return hasConsent;
			}
	
	public void setHasConsent(boolean hsCon)
	{
		this.hasConsent= hsCon;
	}
	
	
	public String getPupilId(){
		return pupilId;
			}
	public void setPupilId(String id)
	{
		this.pupilId= id;
	}
	
	
	public String getPreExistingCondition(){
		return preExistingCondition;
			}
	public void setPreExistingCondition(String pre)
	{
		this.preExistingCondition= pre;
	}
	
	
	
	public Date getAssentDate(){
		return assentDate;
			}
	
	public void setAssentDate(Date d)
	{
		this.assentDate= d;
	}
	
}
