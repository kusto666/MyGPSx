package mygpsx;

//Структура типов задач!!!
public class CStructAttrobj {
	private String MyIDUnique;
	private String MyCLassAttrobj;
	private String MyNameAttrobj;
	
	public CStructAttrobj() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructAttrobj(String MyIDUnique, String MyCLassAttrobj, String MyNameAttrobj) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassAttrobj = MyCLassAttrobj;
		this.MyNameAttrobj = MyNameAttrobj;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassAttrobj()
    {
        return MyCLassAttrobj;
    }

    public String getMyNameAttrobj()
    {
        return MyNameAttrobj;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassAttrobj(String MyCLassAttrobj)
    {
        this.MyCLassAttrobj = MyCLassAttrobj;
    }
    public void setMyNameAttrobj(String MyNameAttrobj)
    {
        this.MyNameAttrobj = MyNameAttrobj;
    }
}
