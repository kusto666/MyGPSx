package mygpsx;

//—ÚÛÍÚÛ‡ ÚËÔÓ‚ Á‡‰‡˜!!!
public class CStructAttrTmpl {
	private String MyIDUnique;
	private String MyAttrID;// ID ‡ÚË·ÛÚ‡ ËÁ ÛÁÎ‡ "my_owner_settings"->"my_attrjob"
	private String MyAttrName;
	private String MyAttrType;
	private String MyAttrHeight;
	private String MyAttrWidth;
	private long MyAttrOrder;
	
	public CStructAttrTmpl() {// Œ¡ﬂ«¿“≈À‹ÕŒ ” ¿«€¬¿≈Ã, ¿ “Œ ¡”ƒ≈“ Œÿ»¡ ¿ “»œ¿  ¿  «ƒ≈—‹ ¡€ÀŒ:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrTmpl(
			String MyIDUnique, 
			String MyAttrID, 
			String MyAttrName,
			String MyAttrHeight,
			String MyAttrWidth,
			String MyAttrType, 
			long MyAttrOrder) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyAttrID = MyAttrID;
		this.MyAttrName = MyAttrName;
		this.MyAttrHeight = MyAttrHeight;
		this.MyAttrWidth = MyAttrWidth;
		this.MyAttrType = MyAttrType;
		this.MyAttrOrder = MyAttrOrder;
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
    public String getMyAttrHeight()
    {
        return MyAttrHeight;
    }
    public String getMyAttrWidth()
    {
        return MyAttrWidth;
    }
    public String getMyAttrType()
    {
        return MyAttrType;
    }
    public long getMyAttrOrder()
    {
        return MyAttrOrder;
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
    public void setMyAttrHeight(String MyAttrHeight)
    {
        this.MyAttrHeight = MyAttrHeight;
    }
    public void setMyAttrWidth(String MyAttrWidth)
    {
        this.MyAttrWidth = MyAttrWidth;
    }
    public void setMyAttrType(String MyAttrType)
    {
        this.MyAttrType = MyAttrType;
    }
    public void setMyAttrOrder(long MyAttrOrder)
    {
        this.MyAttrOrder = MyAttrOrder;
    }
}
