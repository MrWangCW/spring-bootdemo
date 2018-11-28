package com.wang.util.redis;

import redis.clients.util.JedisClusterCRC16;

public enum RedisKeyType {
	/**
	 *同一类型数据，使用{}放在同一slot槽里面 
	 */
	
	REDIS_KEY_DICT("{GYZ_DICT_}%s"),
	REDIS_KEY_USERBASE("{GYZ_USERBASE_}%s"),
	REDIS_KEY_ZAN_DYNAMIC("{GYZ_ZAN_}DYNAMIC_%s"),
	REDIS_KEY_ZAN_FORUM("{GYZ_ZAN_}FORUM_%s"),
	REDIS_KEY_ZAN_COMMENT("{GYZ_ZAN_}COMMENT_%s"),

	REDIS_KEY_ACTIVITY_NEWYEAR("{GYZ_ACTIVITY_}NEWYEAR_%s"),

	//关注 2种纬度
	REDIS_KEY_INFO_FANS("{GYZ_FANS_}INFO_LIKE_%s_FANS_%s"),
	REDIS_KEY_LIKE("{GYZ_FANS_}WEIDU_LIKE_%s"),
	REDIS_KEY_FANS("{GYZ_FANS_}WEIDU_FANS_%s"),
	/**
	 * 群组用户维度
	 */
	REDIS_KEY_GROUP("{GYZ_GROUP_}%s"),
	/**
	 * 群组信息
	 */
	REDIS_KEY_GROUPINFO("{GYZ_GROUPINFO_}%s"),
	REDIS_KEY_GROUPUSER_INFO("{GYZ_GROUP_}%s_USER_%s"),

	REDIS_KEY_JURIS_BLOCK("{GYZ_JURIS}BLOCK_%s"),


	REDIS_KEY_DYNAMICOPERATION("{GYZ_DYNAMIC_OPERATION}"),


	/**
	 * 所有评论相关，分5个维度
	 *
	 *
	 */
	//动态评论 key：动态id score ： 一级评论 创建时间  二级评论：父评论id
	REDIS_KEY_COMMENT_DYNAMIC("{GYZ_COMMENT_DYNAMIC}_%s"),
	//帖子评论 key：帖子id score ： 一级评论 创建时间  二级评论：父评论id
	REDIS_KEY_COMMENT_FORUM("{GYZ_COMMENT_FORUM}_%s"),


	//评论基本信息   score ： 评论id  member：评论基本信息
	REDIS_KEY_COMMENT_DETAIL("{GYZ_COMMENT}_DETAIL"),

	//1级评论相关(点赞数排序用)  key 动态id   score ： 点赞数  member：评论id
	REDIS_KEY_COMMENT_DYNAMIC_ONELEVEL_COUNTZAN("{GYZ_COMMENT_DYNAMIC}_ONELEVEL_COUNTZAN_%s"),
	REDIS_KEY_COMMENT_FORUM_ONELEVEL_COUNTZAN("{GYZ_COMMENT_FORUM}_ONELEVEL_COUNTZAN_%s"),


	//登录ip次数
	REDIS_KEY_REGISTERCOUNT_IP("{GYZ_REGISTER_COUNT_}%s"),

	REDIS_KEY_PLAY_KEYWORD("{GYZ_PLAY_KEYWORD}_%s"),

	//通告、人才搜索关键词
	REDIS_KEY_CIRCULAR_KEYWORD("{GYZ_CIRCULAR_KEYWORD_}%s"),


	//推广会员数（推广活动用）  key 推广人numberId   score ：推广成功时间   member：被推广的会员numberId
	REDIS_SPREAD_LEAGUER_ACTIV("{REDIS_SPREAD_LEAGUER_ACTIV}_%s"),

	//用户推广数（用户推广发送消息用）
	REDIS_SPREAD_MSG("{REDIS_SPREAD_MSG}_%s"),

	//用户有效推广数（用户有效推广发送消息用）
	REDIS_SPREAD_VAILD_MSG("{REDIS_SPREAD_VAILD_MSG}_%s"),

	//用户操作记录  key yyyyMM(201803)   score ：用户id   member：操作内容
	OPREATE_RECORD("{OPREATE_RECORD}_%s"),
		//接口维度
	OPREATE_RECORD_BUSTYPE("{OPREATE_RECORD_BUSTYPE}_%s_%s"),
	//ip，设备，app版本
	OPREATE_RECORD_IP("{OPREATE_RECORD_IP}_%s_%s"),
	OPREATE_RECORD_DEVICEID("{OPREATE_RECORD_DEVICEID}_%s_%s"),
	OPREATE_RECORD_APPVERSION("{OPREATE_RECORD_APPVERSION}_%s_%s"),
	//接口维度 score 分数为userId
	OPREATE_RECORD_BUSTYPE_SCOREUSER("{OPREATE_RECORD_BUSTYPE_SCOREUSER}_%s_%s"),

	//日活点击记录  key yyyyMMdd(20180301)   score ：请求数  member：用户id
	DAY_ALIVE_USER("{DAY_ALIVE_USER}_%s"),
	DAY_ALIVE_USER_ANDRIOD("{DAY_ALIVE_USER_ANDRIOD}_%s"),
	DAY_ALIVE_USER_IOS("{DAY_ALIVE_USER_IOS}_%s"),
	DAY_ALIVE_USER_PC("{DAY_ALIVE_USER_PC}_%s"),
	//月活点击记录  key yyyyMM(201803)   score ：请求数   member：用户id
	MONTH_ALIVE_USER("{MONTH_ALIVE_USER}_%s"),
	MONTH_ALIVE_USER_ANDRIOD("{MONTH_ALIVE_USER_ANDRIOD}_%s"),
	MONTH_ALIVE_USER_IOS("{MONTH_ALIVE_USER_IOS}_%s"),
	MONTH_ALIVE_USER_PC("{MONTH_ALIVE_USER_PC}_%s"),


	BLACK_IP("{BLACK_IP}"),//IP黑名单
	BLACK_DEVICEID("{BLACK_DEVICEID}"),//设备id黑名单

	/**
	 * 推广人数相关维度
	 * 动态维度--记录用户每天的动态  有序集合（score: 用户号   member: 用户号+日期）
	 * 设备维度-记录设备信息和用户的对应关系 map结构（key：设备号 value：用户号）
	 * 推广人维度-记录有效的被推广人信息 有序集合（score：时间  member：用户号）
	 */
	REDIS_KEY_DDYNAMIC_DYA("{GYZ_DYNAMIC_DAY}"),
	REDIS_KEY_DEVICE_USER("{GYZ_DEVICE_USER}"),
	REDIS_KEY_SPREAD("{GYZ_SPREAD_SUCCESS}_%s"),

	/*微信access_token*/
	WECHAT_ACCESS_TOKEN("{WECHAT_ACCESS_TOKEN}_%s"),

	/*微信jsapi_ticket*/
	WECHAT_JSAPI_TICKET("{WECHAT_JSAPI_TICKET}_%s"),


	/**
	 * 登陆key，map类型  field 设备id  value 登陆key
	 */
	USER_LOGIN_KEY("USER_LOGIN_KEY"),

	/**
	 * 票选信息
	 */
	VOTE_KEY("{VOTE_KEY_}%s"),
	/**
	 * 招募商信息    set结构    json数据
	 */
	NOTICE_RECRUIT("GYZ_NOTICE_RECRUIT"),
	/**
	 * 用户的招募商信息   map结构   用户id  json数据
	 */
	USER_NOTICE_RECRUIT("GYZ_USER_RECRUIT"),
	;

	private final String text;

    private RedisKeyType(String text)
    {
        this.text=text;
    }
    public String getText() {
        return text;
    }
    
    public static void main(String[] args) {
//		String s = String.format(RedisKeyType.REDIS_KEY_DICT.getText(), "zzzz");
//
//		System.out.println(s);

		String dictkey = RedisKeyUtil.getDictKey("dynamicNew");
		int dictslot = JedisClusterCRC16.getSlot(dictkey);
		System.out.println("key:  " + dictkey + "  ,slot:  " + dictslot);

		String userkey = RedisKeyUtil.getUserBaseKey(123456L);
		int userslot = JedisClusterCRC16.getSlot(userkey);
		System.out.println("key:  " + userkey + "  ,slot:  " + userslot);

		String activity = RedisKeyUtil.getActivityNewyear();
		int activityslot = JedisClusterCRC16.getSlot(activity);
		System.out.println("key:  " + activity + "  ,slot:  " + activityslot);

		String block = REDIS_KEY_JURIS_BLOCK.getText();
		int blockslot = JedisClusterCRC16.getSlot(block);
		System.out.println("key:  " + block + "  ,slot:  " + blockslot);

		String like = REDIS_KEY_LIKE.getText();
		int likeslot = JedisClusterCRC16.getSlot(like);
		System.out.println("key:  " + like + "  ,slot:  " + likeslot);

		String fans = REDIS_KEY_FANS.getText();
		int fansslot = JedisClusterCRC16.getSlot(fans);
		System.out.println("key:  " + fans + "  ,slot:  " + fansslot);

		String fansinfo = REDIS_KEY_INFO_FANS.getText();
		int fansinfoslot = JedisClusterCRC16.getSlot(fansinfo);
		System.out.println("key:  " + fansinfo + "  ,slot:  " + fansinfoslot);

		String group = "{GYZ_GROUPS_}";
		int groupslot = JedisClusterCRC16.getSlot(group);
		System.out.println("key:  " + group + "  ,slot:  " + groupslot);
	}
}
