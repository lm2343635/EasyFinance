package edu.kit.ActMgr.dao;

import java.util.List;

import edu.kit.ActMgr.domain.User;

public interface UserDao 
{
	User get(Integer uid);
	Integer save(User user);
	void update(User user);
	void delete(User user);
	void delete(Integer uid);
	List<User> findAll();
	User findByEmail(String email);
}
