package ansteph.cct.healthscreener.entity;

public class AttemptCount {

	public AttemptCount() {
		// TODO Auto-generated constructor stub
	}

	private int bmiCount;
	private int openQuizzCount;
	private int closeQuizzCount;
	private int hearingCount;
	private int visionCount;
	private int oralCount;
	//openc=0, hearc=0,visc=0,oralc=0;
	
	public AttemptCount(int b , int hc, int vc, int orc, int op ,int cl) {
		// TODO Auto-generated constructor stub
		bmiCount = b; openQuizzCount =op; closeQuizzCount= cl;
		hearingCount = hc; visionCount= vc; oralCount =orc;
	}

	public AttemptCount(int b , int hc, int vc, int orc, int op ) {
		// TODO Auto-generated constructor stub
		bmiCount = b; openQuizzCount =op; 
		hearingCount = hc; visionCount= vc; oralCount =orc;
	}
	
	public int getBmiCount()
	{
		return bmiCount;
	}
	
	public void setBmiCount(int b)
	{
		bmiCount= b;
	}
	
	
	public int getOpenQuizzCount()
	{
		return openQuizzCount;
	}
	
	public void setOpenQuizzCount(int op)
	{
		openQuizzCount= op;
	}
	
	
	public int getCloseQuizzCount()
	{
		return closeQuizzCount;
	}
	
	public void setCloseQuizzCount(int cl)
	{
		closeQuizzCount= cl;
	}
	
	
	public int getHearingCount()
	{
		return hearingCount;
	}
	
	public void setHearingCount(int cl)
	{
		hearingCount= cl;
	}
	
	public int getVisionCount()
	{
		return visionCount;
	}
	
	public void setVisionCount(int cl)
	{
		visionCount= cl;
	}
	
	
	public int getOralCount()
	{
		return oralCount;
	}
	
	public void setOralCount(int oc)
	{
		oralCount= oc;
	}
}
