package controllers;


import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Set;

import models.AdminManagement;
import play.data.binding.Binder;
import play.db.Model;
import play.db.jpa.Blob;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class AdminManagements extends CRUD {
	
//	public static void create() throws Exception {
//        ObjectType type = ObjectType.get(getControllerClass());
//        notFoundIfNull(type);
//        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        AdminManagement object = (AdminManagement) constructor.newInstance();
//        Binder.bindBean(params.getRootParamNode(), "object", object);
//        Set<String> s =params.data.keySet();
//        Iterator<String> i = s.iterator();
//        while(i.hasNext()){
//        	String name = i.next();
//        	if(name.contains("_hiddenblob")){
//        		String[] tmpParam = params.get(name).split(",");
//        		if(tmpParam.length == 2 && !tmpParam[0].equals("null")){
//                	Blob tmp = new Blob();
//                	tmp.set(new FileInputStream(tmpParam[0]), tmpParam[1]);
//                	name = name.replace("_hiddenblob", "").replace("object.", "");
//                	object.getClass().getField(name).set(object, tmp);
//        		}
//        	}
//        }
//        object.auth = session.get("admin_name");
//        validation.valid(object);
//        if (validation.hasErrors()) {
//            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
//            try {
//                render(request.controller.replace(".", "/") + "/blank.html", type, object);
//            } catch (TemplateNotFoundException e) {
//                render("CRUD/blank.html", type, object);
//            }
//        }
//        object._save();
//        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
//        if (params.get("_save") != null) {
//            redirect(request.controller + ".list");
//        }
//        if (params.get("_saveAndAddAnother") != null) {
//            redirect(request.controller + ".blank");
//        }
//        redirect(request.controller + ".show", object._key());
//    }
//	
//	public static void save(String id) throws Exception {
//        ObjectType type = ObjectType.get(getControllerClass());
//        notFoundIfNull(type);
//        AdminManagement object = (AdminManagement)type.findById(id);
//        notFoundIfNull(object);
//        Binder.bindBean(params.getRootParamNode(), "object", object);
//        object.auth = session.get("admin_name");
//        validation.valid(object);
//        if (validation.hasErrors()) {
//            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
//            try {
//                render(request.controller.replace(".", "/") + "/show.html", type, object);
//            } catch (TemplateNotFoundException e) {
//                render("CRUD/show.html", type, object);
//            }
//        }
//        object._save();
//        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
//        if (params.get("_save") != null) {
//            redirect(request.controller + ".list");
//        }
//        redirect(request.controller + ".show", object._key());
//    }

}