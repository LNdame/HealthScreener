package ansteph.cct.healthscreener.entity;

public class OpCategory {

	public OpCategory() {
		// TODO Auto-generated constructor stub
	}
	
	public OpCategory(String id,String name, String descrip) {
		// TODO Auto-generated constructor stub
		this.id= id; 
		this.name=name;
		this.descrip= descrip;
	}
	private String id;
	private String name;
	private String descrip;
	
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	public String getName(){
		return name;
			}
	public void setName(String n)
	{
		this.name= n;
	}
	
	public String getDescrip(){
		return descrip;
			}
	public void setDescrip(String de)
	{
		this.descrip= de;
	}

}
