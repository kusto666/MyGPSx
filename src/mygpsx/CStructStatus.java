package mygpsx;

// ��������� ����������� �����!!!
public class CStructStatus 
{
	private String MyIDUnique;
	private String MyCLassStatus;
	private String MyNameStatus;
	
	public CStructStatus() {// ����������� ���������, � �� ����� ������ ���� ��� ����� ����:
    }// Class mygpsx.CStructPriority is missing a constructor with no arguments !!!!!!!!!!!!!!!!!!
	
	public CStructStatus(String MyIDUnique, String MyCLassStatus, String MyNameStatus) 
	{
		this.MyIDUnique = MyIDUnique;
		this.MyCLassStatus = MyCLassStatus;
		this.MyNameStatus = MyNameStatus;
	}
	
	public String getMyIDUnique()
    {
        return MyIDUnique;
    }

    public String getMyCLassStatus()
    {
        return MyCLassStatus;
    }

    public String getMyNameStatus()
    {
        return MyNameStatus;
    }
    
    public void setMyIDUnique(String MyIDUnique)
    {
        this.MyIDUnique = MyIDUnique;
    }
    public void setMyCLassStatus(String MyCLassStatus)
    {
        this.MyCLassStatus = MyCLassStatus;
    }
    public void setMyNameStatus(String MyNameStatus)
    {
        this.MyNameStatus = MyNameStatus;
    }
}
