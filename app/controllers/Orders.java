package controllers;

import java.lang.reflect.Constructor;
import java.util.Date;

import models.Goods;
import models.Logistics;
import models.Order;
import models.PayStatus;
import models.Storehouse;
import models.UserModel;
import play.data.binding.Binder;
import play.db.Model;
import play.db.jpa.Transactional;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import utils.StringUtil;

@Check("admin")
@With(Secure.class)
public class Orders extends CRUD {

	@Transactional
	public static void create() throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
	        constructor.setAccessible(true);
	        Order object = (Order) constructor.newInstance();
	        Binder.bindBean(params.getRootParamNode(), "object", object);
	     
	        if(object.user == null)object.user = UserModel.find("byMobile", object.user_tmp).first();
	        if(StringUtil.isEmpty(object.user_tmp) && object.user != null)object.user_tmp = object.user.mobile+"";
	        object.updateDate = new Date();
	        object.createDate = new Date();
	        object.code = object.goods.code;
	        validation.valid(object);
	        if (validation.hasErrors()) {
	            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
	            try {
	                render(request.controller.replace(".", "/") + "/blank.html", type, object);
	            } catch (TemplateNotFoundException e) {
	                render("CRUD/blank.html", type, object);
	            }
	        }
	        if(object.goods != null){
	        	
	        	Storehouse sh = Storehouse.find("byCode", object.goods.code).first();
	        	if(sh == null || sh.code == null || sh.storehouse_num < 1 || sh.storehouse_num - object.order_num < 0){
	        		renderArgs.put("error", play.i18n.Messages.get("storehouse.zero"));
	        		render(request.controller.replace(".", "/") + "/blank.html", type, object);
	    		}
	        	sh.storehouse_num = sh.storehouse_num - object.order_num;
	        	sh.updateDate = new Date();
	        	sh._save();
	        	
	        	Goods g = Goods.find("byCode", object.code).first();
	        	g.goods_num = g.goods_num + object.order_num;
	        	g._save();
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
//	        ObjectType type = ObjectType.get(getControllerClass());
//	        notFoundIfNull(type);
//	        Model object = type.findById(id);
//	        notFoundIfNull(object);
//	        try {
//	            object._delete();
//	        } catch (Exception e) {
//	            flash.error(play.i18n.Messages.get("crud.delete.error", type.modelName));
//	            redirect(request.controller + ".show", object._key());
//	        }
//	        flash.success(play.i18n.Messages.get("crud.deleted", type.modelName));
	        redirect(request.controller + ".list");
	    }
	    
	    public static void save(String id) throws Exception {
	        ObjectType type = ObjectType.get(getControllerClass());
	        notFoundIfNull(type);
	        Order object = (Order)type.findById(id);
	        notFoundIfNull(object);
//	        Binder.bindBean(params.getRootParamNode(), "object", object);
	        
	        object.isShow = Integer.parseInt(params.get("object.isShow"));
	        object.logistics = Logistics.findById(Long.parseLong(params.get("object.paystatus.id")));
	        object.paystatus = PayStatus.findById(Long.parseLong(params.get("object.logistics.id")));
	        
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
	        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
	        if (params.get("_save") != null) {
	            redirect(request.controller + ".list");
	        }
	        redirect(request.controller + ".show", object._key());
	    }
}