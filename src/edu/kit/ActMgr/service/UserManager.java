package edu.kit.ActMgr.service;

import javax.servlet.http.HttpSession;

import edu.kit.ActMgr.bean.UserBean;

public interface UserManager 
{
	public static final int LOGIN_SUCCESS=0;
	public static final int PASSWORD_WRONG=1;
	public static final int NO_USING_ACCOUNT_BOOK=2;
	
	/**
	 * 得到当前用户
	 * @param session
	 * @return 当前用户
	 */
	UserBean getUser(HttpSession session);
	
	/**
	 * 处理用户注册
	 * @param uname 用户名
	 * @param email 邮箱
	 * @param password 密码
	 * @return 用户id
	 */
	int register(String uname,String email,String password);
	
	/**
	 * 判断注册邮箱是否存在
	 * @param email 邮箱
	 * @return 邮箱是否存在
	 */
	boolean isEmailExist(String email);
	
	/**
	 * 用户登录
	 * @param email 邮箱
	 * @param password 密码
	 * @return 登录是否成功 成功返回0 密码错误返回1 不存在账本返回2
	 */
	int login(String email,String password,HttpSession session);
	
	/**
	 * 验证密码是否正确
	 * @param password 密码
	 * @param session
	 * @return 验证结果
	 */
	boolean isPasswordRight(String password,HttpSession session);
	
	/**
	 * 修改密码
	 * @param password 新密码
	 * @param session
	 * @return 修改结果
	 */
	boolean modifyPassword(String password,HttpSession session);
	
	/**
	 * 检查用户session是否过期
	 * @param session
	 * @return
	 */
	boolean checkSession(HttpSession session);

	/**
	 * 手动刷新Session
	 * @param session
	 * @return
	 */
	boolean refreshSession(HttpSession session);
	
	/**
	 * iOS客户端用户登录
	 * @param email 邮箱
	 * @param password 密码
	 * @return 登录是否成功 成功返回0 密码错误返回1 不存在账本返回2
	 * @return
	 */
	int iOSLogin(String email,String password);
}
