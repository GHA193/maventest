package com.sdut.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sdut.dao.UsersMapper;
import com.sdut.model.Users;
import com.sdut.utils.CookieUtils;

/**
 * filter过滤器 每一个过滤器都应该实现filter接口 可以使用过滤器拦截所有的请求，对请求进行验证，如果请求合法，就放行，否则拦截
 * 
 * 权限控制，登录认证 日志
 * 
 * @author Administrator
 *
 */
public class AutoLoginFilter implements Filter {

	private UsersMapper usersMapper;

	// 对象被销毁时调用的方法
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 验证代码
		System.out.println("拦截到用户的请求");
		// 向下转型
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 获取用户的请求路径
		String uri = request.getRequestURI();

		// 获取项目根路径
		String contextPath = request.getContextPath();

		// 对于登录的请求，注册的请求，不需要进行自动登录
		if (uri.indexOf("Login") == -1 && uri.indexOf("login") == -1 && uri.indexOf("reg") == -1
				&& uri.indexOf("Reg") == -1) {

			// 获取session
			HttpSession session = request.getSession();
			// 获取session中登录的对象
			Users users = (Users) session.getAttribute("user");
			System.out.println(users);

			// 判断用户是否登录
			// 如果users为null，说明没有登录
			if (users == null) {
				// 自动登录
				// 获取所有cookie
				Cookie[] cookies = request.getCookies();
				// 获取需要用到的cookie，即autologin
				Cookie cookie = CookieUtils.getCookie(cookies, "autologin");
				// cookie!=null说明存在用户信息
				if (cookie != null) {
					System.out.println(cookie);
					// 获取cookie中的信息
					String autoLogin = cookie.getValue();
					// 判断cookies中是否有信息，有的话才登录
					if (autoLogin != null && !autoLogin.equals("")) {
						// cookie中的格式为username-password
						// 所以需要进行分割，分别获得用户名和密码
						String[] names = autoLogin.split("-");
						// 将登录信息封装到对象中
						Users user = new Users();
						// 用utf8对用户名解码
						user.setUsername(URLDecoder.decode(names[0], "utf-8"));
						user.setPassword(names[1]);

						// 调用UserMapper进行登录
						Users user2 = usersMapper.login(user);

						// 将登录的对象存放到session中
						session.setAttribute("user", user2);
					}
				} else {
					System.out.println("*******************************");
					// 如果是订单请求，用户必须登录
					if (uri.indexOf("order") != -1 || uri.indexOf("Order") != -1) {
						System.out.println("-----------------------------");
						// 跳转到登录界面,这里使用重定向，因为没有带参数

						// 最原始的重定向方式
						response.sendRedirect("showLogin?type=3");
						return;

						// 请求转发的方式
						// request.getRequestDispatcher("").forward(request, response);
						// Redirect和forward的区别就在这

					}
				}
			}

		}
		// 对请求放行
		chain.doFilter(req, resp);
	}

	// 在创建对象时会自动调用init方法对对象初始化
	@Override
	public void init(FilterConfig config) throws ServletException {
		// 获取到spring容器

		// ServletContext代表整个web应用
		ServletContext context = config.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
		System.out.println(ac);
		usersMapper = ac.getBean(UsersMapper.class);

	}

}
