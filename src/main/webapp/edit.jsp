<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<<<<<<< HEAD
    <title>Edit Post2</title>
</head>
<body>
    <div><h1>Edit Post2</h1></div>
=======
    <title>Edit  12 Post</title>
</head>
<body>
    <div><h1>Edit 12 Post</h1></div>
>>>>>>> 19b6c8ce64f6d76e83989ac98a8d4597483dcc7d
    <form>
        <div>
            <button type="submit">Save</button>
            <button type="submit">Close</button>
            <button type="submit">Preview</button>
            <button type="submit">Delete</button>
<!-- so eventually we can implement these action buttons
    maybe we could test these one at a time
            <button type="submit" name="action" value="save">Save</button>
            <button type="submit" name="action" value="list">Close</button>
            <button type="submit" name="action" value="preview">Preview</button>
            <button type="submit" name="action" value="delete">Delete</button>
-->


        </div>
        <div>
            <label for="title">Title</label>
            <input type="text" id="title">
            <!--changed id to title here*/-->
        </div>
        <div>
            <label for="body">Body</label>
<<<<<<< HEAD
            <textarea style="height: 20rem;" id="body"><%= request.getAttribute("body") %></textarea>
=======
            <textarea style="height: 20rem;" id="body">
                <%= request.getAttribute("body") %>

            </textarea>
>>>>>>> 19b6c8ce64f6d76e83989ac98a8d4597483dcc7d
        </div>
    </form>
</body>
</html>
