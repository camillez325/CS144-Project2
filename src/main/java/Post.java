import java.util.*;
import java.sql.*;

public class Post {
	public String username;
	public int postid;
	public String title;
	public String body;
	public Timestamp created;
	public Timestamp modified;
	public Post(String username, int postid, String title, String body,Timestamp  created,Timestamp  modified) {
		this.username = username;
		this.postid = postid;
		this.title = title;
		this.body = body;
		this.created=created;
		this.modified=modified;
	}
}