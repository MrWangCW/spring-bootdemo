package com.wang.rptimpl.wechatrpt;

import com.wang.domain.wechat.rpt.WeChatRpt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by wangyanwei on 2018/11/21.
 *
 * @author wangyanwei
 * @version 1.0
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class WeChatRptImpl implements WeChatRpt{



}
