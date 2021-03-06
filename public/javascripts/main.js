var successMsg;

function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount; //当前剩余秒数
function sendCode(type){

	
	var p = $("#phone").val();
//	if(p == null || p == ''){
//		alert("手机号不能为空");
//		return;
//	}
	var url;
	if(type == 0)url = "/api/cd";
	else if(type == 1)url = "/api/cdf";
	var data = {
            phone:p
        };
	
	$.ajax({
		url: url,
        type: "post",
        data: data,
        dataType: "text",
        success:function(msg){
            if(msg=='OK'){
            	SetVCCode();
            	curCount = count;
            	$("#vcButton").attr("disabled", true);
            	$("#vcButton").val(curCount+"秒");
            	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            }else{
            	var obj = jQuery.parseJSON(msg);
            	findError(obj);
            }
        }
	});

}

//timer处理函数
function SetRemainTime() {
	if (curCount == 0) {                
		window.clearInterval(InterValObj);//停止计时器
		$("#vcButton").attr("disabled", false);
		$("#vcButton").text("重新发送");    
	} else {
		curCount--;
		$("#vcButton").text(curCount + "秒");
	}
}

function regSubmit() {
	var code = $("#code").val();
	var p = $("#phone").val();
	var vc = $("#vc").val();
	var pwd = $("#pwd").val();
	if(p == null || p == ''){
		alert("手机号不能为空");
		return;
	}
	if(vc == null || vc == ''){
		alert("验证码不能为空");
		return;
	}
	if(pwd == null || pwd == ''){
		alert("密码不能为空");
		return;
	}
	var data = {
			code:code,
            phone:p,
            vc:vc,
            pwd:pwd
        };
	
	sendRequest("/api/reg", "post", data, "text", 12, 1, 0);
}

function forgetPWDSubmit() {
	var n = $("#phone").val();
	var vc = $("#vc").val();
	var pwd = $("#pwd").val();
	
	var data = {
			phone:n,
            vc:vc,
            pwd:pwd
        };
	sendRequest("/c/fps", "post", data, "text", 11, 2, 0);
}

function loginSubmit() {
	var n = $("#name").val();
	var pwd = $("#pwd").val();
	var repage = GetQueryString("repage");
	if(repage == null)repage = 14;
	else repage = Number(repage);
	if(n == null || n == ''){
		alert("用户名不能为空");
		return;
	}
	if(pwd == null || pwd == ''){
		alert("密码不能为空");
		return;
	}
	var data = {
            name:n,
            pwd:pwd
        };
	sendRequest("/api/login", "post", data, "text", repage, 0, 0);
}

function sendRequest(url, method, data, dataType, forword, successMsg, repage){
	
	$.ajax({
        url: url,
        type: method,
        data: data,
        dataType: dataType,
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.session != null && obj.session != ''){
    			sessionStorage.setItem("sessionID", obj.session);
    		}
        	if(obj.state==1){
        		if(forword != null && forword != ''){
        			jumppage(forword, successMsg, repage);
        		}
        	}else{
        		findError(obj);
        	}
        }
    });
}

function loadOrderList(){
	var code = $("#code").val();
	var data = {
			code:code,
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/api/order/list",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	sessionStorage.setItem("sessionID", obj.session);
        	
        	if(obj.state==1){
        		str = "";
        		$.each(obj.results.list, function(index, json) {
        			str+="<tr>";
        			str+="<td><a href='/public/html5/order/view.html?oCode="+json.id+"'>"+json.name+"</a></td>";
        			str+="<td>"+json.order_num+"</td>";
        			str+="<td>"+json.paystatus+"</td>";
        			str+="<td>"+json.logistics+"</td>";
        			str+="<td>"+json.sales_date+"</td>";
                	str+="</tr>";
        		});
        		$("#o_list").append(str);
        	}else{
        		findError(obj);
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 120);
        		}
        	}	
        }
    });
}

function loadViewOrderData(){

	var data = {
			oCode:GetQueryString("oCode"),
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/api/order/info",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		
        		$("#name").text(obj.results.name);
        		$("#goods_img").attr("src", obj.results.goods_img);
        		$("#nameplate_price").text(obj.results.nameplate_price);
        		$("#rebate").text(obj.results.rebate);
        		$("#sales_price").text(obj.results.sales_price);
        		$("#order_num").text(obj.results.order_num+obj.results.order_unit);
        		$("#sales_date").text(obj.results.sales_date);
        		$("#memo").text(obj.results.memo);
        		$("#logistics").text(obj.results.logistics);
        		$("#paystatus").text(obj.results.paystatus);
        		$("#user").text(obj.results.user);
        	}else{
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 111);
        		}
        	}	
        }
    });
}

function loadViewPersonalData(){

	var data = {
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/api/user/all",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		$("#name").text(obj.results.name);
        		$("#nickname").text(obj.results.nickname);
        		$("#birthday").text(obj.results.birthday);
        		$("#gender").text(obj.results.gender);
        		$("#email").text(obj.results.email);
        		$("#phone").text(obj.results.phone);
        		$("#weixin").text(obj.results.weixin);
        		$("#school").text(obj.results.school);
        		$("#size").text(obj.results.size);
        		$("#face").text(obj.results.face);
        		$("#skincolor").text(obj.results.skincolor);
        		$("#pricerange").text(obj.results.pricerange);
        		$("#hair").text(obj.results.hair);
        		$("#inclination").text(obj.results.inclination);
        		$("#body").text(obj.results.body);
        		$("#city").text(obj.results.city);
        		$("#address").text(obj.results.address);
        		$("#height").text(obj.results.height);
        		$("#weight").text(obj.results.weight);
        		$("#constellation").text(obj.results.constellation);
        		$("#blood").text(obj.results.blood);
        		$("#updated_at_ch").text(obj.results.updated_at_ch);
            	
        	}else{
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 111);
        		}
        	}	
        }
    });
}

function loadEditPersonalData(){

	var gender;
	var data = {
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/api/user/info",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
//        		$("#v_img_ch").attr("src", obj.results.img_ch);
        		$("#id").val(obj.results.pId);
        		$("#name").val(obj.results.name);
        		$("#nickname").val(obj.results.nickname);
        		$("#birthday").val(obj.results.birthday);
        		if('女'==obj.results.gender){
        			$("#gender").prepend("<option value='1'>男</option>");
        			$("#gender").prepend("<option value='2' selected>女</option>");
        		}else{
        			$("#gender").prepend("<option value='1' selected>男</option>");
        			$("#gender").prepend("<option value='2'>女</option>");
        		}
        		
        		
        		$("#email").val(obj.results.email);
        		$("#weixin").val(obj.results.weixin);
            	$("#mobile").val(obj.results.phone);
            	$("#constellation").val(obj.results.constellation);
            	$("#school").val(obj.results.school);
            	
        	}else{
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 111);
        		}
        	}	
        }
    });
}

function loadEditPersonalDetailData(){
	var size,face,skincolor,pricerange,hair,blood, inclination,body,hierarchy,postDay,frequency,constellation, job1, job2, gender;
	var data = {
            z:sessionStorage.getItem("sessionID")
        };
	
	$.ajax({
        url: "/api/userdetail/info",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		$("#size").val(obj.results.size);
        		$("#face").val(obj.results.face);
        		$("#skincolor").val(obj.results.skincolor);
        		$("#pricerange").val(obj.results.pricerange);
        		$("#hair").val(obj.results.hair);
        		$("#inclination").val(obj.results.inclination);
        		$("#bodymodel").val(obj.results.body);
        		$("#hierarchy").val(obj.results.hierarchy);
        		$("#postDay").val(obj.results.postDay);
        		$("#frequency").val(obj.results.frequency);
        		$("#city").val(obj.results.city);
        		$("#address").val(obj.results.address);
        		$("#height").val(obj.results.height);
        		$("#weight").val(obj.results.weight);
        		$("#constellation").val(obj.results.constellation);
        		$("#blood").val(obj.results.blood);
            	
        		size = obj.results.size;
        		face = obj.results.face;
        		skincolor = obj.results.skincolor;
        		pricerange = obj.results.pricerange;
        		hair = obj.results.hair;
        		inclination = obj.results.inclination;
        		bodymodel = obj.results.body;
        		hierarchy = obj.results.hierarchy;
        		postDay = obj.results.postDay;
        		frequency = obj.results.frequency;
        		constellation = obj.results.constellation;
        		blood = obj.results.blood;

        	}else{
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 111);
        		}
        	}	
        }
    });
	
	$.ajax({
        url: "/api/userdetail/init",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		$.each(obj.results.bloodlist, function(index, json) { 
        			for(var key in json){  
        				if(blood == key){
        					$("#blood").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#blood").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
        		$.each(obj.results.constellationlist, function(index, json) { 
        			for(var key in json){  
        				if(constellation == key){
        					$("#constellation").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#constellation").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.bodymodellist, function(index, json) { 
        			for(var key in json){  
        				if(bodymodel == key){
        					$("#bodymodel").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#bodymodel").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.bodysizelist, function(index, json) { 
        			for(var key in json){  
        				
        				if(size == key){
        					$("#size").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#size").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.facemodellist, function(index, json) { 
        			for(var key in json){  
        				
        				if(face == key){
        					$("#face").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#face").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.hairmodellist, function(index, json) { 
        			for(var key in json){  
        				if(hair == key){
        					$("#hair").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#hair").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.inclinationmodellist, function(index, json) { 
        			for(var key in json){  
        				if(inclination == key){
        					$("#inclination").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#inclination").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.pricerangelist, function(index, json) { 
        			for(var key in json){  
        				if(pricerange == key){
        					$("#pricerange").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#pricerange").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
          		$.each(obj.results.skincolormodellist, function(index, json) { 
        			for(var key in json){  
        				if(skincolor == key){
        					$("#skincolor").prepend("<option value="+key+" selected>"+json[key]+"</option>");
        				}else{
        					$("#skincolor").prepend("<option value="+key+">"+json[key]+"</option>");
        				}
        			}
        		});
//          		$.each(obj.results.hierarchymodellist, function(index, json) { 
//        			for(var key in json){  
//        				if(constellation == json[key]){
//        					$("#hierarchy").prepend("<option value="+key+" selected>"+json[key]+"</option>");
//        				}else{
//        					$("#hierarchy").prepend("<option value="+key+">"+json[key]+"</option>");
//        				}
//        			}
//        		});
//           		$.each(obj.results.frequencymodellist, function(index, json) { 
//        			for(var key in json){  
//        				if(constellation == json[key]){
//        					$("#face").prepend("<option value="+key+" selected>"+json[key]+"</option>");
//        				}else{
//        					$("#face").prepend("<option value="+key+">"+json[key]+"</option>");
//        				}
//        			}
//        		});
        	}	
        }
    });
}

function personalSubmit(){
//	$("#img_ch").attr();

	var data = {
			name: $("#name").val(),
			nickname: $("#nickname").val(),
			birthday: $("#birthday").val(),
			gender: $("#gender").val(),
			job2: $("#job2").val(),
			email: $("#email").val(),
			phone: $("#mobile").val(),
			school: $("#school").val(),
			z:sessionStorage.getItem("sessionID")
        };
	
	sendRequest("/api/user/save", "post", data, "text", 13, 3, 0);
}

function personalDetailSubmit(){
//	$("#img_ch").attr();

	var data = {
			size: $("#size").val(),
			face: $("#face").val(),
			skincolor: $("#skincolor").val(),
			pricerange: $("#pricerange").val(),
			hair: $("#hair").val(),
			inclination: $("#inclination").val(),
			bodymodel: $("#bodymodel").val(),
			city: $("#city").val(),
			address: $("#address").val(),
			height: $("#height").val(),
			weight: $("#weight").val(),
			constellation: $("#constellation").val(),
			blood: $("#blood").val(),
			z:sessionStorage.getItem("sessionID")
        };
	
	sendRequest("/api/userdetail/save", "post", data, "text", 14, 3, 0);
}

function loadPGameList(){
	var data = {
			pId:GetQueryString("pId")
        };
	$.ajax({
        url: "/c/g/ggl",
        data: data,
        type: "get",
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		var n=1;
        		str = "";
        		$.each(obj.results.gamelist, function(index, json) {
        			str+="<tr><td>";
        			str+=n;
        			str+="</td><td>";
        			str+=json.name;
        			str+="</td><td>";
        			str+="<img src='"+json.logo+"' width='100' height='100'>";
                	str+="</td></tr>";
                	n++;
        		});
        		$("#p_l_games").append(str);
        	}else{
//        		alert(obj.msg);
        		findError(obj);
        	}	
        }
    });
}

function loadPTeamList(){
	var data = {
			pId:GetQueryString("pId")
        };
	$.ajax({
        url: "/c/t/gtl",
        data: data,
        type: "get",
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		var n=1;
        		str = "";
        		$.each(obj.results.teamlist, function(index, json) {
        			str+="<tr><td>";
        			str+=n;
        			str+="</td><td>";
        			str+=json.name;
        			str+="</td><td>";
        			str+="<img src='"+json.logo+"' width='100' height='100'>";
        			str+="</td><td>";
        			str+=json.updated_at_ch;
                	str+="</td></tr>";
                	n++;
        		});
        		$("#p_l_teams").append(str);
        	}else{
//        		alert(obj.msg);
        		findError(obj);
        	}	
        }
    });
}

function findError(obj){
	if(obj.msg == 'error_parameter_phone'){
		SetErrSMsg(100);
	}else if(obj.msg == 'error_username_already_used'){
		SetErrSMsg(101);
	}else if(obj.msg == 'session_expired'){
		SetSMsg(11, 4, 0);
	}else if(obj.msg == 'error_parameter_pwd'){
		SetErrSMsg(104);
	}else if(obj.msg == 'error_parameter_vc'){
		SetErrSMsg(105);
	}else if(obj.msg == 'error_checkdigit'){
		SetErrSMsg(106);
	}else if(obj.msg == 'error_checkdigit_phone'){
		SetErrSMsg(107);
	}else if(obj.msg == 'error_checkdigit_time'){
		SetErrSMsg(108);
	}else if(obj.msg == 'error_parameter_name'){
		SetErrSMsg(109);
	}else if(obj.msg == 'error_username_or_password_not_match'){
		SetErrSMsg(110);
	}else if(obj.msg == 'error_username_not_exist'){
		SetErrSMsg(111);
	}else if(obj.msg == 'error_team_notexist'){
		SetErrSMsg(112);
	}else if(obj.msg == 'error_nothave_game'){
		SetErrSMsg(113);
	}else if(obj.msg == 'error_notadde_team'){
		SetErrSMsg(114);
	}else if(obj.msg == 'error_team_su_exist'){
		SetErrSMsg(115);
	}else if(obj.msg == 'error_teamname_null'){
		SetErrSMsg(116);
	}else if(obj.msg == 'error_parameter_date'){
		SetErrSMsg(117);
	}else if(obj.msg == 'error_parameter_pname'){
		SetErrSMsg(118);
	}else if(obj.msg == 'error_parameter_pnickname'){
		SetErrSMsg(119);
	} else {
		SetErrSMsg(200);
	}
}

function jumppage(page, smsg, repage){

	var successMsg="";
	var resource="";
	if(smsg != 0){
		successMsg = "?successMsg="+smsg;
	}
	if(repage != 0){
		resource = "&repage="+repage;
	}
	
	var regpage = "/public/html5/reg.html"+successMsg+resource;
	var loginpage = "/public/html5/login.html"+successMsg+resource;
	var userpage = "/public/html5/user/form.html"+successMsg+resource;
	var userdetailpage = "/public/html5/userdetail/form.html"+successMsg+resource;
	var userviewpage = "/public/html5/user/view.html"+successMsg+resource;
	switch(page){
	case 10:
		window.location = regpage;
		break;
	case 11:
		window.location = loginpage;
		break;
	case 12:
		window.location = userpage;
		break;
	case 13:
		window.location = userdetailpage;
		break;
	case 14:
		window.location = userviewpage;
		break;
	default:
	}
}
var msgCount=3;

function SetVCCode(){
	$("#gmsg").text("验证码已发出!");
	$("#gmsg").show();
}

function SetSMsg(code){
	curCount = msgCount;
	
	if('1' == code){
		$("#gmsg").text("注册成功！");
	}else if('2' == code){
		$("#gmsg").text("请等待短信找回密码!");
	}else if('3' == code){
		$("#gmsg").text("修改成功!");
	}else if('4' == code){
		$("#gmsg").text("请先登录");
	}else if('5' == code){
		$("#gmsg").text("报名成功,请等待审核！");
	}
	$("#gmsg").show();
	InterValObj = window.setInterval(SetSMsgTime, 1000); //启动计时器，1秒执行一次
}

function SetErrSMsg(code){
	curCount = msgCount;
	if(99 == code){
		$("#gmsg").text("验证码已发出!");
	}else if(100 == code){
		$("#gmsg").text("请输入有效手机号码!");
	}else if(101 == code){
		$("#gmsg").text("该手机号已被注册!");
	}else if(102 == code){
		$("#gmsg").text("参数验证错误!");
	}else if(200 == code){
		$("#gmsg").text("系统错误!");
	}else if(104 == code){
		$("#gmsg").text("请输入密码!");
	}else if(105 == code){
		$("#gmsg").text("请输入验证码!");
	}else if(106 == code){
		$("#gmsg").text("验证码错误!");
	}else if(107 == code){
		$("#gmsg").text("手机号与验证码不匹配!");
	}else if(108 == code){
		$("#gmsg").text("验证码已过期!");
	}else if(109 == code){
		$("#gmsg").text("请输入用户名!");
	}else if(110 == code){
		$("#gmsg").text("用户名和密码不匹配!");
	}else if(111 == code){
		$("#gmsg").text("用户名不存在!");
	}else if(112 == code){
		$("#gmsg").text("尚未创建球队!");
	}else if(113 == code){
		$("#gmsg").text("最近没有比赛!");
	}else if(114 == code){
		$("#gmsg").text("没有加入任何球队!");
	}else if(115 == code){
		$("#gmsg").text("球队已经报名!");
	}else if(116 == code){
		$("#gmsg").text("请填写球队名称!");
	}else if(117 == code){
		$("#gmsg").text("日期格式错误 例:1990-01-01");
	}else if(118 == code){
		$("#gmsg").text("姓名不能为空!");
	}else if(119 == code){
		$("#gmsg").text("绰号已被占用，请重新填写!");
	}
	
	$("#gmsg").show();
	InterValObj = window.setInterval(SetSMsgTime, 1000); //启动计时器，1秒执行一次
}
//timer处理函数
function SetSMsgTime() {
	if (curCount == 0) {                
		window.clearInterval(InterValObj);//停止计时器
		$("#gmsg").hide();    
	} else {
		curCount--;
	}
}


function loadInitTeamData(){
	var code = $("#code").val();
	var data = {
			code:code,
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/c/t/gti",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	sessionStorage.setItem("sessionID", obj.session);
        	
        	if(obj.state==1){
        		$("#v_img_t_logo").attr("src", obj.results.logo);
        		$("#name").text(obj.results.name==null?'':obj.results.name);
        		$("#v_coach_img").attr("src", obj.results.coach_img);
        		$("#coach").text(obj.results.coach==null?'':obj.results.coach);
        		$("#coach").attr("href","/public/html5/personal/pubinfo_view.html?pId="+obj.results.coach_id);
          		$("#v_captain_img").attr("src", obj.results.captain_img);
        		$("#captain").text(obj.results.captain==null?'':obj.results.captain);
        		$("#captain").attr("href","/public/html5/personal/pubinfo_view.html?pId="+obj.results.captain_id);
        		$("#contact").text(obj.results.contact==null?'':obj.results.contact);
        		$("#updated_at_ch").text(obj.results.updated_at_ch);
        		
        		str = "";
        		$.each(obj.results.members, function(index, json) {
        			str+="<tr><td>";
        			str+="姓名: "+json.name;
        			str+="</br>";
        			str+="场上位置: "+json.job1;
        			str+="</br>";
        			str+="号码: "+(json.number==null?'':json.number+" 号");
        			str+="</br>";
        			str+="身高: "+(json.height==null?'':json.height+" CM");
        			str+="</br>";
        			str+="体重: "+(json.weight==null?'':json.weight+" KG");
        			str+="</td>";
        			str+="<td>";
        			str+="<img src='"+json.img_ch+"' width='100' height='100'>";
                	str+="</td></tr>";
        		});
        		$("#t_v_members").append(str);
        	}else{
        		findError(obj);
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 120);
        		}
        	}	
        }
    });
}

function loadEditTeamData(){
	var data = {
            z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/c/t/gti",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	sessionStorage.setItem("sessionID", obj.session);
        	if(obj.state==1){
        		$("#v_img_t_logo").attr("src", obj.results.logo);
        		$("#name").val(obj.results.name);
        		$("#v_coach_img").attr("src", obj.results.coach_img);
        		$("#coach").val(obj.results.coach);
        		$("#v_captain_img").attr("src", obj.results.captain_img);
          		$("#captain").val(obj.results.captain);
        		$("#contact").val(obj.results.contact);
        		
        		str = "";
        		$.each(obj.results.members, function(index, json) {
        			str+="<tr><td>";
        			str+="姓名: "+json.name;
        			str+="</br>";
        			str+="场上位置: "+json.job1;
        			str+="</br>";
        			str+="号码: "+(json.number==null?'':json.number+" 号");
        			str+="</br>";
        			str+="身高: "+(json.height==null?'':json.height+" CM");
        			str+="</br>";
        			str+="体重: "+(json.weight==null?'':json.weight+" KG");
        			str+="</td>";
        			str+="<td>";
        			str+="<img src='"+json.img_ch+"' width='100' height='100'>";
                	str+="</td></tr>";
        		});
        		$("#t_e_members").append(str);
        	}else{
        		if(obj.msg == 'session_expired'){
        			jumppage(11, 4, 121);
        		}
        	}	
        }
    });
}

function teamSubmit(){
	var data = {
			name: $("#name").val(),
			coach: $("#coach").val(),
			captain: $("#captain").val(),
			contact: $("#contact").val(),
			z:sessionStorage.getItem("sessionID")
        };
	
	sendRequest("/c/t/uti", "post", data, "text", 120, 3, 0);
}

function loadInitGameList(){
	var code = $("#code").val();
	data = {
		code:code
	};
	$.ajax({
        url: "/c/g/ggl",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	sessionStorage.setItem("sessionID", obj.session);
        	if(obj.state==1){
        		var n=1;
        		str = "";
        		$.each(obj.results.gamelist, function(index, json) {
        			str+="<tr><td>";
        			str+=n;
        			str+="</td><td>";
        			str+="<a href='/public/html5/game/info_view.html?gId="+json.id+"' target='_blank'>";
        			str+=json.name;
        			str+="</a>";
        			str+="</td><td>";
        			str+=json.state;
        			str+="</td><td>";
        			str+=json.startDate;
                	str+="</td></tr>";
                	n++;
        		});
        		$("#g_l_games").append(str);
        	}else{
//        		alert(obj.msg);
        		findError(obj);
        	}	
        }
    });
}

function loadInitGameData(){
	var data = {
			gId:GetQueryString("gId"),
			z:sessionStorage.getItem("sessionID")
        };
	$.ajax({
        url: "/c/g/ggi",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	
        	if(obj.state==1){
        		$("#gId").val(obj.results.id);
        		$("#v_img_g_logo").attr("src", obj.results.logo);
        		$("#name").text(obj.results.name);
        		$("#describtion").text(obj.results.describtion);
        		//$("#schedule").text(obj.results.schedule);
        		$("#a_g_schedule").attr("href", obj.results.schedule==null?'':obj.results.schedule);
        		$("#startSignUp").text(obj.results.startSignUp);
        		$("#endSignUp").text(obj.results.endSignUp);
        		$("#startDate").text(obj.results.startDate);
        		$("#endDate").text(obj.results.endDate);
        		if(obj.results.isSignUp == 0){
        			$("#b_g_signup").attr("disabled", true);
        			$("#b_g_signup").text("报名结束");
        		}else{
        			$("#b_g_signup").attr("disabled", false);
        			$("#b_g_signup").val("我要报名");
        		}
        		$("#a_g_standings").attr("href", "/public/html5/game/standings.html?gid="+obj.results.id);
        		$("#a_g_results").attr("href", "/public/html5/game/results.html?gid="+obj.results.id);
        		str = "";
        		$.each(obj.results.teamlist, function(index, json) {
        			str+="<tr><td>";
        			str+="球队名称: ";
        			str+="<a href='/public/html5/game/team_view.html?tId="+json.tId+"' target='_blank'>";
        			str+=json.name;
        			str+="</a>";
        			str+="<br/>";
        			str+="教练: "+json.coach;
        			str+="<br/>";
        			str+="队长: "+json.captain;
        			str+="</td>";
        			str+="<td>";
        			str+="<img src='"+json.logo+"' width='100' height='100'>";
                	str+="</td></tr>";
        		});
        		$("#g_v_teams").append(str);
        	}else{
//        		alert(obj.msg);
        		findError(obj);
        	}	
        }
    });
}

function loadGameTeamData(){
	var data = {
            tId:GetQueryString("tId")
        };
	$.ajax({
        url: "/c/g/ggti",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	sessionStorage.setItem("sessionID", obj.session);
        	
        	if(obj.state==1){
        		$("#v_img_t_logo").attr("src", obj.results.logo);
        		$("#name").text(obj.results.name);
        		$("#v_coach_img").attr("src", obj.results.coach_img);
        		$("#coach").text(obj.results.coach);
          		$("#v_captain_img").attr("src", obj.results.captain_img);
        		$("#captain").text(obj.results.captain);
        		$("#contact").text(obj.results.contact);
        		$("#updated_at_ch").text(obj.results.updated_at_ch);
        		
        		str = "";
        		$.each(obj.results.members, function(index, json) {
        			str+="<tr><td>";
        			str+="绰号: "+json.nickname;
        			str+="</br>";
        			str+="年龄: "+json.age;
        			str+="</br>";
        			str+="场上位置: "+json.job1;
        			str+="</br>";
        			str+="号码: "+(json.number==null?'':json.number+" 号");
        			str+="</br>";
        			str+="身高: "+(json.height==null?'':json.height+" CM");
        			str+="</br>";
        			str+="体重: "+(json.weight==null?'':json.weight+" KG");
        			str+="</td>";
        			str+="<td>";
        			str+="<img src='"+json.img_ch+"' width='100' height='100'>";
                	str+="</td></tr>";
        		});
        		$("#t_v_members").append(str);
        	}else{
        		findError(obj);
        	}	
        }
    });
}

function signup(){
	if(sessionStorage.getItem("sessionID")==null||sessionStorage.getItem("sessionID")==''){
		jumppage(11, 4, 133);
		return;
	}
	if(confirm("确认要报名吗?")){
		var id = $("#gId").val();
		var data = {
	            gId:id,
	            z:sessionStorage.getItem("sessionID")
		    };
		$.ajax({
	        url: "/c/g/su",
	        type: "get",
	        data: data,
	        dataType: "text",
	        success:function(msg){
	        	var obj = jQuery.parseJSON(msg);
	        	if(obj.session != null && obj.session != ''){
	    			sessionStorage.setItem("sessionID", obj.session);
	    		}
	        	if(obj.state==1){
	        		SetSMsg(5);
	        	}else{
	        		findError(obj);
	        		if(obj.msg == 'session_expired'){
	        			jumppage(11, 4, 133);
	        		}
	        	}
	        }
	    });
    }else{
        return;
    }
}

function loadGameStandingsData(){

	var gId = GetQueryString("gid");
	var data = {
            gId:gId
        };
	$.ajax({
        url: "/c/g/ggsd",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		$("#v_s_t_name").text(obj.results.game);
        		str = "";
        		$.each(obj.results.datalist, function(index, json) {
        			str+="<tr><td>";
        			str+=json.standing;
        			str+="</td>";
        			str+="<td>";
        			str+=json.name;
        			str+="</td>";
        			str+="<td>";
        			str+=json.round;
        			str+="</td>";
        			str+="<td>";
        			str+=json.win;
        			str+="</td>";
        			str+="<td>";
        			str+=json.lose;
        			str+="</td>";
        			str+="<td>";
        			str+=json.rate;
        			str+="</td>";
                	str+="</td></tr>";
        		});
        		$("#g_v_standings").append(str);
        	}else{
        		if(obj.msg == 'error_parameter_required'){
        			jumppage(133, '0', 0);
        		}
        	}	
        }
    });
}

function loadGameResultsData(){
	
	var gId = GetQueryString("gid");
	var data = {
            gId:gId
        };
	$.ajax({
        url: "/c/g/ggrd",
        type: "get",
        data: data,
        dataType: "text",
        success:function(msg){
        	var obj = jQuery.parseJSON(msg);
        	if(obj.state==1){
        		$("#v_r_t_name").text(obj.results.game);
        		str = "";
        		$.each(obj.results.datalist, function(index, json) {
        			str+="<tr><td>";
        			str+=json.ruond;
        			str+="</td>";
        			str+="<td>";
        			str+=json.name;
        			str+="</td>";
        			str+="<td>";
        			str+=json.point;
        			str+="</td>";
        			str+="<td>";
        			str+=json.type;
        			str+="</td>";
        			str+="<td>";
        			str+=json.date;
                	str+="</td></tr>";
        		});
        		$("#g_v_results").append(str);
        	}else{
        		jumppage(130, '0', 0);
        	}	
        }
    });
}

function setPPFileUpload(){
	$('#f_edit_img').click();
}

function ajaxFileUpload() {
	var rs = GetQueryString("rs");
	var tfId = $("#h_s_tfId").val()
	var data = {
			rs:rs,
			sType:'0',
			tfId:tfId,
            z:sessionStorage.getItem("sessionID")
        };
	
    $.ajaxFileUpload({
            url: '/c/stf',
            secureuri: false,
            fileElementId: 'f_edit_img',
            data: data,
            dataType: 'text',
            success: function (data, status) {
            	var reg = /<pre.+?>(.+)<\/pre>/g;  
            	var result = data.match(reg);
            	data = RegExp.$1;
            	var obj = jQuery.parseJSON(data);
            	if(obj.state==1){
            		var t = obj.results.tf.replace(/&amp;/g,'&');
            		$("#h_s_tfId").val(obj.results.tfId);
            		$("#c_edit_img").attr("src", t);
            	}else{
            		alert(obj.msg);
            	}
            }
        });
    return false;
}

function setImg(resource){
	var saveimgpage = "/public/html5/save_img.html?rs="+resource;
	window.location = saveimgpage;
}

function saveImg(){
	
	var rs = Number(GetQueryString("rs"));
	var tfId = $("#h_s_tfId").val()
	var data = {
			rs:rs,
			sType:'1',
			tfId:tfId,
            z:sessionStorage.getItem("sessionID")
        };
	
    $.ajaxFileUpload({
            url: '/c/stf',
            secureuri: false,
            fileElementId: 'f_edit_img',
            data: data,
            dataType: 'text',
            success: function (data, status) {
            	var reg = /<pre.+?>(.+)<\/pre>/g;  
            	var result = data.match(reg);
            	data = RegExp.$1;
            	var obj = jQuery.parseJSON(data);
            	if(obj.state==1){
            		jumppage(rs, 0, 0);
            	}else{
            		jumppage(13, '10', 0)
            	}
            }
        });
    return false;
}

function setPData(xmlhttp){
	if (xmlhttp.readyState==4 && xmlhttp.status==200){
		var jsonData = eval("(" + xmlhttp.responseText + ")");

		if(jsonData.state == 1){
			var jsonObject = jsonData.results;
			var jsonArr = jsonObject.list;
			
			for(var i=0;i<jsonArr.length;i++){
				if(i==0){
					var opp=new Option("Global","0");
				}else{
					var opp=new Option(jsonArr[i].name+"  "+jsonArr[i].abbr,jsonArr[i].code);
				}
				
				if(mcc == jsonArr[i].code+'')sel.options[i].selected=true;
			 }
			alert(jsonObject);
		}else{
			alert(jsonData.msg);
		}
	}
}

