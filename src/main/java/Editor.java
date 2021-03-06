import java.io.IOException;
import java.sql.* ;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class for Servlet: ConfigurationTest
 *
 */
public class Editor extends HttpServlet {
    /**
     * The Servlet constructor
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public Editor() {}

    public static String username = "CS144";
    public static String password = "";
    private PreparedStatement retrievedStmt = null;
    private PreparedStatement postStmt = null;
    private PreparedStatement updateStmt = null;
    private PreparedStatement maxStmt = null;
    private PreparedStatement dltStmt = null;
    private int maxid = 0;

    public void init() throws ServletException
    {
        /*  write any servlet initialization code here or remove this function */
        // try {
        //     Class.forName("com.mysql.jdbc.Driver");
        // } catch (ClassNotFoundException ex) {
        //     System.out.println(ex);
        //     return;
        // }
    
        // Connection c = null;
        // Statement  s = null; 
        // ResultSet rs = null; 

        // try {
        //     /* create an instance of a Connection object */
        //     c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
        //     retrievedStmt = c.prepareStatement(
        //         "SELECT * FROM Posts where username = ? and postid = ?"
        //     );
        //     postStmt = c.prepareStatement(
        //         "INSERT INTO Posts (username, postid, title, body, modified, created) VALUES (?, ?, ?, ?, ?, ?)"
        //     );
        //     updateStmt = c.prepareStatement(
        //         "UPDATE Posts SET title = ?, body = ?, modified = ? WHERE username = ? AND postid = ?"
        //     );    
        // } catch (SQLException ex){
        //     System.out.println("SQLException caught");
        //     System.out.println("---");
        //     while ( ex != null ) {
        //         System.out.println("Message   : " + ex.getMessage());
        //         System.out.println("SQLState  : " + ex.getSQLState());
        //         System.out.println("ErrorCode : " + ex.getErrorCode());
        //         System.out.println("---");
        //         ex = ex.getNextException();
        //     }
        // }
        
    }
    
    public void destroy()
    {
        /*  write any servlet cleanup code here or remove this function */
    }

    /**
     * Handles HTTP GET requests
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
    // implement your GET method handling code here
    // currently we simply show the page generated by "edit.jsp"

    // retrieve parameter vales
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String post_id = request.getParameter("postid");
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        int postid = (post_id == null) ? 0 : Integer.parseInt(post_id);
        //handles open, preview, list
        switch (action) {
            case "open":
                if (postid<=0) {
                request.setAttribute("title","");
                request.setAttribute("body", "");
                request.setAttribute("username",username);
                request.setAttribute("postid", postid);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                                   
                }
                else {Post post = retrievePost(username, postid);
                request.setAttribute("title",post.title);
                request.setAttribute("body", post.body);
                request.setAttribute("username",username);
                request.setAttribute("postid", postid);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);}
                break;

            case "preview":
                Parser parser = Parser.builder().build();
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String mktitle = renderer.render(parser.parse(title));
                String mkbody = renderer.render(parser.parse(body));  
                request.setAttribute("mktitle",mktitle);
                request.setAttribute("mkbody", mkbody);
                request.setAttribute("username",username);
                request.setAttribute("postid", post_id);
                request.getRequestDispatcher("/preview.jsp").forward(request, response);
                break;
            case "list":
                request.setAttribute("username",username);
                List<Integer> lid= new ArrayList<Integer>();
                List<String> ltitle= new ArrayList<String>();
                List<String> lcreated= new ArrayList<String>();
                List<String> lmodified= new ArrayList<String>();
                List<Post> lpost= listposts(username);
                for (Post postloop: lpost)
                {
                    lid.add(postloop.postid);
                    ltitle.add(postloop.title);
                    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    String strC = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.created);
                    String strM = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.modified);
     
                    //String strC = dateFormat.format(postloop.created);
                    //String strM = dateFormat.format(postloop.modified);
                    lcreated.add(strC);
                    lmodified.add(strM);
                }

                request.setAttribute("lcount",lpost.size());
                request.setAttribute("lid",lid);
                request.setAttribute("ltitle",ltitle);
                request.setAttribute("lcreated",lcreated);
                request.setAttribute("lmodified",lmodified);
                request.setAttribute("lpost",lpost);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
                break;
            default:
                System.out.println("ok works");
                request.setAttribute("body", "ok this works");
                request.getRequestDispatcher("/edit.jsp").forward(request, response);

        }
        //request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
    
    /**
     * Handles HTTP POST requests
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException 
    {
    // implement your POST method handling code here
    // currently we simply show the page generated by "edit.jsp"
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String post_id = request.getParameter("postid");
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        int postid = (post_id == null) ? 0 : Integer.parseInt(post_id);
        List<Integer> lid= new ArrayList<Integer>();
        List<String> ltitle= new ArrayList<String>();
        List<String> lcreated= new ArrayList<String>();
        List<String> lmodified= new ArrayList<String>();
        String strC="";
        String strM="";
        List<Post> lpost= null;
        switch (action) {
            case "save":
                // System.out.println("arrived at save");
                doSave(username, postid, title, body);
                request.setAttribute("username",username);

                lpost= listposts(username);
                for (Post postloop: lpost)
                {
                    lid.add(postloop.postid);
                    ltitle.add(postloop.title);
                    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    strC = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.created);
                    strM = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.modified);
     
                    //String strC = dateFormat.format(postloop.created);
                    //String strM = dateFormat.format(postloop.modified);
                    lcreated.add(strC);
                    lmodified.add(strM);
                }

                request.setAttribute("lcount",lpost.size());
                request.setAttribute("lid",lid);
                request.setAttribute("ltitle",ltitle);
                request.setAttribute("lcreated",lcreated);
                request.setAttribute("lmodified",lmodified);
                request.setAttribute("lpost",lpost);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
                break;
            case "delete":
                doDelete(username, postid);
                request.setAttribute("username",username);
                lpost= listposts(username);
                for (Post postloop: lpost)
                {
                    lid.add(postloop.postid);
                    ltitle.add(postloop.title);
                    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    strC = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.created);
                    strM = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.modified);
     
                    //String strC = dateFormat.format(postloop.created);
                    //String strM = dateFormat.format(postloop.modified);
                    lcreated.add(strC);
                    lmodified.add(strM);
                }

                request.setAttribute("lcount",lpost.size());
                request.setAttribute("lid",lid);
                request.setAttribute("ltitle",ltitle);
                request.setAttribute("lcreated",lcreated);
                request.setAttribute("lmodified",lmodified);
                request.setAttribute("lpost",lpost);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
                break;
            case "open":
                if (postid<=0) {
                request.setAttribute("title","");
                request.setAttribute("body", "");
                request.setAttribute("username",username);
                request.setAttribute("postid", postid);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                                   
                }
                else {Post post = retrievePost(username, postid);
                request.setAttribute("title",post.title);
                request.setAttribute("body", post.body);
                request.setAttribute("username",username);
                request.setAttribute("postid", postid);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);}
                break;

            case "preview":
                Parser parser = Parser.builder().build();
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String mktitle = renderer.render(parser.parse(title));
                String mkbody = renderer.render(parser.parse(body));  
                request.setAttribute("mktitle",mktitle);
                request.setAttribute("mkbody", mkbody);
                request.setAttribute("username",username);
                request.setAttribute("postid", post_id);
                request.getRequestDispatcher("/preview.jsp").forward(request, response);
                break;
         case "list":
                request.setAttribute("username",username);
                lpost= listposts(username);
                for (Post postloop: lpost)
                {
                    lid.add(postloop.postid);
                    ltitle.add(postloop.title);
                    //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    strC = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.created);
                    strM = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(postloop.modified);
     
                    //String strC = dateFormat.format(postloop.created);
                    //String strM = dateFormat.format(postloop.modified);
                    lcreated.add(strC);
                    lmodified.add(strM);
                }

                request.setAttribute("lcount",lpost.size());
                request.setAttribute("lid",lid);
                request.setAttribute("ltitle",ltitle);
                request.setAttribute("lcreated",lcreated);
                request.setAttribute("lmodified",lmodified);
                request.setAttribute("lpost",lpost);
                request.getRequestDispatcher("/list.jsp").forward(request, response);
                break;
            
            default:
                System.out.println("ok works");
                request.setAttribute("body", "ok this works");
                request.getRequestDispatcher("/edit.jsp").forward(request, response);


        }
        // request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    // retrieve single post from DB with known username and postid
    Post retrievePost(String username, int postid) {
        //--------//
        Post post = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            return post;
        }
    
        Connection c = null;
        Statement  s = null; 

        try {
            /* create an instance of a Connection object */
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
            retrievedStmt = c.prepareStatement(
                "SELECT * FROM Posts where username = ? and postid = ?"
            );   
        } catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } 
        //--------//
        try {
            retrievedStmt.setString(1, username);
            retrievedStmt.setInt(2, postid);
            ResultSet rs = retrievedStmt.executeQuery();
            while (rs.next()) {
                post = new Post(rs.getString("username"), rs.getInt("postid"), rs.getString("title"), rs.getString("body"), rs.getTimestamp("created") 
                    , rs.getTimestamp("modified"));
            }
        } catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }
        return post;
    }

    private void doSave(String username, int postid, String title, String body) 
    { 
        try {
            if (postid <= 0) {//assign new postid, save content as new post
                //--------//
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                    //return;
                }
            
                Connection c = null;
                Statement  s = null; 
                ResultSet rs = null; 

                try {
                    /* create an instance of a Connection object */
                    c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
                    postStmt = c.prepareStatement(
                        "INSERT INTO Posts (username, postid, title, body, modified, created) VALUES (?, ?, ?, ?, ?, ?)"
                    );   
                    maxStmt = c.prepareStatement(
                        "select Max(postid) postid from Posts where username=? "
                    );    
                } catch (SQLException ex){
                    System.out.println("SQLException caught");
                    System.out.println("---");
                    while ( ex != null ) {
                        System.out.println("Message   : " + ex.getMessage());
                        System.out.println("SQLState  : " + ex.getSQLState());
                        System.out.println("ErrorCode : " + ex.getErrorCode());
                        System.out.println("---");
                        ex = ex.getNextException();
                    }
                }
                //--------//
                maxStmt.setString(1, username);
                rs=maxStmt.executeQuery();
                rs.next();
                int curr_max=rs.getInt("postid");

                postStmt.setString(1, username);
                postStmt.setInt(2, curr_max+1);
                postStmt.setString(3, title);
                postStmt.setString(4, body);
                postStmt.setTimestamp(5, getCurrentTimeStamp());
                postStmt.setTimestamp(6, getCurrentTimeStamp());
                postStmt.executeUpdate();
            } else { //if tuple exists, update data; if not, don't update
                //--------//
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                    //return;
                }

                try {
                    /* create an instance of a Connection object */
                    Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
                    retrievedStmt = c.prepareStatement(
                        "SELECT * FROM Posts where username = ? and postid = ?"
                    );   
                } catch (SQLException ex){
                    System.out.println("SQLException caught");
                    System.out.println("---");
                    while ( ex != null ) {
                        System.out.println("Message   : " + ex.getMessage());
                        System.out.println("SQLState  : " + ex.getSQLState());
                        System.out.println("ErrorCode : " + ex.getErrorCode());
                        System.out.println("---");
                        ex = ex.getNextException();
                    }
                }
                //--------//
                retrievedStmt.setString(1, username);
                retrievedStmt.setInt(2, postid);
                ResultSet rs = retrievedStmt.executeQuery();
                if (rs.next()) { // if tuple exists
                    System.out.println("found tuple");
                    //--------//
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
                        return;
                    }

                    try {
                        /* create an instance of a Connection object */
                        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
                        updateStmt = c.prepareStatement(
                            "UPDATE Posts SET title = ?, body = ?, modified = ? WHERE username = ? AND postid = ?"
                        );    
                    } catch (SQLException ex){
                        System.out.println("SQLException caught");
                        System.out.println("---");
                        while ( ex != null ) {
                            System.out.println("Message   : " + ex.getMessage());
                            System.out.println("SQLState  : " + ex.getSQLState());
                            System.out.println("ErrorCode : " + ex.getErrorCode());
                            System.out.println("---");
                            ex = ex.getNextException();
                        }
                    }
                    //--------//
                    updateStmt.setString(1, title);
                    updateStmt.setString(2, body);
                    updateStmt.setTimestamp(3, getCurrentTimeStamp());
                    updateStmt.setString(4, username);
                    updateStmt.setInt(5, postid);
                    updateStmt.executeUpdate();
                    System.out.println("executed update");
                }

             }
         } catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }

    }
    private void doDelete(String username, int postid) { 
    try{ 
        try {  
                    Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                    System.out.println(ex);
                    return;
                }
            
                Connection c = null;
                //Statement  s = null; 
                //ResultSet rs = null; 

        try {
                    /* create an instance of a Connection object */
                    c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
                    dltStmt = c.prepareStatement(
                        "DELETE FROM Posts where username=? and postid=?"
                    );   
            } catch (SQLException ex){
                    System.out.println("SQLException caught");
                    System.out.println("---");
                    while ( ex != null ) {
                        System.out.println("Message   : " + ex.getMessage());
                        System.out.println("SQLState  : " + ex.getSQLState());
                        System.out.println("ErrorCode : " + ex.getErrorCode());
                        System.out.println("---");
                        ex = ex.getNextException();
                    }
                }
                //--------//
                dltStmt.setString(1, username);
                dltStmt.setInt(2, postid);
                dltStmt.executeUpdate();
        }  catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }

    }
    // in the following array list you have to set each of the title, created and modified for the lisp jsp file to easily process.
    private List<Post> listposts(String username) { 
    List<Post> userposts= new ArrayList<Post>(); 
           try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            return userposts;
        }
    
        Connection c = null;
        Statement  s = null; 

        try {
            /* create an instance of a Connection object */
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
            retrievedStmt = c.prepareStatement(
                "SELECT * FROM Posts where username = ?"
            );   
            retrievedStmt.setString(1, username);
           // retrievedStmt.setInt(2, postid);
            ResultSet rsl = retrievedStmt.executeQuery();
                while (rsl.next()) {
                    // For each row, set that row to be a new post object in your list array.
                Post curpost = new Post(rsl.getString("username"), rsl.getInt("postid"), rsl.getString("title"), rsl.getString("body"), rsl.getTimestamp("created") 
                    , rsl.getTimestamp("modified"));
                userposts.add(curpost);

                }
               return userposts;

        }  catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        }
        finally {return userposts;}

    }
    
}



