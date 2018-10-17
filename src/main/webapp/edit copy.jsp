<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Post</title>
</head>
<body>
    <div><h1>Edit Post</h1></div>
    <form action="post" method="POST">
        <!-- Add the hidden inputs as described in 144 discussion session on Friday-->
        <input type="hidden"  name="username"  value='<%= request.getAttribute("username") %>'>
        <input  type="hidden" name="postid" value=0>
        <div>
            <button type="submit" name="action" value="Open">New Post</button>
        
        </div>
    </form>


    <table>
    <th> Will this work
    </th>
    </table>
</body>
</html>
