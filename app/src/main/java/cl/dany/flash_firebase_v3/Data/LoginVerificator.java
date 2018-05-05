package cl.dany.flash_firebase_v3.Data;

public class LoginVerificator {
    private LoginCallback callBack;

    public LoginVerificator(LoginCallback callBack) {
        this.callBack = callBack;
    }

    public void checkLogin()
    {
        if(new CurrentUser().getCurrentUser() != null)
        {
            callBack.logged();
        }else
        {
            callBack.signUp();
        }
    }
}
