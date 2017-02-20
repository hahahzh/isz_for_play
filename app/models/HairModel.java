package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_hair")
public class HairModel extends Model {

	public String hair_name;
	
	public String toString() {
		return hair_name;
	}
}
