package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;


@Table(name = "order_info")
@Entity
public class Order extends Model {

	@ManyToOne
	@Required
	public Goods goods;

	@Required
	@Hidden
	public String code;
	
	@Required
	public Integer order_num;
	
	@Required
	public String order_unit;
	

	public Date sales_date; 
	
	@MaxSize(200)
	public String memo;
	// 1 未发出 2 已发出 3 在途 4 签收
	@ManyToOne
	public Logistics logistics;
	// 1 未支付 2 取消 3 已支付
	@ManyToOne
	public PayStatus paystatus;
	
	@ManyToOne
	@Required
	public UserModel user;
	@Required
	public String user_tmp;
		
	@Hidden
	public Date updateDate;

	@Hidden
	public Date createDate;
	
	public int isShow;
	

	public String toString() {
		return goods.name;
	}

}