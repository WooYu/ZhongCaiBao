package com.developer.lecai.control;

import android.text.TextUtils;

import com.developer.lecai.http.H;
import com.developer.lecai.http.HttpCallback;
import com.developer.lecai.http.HttpRequest;
import com.developer.lecai.http.HttpRequestRegister;

/**
 * Created by liuwei on 2017/7/17.
 */

public class MsgController {
    private static MsgController instance;

    private MsgController() {

    }

    public static MsgController getInstance() {
        if (instance == null) {
            synchronized (MsgController.class) {
                if (instance == null) {
                    instance = new MsgController();
                }
            }
        }

        return instance;
    }

    /**
     * 获取公告
     *
     * @param callback
     */
    public void getGG(int page, HttpCallback callback) {
        getNotice(0, page, callback);
    }

    /**
     * 系统强制弹出消息
     */
    public void getSysNotice(int page, HttpCallback callback) {
        getNotice(1, page, callback);
    }

    /**
     * 获取轮播图
     *
     * @param callback
     */
    public void getLBT(int page, HttpCallback callback) {
        getNotice(2, page, callback);
    }

    /**
     * 获取系统消息 0:公告 1:系统 2:轮播图
     */
    public void getNotice(int type, int page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page + "")
                .addParam(H.Param.type, type + "")
                .build()
                .post(H.URL.Notice, callback);
    }

    /**
     * 获取公告里的消息
     */
    public void getMessage(int page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page + "")
                .build()
                .post(H.URL.MESSAGE, callback);
    }

    /**
     * 获取我的消息
     */
  /*  public void getMyMsg() {
        new HttpRequest.Builder()
                .addParam(H.Param.page, 0 + "")
                .addParam(H.Param.type, 2 + "")
                .build()
                .post(H.URL.Notice, new HttpCallback() {
                    @Override
                    public void onSuccess(Call call, String s) {
                        // 成功处理
                        String state = JsonUtil.getStringValue(s, "state");
                        String biz_content = JsonUtil.getStringValue(s, "biz_content");
                        if (state.equals("success")) {
                            Toast.makeText(XyApplication.appContext, biz_content, Toast.LENGTH_LONG).show();
                        } else if (state.equals("error")) {
                            Toast.makeText(XyApplication.appContext, biz_content, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
*/

    //首页强制弹出
    public void getHomeTan(int type, int page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page + "")
                .addParam(H.Param.type, type + "")
                .build()
                .post(H.URL.HOMETAN, callback);
    }

    //修改提现密码
    public void getTiXianPsw(String password, String oldpwd, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.password, password)
                .addParam(H.Param.oldpwd, oldpwd)
                .build()
                .post(H.URL.DRAWPWD, callback);
    }

    //系统返回银行列表
    public void getBannerList(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.BANNERLIST, callback);
    }

    //用户信息修改
    public void getUpduserinfo(String username, String qq, String mobile, String realname, String idcard, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.username, username)
                .addParam(H.Param.qq, qq)
                .addParam(H.Param.mobile, mobile)
                .addParam(H.Param.realname, realname)
                .addParam(H.Param.idcard, idcard)
                .build()
                .post(H.URL.UPDUSERINFO, callback);
    }

    //用户信息修改
    public void getHomeData(HttpCallback callback) {
        new HttpRequest().postNoParams(H.URL.HOMEDATA, null, callback);
    }

    //开奖记录
    public void getOpenResultList(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.OPENRESULTLIST, callback);
    }

    //按彩种获取开奖记录
    public void getOpenListByCode(String page, String cpCode, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page)
                .addParam(H.Param.cpcode, cpCode)
                .build()
                .post(H.URL.OPENLISTBYCODE, callback);
    }

    //获取聊天室id
    public void getChatRoomId(String type, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.type, type)
                .build()
                .post(H.URL.CHATROOM, callback);
    }

    //聊天室在线人数
    public void getRoomonline(String type, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.type, type)
                .build()
                .post(H.URL.ROOMONLINE, callback);
    }

    //房间信息
    public void getRoomInfo(String type, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.type, type)
                .build()
                .post(H.URL.ROOM, callback);
    }

    //获取余额
    public void getMoney(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.GETMONEY, callback);
    }

    //彩种列表
    public void getLotteryList(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.LOTTERYLIST, callback);
    }

    //彩票玩法
    public void getLotteryPlays(String cpCode, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.cpcode, cpCode)
                .build()
                .post(H.URL.LOTTERYPLAYS, callback);
    }

    //彩票玩法28
    public void getLotteryPlays28(String cpCode, String roomtype, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.cpcode, cpCode)
                .addParam(H.Param.roomtype, roomtype)
                .build()
                .post(H.URL.LOTTERYPLAYS28, callback);
    }

    //分分彩里面的期数和时间
    public void getLastlottery(String caiType, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.type, caiType)
                .build()
                .post(H.URL.LASTLOTTERY, callback);
    }

    //页面—合买
    public void getHmbettdetail(String orderby, String page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.orderby, orderby)
                .addParam(H.Param.page, page)
                .build()
                .post(H.URL.HMBETTRECORD, callback);
    }

    //页面—合买详情
    public void getHemaidetail(String orderId, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.lcode, orderId)
                .build()
                .post(H.URL.HMBETTDETAIL, callback);
    }

    //系统返点模式
    public void getBackModelList(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.BACKMODELLIST, callback);
    }

    //投注
    public void getTouZhu(String totalfee, String isstart, String list, String hmclasss, String zhclasss, HttpCallback callback) {
        HttpRequest.Builder builder = new HttpRequest.Builder();
        builder.addParam(H.Param.totalfee, totalfee);
        builder.addParam(H.Param.list, list);
        if (!"".equals(hmclasss)) {
            builder.addParam(H.Param.isstart, isstart);
            builder.addParam(H.Param.hmclasss, hmclasss);
        }
        if (!"".equals(zhclasss)) {
            builder.addParam(H.Param.zhclasss, zhclasss);
        }
        builder.build().post(H.URL.BETT, callback);
    }

    //提交回执单接口
    public void getHuiZhi(String playtype, String othercode, String truename, String cardcode, String money, String bankname, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.playtype, playtype)
                .addParam(H.Param.othercode, othercode)
                .addParam(H.Param.truename, truename)
                .addParam(H.Param.cardcode, cardcode)
                .addParam(H.Param.money, money)
                .addParam(H.Param.bankname, bankname)
                .build()
                .post(H.URL.ADDREMIT, callback);
    }

    //获取当前账户绑定的银行卡
    public void getBankCard(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.MYBANKS, callback);
    }

    //添加银行卡
    public void getAddBankCard(String username, String cardname, String cardnum, String cardaddress, String bankcode, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.username, username)
                .addParam(H.Param.cardname, cardname)
                .addParam(H.Param.cardnum, cardnum)
                .addParam(H.Param.cardaddress, cardaddress)
                .addParam(H.Param.bankcode, bankcode)
                .build()
                .post(H.URL.BINDBANK, callback);
    }

    //提现提款
    public void getTiKuan(String password, String money, String bankid, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.password, password)
                .addParam(H.Param.money, money)
                .addParam(H.Param.bankid, bankid)
                .build()
                .post(H.URL.USERDRAW, callback);
    }

    //积分兑换
    public void getJiFen(String intefee, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.intefee, intefee)
                .build()
                .post(H.URL.INTEFEEEXCHANGE, callback);
    }

    //优惠大厅
    public void getYouHui(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.ACTIVELIST, callback);
    }

    //优惠大厅
    public void getYouHuiLinQu(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.GETSENDFEE, callback);
    }

    //我的回水
    public void getHuiShui(String page, String roomname, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page)
                .addParam(H.Param.roomname, roomname)
                .build()
                .post(H.URL.USERBACK, callback);
    }

    public void getRegister(String account, String password, String invitecode, String signature, HttpCallback callback) {
        if (TextUtils.isEmpty(invitecode)) {
            new HttpRequestRegister.Builder()
                    .addParam(H.Param.account, account)
                    .addParam(H.Param.password, password)
                    .addParam(H.Param.signature, signature)
                    .build()
                    .post(H.URL.Regist, callback);

        } else {
            new HttpRequestRegister.Builder()
                    .addParam(H.Param.account, account)
                    .addParam(H.Param.password, password)
                    .addParam(H.Param.invitecode, invitecode)
                    .addParam(H.Param.signature, signature)
                    .build()
                    .post(H.URL.Regist, callback);
        }
    }

    //vip分享
    public void getVipShare(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.SHARERULELIST, callback);
    }

    //我的收益
    public void getShouYi(String page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page)
                .build()
                .post(H.URL.SHAREACCOUNT, callback);
    }

    public void getTouZhu(HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.type, "")
                .addParam(H.Param.page, "1")
                .build()
                .post(H.URL.BETTRECORD, callback);
    }

    //首页中奖排行
    public void getWinnerRecord(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.WIINERRECORD, callback);
    }

    //我的收益分析
    public void getShouYiFenXi(String page, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.page, page)
                .build()
                .post(H.URL.AGENTSYFX, callback);
    }

    //代理后台首页信息
    public void getDaiLiHouTai(HttpCallback callback) {
        new HttpRequest.Builder()
                .build()
                .post(H.URL.AGENTHOME, callback);
    }

    //用户信息
    public void getUserInfo(String page, String years, String mouth, HttpCallback callback) {
        if (TextUtils.isEmpty(mouth)) {
            new HttpRequest.Builder()
                    .addParam(H.Param.page, page)
                    .addParam(H.Param.years, years)
                    .build()
                    .post(H.URL.AGENTXJUSER, callback);
        } else {
            new HttpRequest.Builder()
                    .addParam(H.Param.page, page)
                    .addParam(H.Param.years, years)
                    .addParam(H.Param.mouth, mouth)
                    .build()
                    .post(H.URL.AGENTXJUSER, callback);
        }
    }

    //获取投注详情
    public void getBettingDetails(String lcode, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.lcode, lcode)
                .build()
                .post(H.URL.BETTINGDETAIL, callback);
    }

    //取消投注
    public void getCancelTheBetting(String lcode, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.lcode, lcode)
                .build()
                .post(H.URL.CANCELBETTING, callback);
    }

    //修改登录密码password oldpwd
    public void getUpdateLoginPWD(String password, String oldpwd, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.password, password)
                .addParam(H.Param.oldpwd, oldpwd)
                .build()
                .post(H.URL.LOGINPWD, callback);
    }

    //获取充值通道
    public void getRechargeChannel(HttpCallback callback){
        new HttpRequest.Builder()
                .build()
                .post(H.URL.PAYINFO,callback);
    }
    //参与合买
    public void getParticipateInChipped(String lcode, String num, HttpCallback callback) {
        new HttpRequest.Builder()
                .addParam(H.Param.lcode, lcode)
                .addParam(H.Param.num, num)
                .build()
                .post(H.URL.PARTAKEHM, callback);
    }
}
