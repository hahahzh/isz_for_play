package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import controllers.CRUD.Hidden;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
@Table(name = "user_detail")
public class UserDetailModel extends Model {

	@Required
	@OneToOne
	public UserModel user;
	
	@ManyToOne
	public BodySize size;
	
	@ManyToOne
	public FaceModel face;
	
	@ManyToOne
	public SkinColorModel skincolor;
	
	@ManyToOne
	public PriceRange pricerange;
	
	@ManyToOne
	public HairModel hair;
	
	@ManyToOne
	public InclinationModel inclination;
	
	@ManyToOne
	public BodyModel body;
	
	@ManyToOne
	public HierarchyModel hierarchy;
	
	@ManyToOne
	public PostDayModel postDay;
	
	@ManyToOne
	public FrequencyModel frequency;
	
	public String city;
	@Required
	@MaxSize(500)
	public String address;
	
	@Required
	public Integer height;
	@Required
	public Integer weight;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade=javax.persistence.CascadeType.REFRESH)
	public Constellation constellation;
	@ManyToOne(fetch=FetchType.EAGER,cascade=javax.persistence.CascadeType.REFRESH)
	public Blood blood;
	@Hidden
	public Date updateDate;
	
	public String toString() {
		return user.name;
	}

}
