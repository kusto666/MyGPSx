package mygpsx;

public class CStructSysUser 
{
	private String MyIDSysUser;
	private String MyEmail;
	private String MyPass;
	private String MyPhoneBinding;
	private String myCurrentTaskID;// Else noting, but "none" or ID of Task!!!
	
	public CStructSysUser()
	{
		
	}
	
	public CStructSysUser(String MyIDSysUser, String MyEmail, String MyPass, String MyPhoneBinding, String myCurrentTask)
	{
		this.MyIDSysUser = MyIDSysUser;
		this.MyEmail = MyEmail;
		this.MyPass = MyPass;
		this.MyPhoneBinding = MyPhoneBinding;
		this.myCurrentTaskID = myCurrentTask;
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
