package mygpsx;

import com.google.firebase.database.IgnoreExtraProperties;

/////////////////////////////////////////////////////////////
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
/////////////////////////////////////////////////////////////

@IgnoreExtraProperties
public class CStructUser 
{
	private String MyFreeNameFirst;// Первое имя(название) в свободной форме - пригодиться, например для первых пустых строк(ComboBox`ов)
	private String MyPhoneID;
	private String MyLatitude;
	private String MyLongitude;
	private String MyEmail;

    
    private String MyNameShip;
    private String MyDirectorShip;
    private String MyShortDescriptionShip;
    
    private String MyIsUserSelected; // Переменная отвечает за то что обект выбран - true, и наоборот - false!!!
	private String MyPass;
	private String mySysUserBinding;// Показывает связано ли судно с сисемным пользователем!!!
   
    
    enum GENDER {
        MALE,
        FEMALE
    }

    public CStructUser() 
    {

    }

    
   /* public CStructUser(String MyPhoneID, String MyNameShip, String MyDirectorShip) 
    {
        this.MyPhoneID = MyPhoneID;
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
    }*/

    public CStructUser(
    			 String MyFreeNameFirst,
    			 String MyPhoneID,
    			 String MyLatitude, 
    			 String MyLongitude,  
    			 String MyEmail,
    			 String MyNameShip,
    			 String MyDirectorShip,
    			 String MyShortDescriptionShip, 
    			 String MyIsUserSelected,
    			 String MyPass,
    			 String mySysUserBinding) 
    {
    	this.MyFreeNameFirst = MyFreeNameFirst;
    	
        this.MyPhoneID = MyPhoneID;
        this.MyLatitude = MyLatitude;
        this.MyLongitude = MyLongitude;
        this.MyEmail = MyEmail;
        
        this.MyNameShip = MyNameShip;
        this.MyDirectorShip = MyDirectorShip;
        this.MyShortDescriptionShip = MyShortDescriptionShip;
        this.MyIsUserSelected = MyIsUserSelected;
        this.MyPass = MyPass;
        this.mySysUserBinding = mySysUserBinding;
    }
    public String getMyFreeNameFirst()
    {
    	return MyFreeNameFirst;
    }
    public String getMyPhoneID()
    {
    	return MyPhoneID;
    }
    public String getMyLatitude()
    {
    	return MyLatitude;
    }
    public String getMyLongitude()
    {
    	return MyLongitude;
    }
    public String getMyEmail()
    {
    	return MyEmail;
    }
    public String getMyShortDescriptionShip()
    {
    	return MyShortDescriptionShip;
    }
    public String getMyDirectorShip()
    {
    	return MyDirectorShip;
    }
    public String getMyNameShip()
    {
    	return MyNameShip;
    }
    public String getMyIsUserSelected()
    {
    	return MyIsUserSelected;
    }
    public String getMyPass()
    {
    	return MyPass;
    }
    public String getMySysUserBinding()
    {
    	return mySysUserBinding;
    }
    /////////////////////////////////////SET////////////////////////////////////////
    public void setMyFreeNameFirst(String MyFreeNameFirst)
    {
        this.MyFreeNameFirst = MyFreeNameFirst;
    }
    public void setMyPhoneID(String MyPhoneID)
    {
        this.MyPhoneID = MyPhoneID;
    }
    public void setMyLatitude(String MyLatitude)
    {
        this.MyLatitude = MyLatitude;
    }
    public void setMyLongitude(String MyLongitude)
    {
        this.MyLongitude = MyLongitude;
    }
    public void setMyEmail(String MyEmail)
    {
        this.MyEmail = MyEmail;
    }
    public void setMyShortDescriptionShip(String MyShortDescriptionShip)
    {
        this.MyShortDescriptionShip = MyShortDescriptionShip;
    }
    public void setMyDirectorShip(String MyDirectorShip)
    {
        this.MyDirectorShip = MyDirectorShip;
    }
    public void setMyNameShip(String MyNameShip)
    {
        this.MyNameShip = MyNameShip;
    }
    public void setMyIsUserSelected(String MyIsUserSelected)
    {
        this.MyIsUserSelected = MyIsUserSelected;
    }
    public void setMyPass(String MyPass)
    {
        this.MyPass = MyPass;
    }
    public void setMySysUserBinding(String mySysUserBinding)
    {
        this.mySysUserBinding = mySysUserBinding;
    }
    @Override
    /*public String toString()  {
        return ("Капитан: '" + this.getMyDirectorShip() + "', Судно: '" + this.getMyNameShip() + "'");
    }*/
    public String toString()  {
        return (this.getMyFreeNameFirst());
    }
  
}