package controllers;

import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class UserDetailModels extends CRUD {
	 public static void delete(String id) throws Exception {
	        redirect(request.controller + ".list");
	    }
}