package mygpsx;

//—ÚÛÍÚÛ‡ ÚËÔÓ‚ Á‡‰‡˜!!!
public class CStructAttrTmpl {
	private String MyIDUnique;
	private String MyAttrID;// ID ‡ÚË·ÛÚ‡ ËÁ ÛÁÎ‡ "my_owner_settings"->"my_attrjob"
	private String MyAttrName;
	
	public CStructAttrTmpl() {// Œ¡ﬂ«¿“≈À‹ÕŒ ” ¿«€¬¿≈Ã, ¿ “Œ ¡”ƒ≈“ Œÿ»¡ ¿ “»œ¿  ¿  «ƒ≈—‹ ¡€ÀŒ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrTmpl(String MyIDUnique, String MyAttrID, String MyAttrName) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyAttrID = MyAttrID;
		this.MyAttrName = MyAttrName;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyAttrID()
    {
        return MyAttrID;
    }

    public String getMyAttrName()
    {
        return MyAttrName;
    }
   
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyAttrID(String MyAttrID)
    {
        this.MyAttrID = MyAttrID;
    }
    public void setMyAttrName(String MyAttrName)
    {
        this.MyAttrName = MyAttrName;
    }

}
