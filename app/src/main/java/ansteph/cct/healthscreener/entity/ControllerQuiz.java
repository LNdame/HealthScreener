package ansteph.cct.healthscreener.entity;

import java.util.ArrayList;

import android.app.Application;

public class ControllerQuiz extends Application {

	public ControllerQuiz() {
		// TODO Auto-generated constructor stub
	}

	private ArrayList<CloseQuestion> closeList = new ArrayList<CloseQuestion>();//this will gather the question as they appear on the form
 	private ArrayList<Boolean> closeAnswer = new ArrayList<Boolean>();//get the boolean answer
 	
 	private Pupil quizzedPupil = new Pupil();
 	private AttemptCount attcount = new AttemptCount();

    private Nurse loggedNurse = new Nurse();

 	private ArrayList<OpenQuestion> openList = new ArrayList<OpenQuestion>();//this will gather the question as they appear on the form
 	private ArrayList<String> openAnswer = new ArrayList<String>();
 	
 	private Referral quizzReferral = new Referral();
 	
 	public void setQuizzedPupil(Pupil apup)
 	{
 		quizzedPupil= apup;
 	}
 	
 	public Pupil getQuizzedPupil()
 	{
 		return quizzedPupil;
 	}
 	
 	
 	public void setattcount(AttemptCount att)
 	{
 		attcount= att;
 	}
 	
 	public AttemptCount getattcount()
 	{
 		return attcount;
 	}
 	//close answer
 	public void setCloseList (CloseQuestion ques)
 	{
 		closeList.add(ques);
 	}
 	
 	public CloseQuestion getCloseQuestion (int pos)
 	{
 		return closeList.get(pos);
 	}
 	
 	public void setCloseAnser(Boolean tf) /**** pay a special attention to this might crash in case of nullable ****/
 	{
 		closeAnswer.add(tf);
 	}
 	
 	public Boolean getCloseAnswer(int pos)
 	{
 		return closeAnswer.get(pos);
 	}
 	
 	public int getCloseListSize()
 	{
 		return closeList.size();
 	}
 	
 	public void clearCloseQuestion()
 	{
 		closeList.clear();
 	}
 	
 	public void clearCloseAnswer()
 	{
 		closeAnswer.clear();
 	}
 	//open answers
 	public void setOpenList (OpenQuestion ques)
 	{
 		openList.add(ques);
 	}
 	
 	public OpenQuestion getOpenQuestion (int pos)
 	{
 		return openList.get(pos);
 	}
 	
 	public int getOpenListSize()
 	{
 		return openList.size();
 	}
 	
 	public void setOpenAnser(String measure)
 	{
 		openAnswer.add(measure);
 	}
 	
 	public String getOpenAnswer(int pos)
 	{
 		return openAnswer.get(pos);
 	}
 	
 	public void clearOpenQuestion()
 	{
 		openList.clear();
 	}
 	
 	public void clearOpenAnswer()
 	{
 		openAnswer.clear();
 	}
 	
 	
 	//referral
 	public void setQuizzReferral(Referral aref)
 	{
 		quizzReferral= aref;
 	}
 	
 	public Referral getQuizzReferral()
 	{
 		return quizzReferral;
 	}
 	
 	//nurse

    public Nurse getLoggedNurse() {
        return loggedNurse;
    }

    public void setLoggedNurse(Nurse loggedNurse) {
        this.loggedNurse = loggedNurse;
    }
}
