package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Table(name = "constellations")
@Entity
public class Constellation extends Model{

	public String name;


	public String toString() {
		return name;
	}

}