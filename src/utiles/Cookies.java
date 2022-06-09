package utiles;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Cookies {
	
	public static Cookie getCookie(String name, HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		
		Cookie cookie = null;
		
		for(int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals(name)) {
				cookie = cookies[i];
			}
		}
		return cookie;
	}

}
