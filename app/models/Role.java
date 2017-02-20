package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Table(name = "role")
@Entity
public class Role extends Model {

	@Required
	@MaxSize(15)
	@MinSize(3)
	public String name;

	public String toString() {
		return name;
	}

}