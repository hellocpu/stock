package com.bs.wt.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.wt.bean.PushForm;
import com.bs.wt.bean.TmpTitle;
import com.bs.wt.service.PostService;
import com.bs.wt.service.TestService;
import com.bs.wt.util.UpTokenUtil;


@Controller
public class SimpleController {

	Logger log = LoggerFactory.getLogger(SimpleController.class);
	
	@Value("${qiniu.ak}")
	private String qnAk;
	
	@Value("${qiniu.sk}")
	private String qnSk;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TestService testService;
	
	private static final String QINIU_PRE = "http://ojye0ss9l.bkt.clouddn.com/";
	
	private static final String QINIU_END = "?imageView2/1/w/130/h/130";
	
	private static final String QINIU_END_ = "?imageView2/1/w/650/h/440";
	
	private static final String QINIU_END__ = "?imageView2/1/w/200/h/150";
	
	private static final String YMD = "yyyy年MM月dd日";
	private static final String YEAR = "yyyy";
	private static final String MD = "MM.dd";
	private static final String EEEE = "EEEE";
	
	private static final String TITLE = "%s︱盘后票截图 周%s（%s）必涨！";
	private static final String CONTENT = "周%s早盘预计拉升5%%以上，方便于验证实力，附图一份注意：截图并非股票最近截图，截图都是随机截取某年某月某天的截图截图为X年X月X日-X年X月X日截图，明日早盘09点14分公布截图时间盘后票收盘十分钟内出票，网站仅供验证实力 需要...";
	private static final String CODECONTENT = "公布昨日代码与截图时间%s当天需要盘后票请联系QQ：362562938 ";
	private static final String PUSHCONTENT = "%s购买盘后票赠送午盘集结号，网站12点59分提供验证...";
	
	private static final int UP = 1;
	private static final int CODE = 2;
	private static final int SHEN = 3;
	public static final int DEFAULT_PAGE_SIZZE = 10;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String toIndex(Model model){
		testService.sayHello();
		return index(null, model);
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String index(@RequestParam(value = "currentpage",required=false) Integer cp,Model model){
		int indexCount = postService.getIndexCount(null);
		int currentpage = (cp == null?0:cp);
		List<PushForm> list = postService.getIndexPostByPage(currentpage, DEFAULT_PAGE_SIZZE,null);
		for(PushForm obj : list){
			obj.setHash(QINIU_PRE + obj.getHash() + QINIU_END);
			if(obj.getType() == 1){
				obj.setContent(String.format(CONTENT, ymd(EEEE).substring(ymd(EEEE).length()-1, ymd(EEEE).length())));
			}else if(obj.getType() == 2){
				obj.setContent(String.format(CODECONTENT, obj.getContent()));
				obj.setHash(QINIU_PRE + "FjnYscj9wqxX7DeMO_aoPHXv5WaH" + QINIU_END__);
			}else if(obj.getType() == 3){
				obj.setContent(String.format(PUSHCONTENT, obj.getTitle()));
			}
		}
 		model.addAttribute("count", indexCount);
		model.addAttribute("list", list);
		model.addAttribute("currentpage", currentpage);
		return "index";
	}
	
	/**
	 * 明日必涨 列表
	 * @return
	 */
	@RequestMapping("/dayup")
	public String dayup(@RequestParam(value = "currentpage",required=false) Integer cp,Model model){
		int indexCount = postService.getIndexCount(UP);
		int currentpage = (cp == null?0:cp);
		List<PushForm> list = postService.getIndexPostByPage(currentpage, DEFAULT_PAGE_SIZZE,1);
		for(PushForm obj : list){
			obj.setHash(QINIU_PRE + obj.getHash() + QINIU_END);
			obj.setContent(String.format(CONTENT, week(EEEE,obj.getCreatetime()).substring(week(EEEE,obj.getCreatetime()).length()-1, week(EEEE,obj.getCreatetime()).length())));
		}
 		model.addAttribute("count", indexCount);
		model.addAttribute("list", list);
		model.addAttribute("currentpage", currentpage);
		return "dayuplist";
	}
	
	
	@RequestMapping(value="/cjl",method=RequestMethod.GET)
	public String stockBack(PushForm pushForm,Model model){
		log.info("进入控制台");
		pushForm.setTitle(String.format(TITLE, ymd(YMD),week(EEEE).substring(week(EEEE).length()-1, week(EEEE).length()),week(MD)));
		model.addAttribute("token", getToken());
		return "push";
	}
	
	//提交表单后进行数据读取，并将数据传出
    @RequestMapping(value = "/push",method = RequestMethod.POST)
    public String bb(@Valid PushForm pushForm,BindingResult bindingResult,Model model) {
    	model.addAttribute("token", getToken());
        if(bindingResult.hasErrors()){
        }else{
    	 int returnI = postService.savePost(pushForm);
    	 if(returnI > 0){
    		 model.addAttribute("success", "发布成功");
    	 }else{
    		 model.addAttribute("error","发布失败");
    	 }
     }
        pushForm.setHash("");
        return "push";
    }
    
    @RequestMapping(value="/controllist",method=RequestMethod.GET)
    public String controlList(Model model){
    	List<PushForm> list = postService.getTopTenPost();
    	model.addAttribute("list", list);
    	return "list";
    }
    
    @ResponseBody
    @RequestMapping(value="/delete")
    public String delete(int uuid){
    	postService.deletePost(uuid);
    	return "success";
    }
    
    @RequestMapping(value="/pushtype",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String pushType(int type){
    	if(type == 1){
    		return String.format(TITLE, ymd(YMD),week(EEEE).substring(week(EEEE).length()-1, week(EEEE).length()),week(MD));
    	}else if(type == 2){
    		String title = "%s︱盘后票截图时间与代码 开盘预计拉升五个点以上";
    		return String.format(title, ymd(YMD));
    	}else if(type == 3){
    		String title = "%s午盘集结号  股票代码 股票名称 午后开盘预计直线拉升";
    		return String.format(title, ymd(MD));
    	}
    	return "";
    }
    
    @RequestMapping("/readdayup")
    public String readDayUp(int uuid,Model model){
    	PushForm push = postService.dayupById(uuid);
    	push.setDateStr(push.getDateStr());
    	push.setHash(QINIU_PRE + push.getHash() + QINIU_END_);
    	model.addAttribute("object", push);
    	
    	TmpTitle tt = new TmpTitle();
    	String title1 = "周%s早盘预计拉升5%%以上，方便于验证实力，附图一份";
    	tt.setTitle1(String.format(title1, week(EEEE,push.getCreatetime()).substring(week(EEEE,push.getCreatetime()).length()-1, week(EEEE,push.getCreatetime()).length())));//周二早盘预计拉升5%以上，方便于验证实力，附图一份
    	String title2 = "截图并非股票最近截图，截图都是随机截取某年某月某天的截图";
    	tt.setTitle2(title2);
    	String title3 = "截图为X年X月X日-X年X月X日截图，明日早盘09点14分公布截图时间";
    	tt.setTitle3(title3);
    	String title4 = "盘后票收盘十分钟内出票，网站仅供验证实力  需要请联系QQ：";
    	tt.setTitle4(title4);
    	String title5 = "每日一股 长期提供 "+ymd(YEAR)+"最新三点盘后票";
    	tt.setTitle5(title5);
    	model.addAttribute("tmp", tt);
    	
    	return "dayupdetail";
    }
    
    
    @RequestMapping("/codelist")
    public String codeList(@RequestParam(value = "currentpage",required=false) Integer cp,Model model){
    	int indexCount = postService.getIndexCount(CODE);
		int currentpage = (cp == null?0:cp);
		List<PushForm> list = postService.getIndexPostByPage(currentpage, DEFAULT_PAGE_SIZZE,2);
		for(PushForm obj : list){
			obj.setHash(QINIU_PRE + "FjnYscj9wqxX7DeMO_aoPHXv5WaH" + QINIU_END__);
			obj.setContent(String.format(CODECONTENT, obj.getContent()));
		}
 		model.addAttribute("count", indexCount);
		model.addAttribute("list", list);
		model.addAttribute("currentpage", currentpage);
    	return "codelist";
    }
    
    @RequestMapping("/readcode")
    public String readCode(int uuid,Model model){
    	PushForm push = postService.dayupById(uuid);
    	push.setDateStr(push.getDateStr());
    	push.setHash(QINIU_PRE + push.getHash() + QINIU_END_);
    	model.addAttribute("object", push);
    	
    	TmpTitle tt = new TmpTitle();
    	String title1 = "公布昨日代码与截图时间";
    	tt.setTitle1(title1);
    	
    	String title4 = "当天需要盘后票请联系 QQ：";
    	tt.setTitle4(title4);
    	tt.setHash(push.getHash());
    	model.addAttribute("tmp", tt);
    	return "codedetail";
    }
    
    /**
     * 拉升
     * @return
     */
    @RequestMapping("/pullup")
    public String pullup(@RequestParam(value = "currentpage",required=false) Integer cp,Model model){
    	int indexCount = postService.getIndexCount(SHEN);
		int currentpage = (cp == null?0:cp);
		List<PushForm> list = postService.getIndexPostByPage(currentpage, DEFAULT_PAGE_SIZZE,3);
		for(PushForm obj : list){
			obj.setHash(QINIU_PRE + obj.getHash() + QINIU_END);
			obj.setContent(String.format(PUSHCONTENT, obj.getTitle()));
		}
 		model.addAttribute("count", indexCount);
		model.addAttribute("list", list);
		model.addAttribute("currentpage", currentpage);
    	return "pullup";
    }

    @RequestMapping("/readpullup")
    public String readpullup(int uuid,Model model){
    	PushForm push = postService.dayupById(uuid);
    	push.setDateStr(push.getDateStr());
    	push.setHash(QINIU_PRE + push.getHash() + QINIU_END_);
    	model.addAttribute("object", push);
    	TmpTitle tt = new TmpTitle();
    	String title2 = "购买盘后票赠送午盘集结号，网站12点59分提供验证";
    	tt.setTitle2(title2);
    	model.addAttribute("tmp", tt);
    	return "pullupdetail";
    }
    
    @RequestMapping("/layout")
    public String testLayout(){
    	return "layout/content";
    }
    
    
    private String getToken(){
    	String token = UpTokenUtil.genUpToken(qnAk,qnSk);
		log.info("token : {}",token);
		return token;
    }
    
    /**
     * 年月日
     * @return
     */
    private String ymd(String pattern){
    	SimpleDateFormat sdf=new SimpleDateFormat(pattern); 
    	String datestr=sdf.format(new Date()); 
    	return datestr;
    }
    
    /**
     * 因为要推荐明天的票，所以今天基础上加1天
     */
    private String week(String pattern){
    	Calendar ca = Calendar.getInstance();
    	ca.set(Calendar.DATE, ca.get(Calendar.DATE) + 1);
    	SimpleDateFormat sdf=new SimpleDateFormat(pattern); 
    	String datestr=sdf.format(ca.getTime()); 
    	return datestr;
    }
    
    private String week(String pattern,Date date){
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	ca.set(Calendar.DATE, ca.get(Calendar.DATE) + 1);
    	SimpleDateFormat sdf=new SimpleDateFormat(pattern); 
    	String datestr=sdf.format(ca.getTime()); 
    	return datestr;
    }
    public static void main(String[] args) {
		System.out.println("你好nihao");
	}
}
