package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;


@Table(name = "appsession")
@Entity
public class Session extends Model {

	@OneToOne(optional = false, cascade = { CascadeType.REFRESH},fetch=FetchType.EAGER) 
	@JoinColumn(name = "user_openid", unique = true)
	public UserModel user;
	
	@Required
	@Index(name = "idx_sessionID")
	@Unique
	public String sessionID;
	
	public Date date;

	public String toString() {
		return user.toString();
	}

}