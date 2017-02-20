package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_body")
public class BodyModel extends Model {

	public String body_name;
	


	public String toString() {
		return body_name;
	}
}
