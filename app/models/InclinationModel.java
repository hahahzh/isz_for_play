package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_inclination")
public class InclinationModel extends Model {

	public String inclination_name;
	
	public String toString() {
		return inclination_name;
	}
}
