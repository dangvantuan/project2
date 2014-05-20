package com.course.mvp.demo.shared;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {
	@Id
	String userName;
	String password;
	int levelCompleted;

	public User() {
	};

	public User(String userName, String password, int levelCompleted) {
		this.userName = userName;
		this.password = password;
		this.levelCompleted = levelCompleted;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevelCompleted() {
		return levelCompleted;
	}

	public void setLevelCompleted(int levelCompleted) {
		this.levelCompleted = levelCompleted;
	}

}
