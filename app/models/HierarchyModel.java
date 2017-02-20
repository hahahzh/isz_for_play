package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Entity
@Table(name = "user_hierarchy")
public class HierarchyModel extends Model {

	public String hierarchy_name;
	

	public String toString() {
		return String.format("Hierarchy[name='%s']", hierarchy_name);
	}
}
