package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

import play.db.jpa.Model;


@Table(name = "storehouse")
@Entity
public class Storehouse extends Model{

	public String name;
	
	@Index(name = "storehouse_code")
	public String code;
	
	public Integer storehouse_num;
	
	public Date updateDate;


	public Date createDate;
	
	

	public String toString() {
		return name;
	}

}