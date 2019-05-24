package mygpsx;

public class CStructSysUser 
{
	private String MyIDSysUser;
	private String MyEmail;
	private String MyPass;
	private String MyPhoneBinding;
	private String myCurrentTaskID;// Else noting, but "none" or ID of Task!!!
	private String myIsRules; // Права пользователя:
								// 0 - Администратор - МОЖЕТ ВСЕ!!!
								// 1 - Дисптечер - МОЖЕТ ПОЧТИ ВСЕ!!! т.е. практически ничего, только  отслеживать, переписываться и делать обмен файлами пока что...
								// 2 - Судно-Пользователь, он может региться на андройде и пока это ВСЕ!
	
	public CStructSysUser()
	{
		
	}
	
	public CStructSysUser(String MyIDSysUser, String MyEmail, String MyPass, String MyPhoneBinding, String myCurrentTask, String myIsRules)
	{
		this.MyIDSysUser = MyIDSysUser;
		this.MyEmail = MyEmail;
		this.MyPass = MyPass;
		this.MyPhoneBinding = MyPhoneBinding;
		this.myCurrentTaskID = myCurrentTask;
		this.myIsRules = myIsRules;
	}
	public String getMyIsRules()
    {
    	return myIsRules;
    }
	public String getMyIDSysUser()
    {
    	return MyIDSysUser;
    }
    public String getMyEmail()
    {
    	return MyEmail;
    }
    public String getMyPass()
    {
    	return MyPass;
    }
    public String getMyPhoneBinding()
    {
    	return MyPhoneBinding;
    }
    public String getMyCurrentTaskID()
    {
    	return myCurrentTaskID;
    }
    /////////////////////////////////////SET////////////////////////////////////////
    public void setMiId(String myIsRules)
    {
        this.myIsRules = myIsRules;
    }
    public void setMyIDSysUser(String MyIDSysUser)
    {
        this.MyIDSysUser = MyIDSysUser;
    }
    public void setMyEmail(String MyEmail)
    {
        this.MyEmail = MyEmail;
    }
    public void setMyPass(String MyPass)
    {
        this.MyPass = MyPass;
    }
    public void setMyPhoneBinding(String MyPhoneBinding)
    {
        this.MyPhoneBinding = MyPhoneBinding;
    }
    public void setMyCurrentTaskID(String myCurrentTaskID)
    {
        this.myCurrentTaskID = myCurrentTaskID;
    }
    
    @Override
    public String toString()  {
        return (this.getMyEmail());
    }
}
