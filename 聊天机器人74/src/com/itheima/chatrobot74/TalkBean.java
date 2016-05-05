package com.itheima.chatrobot74;

/**
 * 提问和回答的对象封装
 * 
 * @author Kevin
 * @date 2015-10-25
 */
public class TalkBean {

	public TalkBean(String content, boolean isAsk, int imageId) {
		super();
		this.content = content;
		this.isAsk = isAsk;
		this.imageId = imageId;
	}

	public String content;

	public boolean isAsk;// 标记是否是提问

	public int imageId;// 回答图片的id
}
