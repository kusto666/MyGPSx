package mygpsx;

//Структура типов задач!!!
public class CStructTypeobj {
	private String MyIDUnique;
	private String MyCLassTypeobj;
	private String MyNameTypeobj;
	
	public CStructTypeobj() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructTypeobj(String MyIDUnique, String MyCLassTypeobj, String MyNameTypeobj) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassTypeobj = MyCLassTypeobj;
		this.MyNameTypeobj = MyNameTypeobj;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassTypeobj()
    {
        return MyCLassTypeobj;
    }

    public String getMyNameTypeobj()
    {
        return MyNameTypeobj;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassTypeobj(String MyCLassTypeobj)
    {
        this.MyCLassTypeobj = MyCLassTypeobj;
    }
    public void setMyNameTypeobj(String MyNameTypeobj)
    {
        this.MyNameTypeobj = MyNameTypeobj;
    }
}
