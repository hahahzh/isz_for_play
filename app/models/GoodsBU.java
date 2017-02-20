package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.Required;
import play.db.jpa.Model;


@Table(name = "goods_info_bu")
@Entity
public class GoodsBU extends Model {

	@Required
	public String name;
	@Required
	public String code;
	
	public Integer goods_num;//销售数量
	@Required
	public Double nameplate_price;
	@Required
	public Float rebate;//折扣
	@Required
	public Double sales_price;
	
	public String memo;
	public Date date_from;
	
	public Date date_end;
	
	public Date updateDate;

	@Hidden
	public Date createDate;
	
	public String goods_unit;
	@Hidden
	public int isShow;


	public String toString() {
		return name;
	}

}