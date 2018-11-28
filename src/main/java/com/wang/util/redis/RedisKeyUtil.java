package com.wang.util.redis;


public class RedisKeyUtil {
	
	public static String getDictKey(String key){
		
		return getKeyByType(key, RedisKeyType.REDIS_KEY_DICT);
	}
	
	public static String getUserBaseKey(Long numberId)
	{
		return getKeyByType(numberId.toString(), RedisKeyType.REDIS_KEY_USERBASE);
	}
	
	/*public static String getZan(Long mainId, ZanType zanType)
	{
		RedisKeyType redisKeyType = null;
		if(ZanType.Dynamic.equals(zanType)){
			redisKeyType = RedisKeyType.REDIS_KEY_ZAN_DYNAMIC;
		}
		else if(ZanType.Forum.equals(zanType)){
			redisKeyType = RedisKeyType.REDIS_KEY_ZAN_FORUM;
		}
		else if(ZanType.ForumComment.equals(zanType) || ZanType.VoteComment.equals(zanType)){
			redisKeyType = RedisKeyType.REDIS_KEY_ZAN_COMMENT;
		}
		else{
			return null;
		}
		return getKeyByType(mainId.toString(), redisKeyType);
	}*/

	/**
	 * 获取评论redisKey
	 * @param mainId  动态或帖子id
	 * @param twitterCommentType  动态评论或者帖子评论
	 * @return
	 */
	/*public static String getCommentKey(Long mainId, TwitterCommentType twitterCommentType)
	{
		RedisKeyType redisKeyType = null;
		if(TwitterCommentType.Dynamic.equals(twitterCommentType)){
			redisKeyType = RedisKeyType.REDIS_KEY_COMMENT_DYNAMIC;
		}
		else if(TwitterCommentType.Forum.equals(twitterCommentType)){
			redisKeyType = RedisKeyType.REDIS_KEY_COMMENT_FORUM;
		}

		else{
			return null;
		}
		return getKeyByType(mainId.toString(), redisKeyType);
	}*/
	/**
	 * 获取评论点赞排序redisKey
	 *  mainId  动态或帖子id
	 *  twitterCommentType  动态评论或者帖子评论
	 *
	 */
	/*public static String getCommentZanCountKey(Long mainId, TwitterCommentType twitterCommentType)
	{
		RedisKeyType redisKeyType = null;
		if(TwitterCommentType.Dynamic.equals(twitterCommentType)){
			redisKeyType = RedisKeyType.REDIS_KEY_COMMENT_DYNAMIC_ONELEVEL_COUNTZAN;
		}
		else if(TwitterCommentType.Forum.equals(twitterCommentType)){
			redisKeyType = RedisKeyType.REDIS_KEY_COMMENT_FORUM_ONELEVEL_COUNTZAN;
		}

		else{
			return null;
		}
		return getKeyByType(mainId.toString(), redisKeyType);
	}*/

	public static String getCommentDetailKey()
	{
		return RedisKeyType.REDIS_KEY_COMMENT_DETAIL.getText();
	}

	public static String getActivityNewyear() {
		return getKeyByType("2018", RedisKeyType.REDIS_KEY_ACTIVITY_NEWYEAR);
	}

	public static String getLikeWeiduKey(Long numberId) {
		return getKeyByType(numberId.toString(), RedisKeyType.REDIS_KEY_LIKE);
	}

	public static String getFansWeiduKey(Long numberId) {
		return getKeyByType(numberId.toString(), RedisKeyType.REDIS_KEY_FANS);
	}

	public static String getFansInfoKey(Long sendId, Long receiveId) {
		return getKeyByType(RedisKeyType.REDIS_KEY_INFO_FANS, sendId.toString(), receiveId.toString());
	}

	public static String getGroupKey(String groupNo) {
		return getKeyByType(groupNo, RedisKeyType.REDIS_KEY_GROUP);
	}

	public static String getGroupInfoKey(String groupNo) {
		return getKeyByType(groupNo, RedisKeyType.REDIS_KEY_GROUPINFO);
	}

	public static String getGroupUserKey(String groupNo, Long numberId) {
		return getKeyByType(RedisKeyType.REDIS_KEY_GROUPUSER_INFO, groupNo, numberId.toString());
	}

	public static String getUserBlockKey(Long userNo) {
		return getKeyByType(userNo.toString(), RedisKeyType.REDIS_KEY_JURIS_BLOCK);
	}

	public static String getDynamicOperationKey() {
		return RedisKeyType.REDIS_KEY_DYNAMICOPERATION.getText();
	}

	public static String getRegisterCountKey(String ip) {
		return getKeyByType(ip, RedisKeyType.REDIS_KEY_REGISTERCOUNT_IP);
	}

	public static String getPlayKeyWordKey(String id){
		return getKeyByType(id, RedisKeyType.REDIS_KEY_PLAY_KEYWORD);
	}

	protected static String getKeyByType(String key, RedisKeyType keyType){
		
		return String.format(keyType.getText(), key);
	}
	protected static String getKeyByType(RedisKeyType keyType, String ...key){

		return String.format(keyType.getText(), key);
	}


	public static String getLeaguerSpreadActivKey(Long spreadUserId)
	{
		return getKeyByType(spreadUserId.toString(), RedisKeyType.REDIS_SPREAD_LEAGUER_ACTIV);
		//return RedisKeyType.REDIS_SPREAD_LEAGUER_ACTIV.getText();
	}

	public static String getSpreadMsgKey(String spreadStr)
	{
		return getKeyByType(spreadStr, RedisKeyType.REDIS_SPREAD_MSG);
	}

	public static String getVaildSpreadMsgKey(String spreadStr)
	{
		return getKeyByType(spreadStr, RedisKeyType.REDIS_SPREAD_VAILD_MSG);
	}

	/**
	 * 获取操作记录
	 * @param yyyyMM
	 * @param type 维度类型 0 numberId 1 bustype 1 ip 3 设备id 4 app版本  5 bustype&scoreUser
	 * @param typeContent 类型内容
	 * @return
	 */
	public static String getOperateRecordKey(String yyyyMM,int type,String typeContent)
	{

		if(type==0){return getKeyByType(yyyyMM, RedisKeyType.OPREATE_RECORD);}
		else if(type==1){return getKeyByType(RedisKeyType.OPREATE_RECORD_BUSTYPE,yyyyMM,typeContent);}
		else if(type==2){return getKeyByType(RedisKeyType.OPREATE_RECORD_IP,yyyyMM,typeContent);}
		else if(type==3){return getKeyByType(RedisKeyType.OPREATE_RECORD_DEVICEID,yyyyMM,typeContent);}
		else if(type==4){return getKeyByType(RedisKeyType.OPREATE_RECORD_APPVERSION,yyyyMM,typeContent);}
		else if(type==5){return getKeyByType(RedisKeyType.OPREATE_RECORD_BUSTYPE_SCOREUSER,yyyyMM,typeContent);}
		return null;
	}

	/**
	 * 日活用户记录
	 * @param yyyyMMdd
	 * @param type 0 all 1 andriod 2 ios 3 pc
	 * @return
	 */
	public static String getDayAliveUserKey(String yyyyMMdd,int type)
	{
		if(type==0){return getKeyByType(yyyyMMdd, RedisKeyType.DAY_ALIVE_USER);}
		else if(type==1){return getKeyByType(yyyyMMdd, RedisKeyType.DAY_ALIVE_USER_ANDRIOD);}
		else if(type==2){return getKeyByType(yyyyMMdd, RedisKeyType.DAY_ALIVE_USER_IOS);}
		else if(type==3){return getKeyByType(yyyyMMdd, RedisKeyType.DAY_ALIVE_USER_PC);}
		return null;
	}
	/**
	 * 月活用户记录
	 * @param yyyyMM
	 * @param type 0 all 1 andriod 2 ios 3 pc
	 * @return
	 */
	public static String getMonthAliveUserKey(String yyyyMM,int type)
	{
		if(type==0){return getKeyByType(yyyyMM, RedisKeyType.MONTH_ALIVE_USER);}
		else if(type==1){return getKeyByType(yyyyMM, RedisKeyType.MONTH_ALIVE_USER_ANDRIOD);}
		else if(type==2){return getKeyByType(yyyyMM, RedisKeyType.MONTH_ALIVE_USER_IOS);}
		else if(type==3){return getKeyByType(yyyyMM, RedisKeyType.MONTH_ALIVE_USER_PC);}
		return null;
	}

	public static String getBlackIPKey()
	{
		return getKeyByType( RedisKeyType.BLACK_IP);
	}
	public static String getBlackDeviceIdKey()
	{
		return getKeyByType( RedisKeyType.BLACK_DEVICEID);
	}


	public static String getCircularWordRecordKey()
	{
		return RedisKeyType.REDIS_KEY_CIRCULAR_KEYWORD.getText();
	}

	public static String getDeviceUserKey() {
		return RedisKeyType.REDIS_KEY_DEVICE_USER.getText();
	}

	public static String getDynamicDayKey() {
		return RedisKeyType.REDIS_KEY_DDYNAMIC_DYA.getText();
	}

	public static String getSpreadSuccess(Long spreadId) {
		return getKeyByType(RedisKeyType.REDIS_KEY_SPREAD, spreadId.toString());
	}


	public static String getLoginKey() {
		return RedisKeyType.USER_LOGIN_KEY.getText();
	}

	/**
	 * 票选信息
	 * @param numberId
	 * @return
	 */
	public static String getVoteKey(Long numberId)
	{
		return getKeyByType(numberId.toString(), RedisKeyType.VOTE_KEY);
	}

	/**
	 * 招募商信息
	 * @return
	 */
	public static String getRecruitKey() {
		return RedisKeyType.NOTICE_RECRUIT.getText();
	}

	/**
	 * 会员招募商信息
	 * @return
	 */
	public static String getUserrecruitKey() {
		return RedisKeyType.USER_NOTICE_RECRUIT.getText();
	}

}
