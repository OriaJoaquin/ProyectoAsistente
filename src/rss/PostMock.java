package rss;

import java.util.ArrayList;

public class PostMock extends PostGetter {

	public PostMock() {
		posteos = new ArrayList<Post>();
		getListaPosts();
	}
	
	@Override
	public void agregarBlog(String nombre, String link) { }

	@Override
	protected void getListaPosts() { 
		posteos.add(new Post("Anandtech", "Multiple AMD B450-Based Motherboards Listed in Europe Ahead of Launch", "https://www.anandtech.com/show/13050/amd-b450-mobos-listed-in-europe-ahead-of-launch"));
	}

	@Override
	public Post getPost() {
		return posteos.get(0);
	}
}
