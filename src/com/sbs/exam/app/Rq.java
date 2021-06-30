package com.sbs.exam.app;

import java.util.HashMap;
import java.util.Map;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Member;

import lombok.Getter;

public class Rq {
	private Map<String, String> params;
	private String command;
	@Getter
	private String controllerTypeCode;
	@Getter
	private String controllerName;
	private String actionMethodName;
	private String queryString = "";
	@Getter
	private boolean isValid = true;

	public Rq() {
	}

	public void setCommand(String command) {
		this.command = command;

		params = new HashMap<>();

		String[] commandBits = command.split("\\?", 2);

		if (commandBits.length == 2) {
			queryString = commandBits[1];

			String[] queryStringBits = queryString.split("&");

			for (String queryStringBit : queryStringBits) {
				String[] queryStringBitBits = queryStringBit.split("=", 2);
				String paramName = queryStringBitBits[0];
				String paramValue = queryStringBitBits[1];

				params.put(paramName, paramValue);
			}
		}

		commandBits = commandBits[0].split("/", 4);

		if (commandBits.length != 4) {
			isValid = false;
			return;
		}

		controllerTypeCode = commandBits[1];
		controllerName = commandBits[2];
		actionMethodName = commandBits[3];
	}

	public String getActionPath() {
		return "/" + controllerTypeCode + "/" + controllerName + "/" + actionMethodName;
	}

	public int getIntParam(String paramName, int defaultValue) {
		if (params.containsKey(paramName) == false) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(params.get(paramName));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public String getStrParam(String paramName, String defaultValue) {
		if (params.containsKey(paramName) == false) {
			return defaultValue;
		}
		return params.get(paramName);
	}

	private void setSessionAttr(String key, Object value) {
		Session session = Container.getSession();

		session.setAttribute(key, value);
	}

	private Object getSessionAttr(String key) {
		Session session = Container.getSession();

		return session.getAttribute(key);
	}

	private void removeSessionAttr(String key) {
		Session session = Container.getSession();

		session.removeAttribute(key);
	}

	private boolean hasSessionAttr(String key) {
		Session session = Container.getSession();

		return session.hasAttribute(key);
	}

	public Member getLoginedMember() {
		return (Member) getSessionAttr("loginedMember");
	}

	public boolean isLogined() {
		return hasSessionAttr("loginedMember");
	}

	public void logout() {
		removeSessionAttr("loginedMember");
	}

	public void login(Member member) {
		setSessionAttr("loginedMember", member);
	}

	

}
