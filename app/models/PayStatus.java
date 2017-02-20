package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;


@Table(name = "pay_status")
@Entity
public class PayStatus extends Model {

	public String name;



	public String toString() {
		return name;
	}

}