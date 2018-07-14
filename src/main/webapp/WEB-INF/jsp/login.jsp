<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 将jstl标签库引入到当前页面 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>欢迎登陆</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
    <link rel="stylesheet" href="css/public.css" type="text/css" media="screen" />
    <link href="css/css.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-3.3.1.js">//引入jquery框架
  	</script>
    <script type="text/javascript">
    	//页面加载事件
    	$(function(){
    		//获取cookie信息
    		var username = "${cookie.username.value}"
    		//中文解码    参数 ：要解码的字符串   解码规则
    		username = window.decodeURI(username,"utf-8");
    		//alert(username);
    		
    		//document.getElementById("username").value = username;
    		//jquery方式
    		$("#username").val(username);
    		var save = "${cookie.save.value}"
    		if(save=="on"){
    			document.getElementById("remember").checked=true;
    			
    		}
    	});
    
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
        <form action="checkLogin" method="post" onsubmit="return checkForm();" id="loginform">
            <input type="hidden" name="method" value="login">
            
            <table>
                <tr>
                    <td>用户名:</td>
                    <td>
                        <input type="text" name="username" id="username" class="a" required="required">
                        <span id="username_span"></span>
                    </td>
                </tr>


                <tr>
                    <td>密码:</td>
                    <td>
                        <input type="password" name="password" id="password" class="a" required="required">
                        <span id="password_span"></span>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="remember" id="remember" value="on" style="width: 25px;line-height: 35px;"/>记住用户 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="checkbox" name="autologin" value="on" style="width: 25px"/>自动登录
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="submit" value="登录" class="a" style="color:white;background:#87520E;font-size:20px;cursor:pointer">&nbsp;&nbsp;

                        <a href="javascript:void(0)">
                            <font style="font-size:15px;color:red;">&nbsp;忘记密码?</font>
                        </a>
                        <br>
                        ${msg }
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
		function checkUser(){
			var flag=false;
			//获取用户输入的用户名
			var username = document.getElementById("username").value;
			var password = document.getElementById("password").value;
			
			//alert("----"+username+"----");
			//去除字符串两端空格
			username = username.trim();
			//var username = $("#username").val();
			//alert("----"+$.trim(username)+"----");
			
			
			//通过外部的js定义非空验证和长度验证
			//var name = checknamenull(username);
			
			//var namelen = checknamelength(username);
			
			
			
			if(username==""){
				document.getElementById("username_span").innerHTML="用户名不能为空";
				flag = false;
			}else{
				document.getElementById("username_span").innerHTML="";
				//用户名不为空，校验用户名是否存在
				//使用ajax异步校验
				$.ajax({
					url:"/estore/checkLogin?",
					type:"POST",
					data:{"username":username,"password":password},
					dataType:"json",
					async:false,
					success:function(data){
						if(data.msg=="false"){
							alert("用户名或密码错误");
							document.getElementById("password").value="";
							flag = false;
						}else if(data.msg=="true"){
							document.getElementById("username_span").innerHTML="";
							flag = true;
						}else{
							document.getElementById("username_span").innerHTML="";
							flag = "admin";
						}
					}
				});
			}
			return flag;
			
		}
		function checkForm(){
			var user = checkUser();
			if(user==true){
				window.location.href="http://127.0.0.1:8080/estore/showIndex";
				//return true;
			}else if(user=="admin"){
				window.location.href="http://127.0.0.1:8080/estore/showAdminIndex";
			}
			return false;
		}
	
	
	
	</script>


</body>

</html>