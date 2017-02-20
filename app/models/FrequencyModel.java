package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_frequency")
public class FrequencyModel extends Model {


	public String frequency_name;
	

	public String toString() {
		return String.format("Goods[name='%s']", frequency_name);
	}
}
