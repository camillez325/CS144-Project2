<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!-- Need to change the header here eventually-->
     <link rel="stylesheet" type="text/css" href="./project2.css">
    <title>Preview Post</title>
</head>
<body>
    <div><h1>Preview Post</h1></div>
    <form>
        <input type="hidden"  name="username"  value='<%= request.getAttribute("username") %>'>
        <input  type="hidden" name="postid" value='<%= request.getAttribute("postid") %>'>
        <div>

            <button type="submit" name="action" value="open">Close Preview</button>

<!-- so eventually we can implement these action buttons
    maybe we could test these one at a time

            <button type="submit" name="action" value="list">Close Preview</button>
    
-->

</form>
<h1><%= request.getAttribute("mktitle") %></h1>
<h2><%= request.getAttribute("mkbody") %></h2>
         



</body>
</html>
