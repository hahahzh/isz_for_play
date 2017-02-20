package controllers;

import java.lang.reflect.Constructor;
import java.util.Date;

import models.Goods;
import models.GoodsBU;
import models.Purchase;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Goodss extends CRUD {
	
	public static void create() throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Goods object = (Goods) constructor.newInstance();
        Binder.bindBean(params.getRootParamNode(), "object", object);
        
        object.code = object.storehouse_goods.code;
        object.name = object.storehouse_goods.name;
        Purchase p = Purchase.find("byCode", object.storehouse_goods.code).first();
        object.nameplate_price = p.nameplate_price;
        object.updateDate = new Date();
        object.createDate = new Date();
		object.goods_num = 0;
		
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
        flash.success(play.i18n.Messages.get("crud.created", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        if (params.get("_saveAndAddAnother") != null) {
            redirect(request.controller + ".blank");
        }
        redirect(request.controller + ".show", object._key());
    }
	
	 public static void delete(String id) throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Goods object = (Goods)type.findById(id);
	        notFoundIfNull(object);
	        try {
	        	GoodsBU g = new GoodsBU();
	        	g.code = object.code;
	        	g.name = object.name;
	        	g.nameplate_price = object.nameplate_price;
	        	g.rebate = object.rebate;
	        	g.sales_price = object.sales_price;
	        	g.memo = object.memo;
	        	g.createDate = object.createDate;
	        	g.updateDate = object.updateDate;
	        	g.date_end = object.date_end;
	        	g.date_from = object.date_from;
	        	g.goods_unit = object.goods_unit;
	        	g.isShow = object.isShow;
	        	g.goods_num = object.goods_num;
	        	g._save();
	        	
	            object._delete();
	            
	        } catch (Exception e) {
	            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
	            redirect(request.controller + ".show", object._key());
	        }
	        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
	        redirect(request.controller + ".list");
	    }
}