package controllers;

import controllers.CRUD.ObjectType;
import play.db.Model;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class UserModels extends CRUD {
    public static void delete(String id) throws Exception {
        redirect(request.controller + ".list");
    }
}