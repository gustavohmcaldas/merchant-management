package codechallenge.gustavohmcaldas.guustocustomers.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String country;
	private String website;
	private String imageUrl;
	
	public Merchant(String name, String country, String website, String imageUrl) {
		super();
		this.name = name;
		this.country = country;
		this.website = website;
		this.imageUrl = imageUrl;
	}
}
