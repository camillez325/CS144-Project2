<html>
<head>
    <meta charset="UTF-8">
    <title>List Page</title>
</head>
<body>
    <div><h1>List Page</h1></div>
    <form>
        <div>
            <button type="submit">New Post</button>
            
            
     <h3>Title     Createds    Modified</h3>       
            
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
            /*changed id to title here*/
        </div>
        <div>
            <label for="body">Body</label>
            <textarea style="height: 20rem;" id="body"></textarea>
        </div>
    </form>
</body>
</html>
