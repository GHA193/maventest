package com.sdut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdut.dao.UsersMapper;
import com.sdut.model.Users;
import com.sdut.service.UsersService;
import com.sdut.utils.MailUtils;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersMapper userMapper;
	
	public int saveUsers(Users user) {
		
		int num = userMapper.saveUsers(user);
		// 发送用户激活邮件
		try {
			MailUtils.sendMail(user.getEmail(), user.getActivecode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return num;
	}

	public int delUsers(int id) {
		// TODO Auto-generated method stub
		return userMapper.delUsers(id);
	}

	public int updateUsers(Users user) {
		// TODO Auto-generated method stub
		return userMapper.updateUsers(user);
	}

	public Users findUsersById(int id) {
		// TODO Auto-generated method stub
		return userMapper.findUsersById(id);
	}

	public List<Users> findUsersList() {
		// TODO Auto-generated method stub
		return userMapper.findUsersList();
	}

	public Users login(Users user) {
		// TODO Auto-generated method stub
		return userMapper.login(user);
	}

	@Override
	public Users findUserByCode(String code) {
		// TODO Auto-generated method stub
		return userMapper.findUserByCode(code);
	}

	@Override
	public Users findUserByName(String username) {
		// TODO Auto-generated method stub
		return userMapper.findUserByName(username);
	}

	

}
