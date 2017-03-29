package ansteph.cct.healthscreener.entity;

import java.util.Date ;

public class Pupil {

	public Pupil() {
		// TODO Auto-generated constructor stub
	}

	
	private String id;
	private String firstname;
	private String surname;
	private String grade;
	private String gender;
	
	private String parentName; 
	private String parentContact; 
	private boolean hasRoadtoHealth; 
	
	private String addline1 ;
	private String addline2;
	private String school;
	private Date dob;
	
	
	public Pupil(String id,String firstname,String surname,String grade,String gender, String addline1 , String addline2, String sc, Date d) {
		// TODO Auto-generated constructor stub
		this.id= id; this.firstname= firstname; this.surname= surname ; this.grade = grade; 
		this.gender= gender;
		this.addline1= addline1;
		this.addline2= addline2;
		this.school= sc; this.dob=d;
	}
	
	
	public Pupil(String id,String firstname,String surname,String grade,String gender, String addline1 , String addline2, String sc, Date d, 
			String pn , String pc, boolean hrh ) {
		// TODO Auto-generated constructor stub
		this.id= id; this.firstname= firstname; this.surname= surname ; this.grade = grade; 
		this.gender= gender;
		this.addline1= addline1;
		this.addline2= addline2;
		this.school= sc; this.dob=d;
		this.parentName = pn; this.parentContact=pc; this.hasRoadtoHealth = hrh;
	}
	
	public Pupil(String id,String firstname,String surname,String grade,String gender, String addline1 , String addline2, String sc) {
		// TODO Auto-generated constructor stub
		this.id= id; this.firstname= firstname; this.surname= surname ; this.grade = grade; 
		this.gender= gender;
		this.addline1= addline1;
		this.addline2= addline2;
		this.school= sc; 
	}
	public Pupil(String firstname,String surname,String grade,String gender, String addline1 , String addline2) {
		// TODO Auto-generated constructor stub
		this.firstname= firstname; this.surname= surname ; this.grade = grade; this.gender= gender;
		this.addline1= addline1;
		this.addline2= addline2;
	}
	
	public String getId(){
		return id;
			}
	public void setId(String id)
	{
		this.id= id;
	}
	
	
	public String getFirstname(){
		return firstname;
			}
	public void setFirstname(String Firstname)
	{
		this.firstname= Firstname;
	}
	
	
	public String getSurname(){
		return surname;
			}
	public void setSurname(String sur)
	{
		this.surname= sur;
	}
	
	public String getGrade(){
		return grade;
			}
	public void setGrade(String gr)
	{
		this.grade= gr;
	}
	
	public String getGender(){
		return gender;
			}
	public void setGender(String gr)
	{
		this.gender= gr;
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
	
	
	public String getSchool(){
		return school;
			}
	
	public void setSchool(String sc)
	{
		this.school= sc;
	}
	
	public Date getDOB(){
		return dob;
			}
	
	public void setDOB(Date d)
	{
		this.dob= d;
	}
	
	
	
	public String getParentName(){
		return parentName;
			}
	
	public void setParentName(String pn)
	{
		this.parentName= pn;
	}
	
	
	public String getParentContact(){
		return parentContact;
			}
	
	public void setParentContact(String pc)
	{
		this.parentContact= pc;
	}
	
	public boolean getHasRoadtoHealth(){
		return hasRoadtoHealth;
			}
	
	public void setHasRoadtoHealth(boolean hrh)
	{
		this.hasRoadtoHealth= hrh;
	}
}
