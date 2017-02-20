package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;

import controllers.CRUD.Hidden;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;



@Entity
@Table(name = "user")
public class UserModel extends Model{

	@Required
	@Size(max=20)
	public String name;
	
	@Size(max=20)
	public String nickname;
	
	@Required
	@Size(max=30,min=6)
	@Hidden
	public String pwd;
	
	@ManyToOne 
	public Gender gender;
	
	public Blob portrait;
	@Hidden
	// 1 IOS正版 2 Android 3 IOS越狱  4 IPad 5 OPad
	public int os;
	@Hidden
	public String type;
	
	public Long exp;
	
	@Required
	@Index(name = "idx_mobile")
	@MaxSize(11)
	public Long mobile;
	
	@Index(name = "idx_email")
	public String email;
	
	public Date birthday;
	
	@Hidden
	public Date createDate;
	@Hidden
	public Date updateDate;
	
	public String school;
	
	
	public String wx_id;
	
	public Integer level;
	
	public Integer point;
	

	public String toString() {
		return String.format("[name=%s, mobile=%s]", name, mobile);
	}

}
