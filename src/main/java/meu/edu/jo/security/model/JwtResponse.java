package meu.edu.jo.security.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String token;
 

    public JwtResponse(String token) {
        this.token = token;

    }

    public String getToken() {
        return this.token;
    }


}