package ansteph.cct.healthscreener.entity;

import java.io.Serializable;

public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7184211525538270088L;


	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public Category(String id,String name, String descrip) {
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

	/* the parcel part
	 public Category (Parcel in)
	 {
		 String[] data = new String [3];
		 in.readStringArray(data);
		 this.id = data[0];
		 this.name = data[1];
		 this.descrip = data[2];
	 }
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		dest.writeStringArray(new String[] {this.id, this.name, this.descrip});
	}*/
}
