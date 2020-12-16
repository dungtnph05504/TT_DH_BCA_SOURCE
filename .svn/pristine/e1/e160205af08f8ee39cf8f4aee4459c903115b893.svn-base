<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="homeUrl" value="/servlet/user/home" />
<style>
.button_grey{
	background-color: #a90329;
	background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #a90329), color-stop(100%, #6d0019));
	background-image: -webkit-linear-gradient(top, #a90329, #6d0019);
	background-image: -moz-linear-gradient(top, #a90329, #6d0019);
	background-image: -ms-linear-gradient(top, #a90329, #6d0019);
	background-image: -o-linear-gradient(top, #a90329, #6d0019);
	background-image: linear-gradient(top, #a90329, #6d0019);
    /*filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#a90329, endColorstr=#6d0019);*/
    border: 1px solid #72021c;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	font-family: arial, helvetica, sans-serif;
	padding: 4px;
	text-shadow: -1px -1px 0 rgba(0,0,0,0.3);
	font-weight: bold;
	text-align: center;
	color: #FFFFFF;
}
.button_grey:hover{
  /*border:1px solid #450111; */
  border: 1px solid #72021c;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
  background-color: #77021d;
 background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#77021d), color-stop(100%, #3a000d));
 background-image: -webkit-linear-gradient(top, #77021d, #3a000d);
 background-image: -moz-linear-gradient(top, #77021d, #3a000d);
 background-image: -ms-linear-gradient(top, #77021d, #3a000d);
 background-image: -o-linear-gradient(top, #77021d, #3a000d);
 background-image: linear-gradient(top, #77021d, #3a000d);
/* filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#77021d, endColorstr=#3a000d);*/
}
 </style>
<script>
	function gotoHomePage(form){
		form.submit();
	}

</script>
<form:form id="homeForm" modelAttribute="homeModel" action="${homeUrl}">

<table width="100%" align="center" style="vertical-align: middle;">
	    <tr>
	      <td align="center">
	      	Trang được yêu cầu không tìm thấy, vui lòng kiểm tra và thử lại.
	      </td>
	    </tr>
	    <tr>
			<td align="center">&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
					 <input align="middle" class="button_grey" type="button" value="Trang chủ" onclick="gotoHomePage(this.form);"/> &nbsp;
			</td>
		</tr>
  </table>
 </form:form>