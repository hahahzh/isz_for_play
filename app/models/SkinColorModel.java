package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_skincolor")
public class SkinColorModel extends Model {


	public String skincolor_name;
	




	public String toString() {
		return skincolor_name;
	}
}
