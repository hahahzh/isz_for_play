package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_pricerange")
public class PriceRange extends Model {

	public String pricerange_name;
	
	public String toString(){
		return this.pricerange_name;
	}


}
