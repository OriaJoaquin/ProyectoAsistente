package rss;

import java.util.List;

public abstract class PostGetter {

	protected List<Post> posteos;
	
	public abstract void agregarBlog(String nombre, String link);
	protected abstract void getListaPosts(); 
	public abstract Post getPost();
}
