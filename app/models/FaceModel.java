package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;

import play.db.jpa.Model;



/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "user_face")
public class FaceModel  extends Model {


	public String face_name;
	


	public String toString() {
		return face_name;
	}

}
