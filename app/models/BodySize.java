package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_size")
public class BodySize extends Model {

	public String size_name;
	
	public String toString() {
		return size_name;
	}
}
