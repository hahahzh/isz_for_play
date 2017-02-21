package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;


@Table(name = "goods_info")
@Entity
public class Goods extends Model {

	@ManyToOne
	@Required
	public Storehouse storehouse_goods;
	
	
	public String name;
	
	public String code;
	
	public Blob goods_img;
	
	public Double nameplate_price;
	
	public Integer goods_num;//销售数量
	
	@Required
	public Float rebate;//折扣
	@Required
	public Double sales_price;
	@MaxSize(500)
	public String memo;
	public Date date_from;
	
	public Date date_end;
	@Hidden
	public Date updateDate;

	@Hidden
	public Date createDate;
	
	public String goods_unit;
	@Hidden
	public int isShow;


	public String toString() {
		return "name="+storehouse_goods.name+",sales price="+sales_price+",date start="+date_from+",date end="+date_end;
	}

}