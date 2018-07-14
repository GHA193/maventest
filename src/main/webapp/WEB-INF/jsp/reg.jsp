<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 将jstl标签库引入到当前页面 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>欢迎注册</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/public.css" type="text/css" media="screen" />
    <link href="css/css.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-3.3.1.js">//引入jquery框架
  </script>
</head>

<body>
    <div class="topbar">
        <div class="topbar_content">
            <div class="topbar_content_left">
                欢迎光临Estore图书商城
            </div>
            <div class="topbar_content_right">
                <ul>
                    <c:if test="${empty user }">
	                    <li>
	                        <a href="${pageContext.request.contextPath}/showLogin" style="color:rgb(241, 187, 10)">亲，请登录</a>
	                    </li>
	                    <li>
                        	<a href="${pageContext.request.contextPath}/showReg" style="color: rgb(241, 187, 10)">免费注册</a>
                    	</li>
                	</c:if>
                	<c:if test="${!empty user }">
	                    <li>
	                        <a href="#" style="color:rgb(241, 187, 10)">${user.nickname }</a>
	                    </li>
	                    <li>
                       		<a href="${pageContext.request.contextPath}/logout" style="color: rgb(241, 187, 10)">退出登录</a>
                    	</li>
                	</c:if>
                    <li>
                        <a href="${pageContext.request.contextPath}/showIndex">首页</a>
                    </li>
                    <li>|</li>
                    <li>
                        <a href="${pageContext.request.contextPath}/showCart">购物车</a>
                    </li>
                    <li>|</li>
                    <li>
                        <a href="${pageContext.request.contextPath}/showOrders">我的订单</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="estore_content" style="min-height: 800px;">
            <div class="header">
                <div class="header_png"></div>
                <div class="estore" align="center">
                        <h1 style="font-size:35px;text-align:center;padding-top:80px;color: #87520E;">Estore
                                <br/>图书商城
                        </h1>
                   
                </div>
            </div>
            <form action="reg" method="post" onsubmit="return checkForm();" id="registform">
                <input type="hidden" name="method" value="regist">
                <!-- 我们当前是一个注册操作 -->
                <table>
                    <tr>
                        <td>用户名:</td>
                        <td>
                            <input type="text" name="username" id="username" onblur="checkName();" required="required">
                            <span id="username_span"></span>
                        </td>
                    </tr>
    
    
                    <tr>
                        <td>密码:</td>
                        <td>
                            <input type="password" name="password" id="password" required="required" onblur="checkPassword()">
                            <span id="password_span"></span>
                        </td>
                    </tr>
    
                    <tr>
                        <td>确认密码:&nbsp;&nbsp;&nbsp;&nbsp;</td>
                        <td>
                            <input type="password" name="repassword" id="repassword" required="required" onblur="checkRepassword()">
                            <span id="repassword_span"></span>
                        </td>
                    </tr>
    
                    <tr>
                        <td>昵称:</td>
                        <td>
                            <input type="text" name="nickname" id="nickname" required="required" onblur="checkNickname()">
                            <span id="nickname_span"></span>
                        </td>
                    </tr>
    
                    <tr>
                        <td>邮箱:</td>
                        <td>
                            <input type="text" name="email" id="email" required="required" onblur="checkEmail()">
                            <span id="email_span"></span>
                        </td>
                    </tr>
    
                    <tr>
                        <td>验证码:</td>
                        <td>
                            <input type="text" name="checkcode" id="checkcode" required="required">
                            <img src='checkImg' id="im" onclick="change()">
                            <span id="checkcode_span">
                            </span>
                            <br/>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <input type="submit" value="注册" style="color:white;background:#87520E;font-size:20px;cursor:pointer">&nbsp;&nbsp;
    
                            <a href="javascript:void(0)" onclick="change()">
                                <font style="font-size:15px;color:red;">&nbsp;看不清，换一张</font>
                            </a>
                            <br>
                            <font color="red">${msg }</font>
                        </td>
    
    
    
    
                    </tr>
    
                </table>
            </form>
    </div>

    
        <div id="color">
            <h1>&nbsp;</h1>
            <h2>&nbsp;</h2>
            <h3>&nbsp;</h3>
            <h4>&nbsp;</h4>
            <h5>&nbsp;</h5>
            <span>&nbsp;</span>
            <h6>&nbsp;</h6>
            <blockquote>&nbsp;</blockquote>
            <font>&nbsp;</font>
            <div>&nbsp; </div>
        </div>
    </div>
  
    <div class="footer1">
            <p align="center">
                总部地址:北京市海淀区小南庄怡秀园甲一号亿德大厦10层 电话：010-61943240
            </p>
            <p align="center"> Copyright © 2005-2020 北京翡翠教育科技有限公司，All Rights Reserved 京ICP备12036804号-23</p>
        </div>
        
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/check.js"></script>
	<script type="text/javascript">
		//改变验证码函数
		function change(){
			//htmldom:js操作html标签的方式
			//获取到验证码img标签
			var obj = document.getElementById("im");
			//alert(obj.src);
			//改变验证码img的src路径
			obj.src = "checkImg?date="+new Date();
			
		}
		//对用户名进行校验是否存在
		function checkName(){
			var flag=false;
			//获取用户输入的用户名
			var username = document.getElementById("username").value;
			
			//alert("----"+username+"----");
			//去除字符串两端空格
			username = username.trim();
			//var username = $("#username").val();
			//alert("----"+$.trim(username)+"----");
			
			
			//通过外部的js定义非空验证和长度验证
			//var name = checknamenull(username);
			
			//var namelen = checknamelength(username);
			
			
			//非空验证，
			if(username==""){
				
				document.getElementById("username_span").innerHTML="用户名不能为空";
				flag = false;
			}else if(username.length<6 || username.length>16){
				document.getElementById("username_span").innerHTML="用户名长度为6-16位";
			}
			else{
				document.getElementById("username_span").innerHTML="";
				//用户名不为空，校验用户名是否存在
				//使用ajax异步校验
				$.ajax({
					url:"/estore/checkName?",
					type:"POST",
					data:{"username":username},
					dataType:"json",
					async:false,
					success:function(data){
						if(data.msg=="false"){
							document.getElementById("username_span").innerHTML="用户名已存在";
							flag = false;
						}else{
							document.getElementById("username_span").innerHTML="";
							flag = true;
						}
					}
				});
			}
			return flag;
			
		}
		//验证表单
		function checkForm(){
			var name = checkName();
			var email = checkEmail();
			var nickname = checkNickname();
			var password = checkPassword();
			var repassword = checkRepassword();
			if(name && email && nickname && password && repassword){
				return true;
			}
			//alert(flag);
			return false;
		}
		//验证邮箱
		function checkEmail(){
			var email = document.getElementById("email").value;
			//两种正则表达式使用方式
			//var reg = new RegExp("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$");
			var reg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
			var flag = reg.test(email);
			if(flag==false){
				document.getElementById("email_span").innerHTML="请输入正确的邮箱格式";
				return false;
			}else{
				document.getElementById("email_span").innerHTML="";
				return true;
			}
			
		}
		
		//验证密码
		function checkPassword(){
			var password = document.getElementById("password").value;
			//alert(password);
			if(password.length<6 || password.length>20){
				document.getElementById("password_span").innerHTML="密码长度为6-20位";
				return false;
			}else{
				document.getElementById("password_span").innerHTML="";
				return true;
			}
		}
		//二次验证密码
		function checkRepassword(){
			var repassword = document.getElementById("repassword").value;
			var password = document.getElementById("password").value;
			if(repassword!=password){
				document.getElementById("repassword_span").innerHTML="两次密码输入不一致";
				return false;
			}else{
				document.getElementById("repassword_span").innerHTML="";
				return true;
			}
		}
		//验证昵称
		function checkNickname(){
			var nickname = document.getElementById("nickname").value;
			if(nickname.length<1 || password.length>20){
				document.getElementById("nickname_span").innerHTML="昵称长度为1-20位";
				return false;
			}else{
				document.getElementById("nickname_span").innerHTML="";
				return true;
			}
		}
	
	</script>

</body>

</html>