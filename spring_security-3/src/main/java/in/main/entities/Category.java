package in.main.entities;


public class Category {
	private String category;
	private String imageUrl;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Category(String category, String imageUrl) {
		super();
		this.category = category;
		this.imageUrl = imageUrl;
	}
		
}
