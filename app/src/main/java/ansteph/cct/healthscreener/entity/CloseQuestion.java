package ansteph.cct.healthscreener.entity;

public class CloseQuestion {

	public CloseQuestion() {
		// TODO Auto-generated constructor stub
	}
	
	public CloseQuestion(String id,String name, String catid) {
		// TODO Auto-generated constructor stub
		this.id= id; this.questionDescr=name;
		this.catID= catid;
	}
	
	private String id;
	private String questionDescr;
	private String catID;
	
	
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	public String getDescr(){
		return questionDescr;
			}
	public void setDEscr(String n)
	{
		this.questionDescr= n;
	}
	
	
	
	public String getCatID(){
		return catID;
			}
	public void setCatID(String de)
	{
		this.catID= de;
	}
	
	
}
