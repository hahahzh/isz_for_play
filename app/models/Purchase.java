package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.Required;
import play.db.jpa.Model;


@Table(name = "purchase_info")
@Entity
public class Purchase extends Model {
	@Required
	public String name;
	@Required
	public String code;
	
	public String attribute;
	
	public Double nameplate_price;
	public Float rebate;
	@Required
	public Double purchase_price;
	@Required
	public String purchasing_agent;
	@Required
	public Integer purchase_num;
	@Required
	public Date purchasing_date;
	
	public String company;
	
	public String account_period;
	@Hidden
	public Date updateDate;
	@Required
	public String purchase_unit;
	@Hidden
	public int isShow;
	

	public String toString() {
		return name;
	}

}