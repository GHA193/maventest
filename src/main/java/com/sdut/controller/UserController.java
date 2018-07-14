package com.sdut.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdut.model.Users;
import com.sdut.service.UsersService;
import com.sdut.utils.DateUtils;
import com.sdut.utils.MailUtils;
import com.sdut.utils.Md5Utils;

import com.sdut.utils.UUIDUtils;

@Controller
public class UserController {

	@Autowired
	private UsersService userService;

	@RequestMapping("showReg")
	public String showReg(String type, HttpServletRequest request) {
		if ( type!=null && type.equals("1")) {
			request.setAttribute("msg", "两次密码不一致");
		} else if (type!=null && type.equals("2")){
			request.setAttribute("msg", "验证码输入错误");
		} else if (type!=null && type.equals("3")) {
			request.setAttribute("msg", "注册失败");
		}
		//返回reg.jsp页面
		return "reg";
	}
	
	@RequestMapping("reg")
	public String reg(Users user, String repassword, String checkcode, String username, HttpServletRequest request) {
		user.setRole("user");
		user.setUpdatetime(DateUtils.formatDate(new Date()));
		//激活码为0，表示激活状态
		user.setState(0);
		
		//使用md5加密 密码
		String password = user.getPassword();
		if(!password.equals(repassword)) {
			//重定向是重定向到某一个controller,
			//请求转发是到一个jsp页面,但是会携带之前的信息
			//?type=1是带了一个参数
			return "redirect:showReg?type=1";
		}
		
		//使用MD5对密码进行加密
		//String password = user.getPassword();
		user.setPassword(Md5Utils.md5(password));
		//设置用户状态,0表示没有激活,1激活
		
		//生成一个激活码,UUID生成一串32位的16进制的数字
		String uuid = UUIDUtils.getUUID();
		user.setActivecode(uuid);
		
		//验证验证码是否正确
		//从session当中获取系统生成的验证码
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		//拿用户输入的验证码与生成的验证码进行比对
		if(!checkcode.equals(checkcode_session)) {
			return "redirect:showReg?type=2";
		}
		//调用service进行保存
		int num = userService.saveUsers(user);
		if(num==0) {
			return "redirect:showReg?type=3";
		}
		
		
		return "redirect:showLogin?type=1";
	}
	
	@RequestMapping("showLogin")
	public String showLogin(String type,Model model) {
		
		if(type != null && type.equals("1")) {
			model.addAttribute("msg","注册成功,请登录");
		}else if(type != null && type.equals("2")) {
			model.addAttribute("msg","用户名或密码错误");
		}else if(type != null && type.equals("0")) {
			model.addAttribute("msg","");
		}else if(type != null && type.equals("3")) {
			model.addAttribute("msg","没有登录请登录");
		}
		return "login";
	}
	
	//激活账户的方法
	@RequestMapping("activation")
	public String activation(String code) {
		//根据激活码查询用户
		Users user = userService.findUserByCode(code);
		//MailUtils.sendMail("ok@book1.com", code);
		
		//将账户状态改为1
		user.setState(1);
		userService.updateUsers(user);
		
		return "login";
	}
	
	//异步校验用户名
	@RequestMapping("checkName")
	@ResponseBody
	public String checkName(String username) {
		System.out.println(username);
		//根据用户名查询是否存在该用户名
		Users user = userService.findUserByName(username);		
		
		//用户存在
		if(user!=null) {
			return "{\"msg\":\"false\"}";
		}
		
		return "{\"msg\":\"true\"}";
	}
	
	//登录验证
	@RequestMapping("checkLogin")
	@ResponseBody
	public String checkLogin(Users user, String remember, String autologin, HttpServletResponse response, HttpServletRequest request) throws Exception {
		System.out.println(user);
		Users users = null;
		String password = user.getPassword();
		user.setPassword(Md5Utils.md5(password));
		
		users = userService.login(user);
		if(users!=null) {
			System.out.println("登录成功");
		}else {
			System.out.println("用户名或密码错误");
//			return "redirect:showLogin?type=2";
			return "{\"msg\":\"false\"}";
		}
		
		//记录用户名
		//判断用户是否勾选  记住用户  选项
		if(remember!=null&&remember.equals("on")) {
			//将用户名以cookie的形式发送到本地
			Cookie cookie = new Cookie("username", URLEncoder.encode(users.getUsername(), "utf-8"));
			//cookie默认为会话级别，浏览器关闭，cookie会消失
			//设置cookie最大存活时间，单位是秒
			cookie.setMaxAge(60*60);//存活1小时
			
			response.addCookie(cookie);
			
			//默认打勾
			
			Cookie cookie1 = new Cookie("save", "on");
			cookie1.setMaxAge(60*60);//存活1小时
			response.addCookie(cookie1);
		}else {
			//将用户名以cookie的形式发送到本地
			Cookie cookie = new Cookie("username", "");
			//cookie默认为会话级别，浏览器关闭，cookie会消失
			//设置cookie最大存活时间，单位是秒
			cookie.setMaxAge(0);//存活0小时
			
			response.addCookie(cookie);
			
			//默认打勾
			
			Cookie cookie1 = new Cookie("save", "");
			cookie1.setMaxAge(0);//存活0小时
			response.addCookie(cookie1);
		}
		//是否设置自动登录
		if(autologin!=null&&autologin.equals("on")) {
			Cookie cookie = new Cookie("autologin", URLEncoder.encode(users.getUsername(), "utf-8")+"-"+user.getPassword());
			cookie.setMaxAge(60*60*24*7);
			response.addCookie(cookie);
		}
		
		//将登录的用户放到session中
		request.getSession().setAttribute("user", users);
		
		
		//登录角色判断
		//如果是管理员，跳转到后台页面
		if(users.getRole().equals("admin")) {
//		return "redirect:showAdminIndex";
			return "{\"msg\":\"admin\"}";
		}
		
		
//		return "redirect:showIndex";
		return "{\"msg\":\"true\"}";
	}
	
	//推出登录
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		//删除session中的登录对象
		//session.removeAttribute("user");
		
		//销毁session
		session.invalidate();
		
		//去除自动登录功能
		//将自动登录cookie中的信息清空
		Cookie cookie = new Cookie("autologin", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "redirect:showIndex";
	}
	
}





