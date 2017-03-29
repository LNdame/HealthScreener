package ansteph.cct.healthscreener.localstorage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import ansteph.cct.healthscreener.entity.Assent;
import ansteph.cct.healthscreener.entity.AttemptCount;
import ansteph.cct.healthscreener.entity.Category;
import ansteph.cct.healthscreener.entity.CloseQuestion;
import ansteph.cct.healthscreener.entity.Nurse;
import ansteph.cct.healthscreener.entity.OpCategory;
import ansteph.cct.healthscreener.entity.OpenQuestion;
import ansteph.cct.healthscreener.entity.Pupil;
import ansteph.cct.healthscreener.entity.Referral;
import ansteph.cct.healthscreener.entity.School;

public class DbHelper extends SQLiteOpenHelper {

	public static  String DATABASE_NAME = "healthscreen.db";//healthscreen.sqlite
	public static  String DB_PATH= "/data/data/ansteph.cct.healthscreener/databases/";

	private static SQLiteDatabase htdb;
	private final Context context;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		// TODO Auto-generated constructor stub
		
		this.context = context;
	}

	
	///check that the database is there
		public boolean checkDatabase()
		{
			SQLiteDatabase db = null;
			try{
				String mypath = DB_PATH +DATABASE_NAME;
				db= SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
				
			}catch(SQLiteException e)
			{
				//too bad no db add a toast here
			}
			if(db!=null)
			{
				db.close();
			}
			
			return db!=null? true : false;
		}
	
		public void createDatabase() throws IOException
		{
			boolean dbExist = checkDatabase();
			
			if(!dbExist)
			{
				
				//empty db detected create new one and overwrite 
				this.getReadableDatabase();
				try{
					copyDatabase();
				}catch(IOException e)
				{
					throw new Error ("Error copying the database");}
			}
			
			
		}
	
		private void copyDatabase()throws IOException
		{
			//open your local db input
			InputStream inputSt = context.getAssets().open(DATABASE_NAME);
			
			//path to create an empty db
			String outFileName = DB_PATH+DATABASE_NAME;
			
			OutputStream outputSt = new FileOutputStream(outFileName);
			
			//tranfers bytes  from inputfile to outputfile
			byte[] buffer = new byte[1024];
			int length;
			while((length= inputSt.read(buffer))>0)
			{
				outputSt.write(buffer,0,length);
				
			}
			//close the stream
			outputSt.flush();
			outputSt.close();
			inputSt.close();
		}
		
		public static void openDatabase () throws SQLException
		 {
			 //open db
			 String path = DB_PATH+DATABASE_NAME;
			 htdb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
					 
			 
		 }
		 
		public SQLiteDatabase getReadDatabase()
		{
			 //open db
			 String path = DB_PATH+DATABASE_NAME;
			 htdb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
				return htdb;
		}
		
		 @Override
		 public synchronized void close()
		 {
			 if(htdb!=null)
				 htdb.close();
			 
			 super.close();
		 }	
		
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	/*********************** Escape string for single quotes (Insert,Update)************/
	private static String sqlEscapeString(String aString) {
		String aReturn = "";
		
		if (null != aString) {
			//aReturn = aString.replace("'", "''");
			aReturn = DatabaseUtils.sqlEscapeString(aString);
			// Remove the enclosing single quotes ...
			aReturn = aReturn.substring(1, aReturn.length() - 1);
		}
		
		return aReturn;
	}
	
	/***********************************this part concerned the entities in the database*******************************************/
	/** Table names */
	public static final String pupil_TABLE = "pupil";
	public static final String altenateTest_TABLE = "altenateTest";
	public static final String alternateTesting_TABLE = "alternateTesting";
	public static final String closeAnswer_TABLE = "closeAnswer";
	public static final String closeQuestion_TABLE = "closeQuestion";
	public static final String openAnswer_TABLE = "openAnswer";
	public static final String openQuestion_TABLE = "openQuestion";
	public static final String questionCategory_TABLE = "questionCategory";
	public static final String questionCategoryOpen_TABLE = "questionCategoryOpen";
	public static final String takenCountRecord_TABLE = "takenCountRecord";
	public static final String school_TABLE = "school";
	public static final String referral_TABLE = "referral";
	public static final String assent_TABLE = "assent";
    public static final String nurse_TABLE = "nurse";
	
	/********************pupil Table Fields ************/
	public static final String KEY_PUPIL_ID = "pupilID";
	public static final String KEY_PUPIL_FNAME = "firstname";
	public static final String KEY_PUPIL_SNAME = "surname";	
	public static final String KEY_PUPIL_GRADE = "grade";
	public static final String KEY_PUPIL_GENDER = "gender";
	public static final String KEY_PUPIL_DOB = "DOB";
	public static final String KEY_PUPIL_SCHOOL = "school";
	
	public static final String KEY_PUPIL_PARNAME = "parentname";
	public static final String KEY_PUPIL_PARCONTACT = "parentcontact";
	public static final String KEY_PUPIL_RODHEALTH = "roadtohealth";
	
	public static final String KEY_PUPIL_ADD1 = "addressline1";
	public static final String KEY_PUPIL_ADD2 = "addressline2";
	
	


	 // Adding new pupil
    public boolean addPupilData(Pupil pup) {
    	
    	boolean added = false;
    	try {
    		if(htdb==null)
			{
				openDatabase();
			}
    		
    		ContentValues cVal = new ContentValues();
    		
    		cVal.put(KEY_PUPIL_FNAME, sqlEscapeString(pup.getFirstname()));
    		cVal.put(KEY_PUPIL_SNAME, sqlEscapeString(pup.getSurname()));
    		cVal.put(KEY_PUPIL_GRADE, sqlEscapeString(pup.getGrade()));
    		cVal.put(KEY_PUPIL_GENDER, sqlEscapeString(pup.getGender()));
    		cVal.put(KEY_PUPIL_SCHOOL, sqlEscapeString(pup.getSchool()));
    		cVal.put(KEY_PUPIL_DOB, DateFormat.format("dd/MM/yyyy", pup.getDOB()).toString());
    		
    		cVal.put(KEY_PUPIL_PARNAME, sqlEscapeString(pup.getParentName()));
    		cVal.put(KEY_PUPIL_PARCONTACT, sqlEscapeString(pup.getParentContact()));
    		cVal.put(KEY_PUPIL_RODHEALTH, sqlEscapeString(Boolean.toString(pup.getHasRoadtoHealth()) ));
    		
    		cVal.put(KEY_PUPIL_ADD1, sqlEscapeString(pup.getAddline1()));
    		cVal.put(KEY_PUPIL_ADD2, sqlEscapeString(pup.getAddline2()));
    		
    		htdb.insert(pupil_TABLE, null, cVal);
    		added = true;
		} catch (Exception e) {
			// TODO: handle exception
			added= false;
		}
    	
    	return added;
      // Closing database connection
    }
    
 public boolean editPupilData(Pupil pup) {
    	
    	boolean added = false;
    	try {
    		if(htdb==null)
			{
				openDatabase();
			}
    		
    		ContentValues cVal = new ContentValues();
    		
    		cVal.put(KEY_PUPIL_FNAME, sqlEscapeString(pup.getFirstname()));
    		cVal.put(KEY_PUPIL_SNAME, sqlEscapeString(pup.getSurname()));
    		cVal.put(KEY_PUPIL_GRADE, sqlEscapeString(pup.getGrade()));
    		cVal.put(KEY_PUPIL_GENDER, sqlEscapeString(pup.getGender()));
    		cVal.put(KEY_PUPIL_SCHOOL, sqlEscapeString(pup.getSchool()));
    		cVal.put(KEY_PUPIL_DOB, DateFormat.format("dd/MM/yyyy", pup.getDOB()).toString());
    		
    		cVal.put(KEY_PUPIL_PARNAME, sqlEscapeString(pup.getParentName()));
    		cVal.put(KEY_PUPIL_PARCONTACT, sqlEscapeString(pup.getParentContact()));
    		cVal.put(KEY_PUPIL_RODHEALTH, sqlEscapeString(Boolean.toString(pup.getHasRoadtoHealth()) ));
    		
    		cVal.put(KEY_PUPIL_ADD1, sqlEscapeString(pup.getAddline1()));
    		cVal.put(KEY_PUPIL_ADD2, sqlEscapeString(pup.getAddline2()));
    		
    		
    		htdb.update(pupil_TABLE,  cVal, KEY_PUPIL_ID+" = ? ", new String[]{pup.getId()});
    		added = true;
		} catch (Exception e) {
			// TODO: handle exception
			added= false;
		}
    	
    	return added;
      // Closing database connection
    }

    
 //return mazeQuizzdb.update(TABLE_PLAYLEVEL, values, CLN_PLID + " = ? ", new String[]{String.valueOf(lv.getID())});
	
    
    //DateFormat.format("yyyy.MM.dd", pup.getDOB()).toString()
   
	  // Getting All pupil
    public  List<Pupil> getAllPupilData() {
        List<Pupil> pupilList = new ArrayList<Pupil>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + pupil_TABLE;
 
     
        try {
			if(htdb==null)
			{
				openDatabase();
			}
			 Cursor cursor = htdb.rawQuery(selectQuery, null);
			 
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		            	Pupil pup = new Pupil(
		            			(cursor.getString(0)),
		            			(cursor.getString(1)),
		            			(cursor.getString(2)),
		            			(cursor.getString(3)),
		            			(cursor.getString(4)),
		            			(cursor.getString(5)),
		            			(cursor.getString(6)),
		            			(cursor.getString(7))
		            			
		            			);
		            	
		                // Adding contact to list
		                pupilList.add(pup);
		            } while (cursor.moveToNext());
		        }
		} catch (SQLException se) {
			// TODO: handle exception
			throw se;
		}
        
       
        return pupilList;
}
    
   // SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public  List<Pupil> getAllPupilDatabySurname(String surname) {
        List<Pupil> pupilList = new ArrayList<Pupil>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + pupil_TABLE +" WHERE "+KEY_PUPIL_SNAME+" = '"+ surname+"' COLLATE NOCASE ";
 
     
        try {
			if(htdb==null)
			{
				openDatabase();
			}
			 Cursor cursor = htdb.rawQuery(selectQuery, null);
			 
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		            	Pupil pup = new Pupil(
		            			(cursor.getString(0)),
		            			(cursor.getString(1)),
		            			(cursor.getString(2)),
		            			(cursor.getString(3)),
		            			(cursor.getString(4)),
		            			(cursor.getString(5)),
		            			(cursor.getString(6)),
		            			(cursor.getString(7))
		            			
		            			);
		            	String datedob =cursor.getString(8);
		            	Date dob=null;
		    			
		    			try {
		    				dob =formatter.parse(datedob);
		    				pup.setDOB(dob);
		    			} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		    			
		    			pup.setParentName(cursor.getString(9));
		    			pup.setParentContact(cursor.getString(10));
		    			
		    			if(cursor.getString(11).equalsIgnoreCase("true"))
		    				pup.setHasRoadtoHealth(true);
		    			else
		    				pup.setHasRoadtoHealth(false);
		    			
		                // Adding contact to list
		                pupilList.add(pup);
		            } while (cursor.moveToNext());
		        }
		} catch (SQLException se) {
			// TODO: handle exception
			throw se;
		}
        
    	//public Pupil(String id,String firstname,String surname,String grade,String gender, String addline1 , String addline2, String sc, Date d) {

        return pupilList;
}
    
   public  List<Pupil> getAllPupilDatabyGrade(String grad) {
        List<Pupil> pupilList = new ArrayList<Pupil>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + pupil_TABLE +" WHERE "+KEY_PUPIL_GRADE+" = '"+ grad+"' COLLATE NOCASE ";
      
        try {
			if(htdb==null)
			{
				openDatabase();
			}
			 Cursor cursor = htdb.rawQuery(selectQuery, null);
			 
		        // looping through all rows and adding to list
		        if (cursor.moveToFirst()) {
		            do {
		            	Pupil pup = new Pupil(
		            			(cursor.getString(0)),
		            			(cursor.getString(1)),
		            			(cursor.getString(2)),
		            			(cursor.getString(3)),
		            			(cursor.getString(4)),
		            			(cursor.getString(5)),
		            			(cursor.getString(6)),
		            			(cursor.getString(7))
		            			
		            			);
		            	String datedob =cursor.getString(8);
		            	Date dob=null;
		    			
		    			try {
		    				dob =formatter.parse(datedob);
		    				pup.setDOB(dob);
		    			} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		    			pup.setParentName(cursor.getString(9));
		    			pup.setParentContact(cursor.getString(10));
		    			
		    			if(cursor.getString(11).equalsIgnoreCase("true"))
		    				pup.setHasRoadtoHealth(true);
		    			else
		    				pup.setHasRoadtoHealth(false);
		    			
		                // Adding contact to list
		                pupilList.add(pup);
		            } while (cursor.moveToNext());
		        }
		} catch (SQLException se) {
			// TODO: handle exception
			throw se;
		}
        
       
        return pupilList;
}
   
   public AttemptCount getPupilAttempt(String id)
   {
	   AttemptCount att = new AttemptCount(0,0,0,0,0);
	   String selectQuery = "SELECT  `testID`,  count(*) as attemptCount FROM  `takenCountRecord` WHERE `pupilID` ='"+id+"' group by `testID`";
	   
	   try {
		   
		   if(htdb==null)
			{
				openDatabase();
			}
			 Cursor cursor = htdb.rawQuery(selectQuery, null);
			  if (cursor.moveToFirst()) {
		            do {
		            	int testID =Integer.parseInt(cursor.getString(0)) ;							
						int attcount = Integer.parseInt(cursor.getString(1));
						
						switch (testID) { // as more test will be implemented add more switch case 
							case 1:att.setBmiCount(attcount);							
								break;
							case 2:att.setOralCount(attcount);							
								break;
							case 3:att.setOpenQuizzCount(attcount);							
								break;
							case 4:att.setHearingCount(attcount);							
								break;
							
							case 5:att.setVisionCount(attcount);							
							break;
							default:
								break;
						}
		            
		            }	 while (cursor.moveToNext()); 
		    }
			 
	   		} catch (Exception e) {
	   			// TODO: handle exception
	   		}
	   
	   return att;
   }
   
   
   /********************Close and Cat close Table Fields ************/
	public static final String KEY_CLOSE_ID = "closeID";
	public static final String KEY_CLOSE_QDESC = "questionDesc";
	public static final String KEY_CLOSE_CATID = "catId";	
	
	
	public static final String KEY_CATCLOSE_ID = "id";
	public static final String KEY_CATCLOSE_NAME = "name";
	public static final String KEY_CATCLOSE_CATDESC = "catDescr";	
	
	 public  List<Category> getAllCategoryData() {
	        List<Category> List = new ArrayList<Category>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + questionCategory_TABLE ;
	      
	        try {
				if(htdb==null)
				{
					openDatabase();
				}
				 Cursor cursor = htdb.rawQuery(selectQuery, null);
				 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	Category cat = new Category(
			            			(cursor.getString(0)),
			            			(cursor.getString(1)),
			            			(cursor.getString(2)) 		
			            			);
			            	
			                // Adding contact to list
			                List.add(cat);
			            } while (cursor.moveToNext());
			        }
			} catch (SQLException se) {
				// TODO: handle exception
			se.printStackTrace();;
			}       
	        return List;
	}
   
	 
	 
	 public  List<OpCategory> getAllOPCategoryData() {
	        List<OpCategory> List = new ArrayList<OpCategory>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + questionCategoryOpen_TABLE ;
	      
	        try {
				if(htdb==null)
				{
					openDatabase();
				}
				 Cursor cursor = htdb.rawQuery(selectQuery, null);
				 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	OpCategory cat = new OpCategory(
			            			(cursor.getString(0)),
			            			(cursor.getString(1)),
			            			(cursor.getString(2)) 		
			            			);
			            	
			                // Adding contact to list
			                List.add(cat);
			            } while (cursor.moveToNext());
			        }
			} catch (SQLException se) {
				// TODO: handle exception
			se.printStackTrace();;
			}       
	        return List;
	}
	// openQuestion_TABLE
	 
	 
	 public  List<CloseQuestion> getAlCloseQuestionData() {
	        List<CloseQuestion> List = new ArrayList<CloseQuestion>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + closeQuestion_TABLE;
	      
	        try {
				if(htdb==null)
				{
					openDatabase();
				}
				 Cursor cursor = htdb.rawQuery(selectQuery, null);
				 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	CloseQuestion clques = new CloseQuestion(
			            			(cursor.getString(0)),
			            			(cursor.getString(1)),
			            			(cursor.getString(2)) 		
			            			);
			            	
			                // Adding contact to list
			                List.add(clques);
			            } while (cursor.moveToNext());
			        }
			} catch (SQLException se) {
				// TODO: handle exception
			se.printStackTrace();;
			}       
	        return List;
	}
	 
	 
	 
	 public  List<OpenQuestion> getAllOpenQuestionData() {
	        List<OpenQuestion> List = new ArrayList<OpenQuestion>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + openQuestion_TABLE;
	      
	        try {
				if(htdb==null)
				{
					openDatabase();
				}
				 Cursor cursor = htdb.rawQuery(selectQuery, null);
				 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	OpenQuestion clques = new OpenQuestion(
			            			(cursor.getString(0)),
			            			(cursor.getString(1)),
			            			(cursor.getString(2)) 		
			            			);
			            	
			                // Adding contact to list
			                List.add(clques);
			            } while (cursor.moveToNext());
			        }
			} catch (SQLException se) {
				// TODO: handle exception
			se.printStackTrace();;
			}       
	        return List;
	}
	 
	 /************ answers************/
	 public static final String KEY_CLOSEA_ID = "closeID";
	 public static final String KEY_CLOSEA_PUPID = "pupilID";
	 public static final String KEY_CLOSEA_BAN = "booleanAnswer";	
	 public static final String KEY_CLOSEAL_AN = "alternativeAnswer";//u r here...
		
	 public boolean addCLoseAnswerData(String pupilID, String closeID ,String booleanAnswer) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_CLOSEA_ID, sqlEscapeString(closeID));
	    		cVal.put(KEY_CLOSEA_PUPID, sqlEscapeString(pupilID));
	    		cVal.put(KEY_CLOSEA_BAN , sqlEscapeString(booleanAnswer));
	    		
	    		htdb.insert(closeAnswer_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    } 
	 
	 //overridden function  // didn't want modified the original function to many grinding gears
	 public boolean addCLoseAnswerData(String pupilID, String closeID ,String booleanAnswer, String alterAnswer ) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_CLOSEA_ID, sqlEscapeString(closeID));
	    		cVal.put(KEY_CLOSEA_PUPID, sqlEscapeString(pupilID));
	    		cVal.put(KEY_CLOSEA_BAN , sqlEscapeString(booleanAnswer));
	    		cVal.put(KEY_CLOSEAL_AN, sqlEscapeString(alterAnswer));
	    		
	    		htdb.insert(closeAnswer_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    } 
	 public static final String KEY_OPENA_ID = "openID";
	 public static final String KEY_OPENA_PUPID = "pupilID";
	 public static final String KEY_OPENA_BAN = "answer";	
		
	 public boolean addOpenAnswerData(String pupilID, String openID ,String answer) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_OPENA_ID, sqlEscapeString(openID));
	    		cVal.put(KEY_OPENA_PUPID, sqlEscapeString(pupilID));
	    		cVal.put(KEY_OPENA_BAN ,sqlEscapeString(answer));
	    		
	    		htdb.insert(openAnswer_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    } 
	 
	 public static final String KEY_ALT_ID = "altenateID";
	 public static final String KEY_TEST_ID = "testID";
	 public static final String KEY_ALT_PUPID = "pupilID";
	 public static final String KEY_ALT_RES = "result";	
	 public static final String KEY_ALT_COMMENT = "Comment";	
	 public boolean addAlternatetestingData(String alternateID,String pupilID, String result ,String Comment) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_ALT_ID, sqlEscapeString(alternateID));
	    		cVal.put(KEY_ALT_PUPID, sqlEscapeString(pupilID));
	    		cVal.put(KEY_ALT_RES ,sqlEscapeString(result));
	    		cVal.put(KEY_ALT_COMMENT ,sqlEscapeString(Comment));
	    		
	    		htdb.insert(alternateTesting_TABLE, null, cVal);
	    		
	    		ContentValues cValtk = new ContentValues();
	    		
	    		cValtk.put(KEY_TEST_ID, sqlEscapeString(alternateID)); //Don't have to change a thing(0408)
	    		cValtk.put(KEY_ALT_PUPID, sqlEscapeString(pupilID));
	    		htdb.insert(takenCountRecord_TABLE, null, cValtk);
	    		
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    } 
	 
	 /********************School Table Fields ************/
		public static final String KEY_SCHOOL_ID = "schoolID";
		public static final String KEY_SCHOOL_SCHNAME = "schoolname";
		public static final String KEY_SCHOOLL_PRINNAME = "principalname";	
		public static final String KEY_SCHOOL_PRINCONTACT = "principalContact";
		public static final String KEY_SCHOOL_PERNAME = "personName";
		public static final String KEY_SCHOOL_PERCONTACT = "personContact";
		public static final String KEY_SCHOOL_GPS = "gpsCoordinate";
		public static final String KEY_SCHOOL_ADD1 = "addressline1";
		public static final String KEY_SCHOOL_ADD2 = "addressline2";
		
		
		
		 // Adding new pupil  htdb.update(pupil_TABLE,  cVal, KEY_PUPIL_ID+" = ? ", new String[]{pup.getId()});
	    public boolean addSchoolData(School sch) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_SCHOOL_SCHNAME, sqlEscapeString(sch.getSchoolName()));
	    		cVal.put(KEY_SCHOOLL_PRINNAME, sqlEscapeString(sch.getPrincipalName()));
	    		cVal.put(KEY_SCHOOL_PRINCONTACT, sqlEscapeString(sch.getPrincipalContact()));
	    		cVal.put(KEY_SCHOOL_PERNAME, sqlEscapeString(sch.getPersonName()));
	    		cVal.put(KEY_SCHOOL_PERCONTACT, sqlEscapeString(sch.getPersonContact()));
	    		cVal.put(KEY_SCHOOL_GPS, sqlEscapeString(sch.getGPSCoordinate()));
	    		cVal.put(KEY_SCHOOL_ADD1, sqlEscapeString(sch.getAddline1()));
	    		cVal.put(KEY_SCHOOL_ADD2, sqlEscapeString(sch.getAddline2()));
	    		
	    		htdb.insert(school_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }

	    public boolean UpdateSchoolData(School sch) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_SCHOOL_SCHNAME, sqlEscapeString(sch.getSchoolName()));
	    		cVal.put(KEY_SCHOOLL_PRINNAME, sqlEscapeString(sch.getPrincipalName()));
	    		cVal.put(KEY_SCHOOL_PRINCONTACT, sqlEscapeString(sch.getPrincipalContact()));
	    		cVal.put(KEY_SCHOOL_PERNAME, sqlEscapeString(sch.getPersonName()));
	    		cVal.put(KEY_SCHOOL_PERCONTACT, sqlEscapeString(sch.getPersonContact()));
	    		cVal.put(KEY_SCHOOL_GPS, sqlEscapeString(sch.getGPSCoordinate()));
	    		cVal.put(KEY_SCHOOL_ADD1, sqlEscapeString(sch.getAddline1()));
	    		cVal.put(KEY_SCHOOL_ADD2, sqlEscapeString(sch.getAddline2()));
	    		
	    		htdb.update(school_TABLE,   cVal, KEY_SCHOOL_ID+" = ? ", new String[]{sch.getId()});
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }
		
	    public  List<School> getAlSchoolData() {
	        List<School> List = new ArrayList<School>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + school_TABLE;
	      
	        try {
				if(htdb==null)
				{
					openDatabase();
				}
				 Cursor cursor = htdb.rawQuery(selectQuery, null);
				 
			        // looping through all rows and adding to list
			        if (cursor.moveToFirst()) {
			            do {
			            	School clques = new School(
			            			(cursor.getString(0)),
			            			(cursor.getString(1)),
			            			(cursor.getString(2)),
			            			(cursor.getString(3)), 
			            			(cursor.getString(4)) ,
			            			(cursor.getString(5)) ,
			            			(cursor.getString(6)) ,
			            			(cursor.getString(7)) ,
			            			(cursor.getString(8)) 
			            			);
			            	
			                // Adding contact to list
			                List.add(clques);
			            } while (cursor.moveToNext());
			        }
			} catch (SQLException se) {
				// TODO: handle exception
			se.printStackTrace();;
			}       
	        return List;
	}
	    
	    
	    
	    /********************Referral Table Fields ************/
	    public static final String KEY_REFERRAL_ID = "referralID";
		public static final String KEY_PREFERRALL_HREF = "hasReferral";
		public static final String KEY_REFERRAL_REF = "referee";	
		public static final String KEY_REFERRAL_DREF = "dateOfReferral";
		public static final String KEY_REFERRAL_DFOLL = "dateOfFollowUp";
		public static final String KEY_REFERRAL_PUPID = "pupilID";
		public static final String KEY_REFERRAL_CATID = "catID";
		
		
	/*	CREATE TABLE "referral" ("referralID" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "hasReferral" BOOLEAN NOT
				NULL, "referee" VARCHAR, "dateOfReferral" DATE, "dateOfFollowUp" DATE, "pupilID" INTEGER, "catID" INTEGER)*/


		 // Adding new referal
	    public boolean addReferralData(Referral ref) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_PREFERRALL_HREF, sqlEscapeString(Boolean.toString(ref.getHasReferral()) ));
	    		cVal.put(KEY_REFERRAL_REF, sqlEscapeString(ref.getReferee()));
	    		cVal.put(KEY_REFERRAL_DREF, DateFormat.format("dd/MM/yyyy", ref.getDateOfReferal()).toString());
	    		cVal.put(KEY_REFERRAL_DFOLL, DateFormat.format("dd/MM/yyyy", ref.getDateOfFollowUp()).toString());
	    		cVal.put(KEY_REFERRAL_PUPID, sqlEscapeString(ref.getPupilId()));
	    		cVal.put(KEY_REFERRAL_CATID, sqlEscapeString(ref.getCatID()));
	    		
	    		
	    		
	    		htdb.insert(referral_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }
	    
	    
 public boolean UpdateReferralData(Referral ref) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_PREFERRALL_HREF, sqlEscapeString(Boolean.toString(ref.getHasReferral()) ));
	    		cVal.put(KEY_REFERRAL_REF, sqlEscapeString(ref.getReferee()));
	    		cVal.put(KEY_REFERRAL_DREF, DateFormat.format("dd/MM/yyyy", ref.getDateOfReferal()).toString());
	    		cVal.put(KEY_REFERRAL_DFOLL, DateFormat.format("dd/MM/yyyy", ref.getDateOfFollowUp()).toString());
	    		cVal.put(KEY_REFERRAL_PUPID, sqlEscapeString(ref.getPupilId()));
	    		cVal.put(KEY_REFERRAL_CATID, sqlEscapeString(ref.getCatID()));
	    		
	    	
	    		htdb.update(referral_TABLE,   cVal, KEY_REFERRAL_ID+" = ? ", new String[]{ref.getId()});
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }
 
 
 
 
 /********************Assent Table Fields ************/
 	public static final String KEY_ASSENT_ID = "assentID";
	public static final String KEY_ASSENT_PID = "pupilID";
	public static final String KEY_ASSENTL_PREEX = "preExistingCondition";	
	public static final String KEY_ASSENT_DREF = "signature";
	public static final String KEY_ASSENT_DATE = "assentDate";
	public static final String KEY_ASSENT_HASCON = "hasParentalConsent";

	
	 public boolean addAssentData(Assent as) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_ASSENT_PID, sqlEscapeString(as.getPupilId()));
	    		cVal.put(KEY_ASSENTL_PREEX, sqlEscapeString(as.getPreExistingCondition()));
	    		cVal.put(KEY_ASSENT_DATE, DateFormat.format("dd/MM/yyyy", as.getAssentDate()).toString());
	    		cVal.put(KEY_ASSENT_HASCON, sqlEscapeString(Boolean.toString(as.getHasConsent()) ));
	    		htdb.insert(assent_TABLE, null, cVal);
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }
	
	 public boolean UpdateAssentData(Assent as) {
	    	
	    	boolean added = false;
	    	try {
	    		if(htdb==null)
				{
					openDatabase();
				}
	    		
	    		ContentValues cVal = new ContentValues();
	    		
	    		cVal.put(KEY_ASSENT_PID, sqlEscapeString(as.getPupilId()));
	    		cVal.put(KEY_ASSENTL_PREEX, sqlEscapeString(as.getPreExistingCondition()));
	    		cVal.put(KEY_ASSENT_DATE, DateFormat.format("dd/MM/yyyy", as.getAssentDate()).toString());
	    		cVal.put(KEY_ASSENT_HASCON, sqlEscapeString(Boolean.toString(as.getHasConsent()) ));

	    		htdb.update(assent_TABLE,   cVal, KEY_ASSENT_ID+" = ? ", new String[]{as.getId()});
	    		added = true;
			} catch (Exception e) {
				// TODO: handle exception
				added= false;
			}
	    	
	    	return added;
	      // Closing database connection
	    }



    /************************************************************Report***************************************************************/
    public List<String> getAllDatabyGrade() {
        List<String> gradeList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT distinct grade FROM " + pupil_TABLE +"   ";

        try {
            if(htdb==null)
            {
                openDatabase();
            }
            Cursor cursor = htdb.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {

                    gradeList.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } catch (SQLException se) {
            // TODO: handle exception
            throw se;
        }


        return gradeList ;
    }


    public HashMap<String , String> getPupilAttemptbyGrade (String id)
    {
        HashMap<String , String> attCountHash = new HashMap<>();

        AttemptCount att = new AttemptCount(0,0,0,0,0);
        String selectQuery ="SELECT `name`, `testID`,  count(*) as attemptCount FROM  `takenCountRecord` join altenateTest  on altenateTest.alternateID = takenCountRecord.testID WHERE `pupilID` IN ( select `pupilID` from pupil where grade = '"+id+"') group by `testID` order by `testID` asc";

//"SELECT  `testID`,  count(*) as attemptCount FROM  `takenCountRecord` WHERE `pupilID` ='"+id+"' group by `testID`";
        try{
            if(htdb==null)
            {
                openDatabase();
            }
            Cursor cursor = htdb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do{
                    attCountHash.put(cursor.getString(0),cursor.getString(2)) ;

                }while (cursor.moveToNext());
            }
        }  catch (Exception e)
        {

        }


        try {

            if(htdb==null)
            {
                openDatabase();
            }
            Cursor cursor = htdb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int testID =Integer.parseInt(cursor.getString(1)) ;
                    int attcount = Integer.parseInt(cursor.getString(2));

                    switch (testID) { // as more test will be implemented add more switch case
                        case 1:att.setBmiCount(attcount);
                            break;
                        case 2:att.setOralCount(attcount);
                            break;
                        case 3:att.setOpenQuizzCount(attcount);
                            break;
                        case 4:att.setHearingCount(attcount);
                            break;

                        case 5:att.setVisionCount(attcount);
                            break;
                        default:
                            break;
                    }

                }	 while (cursor.moveToNext());
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return attCountHash ;
    }


    /********************pupil Table Fields ************/
    public static final String KEY_NURSE_ID = "nurseID";
    public static final String KEY_NURSE_FNAME = "firstname";
    public static final String KEY_NURSE_SNAME = "surname";
    public static final String KEY_NURSE_USERNAME = "username";
    public static final String KEY_NURSE_PWD = "nu_password";
    public static final String KEY_NURSE_EMAIL = "email";



    public Nurse loggedNurse(String usn, String pwd) {

        Nurse logNurse= null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + nurse_TABLE +" WHERE username ='"+usn+"' and nu_password ='"+pwd+"'";

        try {
            if(htdb==null)
            {
                openDatabase();
            }
            Cursor cursor = htdb.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    logNurse = new Nurse(
                            (cursor.getString(0)),
                            (cursor.getString(1)),
                            (cursor.getString(2)),
                            (cursor.getString(4)),
                            (cursor.getString(5)) ,
                            (cursor.getString(3))
                    );



                } while (cursor.moveToNext());
            }
        } catch (SQLException se) {
            // TODO: handle exception
            se.printStackTrace();;
        }
        return logNurse;
    }





    public boolean UserExist(String usn, String pwd){

        boolean exist = false;
        String selectQuery = "SELECT * FROM " + nurse_TABLE +" WHERE username ='"+usn+"' and nu_password ='"+pwd+"'";

        try{
            if(htdb==null)
            {
                openDatabase();
            }
            Cursor cursor = htdb.rawQuery(selectQuery, null);
            if (cursor.getCount()>0) {
               exist = true;
            }
        }  catch (Exception e)
        {

        }

        return exist;
    }
}