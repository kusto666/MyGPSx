package mygpsx;

//—ÚÛÍÚÛ‡ ÚËÔÓ‚ Á‡‰‡˜!!!
public class CStructAttrjob {
	private String MyIDUnique;
	private String MyCLassAttrjob;
	private String MyNameAttrjob;
	
	public CStructAttrjob() {// Œ¡ﬂ«¿“≈À‹ÕŒ ” ¿«€¬¿≈Ã, ¿ “Œ ¡”ƒ≈“ Œÿ»¡ ¿ “»œ¿  ¿  «ƒ≈—‹ ¡€ÀŒ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrjob(String MyIDUnique, String MyCLassAttrjob, String MyNameAttrjob) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassAttrjob = MyCLassAttrjob;
		this.MyNameAttrjob = MyNameAttrjob;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassAttrjob()
    {
        return MyCLassAttrjob;
    }

    public String getMyNameAttrjob()
    {
        return MyNameAttrjob;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassAttrjob(String MyCLassAttrjob)
    {
        this.MyCLassAttrjob = MyCLassAttrjob;
    }
    public void setMyNameAttrjob(String MyNameAttrjob)
    {
        this.MyNameAttrjob = MyNameAttrjob;
    }
}
