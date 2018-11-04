package mygpsx;

//—ÚÛÍÚÛ‡ ÚËÔÓ‚ Á‡‰‡˜!!!
public class CStructTypedescjob {
	private String MyIDUnique;
	private String MyCLassTypedescjob;
	private String MyNameTypedescjob;
	
	public CStructTypedescjob() {// Œ¡ﬂ«¿“≈À‹ÕŒ ” ¿«€¬¿≈Ã, ¿ “Œ ¡”ƒ≈“ Œÿ»¡ ¿ “»œ¿  ¿  «ƒ≈—‹ ¡€ÀŒ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructTypedescjob(String MyIDUnique, String MyCLassTypedescjob, String MyNameTypedescjob) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassTypedescjob = MyCLassTypedescjob;
		this.MyNameTypedescjob = MyNameTypedescjob;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassTypedescjob()
    {
        return MyCLassTypedescjob;
    }

    public String getMyNameTypedescjob()
    {
        return MyNameTypedescjob;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassTypedescjob(String MyCLassTypedescjob)
    {
        this.MyCLassTypedescjob = MyCLassTypedescjob;
    }
    public void setMyNameTypedescjob(String MyNameTypedescjob)
    {
        this.MyNameTypedescjob = MyNameTypedescjob;
    }
}
