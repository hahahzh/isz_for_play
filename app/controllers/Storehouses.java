package controllers;

import java.lang.reflect.Constructor;

import controllers.CRUD.ObjectType;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Storehouses extends CRUD {
	 public static void save(String id) throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Model object = type.findById(id);
	        notFoundIfNull(object);
	
	        redirect(request.controller + ".show", object._key());
	    }

	    public static void blank() throws Exception {
	    }

	    public static void create() throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
	        constructor.setAccessible(true);
	        Model object = (Model) constructor.newInstance();
	       
	        redirect(request.controller + ".show", object._key());
	    }

	    public static void delete(String id) throws Exception {

	        redirect(request.controller + ".list");
	    }
}