package controllers;

import java.lang.reflect.Constructor;
import java.util.Date;

import models.Purchase;
import models.PurchaseBU;
import models.Storehouse;
import play.data.binding.Binder;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Purchases extends CRUD {

	public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Purchase object = (Purchase) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        object.updateDate = new Date();
        object.isShow = 1;
        validation.valid(object);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/blank.html", type, object);
            } catch (TemplateNotFoundException e) {
                render("CRUD/blank.html", type, object);
            }
        }
        object._save();
        
        Storehouse sh = null;
		sh = Storehouse.find("byCode", object.code).first();
		if(sh == null){
			sh = new Storehouse();
			sh.code = object.code;
			sh.createDate = new Date();;
			sh.name = object.name;
			sh.storehouse_num = object.purchase_num;
		}else{
			sh.storehouse_num = sh.storehouse_num + object.purchase_num;
		}
		sh.updateDate = new Date();
		
		sh._save();
		
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }
	
	 public static void save(String id) throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Purchase object = (Purchase)type.findById(id);
	        notFoundIfNull(object);
	        Binder.bindBean(params.getRootParamNode(), "object", object);
	        object.updateDate = new Date();
	        object.isShow = 1;
	        validation.valid(object);
	        if (validation.hasErrors()) {
	            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
	            try {
	                render(request.controller.replace(".", "/") + "/show.html", type, object);
	            } catch (TemplateNotFoundException e) {
	                render("CRUD/show.html", type, object);
	            }
	        }
	        object._save();
	        
	        Storehouse sh = null;
			sh = Storehouse.find("byCode", object.code).first();
			if(sh == null){
				sh = new Storehouse();
				sh.code = object.code;
				sh.createDate = new Date();;
				sh.name = object.name;
				sh.storehouse_num = object.purchase_num;
			}else{
				sh.storehouse_num = sh.storehouse_num + object.purchase_num;
			}
			sh.updateDate = new Date();
			
			sh._save();
			
	        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
	        if (params.get("_save") != null) {
	            redirect(request.controller + ".list");
	        }
	        redirect(request.controller + ".show", object._key());
	    }
	 
	 public static void delete(String id) throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Purchase object = (Purchase)type.findById(id);
	        notFoundIfNull(object);
	        try {
	        	PurchaseBU p = new PurchaseBU();
	        	
	        	p.account_period = object.account_period;
	        	p.attribute = object.attribute;
	        	p.code = object.code;
	        	p.company = object.company;
	        	p.name = object.name;
	        	p.nameplate_price = object.nameplate_price;
	        	p.purchase_num = object.purchase_num;
	        	p.purchase_price = object.nameplate_price;
	        	p.purchase_unit = object.purchase_unit;
	        	p.purchasing_agent = object.purchasing_agent;
	        	p.purchasing_date = object.purchasing_date;
	        	p.rebate = object.rebate;
	        	p.updateDate = object.updateDate;
	        	p._save();
	        	
	        	Storehouse sh = Storehouse.find("byCode", object.code).first();
	        	if(sh != null){
	        		sh.storehouse_num = sh.storehouse_num - object.purchase_num;
	        		sh._save();
	        	}
				
	        	object._delete();
	        } catch (Exception e) {
	            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
	            redirect(request.controller + ".show", object._key());
	        }
	        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
	        redirect(request.controller + ".list");
	    }
}