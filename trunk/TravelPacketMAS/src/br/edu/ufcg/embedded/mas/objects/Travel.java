package br.edu.ufcg.embedded.mas.objects;

import java.io.Serializable;

public class Travel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1735229843031602996L;

	private String price;
	private String quality;
	
	public Travel() {}

	public Travel(String price, String quality) {
		super();
		this.price = price;
		this.quality = quality;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}
	
	public String toString() {
		return "Price: " + price + " - Quality: " + quality;
	}
}
