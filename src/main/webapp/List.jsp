<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<%@ page import ="java.util.*" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Page</title>
</head>
<body>
    <form action="post" method="POST">
        <!-- Add the hidden inputs as described in 144 discussion session on Friday-->
        <div>
        <input type="hidden"  name="username"  value='<%= request.getAttribute("username") %>'>
        <input  type="hidden" name="postid" value=0>
        
            <button type="submit" name="action" value="open">New Post</button>
        
        </div>
    </form>


    <table border="1">
        <tr>
            <th> Title</th>
            <th> Created</th>
            <th> Modified</th>
            <th> Actions</th>
        </tr>
    </table>
        

            <% ArrayList<String> ltitle = (ArrayList)request.getAttribute("ltitle");%>
            <% ArrayList<String> lmodified = (ArrayList)request.getAttribute("lmodified");%>
            <% ArrayList<String> lcreated = (ArrayList)request.getAttribute("lcreated");%>
            <% ArrayList<Integer> lid = (ArrayList)request.getAttribute("lid");%>
            <% Object postcount= request.getAttribute("lcount") ;%>
            <% int lcount=(postcount == null) ? 0 : Integer.parseInt(postcount.toString());%> 
    <% for (int lposts=0; lposts<lcount; lposts++){%>
    <form  method="POST" action="post">
        <input type="hidden"  name="postid"  value='<% out.print(lid.get(lposts)); %>'>
        <td><% out.print(ltitle.get(lposts)); %></td>
        <td><% out.print(lcreated.get(lposts)); %></td>
        <td><% out.print(lmodified.get(lposts)); %></td>
        <td><button type="submit" name="action" value="open">Open</button>
            <button type="submit" name="action" value="delete">Delete</button></td>
        <input type="hidden"  name="username"  value='<%= request.getAttribute("username") %>'>
    </form>
<% } %>
        



    
</body>
</html>
