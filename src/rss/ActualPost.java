package rss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class ActualPost extends PostGetter {

	private HashMap<String, String> blogs;

	public ActualPost() {
		blogs = new HashMap<String, String>();
		posteos = new ArrayList<Post>();
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File("blogs\\info.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(sc.hasNextLine()) {
			String linea = sc.nextLine();
			String[] datos = linea.split(", ");
			
			blogs.put(datos[0], datos[1]);
		}
		sc.close();
	}
	
	@Override
	public void agregarBlog(String nombre, String link) {
		blogs.put(nombre, link);
	}
	
	@Override
	protected void getListaPosts() {
		Set<String> keys = blogs.keySet();
		SyndFeed feed = null; 
		URL feedUrl = null;
		SyndFeedInput input;
		
		for(String key : keys) {
			try {
				feedUrl = new URL(blogs.get(key));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			input = new SyndFeedInput();
			try {
				feed = input.build(new XmlReader(feedUrl));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (FeedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(@SuppressWarnings("rawtypes") Iterator i = feed.getEntries().iterator(); i.hasNext();) {
				SyndEntry entry = (SyndEntry) i.next();
				posteos.add(new Post(key, entry.getTitle(), entry.getLink()));
			}
		}
	}

	@Override
	public Post getPost() {
		
		getListaPosts();
		
		Random r = new Random();
		int post = r.nextInt(posteos.size());
		
		return posteos.get(post);
	}
}
