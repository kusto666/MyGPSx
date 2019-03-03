package mygpsx;

// Структура приоритетов задач!!!
public class CStructPriority 
{
	private String MyIDUnique;
	private String MyCLassPriority;
	private String MyNamePriority;
	
	public CStructPriority() {// ОБЯЗАТЕЛЬНО УКАЗЫВАЕМ, А ТО БУДЕТ ОШИБКА ТИПА КАК ЗДЕСЬ БЫЛО:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructPriority(String MyIDUnique, String MyCLassPriority, String MyNamePriority) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassPriority = MyCLassPriority;
		this.MyNamePriority = MyNamePriority;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassPriority()
    {
        return MyCLassPriority;
    }

    public String getMyNamePriority()
    {
        return MyNamePriority;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassPriority(String MyCLassPriority)
    {
        this.MyCLassPriority = MyCLassPriority;
    }
    public void setMyNamePriority(String MyNamePriority)
    {
        this.MyNamePriority = MyNamePriority;
    }
    @Override
    public String toString()  {
        return (this.getMyNamePriority());
    }
}
