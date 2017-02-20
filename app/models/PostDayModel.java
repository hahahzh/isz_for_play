package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_postday")
public class PostDayModel extends Model {

	public String postday_name;
	

	public String toString() {
		return String.format("postday[name='%s']", postday_name);
	}
}
