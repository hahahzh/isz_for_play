package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import controllers.CRUD.ObjectType;
import models.Blood;
import models.BodyModel;
import models.BodySize;
import models.CheckDigit;
import models.Constellation;
import models.FaceModel;
import models.FrequencyModel;
import models.Gender;
import models.HairModel;
import models.HierarchyModel;
import models.InclinationModel;
import models.Logistics;
import models.Order;
import models.PayStatus;
import models.PriceRange;
import models.Session;
import models.SkinColorModel;
import models.UserDetailModel;
import models.UserModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.Model;
import play.db.jpa.Blob;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http.Header;
import utils.DateUtil;
import utils.HttpSender;
import utils.JSONUtil;
import utils.StringUtil;


/**
 * 
 * 
 * @author hanzhao
 * 
 */
public class Master extends Controller {
	
	public static final SimpleDateFormat SDF_TO_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat SDF_TO_DAY = new SimpleDateFormat("yyyy-MM-dd");
	public static final String TLOGO = "/public/images/tlogo.jpg";
	public static final String BOY = "/public/images/boy.jpg";
	public static final String GIRL = "/public/images/girl.jpg";

	// 定义返回Code
	public static final String SUCCESS = "1";//成功
	public static final String FAIL = "0"; // 失败
	
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;

	private static final String appID= "wx245cd14422e15ae4";
	private static final String AppSecret = "c1bf3b3c3e7430d3871e944f8e29f192";
	private static String CODE = "";
//	private static String urlCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appID+"&redirect_uri=http://m.ballerstarter.com/oauth2&response_type=code&scope=snsapi_userinfo&state=1"; 
	private static String urlToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appID+"&secret="+AppSecret+"&grant_type=authorization_code&code=";
	private static String urlWX = "https://api.weixin.qq.com/sns/userinfo?access_token=_ACCESS_TOKEN&openid=_OPENID";
	// 存储Session副本
	public static ThreadLocal<Session> sessionCache = new ThreadLocal<Session>();
	
	/**
	 * 校验Session是否过期
	 * 
	 * @param sessionID
	 */
	@Before(unless={"checkDigit", "register", "login", "sendResetPasswordMail", "sendResetPasswordSMS",
			"download",	"getRWatchInfo", "syncTime", "initUserDetail"},priority=1)
	public static void validateSessionID(String code, @Required String z) {
		play.Logger.info("validateSessionID start");
		Session s = Session.find("bySessionID",z).first();
		play.Logger.info("validateSessionID z="+z);
		if (s == null) {
			renderFail("error_session_expired");
//			String openID = getWXOpenID(code);
//			if(openID == null){
//				play.Logger.info("openID=null");	
//				renderFail("session_expired");
//			}
//			UserModel user = UserModel.find("byOpenID", openID).first();
//			if(user == null)renderFail("session_expired");
//			play.Logger.info("validateSessionID m="+user.wx_id);
//			s = Session.find("user_openid=?",user.id).first();
//			if(s == null){
//				s = new Session();
//				s.user = user;
//				s.sessionID = UUID.randomUUID().toString();
//				s.date = new Date();
//				s._save();
//			}
//			play.Logger.info("validateSessionID s="+s.user.toString());
		}
//		play.Logger.info("validateSessionID set s="+s.user.toString());
		sessionCache.set(s);
		play.Logger.info("validateSessionID end");
	}
	
//	private static String getWXOpenID(String code){
//		play.Logger.info("getWXOpenID url="+urlToken+code);
//		String rToken = HttpTool.getHttpInputStream(urlToken+code);
//		play.Logger.info("getWXOpenID rToken="+rToken);
//		JSONObject jsonToken = JSONObject.fromObject(rToken);
//		play.Logger.info("jsonToken m="+jsonToken);
//		if(jsonToken.containsKey("openid")){
//			play.Logger.info("jsonToken m="+jsonToken);
//			return jsonToken.getString("openid");
//		}else{
//			play.Logger.info("jsonToken m=null");
//			return null;
//		}
//	}

    
	public static void initUserDetail(){
		
		JSONObject results = initResultJSON();
		
		JSONArray bloodlist = initResultJSONArray();
		List<Blood> ls = Blood.findAll();
		for(Blood b:ls){
			JSONObject data = initResultJSON();
			data.put(b.id, b.name);
			bloodlist.add(data);
		}
		results.put("bloodlist", bloodlist);
		
		JSONArray bodymodellist = initResultJSONArray();
		List<BodyModel> bodyModel = BodyModel.findAll();
		for(BodyModel b:bodyModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.body_name);
			bodymodellist.add(data);
		}
		results.put("bodymodellist", bodymodellist);
		
		JSONArray bodysizelist = initResultJSONArray();
		List<BodySize> bodySize = BodySize.findAll();
		for(BodySize b:bodySize){
			JSONObject data = initResultJSON();
			data.put(b.id, b.size_name);
			bodysizelist.add(data);
		}
		results.put("bodysizelist", bodysizelist);
		
		JSONArray constellationlist = initResultJSONArray();
		List<Constellation> constellation = Constellation.findAll();
		for(Constellation b:constellation){
			JSONObject data = initResultJSON();
			data.put(b.id, b.name);
			constellationlist.add(data);
		}
		results.put("constellationlist", constellationlist);
		
		JSONArray facemodellist = initResultJSONArray();
		List<FaceModel> faceModel = FaceModel.findAll();
		for(FaceModel b:faceModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.face_name);
			facemodellist.add(data);
		}
		results.put("facemodellist", facemodellist);
		
		JSONArray frequencymodellist = initResultJSONArray();
		List<FrequencyModel> frequencyModel = FrequencyModel.findAll();
		for(FrequencyModel b:frequencyModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.frequency_name);
			frequencymodellist.add(data);
		}
		results.put("frequencymodellist", frequencymodellist);
		
		JSONArray hairmodellist = initResultJSONArray();
		List<HairModel> hairModel = HairModel.findAll();
		for(HairModel b:hairModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.hair_name);
			hairmodellist.add(data);
		}
		results.put("hairmodellist", hairmodellist);
		
		JSONArray hierarchymodellist = initResultJSONArray();
		List<HierarchyModel> hierarchyModel = HierarchyModel.findAll();
		for(HierarchyModel b:hierarchyModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.hierarchy_name);
			hierarchymodellist.add(data);
		}
		results.put("hierarchymodellist", hierarchymodellist);
		
		JSONArray inclinationmodellist = initResultJSONArray();
		List<InclinationModel> inclinationModel = InclinationModel.findAll();
		for(InclinationModel b:inclinationModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.inclination_name);
			inclinationmodellist.add(data);
		}
		results.put("inclinationmodellist", inclinationmodellist);
		
		JSONArray pricerangelist = initResultJSONArray();
		List<PriceRange> priceRange = PriceRange.findAll();
		for(PriceRange b:priceRange){
			JSONObject data = initResultJSON();
			data.put(b.id, b.pricerange_name);
			pricerangelist.add(data);
		}
		results.put("pricerangelist", pricerangelist);
		
		JSONArray skincolormodellist = initResultJSONArray();
		List<SkinColorModel> skinColorModel = SkinColorModel.findAll();
		for(SkinColorModel b:skinColorModel){
			JSONObject data = initResultJSON();
			data.put(b.id, b.skincolor_name);
			skincolormodellist.add(data);
		}
		results.put("skincolormodellist", skincolormodellist);
		
		renderSuccess(results);
	}


	
	/**
	 * 更新用户信息
	 * 
	 */
	public static void updateUserInfo(String pwd, String name, String nickname, String birthday, 
			String gender, String specialty, Blob img_ch, String email, String mobile, String school, @Required String z) {

		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Session s = sessionCache.get();
		UserModel m = s.user;
		
		if(!StringUtil.isEmpty(pwd)){
			m.pwd = pwd;
		}
		if(!StringUtil.isEmpty(name)){
			m.name = name;
		}else{
			renderFail("error_parameter_pname");
		}
		if(!StringUtil.isEmpty(nickname)){
			m.nickname = nickname;
		}
		if(!StringUtil.isEmpty(birthday)){
				m.birthday = DateUtil.reverse2Date(birthday);
				if(m.birthday == null)renderFail("error_parameter_date");
			
		}
		if(!StringUtil.isEmpty(gender)){
			m.gender = Gender.findById(Long.parseLong(gender));
		}
		
//		if(img_ch != null){
//			if(m.img_ch.exists()){
//				m.img_ch.getFile().delete();
//			}
//			m.img_ch = img_ch;
//		}
		
		if(!StringUtil.isEmpty(email)){
			m.email = email;
		}
		if(!StringUtil.isEmpty(mobile)){
			m.mobile = Long.parseLong(mobile);
		}
		if(!StringUtil.isEmpty(school)){
			m.school = school;
		}
		
		m.save();
		
		renderSuccess(initResultJSON());
	}
	
	/**
	 * 更新用户信息
	 * 
	 */
	public static void updateUserDetailInfo(String size,
			String face,
			String skincolor,
			String pricerange,
			String hair,
			String inclination,
			String bodymodel,
			String city,
			String address,
			String height,
			String weight,
			String constellation,
			String blood, @Required String z) {

		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}

		Session s = sessionCache.get();
		UserModel u = s.user;
		UserDetailModel udm = UserDetailModel.find("byUser", u).first();
		if(udm == null)renderFail("error_parameter_required");
		
		if(!StringUtil.isEmpty(size)){
			udm.size = BodySize.findById(Long.parseLong(size));
		}
		if(!StringUtil.isEmpty(face)){
			udm.face = FaceModel.findById(Long.parseLong(face));
		}
		if(!StringUtil.isEmpty(skincolor)){
			udm.skincolor = SkinColorModel.findById(Long.parseLong(skincolor));
		}
		if(!StringUtil.isEmpty(pricerange)){
				udm.pricerange = PriceRange.findById(Long.parseLong(pricerange)); 
			
		}
		if(!StringUtil.isEmpty(hair)){
			udm.hair = HairModel.findById(Long.parseLong(hair));
		}
		if(!StringUtil.isEmpty(inclination)){
			udm.inclination = InclinationModel.findById(Long.parseLong(inclination));
		}
		if(!StringUtil.isEmpty(bodymodel)){
			udm.body = BodyModel.findById(Long.parseLong(bodymodel));
		}
		if(!StringUtil.isEmpty(city)){
			udm.city = city;
		}
		if(!StringUtil.isEmpty(address)){
			udm.address = address;
		}
		if(!StringUtil.isEmpty(height)){
			udm.height = Integer.parseInt(height);
		}
		if(!StringUtil.isEmpty(weight)){
			udm.weight = Integer.parseInt(weight);
		}
		if(!StringUtil.isEmpty(constellation)){
			udm.constellation = Constellation.findById(Long.parseLong(constellation));
		}
		if(!StringUtil.isEmpty(blood)){
			udm.blood = Blood.findById(Long.parseLong(blood));
		}
		udm.save();
		
		renderSuccess(initResultJSON());
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param z
	 */
	public static void getUserInfo(String code, @Required String z) {
		play.Logger.info("getMemberInfo start");
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("session_expired");
		}
		
		UserModel m = s.user;
		play.Logger.info("getMemberInfo m="+m.toString());
		JSONObject results = initResultJSON();
		results.put("pId", m.id);
		results.put("name", m.name+"");
		results.put("nickname", m.nickname+"");
		results.put("birthday", m.birthday==null?"":SDF_TO_DAY.format(m.birthday));
		results.put("gender", m.gender+"");
		
//		if(m.img_ch != null && m.img_ch.exists()){
//			results.put("img_ch", "/c/download?id=" + m.id + "&fileID=img_ch&entity=" + m.getClass().getName() + "&z=" + z);
//		}else{
//			if(!StringUtil.isEmpty(m.headimgurl)){
//				results.put("img_ch", m.headimgurl);
//			}else if(m.gender == null || "男".equals(m.gender)){
//				results.put("img_ch", BOY);
//			}else{
//				results.put("img_ch", GIRL);
//			}
//		}
//		results.put("auth", m.isAuth);
		results.put("email", m.email+"");
		results.put("phone", m.mobile+"");
		results.put("weixin", m.wx_id+"");
		results.put("school", m.school);
		results.put("updated_at_ch", m.updateDate+"");
		
		play.Logger.info("getMemberInfo"+results.toString());
		play.Logger.info("getMemberInfo end");
		renderSuccess(results);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param z
	 */
	public static void getUserDetailInfo(String code, @Required String z) {
		play.Logger.info("getUserDetailInfo start");
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("session_expired");
		}
		
		UserModel u = s.user;
		UserDetailModel udm = UserDetailModel.find("byUser", u).first();
		play.Logger.info("getMemberInfo u="+u.toString());
		
		JSONObject results = initResultJSON();
		
		results.put("size",udm.size.id);
		results.put("face",udm.face.id);
		results.put("skincolor",udm.skincolor.id);
		results.put("pricerange",udm.pricerange.id);
		results.put("hair",udm.hair.id);
		results.put("inclination",udm.inclination.id);
		results.put("body",udm.body.id);
//		results.put("hierarchy",udm.hierarchy.id);
//		results.put("postDay",udm.postDay.id);
//		results.put("frequency",udm.frequency.id);
		results.put("city",udm.city);
		results.put("address",udm.address);
		results.put("height",udm.height);
		results.put("weight",udm.weight);
		results.put("constellation",udm.constellation.id);
		results.put("blood",udm.blood.id);
		
		play.Logger.info("getUserDetailInfo"+results.toString());
		play.Logger.info("getUserDetailInfo end");
		renderSuccess(results);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param z
	 */
	public static void getUserAllInfo(String code, @Required String z) {
		play.Logger.info("getUserAllInfo start");
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("session_expired");
		}
		
		UserModel m = s.user;
		play.Logger.info("getMemberInfo m="+m.toString());
		JSONObject results = initResultJSON();

		results.put("name", m.name+"");
		results.put("nickname", m.nickname+"");
		results.put("birthday", m.birthday==null?"":SDF_TO_DAY.format(m.birthday));
		results.put("gender", m.gender+"");
		
//		if(m.img_ch != null && m.img_ch.exists()){
//			results.put("img_ch", "/c/download?id=" + m.id + "&fileID=img_ch&entity=" + m.getClass().getName() + "&z=" + z);
//		}else{
//			if(!StringUtil.isEmpty(m.headimgurl)){
//				results.put("img_ch", m.headimgurl);
//			}else if(m.gender == null || "男".equals(m.gender)){
//				results.put("img_ch", BOY);
//			}else{
//				results.put("img_ch", GIRL);
//			}
//		}
//		results.put("auth", m.isAuth);
		results.put("email", m.email);
		results.put("phone", m.mobile);
		results.put("weixin", m.wx_id);
		results.put("school", m.school);
		UserDetailModel udm = UserDetailModel.find("byUser", m).first();
		results.put("size",udm.size==null?"":udm.size.size_name);
		results.put("face",udm.face==null?"":udm.face.face_name);
		results.put("skincolor",udm.skincolor==null?"":udm.skincolor.skincolor_name);
		results.put("pricerange",udm.pricerange==null?"":udm.pricerange.pricerange_name);
		results.put("hair",udm.hair==null?"":udm.hair.hair_name);
		results.put("inclination",udm.inclination==null?"":udm.inclination.inclination_name);
		results.put("body",udm.body==null?"":udm.body.body_name);
//		results.put("hierarchy",udm.hierarchy);
//		results.put("postDay",udm.postDay);
//		results.put("frequency",udm.frequency);
		results.put("city",udm.city);
		results.put("address",udm.address);
		results.put("height",udm.height);
		results.put("weight",udm.weight);
		results.put("constellation",udm.constellation==null?"":udm.constellation.name);
		results.put("blood",udm.blood==null?"":udm.blood.name);
		
		results.put("updated_at_ch", SDF_TO_DAY.format(m.updateDate));
		
		play.Logger.info("getUserAllInfo"+results.toString());
		play.Logger.info("getUserAllInfo end");
		renderSuccess(results);
	}
	
	  
	public static void initOrderData(){
		
		JSONObject results = initResultJSON();
		
		JSONArray paystatuslist = initResultJSONArray();
		List<PayStatus> ls = PayStatus.findAll();
		for(PayStatus b:ls){
			JSONObject data = initResultJSON();
			data.put(b.id, b.name);
			paystatuslist.add(data);
		}
		results.put("paystatuslist", paystatuslist);
		
		JSONArray logisticslist = initResultJSONArray();
		List<Logistics> logistics = Logistics.findAll();
		for(Logistics b:logistics){
			JSONObject data = initResultJSON();
			data.put(b.id, b.name);
			logisticslist.add(data);
		}
		results.put("logisticslist", logisticslist);
	
		renderSuccess(results);
	}
		
	/**
	 * 获取用户积分
	 * 
	 * @param z
	 */
	public static void getOrderList(@Required String z) {
		
		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("session_expired");
		}
		
		List<Order> ls = Order.find("byUser", s.user).fetch();
		
		JSONObject results = initResultJSON();
		if (!ls.isEmpty()) {
			JSONArray datalist = initResultJSONArray();
			JSONObject data = initResultJSON();
			for(Order o:ls){
				data.put("id", o.id);
				data.put("name", o.goods.name);
				data.put("sales_date", SDF_TO_DAY.format(o.sales_date));
				data.put("order_num", o.order_num);
				data.put("paystatus", o.paystatus.name);
				data.put("logistics", o.logistics.name);
				datalist.add(data);
			}
			results.put("list", datalist);
		}
		renderSuccess(results);
	}
	
	public static void getOrderInfo(@Required String oCode, @Required String z) {
		play.Logger.info("getOrderInfo start");
		
		Session s = sessionCache.get();
		if(s == null){
			renderFail("session_expired");
		}
		
		Order o = Order.findById(Long.parseLong(oCode));
		
		JSONObject results = initResultJSON();
		
		results.put("name",o.goods.name);
		if(o.goods.goods_img != null && o.goods.goods_img.exists()){
			results.put("goods_img", "/api/download?id=" + o.goods.id + "&fileID=goods_img&entity=" + o.goods.getClass().getName() + "&z=" + z);
		}else{
			results.put("goods_img","");
		}
		results.put("nameplate_price",o.goods.nameplate_price);
		results.put("rebate",(o.goods.rebate*100)+"%");
		results.put("sales_price",o.goods.sales_price);
		results.put("order_num",o.order_num);
		results.put("order_unit",o.order_unit);
		results.put("sales_date",SDF_TO_DAY.format(o.sales_date));
		results.put("memo",o.memo);
		results.put("logistics",o.logistics.name);
		results.put("paystatus",o.paystatus.name);
		results.put("user",o.user.name);


		play.Logger.info("getOrderInfo"+results.toString());
		play.Logger.info("getOrderInfo end");
		renderSuccess(results);
	}
//	
//	/**
//	 * 更新球队信息
//	 * 
//	 */
//	public static void updateTeamInfo(Blob logo, String name, String coach, String captain, String contact, String members, @Required String z) {
//
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//
//		Session s = sessionCache.get();
//		
//		Team t = Team.find("coach_id=?", s.member.id).first();
//		if(t == null)Team.find("captain_id=?", s.member.id).first();
//		if(t == null){
//			if(StringUtil.isEmpty(name))renderFail("error_teamname_null");
//			t = new Team();
//			t.updated_at_ch = new Date();
//		}
//		if(t != null){
//			if(logo != null){
//				if(t.logo.exists()){
//					t.logo.getFile().delete();
//				}
//				t.logo = logo;
//			}
//			if(!StringUtil.isEmpty(name)){
//				t.name = name;
//			}
//			if(!StringUtil.isEmpty(coach)){
//				t.coach = Member.find("byName", coach).first();
//			}
////			if(!StringUtil.isEmpty(captain)){
//				t.captain = s.member;//Member.find("byName", captain).first();
////			}
//			if(!StringUtil.isEmpty(contact)){
//				t.contact = contact;
//			}
////			members = "12,13,14,15,16,17,";
////			if(!StringUtil.isEmpty(members)){
////				String[] ma = members.split(",");
////				int count = 0;
////				for(String ms:ma){
////					if(count > 11)break;
////					Member m = Member.findById(Long.parseLong(ms));
////					if(m != null)t.members.add(m);
////					count++;
////				}
////				t.members = StringUtil.removalDup(t.members);
////			}
//			t.save();
//		}
//		renderSuccess(initResultJSON());
//	}
//	
//	/**
//	 * 获取球队信息1
//	 * 
//	 * @param z
//	 */
//	public static void getPTeamInfo(String code, @Required String z) {
//		
////		if (Validation.hasErrors()) {
////			renderFail("error_parameter_required");
////		}
//		
//		Session s = sessionCache.get();
//		if(s == null){
//			renderFail("session_expired");
//		}
//				
//		Team t = Team.find("captain_id=?", s.member.id).first();
//		if(t==null)t = Team.find("coach_id=?", s.member.id).first();
//		if(t == null)renderFail("error_team_notexist");
//				
//		JSONObject results = initResultJSON();
//	
//		if(t.logo != null && t.logo.exists()){
//			results.put("logo", "/c/download?id=" + t.id + "&fileID=logo&entity=" + t.getClass().getName() + "&z=" + z);
//		}else{
//			results.put("logo", TLOGO);
//		}
//		results.put("name", t.name);
//		if(t.coach.img_ch != null && t.coach.img_ch.exists()){
//			results.put("coach_img", "/c/download?id=" + t.coach.id + "&fileID=img_ch&entity=" + t.coach.getClass().getName() + "&z=" + z);
//		}else if(!StringUtil.isEmpty(t.coach.headimgurl)){
//			results.put("coach_img", t.coach.headimgurl);
//		}else{
//			results.put("coach_img", BOY);
//		}
//		results.put("coach_id", t.coach==null?"":t.coach.id);
//		results.put("coach", t.coach==null?"":t.coach.name);
//		if(t.captain.img_ch != null && t.captain.img_ch.exists()){
//			results.put("captain_img", "/c/download?id=" + t.captain.id + "&fileID=img_ch&entity=" + t.captain.getClass().getName() + "&z=" + z);
//		}else if(!StringUtil.isEmpty(t.captain.headimgurl)){
//			results.put("captain_img", t.captain.headimgurl);
//		}else{
//			results.put("captain_img", BOY);
//		}
//		results.put("captain_id", t.captain==null?"":t.captain.id);
//		results.put("captain", t.captain==null?"":t.captain.name);
//		results.put("contact", t.contact);
//		JSONArray datalist = initResultJSONArray();
//		if(t.members.size() >0){
//			JSONObject data = initResultJSON();
//			for(Member m:t.members){
//				data.put("name", m.name+"");
//				data.put("number", m.number+"");
//				data.put("job1", m.job1==null?"":m.job1.full_name);
//				data.put("job2", m.job2==null?"":m.job2.full_name);
//				data.put("height", m.height+"");
//				data.put("weight", m.weight+"");
//				if(m.img_ch != null && m.img_ch.exists()){
//					data.put("img_ch", "/c/download?id=" + m.id + "&fileID=img_ch&entity=" + m.getClass().getName() + "&z=" + z);
//				}else if(!StringUtil.isEmpty(m.headimgurl)){
//					data.put("img_ch", m.headimgurl);
//				}else{
//					data.put("img_ch", BOY);
//				}
//				datalist.add(data);
//			}
//		}
//		results.put("members", datalist);
//		results.put("updated_at_ch", SDF_TO_DAY.format(t.updated_at_ch));
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取球队信息2
//	 * 
//	 * @param z
//	 */
//	public static void getGTeamInfo(Long tId) {
//				
//		Team t = Team.findById(tId);
//		if(t == null)renderFail("error_team_notexist");
//				
//		JSONObject results = initResultJSON();
//	
//		if(t.logo != null && t.logo.exists()){
//			results.put("logo", "/c/download?id=" + t.id + "&fileID=logo&entity=" + t.getClass().getName() + "&z=" + 1);
//		}else{
//			results.put("logo", TLOGO);
//		}
//		results.put("name", t.name);
//		if(t.coach.img_ch != null && t.coach.img_ch.exists()){
//			results.put("coach_img", "/c/download?id=" + t.coach.id + "&fileID=img_ch&entity=" + t.coach.getClass().getName() + "&z=" + 1);
//		}else if(!StringUtil.isEmpty(t.coach.headimgurl)){
//			results.put("coach_img", t.coach.headimgurl);
//		}else{
//			results.put("coach_img", BOY);
//		}
//		results.put("coach", t.coach==null?"":t.coach.name);
//		if(t.captain.img_ch != null && t.captain.img_ch.exists()){
//			results.put("captain_img", "/c/download?id=" + t.captain.id + "&fileID=img_ch&entity=" + t.captain.getClass().getName() + "&z=" + 1);
//		}else if(!StringUtil.isEmpty(t.captain.headimgurl)){
//			results.put("captain_img", t.captain.headimgurl);
//		}else{
//			results.put("captain_img", BOY);
//		}
//		results.put("captain", t.captain==null?"":t.captain.name);
//		results.put("contact", t.contact);
//		JSONArray datalist = initResultJSONArray();
//		if(t.members.size() >0){
//			JSONObject data = initResultJSON();
//			for(Member m:t.members){
//				data.put("nickname", m.nickname+"");
//				data.put("number", m.number+"");
//				data.put("age", "");
//				if(m.birthday!=null){
//					data.put("age", (new Date().getYear() - m.birthday.getYear())+"");
//				}
//				data.put("job1", m.job1==null?"":m.job1.full_name);
//				data.put("job2", m.job2==null?"":m.job2.full_name);
//				data.put("height", m.height+"");
//				data.put("weight", m.weight+"");
//				if(m.img_ch != null && m.img_ch.exists()){
//					data.put("img_ch", "/c/download?id=" + m.id + "&fileID=img_ch&entity=" + m.getClass().getName() + "&z=" + 1);
//				}else if(!StringUtil.isEmpty(m.headimgurl)){
//					data.put("img_ch", m.headimgurl);
//				}else{
//					data.put("img_ch", BOY);
//				}
//				datalist.add(data);
//			}
//		}
//		results.put("members", datalist);
//		results.put("updated_at_ch", SDF_TO_DAY.format(t.updated_at_ch));
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取球队信息 list
//	 * 
//	 * @param z
//	 */
//	public static void getTeamList(@Required Long pId) {
//				
//		List<Team> tl = JPA.em().createNativeQuery("select * from teams, teams_members where teams_members.members_id = "+pId+" and teams.id=teams_members.teams_id", Team.class).getResultList();
//		List<Team> tl2 = Team.find("captain_id=? or coach_id=?", pId, pId).fetch(); 
//		JSONObject results = initResultJSON();
//		
//		if((tl != null && tl.size() > 0) || (tl2 != null && tl2.size() > 0)){
//			JSONArray datalist = initResultJSONArray();
//			for(Team t:tl){
//				JSONObject data = initResultJSON();
//				data.put("id", t.id);
//				data.put("name", t.name);
//				data.put("updated_at_ch", SDF_TO_DAY.format(t.updated_at_ch));
//				if(t.logo != null && t.logo.exists()){
//					data.put("logo", "/c/download?id=" + t.id + "&fileID=logo&entity=" + t.getClass().getName() + "&z=" + 1);
//				}else{
//					data.put("logo", TLOGO);
//				}
//				datalist.add(data);
//			}
//			for(Team t2:tl2){
//				boolean f = true;
//				for(Team t:tl){
//					if(t.id == t2.id)f=false;
//				}
//				if(f){
//					JSONObject data = initResultJSON();
//					data.put("id", t2.id);
//					data.put("name", t2.name);
//					data.put("updated_at_ch", SDF_TO_DAY.format(t2.updated_at_ch));
//					if(t2.logo != null && t2.logo.exists()){
//						data.put("logo", "/c/download?id=" + t2.id + "&fileID=logo&entity=" + t2.getClass().getName() + "&z=" + 1);
//					}else{
//						data.put("logo", TLOGO);
//					}
//					datalist.add(data);
//				}
//			}
//			results.put("teamlist", datalist);
//		}else{
//			renderFail("error_notadde_team");
//		}
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取球队积分
//	 * 
//	 * @param z
//	 */
//	public static void getTeamPoint(String teamId, @Required String z) {
//		
//		Session s = sessionCache.get();
//		if(s == null){
//			renderFail("session_expired");
//		}
//				
//		List<Result> ts = Result.find("home_team_id", teamId).first();
//		List<Result> ts2 = Result.find("visiting_team_id", teamId).first();
//		ts.addAll(ts2);
//		
//		JSONObject results = initResultJSON();
//		JSONArray datalist = initResultJSONArray();
//		if(ts.isEmpty() && ts.size() > 0){
//			JSONObject data = initResultJSON();
//			for(Result t:ts){
//				data.put("game", t.game);
//				data.put("round", t.round);
//				data.put("home_team", t.home_team);
//				data.put("visiting_team", t.visiting_team);
//				data.put("home_team_point", t.home_team_point);
//				data.put("visiting_team_point", t.visiting_team_point);
//				data.put("home_team_integral", t.home_team_integral);
//				data.put("visiting_team_integral", t.visiting_team_integral);
//				data.put("date", t.date);
//				datalist.add(data);
//			}
//			results.put("list", datalist);
//		}
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取新闻
//	 * 
//	 * @param z
//	 */
//	public static void getNewsList(@Required String z) {
//		
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		
//		Session s = sessionCache.get();
//		if(s == null){
//			renderFail("session_expired");
//		}
//				
//		List<Constellation> cs = Constellation.findAll();
//		
//		JSONObject results = initResultJSON();
//		JSONArray datalist = initResultJSONArray();
//		if(cs.isEmpty() && cs.size() > 0){
//			for(Constellation c:cs){
//				JSONObject data = initResultJSON();
//				data.put("id", c.id);
//				data.put("name", c.name);
//				datalist.add(data);
//			}
//			results.put("list", datalist);
//		}
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取赛事列表
//	 * 
//	 * @param z
//	 * @throws ParseException
//	 */
//	public static void getGameList(String code, Long pId) throws ParseException {
//		
//		if(!StringUtil.isEmpty(code))validateSessionID(code, "");
//		
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//						
//		List<Game> gl = null;
//		if(pId == null){
//			gl = Game.find("isShow = 1 order by id desc").fetch();
//		}else{
//			List<Team> tl = JPA.em().createNativeQuery("select * from teams, teams_members where teams_members.members_id = "+pId+" and teams.id=teams_members.teams_id", Team.class).getResultList();
//			List<Team> tl2 = Team.find("captain_id=? or coach_id=?", pId, pId).fetch();
//			String tid ="";
//			for(Team t:tl)tid += t.id+",";
//			
//			for(Team t2:tl2){
//				boolean f = true;
//				for(Team t:tl){
//					if(t.id == t2.id)f=false;
//				}
//				if(f)tid += t2.id+",";
//			}
//			if(tid.length()>1)tid = tid.substring(0, tid.length()-1);
//			gl = JPA.em().createNativeQuery("select * from games, games_teams where games.id=games_teams.games_id and games_teams.teams_id in("+tid+")", Game.class).getResultList();
//		}
//		JSONObject results = initResultJSON();
//		
//		if(gl != null && gl.size() > 0){
//			JSONArray datalist = initResultJSONArray();
//			for(Game g:gl){
//				JSONObject data = initResultJSON();
//				data.put("id", g.id);
//				data.put("logo", "/c/download?id=" + g.id + "&fileID=logo&entity=" + g.getClass().getName() + "&z=" + 1);
//				data.put("name", g.name);
//				data.put("startDate", g.startDate==null?"":SDF_TO_DAY.format(g.startDate));
//				data.put("startSignUp", g.startSignUp==null?"":SDF_TO_DAY.format(g.startSignUp));
//				data.put("state", g.state.name);
//				datalist.add(data);
//			}
//			results.put("gamelist", datalist);
//		}else{
//			renderFail("error_nothave_game");
//		}
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 获取赛事
//	 * 
//	 * @param z
//	 * @throws ParseException
//	 */
//	public static void getGameInfo(@Required Long gId) throws ParseException {
//		
//		if (Validation.hasErrors()) {
//			renderFail("error_nothave_game");
//		}
//						
//		Game g = Game.findById(gId);
//		
//		JSONObject results = initResultJSON();
//		
//		if(g != null){	
//			results.put("id", g.id);
//			results.put("name", g.name);
//			results.put("logo", "/c/download?id=" + g.id + "&fileID=logo&entity=" + g.getClass().getName() + "&z=" + 1);
//			results.put("prize", g.prize);
////			results.put("schedule", g.schedule);
//			if(g.schedule != null && g.schedule.exists()){
//				results.put("schedule", "/c/download?id=" + g.id + "&fileID=schedule&entity=" + g.getClass().getName() + "&z=" + 1);
//			}
//			results.put("startDate", g.startDate==null?"":SDF_TO_DAY.format(g.startDate));
//			results.put("endDate", g.endDate==null?"":SDF_TO_DAY.format(g.endDate));
//			results.put("startSignUp", g.startSignUp==null?"":SDF_TO_DAY.format(g.startSignUp));
//			results.put("endSignUp", g.endSignUp==null?"":SDF_TO_DAY.format(g.endSignUp));
//			results.put("describtion", g.describtion);
//			if("E".equals(g.state.state) && DateUtil.intervalOfHour(new Date(), g.endSignUp) > 0){
//				results.put("isSignUp", 1);
//			}else{
//				results.put("isSignUp", 0);
//			}
//						
//			JSONArray teamlist = initResultJSONArray();
//			List<GameApproval> gas = GameApproval.find("games_id=? and isAppr=1", g.id).fetch();
//			if(gas != null && gas.size() > 0){
//				JSONObject data = initResultJSON();
//				for(GameApproval ga:gas){
//					data.put("tId", ga.teams.id);
//					data.put("name", ga.teams.name);
//					data.put("coach", ga.teams.coach==null?"":ga.teams.coach.name);
//					data.put("captain", ga.teams.captain==null?"":ga.teams.captain.name);
//					if(ga.teams.logo != null && ga.teams.logo.exists()){
//						data.put("logo", "/c/download?id=" + ga.teams.id + "&fileID=logo&entity=" + ga.teams.getClass().getName() + "&z=" + 1);
//					}else{
//						data.put("logo", TLOGO);
//					}
//					teamlist.add(data);
//				}
//			}
//			results.put("teamlist", teamlist);
//		}else{
//			renderFail("error_nothave_game");
//		}
//		renderSuccess(results);
//	}
//	
//	/**
//	 * 赛事报名
//	 * 
//	 * @param z
//	 */
//	public static void signUp(String code, @Required Long gId, @Required String z) {
//		
////		if (Validation.hasErrors()) {
////			renderFail("error_parameter_required");
////		}
//		
//		Session s = sessionCache.get();
//		if(s == null){
//			renderFail("session_expired");
//		}
//				
//		Game g = Game.findById(gId);
//		Team t = Team.find("coach_id=? or captain_id=?",s.member.id, s.member.id).first();
//		GameApproval ga = GameApproval.find("games_id=? and teams_id=?", g.id,t.id).first();
//		if(ga == null){
//			ga = new GameApproval();
//			ga.games = g;
//			ga.teams = t;
//			ga.isAppr = false;
//			ga._save();
//		}else{
//			renderFail("error_team_su_exist");
//		}
//		renderSuccess(initResultJSON());
//	}
//	
//	/**
//	 * 获取赛事排名
//	 * 
//	 */
//	public static void getGameStandingsData(@Required Long gId) {
//		
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		
//		List<Result> rs = Result.find("game_id=? and gameType=1", gId).fetch();
//		List<GameApproval> gas = GameApproval.find("games_id=? and isAppr=1", gId).fetch();
//		List<Standing> ss1 = new ArrayList<Standing>();
//		
//		for(GameApproval ga:gas){
//			Standing tmpS = new Standing();
//			tmpS.name = ga.teams.name;
//			tmpS.id = ga.teams.id;
//			tmpS.round = 0;
//			tmpS.win = 0;
//			tmpS.lose = 0;
//			for(Result r:rs){
//				if(ga.teams.id == r.home_team.id){
//					tmpS.round++;
//					if(r.home_team_integral > 0)
//						tmpS.win++;
//					else
//						tmpS.lose++;
//				}
//			}
//			ss1.add(tmpS);
//		}
//		List<Standing> ss2 = new ArrayList<Standing>();
//		for(GameApproval ga:gas){
//			Standing tmpS = new Standing();
//			tmpS.name = ga.teams.name;
//			tmpS.id = ga.teams.id;
//			tmpS.round = 0;
//			tmpS.win = 0;
//			tmpS.lose = 0;
//			for(Result r:rs){
//				if(ga.teams.id == r.visiting_team.id){
//					tmpS.round++;
//					if(r.visiting_team_integral > 0)
//						tmpS.win++;
//					else
//						tmpS.lose++;
//				}
//			}
//			ss2.add(tmpS);
//		}
//		List<Standing> resultsS = new ArrayList<Standing>();
//		for(Standing s1:ss1){
//			for(Standing s2:ss2){
//				if(s1.id == s2.id){
//					s1.round += s2.round;
//					s1.win += s2.win;
//					s1.lose += s2.lose;
//					break;
//				}
//			}
//			if(s1.win+s1.lose>0){
//				float win = s1.win;
//				float total = s1.win+s1.lose;
//				s1.rate = win/total;
//			}else{
//				s1.rate = 0F;
//			}
//			resultsS.add(s1);
//		}
//		Collections.sort(resultsS, new Comparator<Standing>() {
//	        public int compare(Standing arg0, Standing arg1) {
//	            return arg0.rate.compareTo(arg1.rate);
//	        }
//	    });
//		Collections.reverse(resultsS);
//		JSONObject results = initResultJSON();
//		JSONArray datalist = initResultJSONArray();
//		int n = 1;
//		results.put("game", gas.get(0).games.name);
//		for(Standing r:resultsS){
//			JSONObject data = initResultJSON();
//			data.put("standing", n++);
//			data.put("name", r.name);
//			data.put("round", r.round);
//			data.put("win", r.win);
//			data.put("lose", r.lose);
//			data.put("rate", (r.rate*100)+"%");
//			datalist.add(data);
//		}
//		results.put("datalist", datalist);
//		renderSuccess(results);
//	}
//
//	/**
//	 * 获取赛事排名
//	 * 
//	 */
//	public static void getGameResultsData(@Required Long gId) {
//		
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		
//		List<Result> rs = Result.find("game_id=? and gameType=1", gId).fetch();
//		JSONObject results = initResultJSON();
//		JSONArray datalist = initResultJSONArray();
//		results.put("game", rs.get(0).game.name);
//		for(Result r:rs){
//			JSONObject data = initResultJSON();
//			data.put("ruond", r.round);
//			data.put("name", r.home_team.name+" VS "+r.visiting_team.name);
//			data.put("point", r.home_team_point+" : "+r.visiting_team_point);
//			data.put("type", r.gameType.name);
//			data.put("date", SDF_TO_DAY.format(r.date));
//			datalist.add(data);
//		}
//		results.put("datalist", datalist);
//		renderSuccess(results);
//	}
//	
//	private static void setupWXInfo(Member m, String code){
//		
//		play.Logger.info("setupWXInfo url="+urlToken+code);
//		String rToken = HttpTool.getHttpInputStream(urlToken+code);
//		play.Logger.info("setupWXInfo rToken="+rToken);
//		JSONObject jsonToken = JSONObject.fromObject(rToken);
//		play.Logger.info("setupWXInfo jsonToken="+jsonToken);
//		String access_token = jsonToken.getString("access_token");
//		String openid = jsonToken.getString("openid");
//		if(!StringUtil.isEmpty(access_token) && !StringUtil.isEmpty(openid)){
//			String rWX = HttpTool.getHttpInputStream(urlWX.replace("_ACCESS_TOKEN", access_token).replace("_OPENID", openid));
//			if(!StringUtil.isEmpty(rWX)){
//				JSONObject jsonWX = JSONObject.fromObject(rWX);
//				m.openID = jsonWX.getString("openid");
//				m.nickname = jsonWX.getString("nickname");
//				switch (jsonWX.getInt("sex")) {
//				case 1:
//					m.gender = "男";
//					break;
//				case 2:
//					m.gender = "女";
//					break;
//				default:
//					m.gender = "";
//					break;
//				}
//				m.region = jsonWX.getString("province")+" "+jsonWX.getString("city");
//				m.nationality = jsonWX.getString("country");
//				m.headimgurl = jsonWX.getString("headimgurl");
//				m._save();
//			}
//		}
//	}
//	
//	public static void insertSN(@Required String username, @Required String pwd, @Required String from,@Required String to) {
//		if (Validation.hasErrors()) {
//			renderText("error");
//		}
//		
//		AdminManagement user = AdminManagement.find("byName", username).first();
//		if(user == null)renderText("error username");
//		if(!user.pwd.equals(pwd))renderText("error password");
//		
//		Integer snf = Integer.parseInt(from.substring(from.length()-5,from.length()));
//		Integer snt = Integer.parseInt(to.substring(to.length()-5,to.length()));
//		String str = from.substring(0,from.length()-5);
//		
//		Connection con = DB.getConnection();
//		int count=0;
//		try {
//			con.setAutoCommit(false);
//			con.setTransactionIsolation(con.TRANSACTION_READ_UNCOMMITTED);
//			PreparedStatement pst = (PreparedStatement) con.prepareStatement("insert into serial_number(sn) values (?)");
//			String tmp = "";
//			for(long i=snf;i<=snt;i++){
//				if(i<10)tmp="0000"+i;
//				else if(i<100)tmp="000"+i;
//				else if(i<1000)tmp="00"+i;
//				else if(i<10000)tmp="0"+i;
//				else if(i<100000)tmp=""+i;
//				pst.setString(1, str+tmp);
//				pst.addBatch();
//				count++;
//			}
//			pst.executeBatch();
//			con.commit();
//			renderText(count);
//		}catch(Exception e){
//			play.Logger.error("insertSN:"+e.getMessage());
//			renderText("error2");
//		}finally {
//			DB.close();
//		}
//	}
//	
//	public static void saveTempFile(Blob f_edit_img, Integer rs, Integer sType, Long tfId, @Required String z){
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		JSONObject results = initResultJSON();
//		if(sType == null || sType == 0){
//			if(f_edit_img == null){
//				if(rs!=null){
//					Session s = sessionCache.get();
//					if(rs == 111){
//						if(s.member.img_ch != null && s.member.img_ch.exists()){
//							results.put("tf", "/c/download?id=" + s.member.id + "&fileID=img_ch&entity=" + s.member.getClass().getName() + "&z=" + z);
//						}
//					}else if(rs == 113){
//						if(s.member.identification != null && s.member.identification.exists()){
//							results.put("tf", "/c/download?id=" + s.member.id + "&fileID=identification&entity=" + s.member.getClass().getName() + "&z=" + z);
//						}
//					}else if(rs == 121){
//						Team t = Team.find("coach_id=? or captain_id=?", s.member.id, s.member.id).first();
//						if(t != null && t.logo != null && t.logo.exists()){
//							results.put("tf", "/c/download?id=" + t.id + "&fileID=logo&entity=" + t.getClass().getName() + "&z=" + z);
//						}
//					}
//				}
//				
//				if(!results.containsKey("tf"))results.put("tf", BOY);
//			}else{
//				TempFile tf = new TempFile();
//				tf.tempFile = f_edit_img;
//				tf.save();
//				results.put("tfId", tf.id);
//				results.put("tf", "/c/download?id=" + tf.id + "&fileID=tempFile&entity=" + tf.getClass().getName() + "&z=" + z);
//			}
//		}else{
//			Session s = sessionCache.get();
//			if(tfId != null){
//				TempFile tf = TempFile.findById(tfId);
//				if(rs == 111){
//					s.member.img_ch = tf.tempFile;
//					s.member._save();
//				}else if(rs == 113){
//					s.member.identification = tf.tempFile;
//					s.member._save();
//				}else if(rs == 121){
//					Team t = Team.find("coach_id=? or captain_id=?", s.member.id, s.member.id).first();
//					if(t == null){
//						t = new Team();
//						t.updated_at_ch = new Date();
//						t.captain = s.member;
//						t.coach = s.member;
//					}
//					t.logo = tf.tempFile;
//					t._save();
//				}	
//			}
//		}
//		renderSuccess(results);
//	}
//	
	
//	static final String chStr = "【愛試裝】您好，你的验证码是";
	static final String chStr = "【253云通讯】您好，你的验证码是123456";
	/**
	 * 发送验证码到手机
	 * 
	 * @param m_number
	 */
	public static void checkDigit(@Required String phone) {
		if(!Validation.phone(SUCCESS, phone).ok || phone.length() != 11){
			renderFail("error_parameter_phone");
		}
		
		UserModel m = UserModel.find("mobile=?", Long.parseLong(phone.trim())).first();
		if(m != null){
			renderFail("error_username_already_used");
		}
		
		Random r = new Random();
		int sum = 0;
		for(int i=1;i<=1000;i=i*10){
			int n = 0;
			while(n==0)n = r.nextInt(10);
			sum += n*i;
		}
		
		try {
			String s = HttpSender.batchSend(phone, chStr+sum);
			String[] sArr = s.split(",");
			if(sArr == null || sArr.length<2 || !"0".equals(sArr[1].substring(0, 1))){
				play.Logger.error("checkDigit: result="+s+" PNumber="+phone+" digit="+sum);
				renderText(play.i18n.Messages.get("error_verification_code"));
			}
			
			CheckDigit cd = CheckDigit.find("m=?", Long.parseLong(phone)).first();
			if(cd == null)cd = new CheckDigit();
			cd.d = sum;
			cd.updatetime = new Date().getTime();
			cd.m = Long.parseLong(phone);
			cd._save();
		} catch (Exception e) {
			play.Logger.error("checkDigit: PNumber="+phone+" digit="+sum);
			play.Logger.error(e.getMessage());
			renderText(play.i18n.Messages.get("error_verification_code_sys"));
		}
		renderText("OK");
	}
	
	/**
	 * 用户注册
	 * 
	 * @param z
	 */
	public static void register(@Required String phone, @Required String pwd, @Required String vc, String code) {
		if (Validation.hasErrors()) {
			if(!Validation.phone(SUCCESS, phone).ok || phone.length() != 11)renderFail("error_parameter_phone");
			if(StringUtil.isEmpty(vc))renderFail("error_parameter_vc");
			if(StringUtil.isEmpty(pwd))renderFail("error_parameter_pwd");
		}

		try {
		
			CheckDigit c = CheckDigit.find("d=?", Integer.parseInt(vc)).first();
			if(c == null){
				renderFail("error_checkdigit");
			}
			if(!phone.equals(c.m+"")){
				renderFail("error_checkdigit_phone");
			}
			if(new Date().getTime() - c.updatetime > 1800000000){
				c.delete();
				renderFail("error_checkdigit_time");
			}

			UserModel m = UserModel.find("byMobile", Long.parseLong(phone.trim())).first();
			if(m != null){
				play.Logger.info("register:error_username_already_used");
				renderFail("error_username_already_used");
			}
	
			m = new UserModel();
			m.name = "";
			m.mobile = Long.parseLong(phone);
			m.pwd = pwd;
			m.updateDate = new Date();
			m.createDate = new Date();
			m._save();
			
			UserDetailModel md = new UserDetailModel();
			md.user = m;
			md.address = "快填写邮寄地址";
			md.height = 0;
			md.weight = 0;
			md.updateDate = new Date();
			md._save();
//			if(!StringUtil.isEmpty(code))setupWXInfo(m, code);
			//c.delete();
			
			Session s = new Session();
			s.user = m;
			s.date = new Date();
			s.sessionID = UUID.randomUUID().toString();
			s.save();
			sessionCache.set(s);
			
			JSONObject results = initResultJSON();
			results.put("phone", m.mobile);
			results.put("id", m.id);
			play.Logger.info("register:OK "+m.mobile);
			
			renderSuccess(results);
			
		} catch (Exception e) {
			play.Logger.info("register:error "+e.getMessage());
			renderFail("error_unknown");
		}		
	}
		
	/**
	 * 登录
	 * 
	 * @param phone
	 * @param pwd
	 * @param os
	 * @param serialNumber
	 * @param ip
	 * @param imei
	 * @param mac
	 * @param imsi
	 */
	public static void login(@Required String name, @Required String pwd) {
		// ....
		if (Validation.hasErrors()) {
			if(StringUtil.isEmpty(name))renderFail("error_parameter_name");
			if(StringUtil.isEmpty(pwd))renderFail("error_parameter_pwd");
		}

		UserModel m = UserModel.find("byMobile", Long.parseLong(name)).first();
		
		if(m == null){
			m = UserModel.find("byEmail", name).first();
			if(m == null)m = UserModel.find("byWx_id", name).first();
		}
		
		if(m == null || !m.pwd.equals(pwd)){
			renderFail("error_username_or_password_not_match");
		}
		
		Session s = Session.find("user_openid=?", m.id).first();
		if(s == null){
			s = new Session();
			s.user = m;
			s.sessionID = UUID.randomUUID().toString();
		}
		s.date = new Date();
		s._save();
		
		m.updateDate = new Date();
		m._save();
		sessionCache.set(s);
		
		play.Logger.info("login:OK "+m.mobile);
		renderSuccess(initResultJSON());
	}
	
	
	
//	/**
//	 * 登出
//	 * 
//	 * @param z
//	 */
//	public static void logout(@Required String z) {
//		// ....
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		Session s = sessionCache.get();
//		if(s != null && s.id != 1 && s.id != 2){
//			s.delete();
////			s.sessionID = "";
////			s.save();
//		}
//		renderSuccess(initResultJSON());
//	}
//
//	/**
//	 * 重置密码
//	 * 
//	 * @param m
//	 * @throws UnsupportedEncodingException
//	 */
//	@SuppressWarnings("deprecation")
//	public static void sendResetPasswordMail(@Required String p)
//			throws UnsupportedEncodingException {
//
//            if (Validation.hasErrors()) {
//                    renderFail("error_parameter_required");
//            }
//
//            Member m = Member.find("byPhone", p).first();
//            if (m == null) {
//                m = Member.find("byWeixin", m).first();
//                if(m == null){
//                	List<Member> tmp = Member.find("byEmail", m).fetch();
//                	if(tmp.size() == 0)renderFail("error_username_not_exist");
//                	if(StringUtil.isEmpty(tmp.get(0).email))renderFail("error_email_empty");
//                	SendMail mail = new SendMail(
//                            Play.configuration.getProperty("mail.smtp.host"),
//                            Play.configuration.getProperty("mail.smtp.user"),
//                            Play.configuration.getProperty("mail.smtp.pass"));
//
//                	mail.setSubject(Messages.get("mail_resetpassword_title"));
//                	String text = "";
//                	for(Member tmpC : tmp){
//                		text += "phone:"+tmpC.phone+"password:"+tmpC.pwd+"\n";
//                	}
//                	mail.setBodyAsText(text);
//
//	            String nick = Messages.get("mail_show_name");
//	            try {
//	                    nick = javax.mail.internet.MimeUtility.encodeText(nick);
//	                    mail.setFrom(new InternetAddress(nick + " <"
//	                                    + Play.configuration.getProperty("mail.smtp.from") + ">")
//	                                    .toString());
//	                    mail.setTo(tmp.get(0).email);
//	                    mail.send();
//	            } catch (Exception e) {
//	                    renderFail("error_mail_resetpassword");
//	            }
//	            renderSuccess(initResultJSON());
//                	
//                }
//            }
//
//            if(StringUtil.isEmpty(m.email))renderFail("error_email_empty");
//            SendMail mail = new SendMail(
//                            Play.configuration.getProperty("mail.smtp.host"),
//                            Play.configuration.getProperty("mail.smtp.user"),
//                            Play.configuration.getProperty("mail.smtp.pass"));
//
//            mail.setSubject(Messages.get("mail_resetpassword_title"));
//            mail.setBodyAsText("password:"+m.pwd);
//
//            String nick = Messages.get("mail_show_name");
//            try {
//                    nick = javax.mail.internet.MimeUtility.encodeText(nick);
//                    mail.setFrom(new InternetAddress(nick + " <"
//                                    + Play.configuration.getProperty("mail.smtp.from") + ">")
//                                    .toString());
//                    mail.setTo(m.email);
//                    mail.send();
//            } catch (Exception e) {
//                    renderFail("error_mail_resetpassword");
//            }
//            renderSuccess(initResultJSON());
//    }
//	
//	/**
//	 * 重置密码
//	 * 
//	 * @param m
//	 * @throws UnsupportedEncodingException
//	 */
//	@SuppressWarnings("deprecation")
//	public static void sendResetPasswordSMS(@Required String phone, @Required String pwd, @Required String vc)
//			throws UnsupportedEncodingException {
//
//            if (Validation.hasErrors()) {
//            	if(!Validation.phone(SUCCESS, phone).ok || phone.length() != 11)renderFail("error_parameter_phone");
//    			if(StringUtil.isEmpty(vc))renderFail("error_parameter_vc");
//    			if(StringUtil.isEmpty(pwd))renderFail("error_parameter_pwd");
//            }
//
//            Member m = Member.find("byPhone", phone).first();
//            if (m == null) {
//                m = Member.find("byWeixin", m).first();
//                if(m == null){
//                	List<Member> tmp = Member.find("byEmail", m).fetch();
//                	if(tmp.size() == 0)renderFail("error_username_not_exist"); 	
//                }
//            }
//
//            if(m == null)renderFail("error_username_not_exist");
//            
//            CheckDigit c = CheckDigit.find("d=?", vc).first();
//			if(c == null){
//				renderFail("error_checkdigit");
//			}
//			if(!c.m.equals(phone)){
//				renderFail("error_checkdigit");
//			}
//			if(new Date().getTime() - c.updatetime > 1800000){
//				c.delete();
//				renderFail("error_checkdigit");
//			}
//            m.pwd = pwd.trim();
//            m._save();
////            boolean flag = SendSMSMy.sendMsg(m.phone, m.pwd, "5");
////            if(!flag)renderFail("error_unknown");
//            renderSuccess(initResultJSON());
//    }
//	
//	/**
//	 * 手表同步时间(定位器)
//	 * 
//	 * @param sn
//	 */
//	public static void syncTime(String sn) {		
//		renderText(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
//	}
//	
//	/**
//	 * 重置密码
//	 * 
//	 * @param oldPassword
//	 * @param newPassword
//	 * @param z
//	 */
//	public static void changePassword(@Required String oldPassword, @Required String newPassword, @Required String z) {
//		JSONObject results = initResultJSON();
//		// ....
//		if (Validation.hasErrors()) {
//			renderFail("error_parameter_required");
//		}
//		Session s = Session.find("bySessionID",z).first();
//		if(s.member.pwd.equals(oldPassword) && !StringUtil.isEmpty(newPassword)){
//			s.member.pwd = newPassword;
//			s.member._save();
//			renderSuccess(results);
//		}else{
//			renderFail("error_old_password_not_match");
//		}
//	}
	
	/**
	 * 下载
	 * 
	 * @param id
	 * @param fileID
	 * @param entity
	 */
	public static void download(@Required String id, @Required String fileID, @Required String entity) {

		if (Validation.hasErrors()) {
			renderFail("error_parameter_required");
		}
		ObjectType type;
		try {
			type = new ObjectType(entity);
			notFoundIfNull(type);

			Model object = type.findById(id);
			notFoundIfNull(object);
			Object att = object.getClass().getField(fileID).get(object);
			if (att instanceof Model.BinaryField) {
				Model.BinaryField attachment = (Model.BinaryField) att;
				if (attachment == null || !attachment.exists()) {
					renderFail("error_download");
				}
				long p = 0;
				Header h = request.headers.get("Range");
				play.Logger.info("download header:", h);
				if(h != null){
					p = Long.parseLong(h.value().replaceAll("bytes=", "").replaceAll("-", ""));
				}
				play.Logger.info("download header:", p);
				response.contentType = attachment.type();
				if(p > 0){
					renderBinary(attachment.get(), attachment.get().skip(p));
				}else{
					renderBinary(attachment.get(), attachment.length());
				}
				
			}
		} catch (Exception e) {
			renderText("Download failed");
		}
		renderFail("error_download");
	}

	protected static JSONObject initResultJSON() {
		return JSONUtil.getNewJSON();
	}
	
	protected static JSONArray initResultJSONArray() {
		return JSONUtil.getNewJSONArray();
	}

	protected static void renderSuccess(JSONObject results) {
		JSONObject jsonDoc = new JSONObject();
		jsonDoc.put("state", SUCCESS);
		Session s = sessionCache.get();
		if(s != null && !StringUtil.isEmpty(s.sessionID))jsonDoc.put("session", s.sessionID);
		jsonDoc.put("results",results);
		response.setHeader("Access-Control-Allow-Origin", "*");
		renderJSON(jsonDoc.toString());
	}

	protected static void renderFail(String key, Object... objects) {
		JSONObject jsonDoc = new JSONObject();
		jsonDoc.put("state", FAIL);
		Session s = sessionCache.get();
		if(s != null && !StringUtil.isEmpty(s.sessionID))jsonDoc.put("session", s.sessionID);
		jsonDoc.put("msg", Messages.get(key));
		response.setHeader("Access-Control-Allow-Origin", "*");
		renderJSON(jsonDoc.toString());
	}

}
