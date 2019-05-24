package mygpsx;

//Структура типов задач!!!
public class CStructAttrTmplFilling {
	private String MyIDUnique;
	private String MyAttrID;// ID атрибута из узла "my_owner_settings"->"my_attrjob"
	private String MyAttrName;
	private String MyAttrType;
	private String MyAttrHeight;
	private String MyAttrWidth;
	private long MyAttrOrder;
	
	public CStructAttrTmplFilling() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrTmplFilling(
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
