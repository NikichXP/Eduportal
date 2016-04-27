package eduportal.model;

import java.util.*;
import eduportal.dao.UserDAO;
import eduportal.dao.entity.*;
import eduportal.util.AuthToken;

public class AuthContainer {

	private static HashMap<String, AuthSession> sessions = new HashMap<>();
	
	public static ArrayList<String> testMethod () {
		ArrayList<String> ret = new ArrayList<>();
		for (String s : sessions.keySet()) {
			ret.add("Session: " + s + "  ==  "+ sessions.get(s).getUser());
			ret.add("Token_t: " + s + "  ==  "+ (((double)sessions.get(s).getTimeout() - System.currentTimeMillis())/(1000*3600)));
			ret.add("Access : " + s + "  ==  "+ sessions.get(s).getAccessLevel());
		}
		return ret;
	}
	
	/**
	 * Session timeout
	 */
	public static final long SESSION_TIME = 3600 * 1000; // 1h

	/**
	 * Authenticate user
	 * 
	 * @param login
	 *            - user's login
	 * @param pass
	 *            - user's pass
	 * @return String token if login and pass are true; null if bad credentials
	 */
	public static AuthToken authenticate (String login, String pass) {
		UserEntity user = UserDAO.get(login, pass);
		if (user == null) {
			return null;
		}
		String token = UUID.randomUUID().toString();
		AuthSession session = new AuthSession (user);
		sessions.put(token, session);
		AuthToken ret = new AuthToken();
		ret.setSessionId(token);
		ret.setTimeoutTimestamp(System.currentTimeMillis() + SESSION_TIME);
		return ret;
	}

	/**
	 * Return user if token is valid & session not over
	 * 
	 * @param token - session id
	 * @return - User
	 */
	public static UserEntity getUser(String token) {
		UserEntity u = sessions.get(token).getUser();
		if (u != null) {
			if (sessions.get(token).getTimeout() > System.currentTimeMillis()) {
				return u;
			}
		}
		return null;
	}
	
	public static int getAccessGroup (String token) {
		return sessions.get(token).getAccessLevel();
	}

	/**
	 * Reset session timeout
	 * 
	 * @param token
	 *            - sessionID to update
	 */
	public static void updateTimeout(String token) {
		if (sessions.get(token).getTimeout() < System.currentTimeMillis()) {
			sessions.get(token).setTimeout(System.currentTimeMillis() + SESSION_TIME);
		}
	}
}
