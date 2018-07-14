package rss;

public class Post {
	
	private String blogOrigen;
	private String titulo;
	private String link;
	
	public Post(String blogOrigen, String titulo, String link) {
		this.blogOrigen = blogOrigen;
		this.titulo = titulo;
		this.link = link;
	}

	@Override
	public String toString() {
		return "Post de " + blogOrigen + ":\nTitulo: " + titulo + "\nLink: " + link + "\n";
	}
}
