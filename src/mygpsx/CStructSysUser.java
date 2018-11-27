package mygpsx;

public class CStructSysUser 
{
	private String MyIDSysUser;
	private String MyEmail;
	private String MyPass;
	
	public CStructSysUser()
	{
		
	}
	
	public CStructSysUser(String MyIDSysUser, String MyEmail, String MyPass)
	{
		this.MyIDSysUser = MyIDSysUser;
		this.MyEmail = MyEmail;
		this.MyPass = MyPass;
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
    
    @Override
    public String toString()  {
        return (this.getMyEmail());
    }
}
