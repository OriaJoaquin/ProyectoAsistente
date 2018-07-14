package rss;

public class Main {

	public static void main(String[] args) {

		PostGetter pg = new ActualPost();
		PostBlog pb = new PostBlog();
		
		//pb.atender("@jenkins agregar blog Anandtech, https://www.anandtech.com/rss/", "@jenkins", "@daniel");
		//pb.atender("@jenkins agregar rss ArsTechnica, http://feeds.arstechnica.com/arstechnica/index", "@jenkins", "@daniel");
		System.out.println(pb.atender("@jenkins revisar rss", "@jenkins", "@daniel"));
		
		//System.out.println(pg.getPost());
		
	}

}
