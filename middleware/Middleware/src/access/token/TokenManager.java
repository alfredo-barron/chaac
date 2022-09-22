package access.token;

import java.util.Arrays;
import java.util.LinkedList;

public class TokenManager {

    private TokenGenerate generate;
    private LinkedList<TokenParts> tokenParts = new LinkedList<>();

    public TokenManager() {}

    public TokenManager(TokenGenerate generate) {
        this.generate = generate;
    }

    public void setGenerate(TokenGenerate generate) {
        this.generate = generate;
    }

    public boolean addTokenParts(TokenParts tokenParts) {
        return this.tokenParts.add(tokenParts);
    }

    public boolean removeTokenParts(TokenParts tokenParts) {
        return this.tokenParts.removeFirstOccurrence(tokenParts);
    }

    private String combineTokenParts(String outComeMsg[]) {
        StringBuffer buffer = new StringBuffer();
        Arrays.asList(outComeMsg).forEach(buffer::append);
        this.tokenParts.forEach(x -> buffer.append(x.getParts()));
        return buffer.toString();
    }

    public String generateToken(String outComeMsg[]) {
        assert this.generate != null;
        return clipToken(this.generate.generate(this.combineTokenParts(outComeMsg)));
    }

    public boolean authenticateToken(String token, String outComeMsg[]) {
        return this.generateToken(outComeMsg).equals(token);
    }

    private String clipToken(String token){
        return token;
    }

}
