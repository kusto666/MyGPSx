package mygpsx;

//Структура типов задач!!!
public class CStructTypejob {
	private String MyIDUnique;
	private String MyCLassTypejob;
	private String MyNameTypejob;
	
	public CStructTypejob() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
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
