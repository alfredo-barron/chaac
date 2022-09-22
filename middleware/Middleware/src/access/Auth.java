package access;

import java.text.SimpleDateFormat;
import java.util.Date;

import access.token.MDBuilder;
import access.token.TokenManager;

public class Auth {

    private SimpleDateFormat sdf;
    private String key = "yamerito";
    private TokenManager tkm;

    public Auth() {
        this.sdf = new SimpleDateFormat("YYYY-MM-dd");
        tkm = new TokenManager(MDBuilder::getMD5);
    }

    public String createAccessToken(String msg) {
        String[] msgs = new String[]{msg};
        tkm.addTokenParts(() -> key);
        tkm.addTokenParts(() -> sdf.format(new Date()));
        return tkm.generateToken(msgs);
    }

    public boolean authToken(String token, String msg) {
        String[] msgs = new String[]{msg};
        return tkm.authenticateToken(token, msgs);
    }

}
