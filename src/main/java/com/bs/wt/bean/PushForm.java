package com.bs.wt.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Validator主要是校验用户提交的数据的合理性的 具体可以参考：
 * http://blog.csdn.net/linxingliang/article/details/52350246
 * 
 * @author bangsun
 *
 */
public class PushForm {

	private int id;
	private int type;
	@NotEmpty(message = "标题不能为空")
	private String title;

	private String content;
	@NotEmpty(message = "图片不能为空")
	private String hash;

	private Date createtime;

	private String dateStr;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDateStr() {
		if (createtime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			return sdf.format(createtime);
		}
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
