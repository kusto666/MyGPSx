package mygpsx;

//—ÚÛÍÚÛ‡ ÚËÔÓ‚ Á‡‰‡˜!!!
public class CStructTypejob {
	private String MyIDUnique;
	private String MyCLassTypejob;
	private String MyNameTypejob;
	
	public CStructTypejob() {// Œ¡ﬂ«¿“≈À‹ÕŒ ” ¿«€¬¿≈Ã, ¿ “Œ ¡”ƒ≈“ Œÿ»¡ ¿ “»œ¿  ¿  «ƒ≈—‹ ¡€ÀŒ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructTypejob(String MyIDUnique, String MyCLassTypejob, String MyNameTypejob) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassTypejob = MyCLassTypejob;
		this.MyNameTypejob = MyNameTypejob;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassTypejob()
    {
        return MyCLassTypejob;
    }

    public String getMyNameTypejob()
    {
        return MyNameTypejob;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassTypejob(String MyCLassTypejob)
    {
        this.MyCLassTypejob = MyCLassTypejob;
    }
    public void setMyNameTypejob(String MyNameTypejob)
    {
        this.MyNameTypejob = MyNameTypejob;
    }
}
