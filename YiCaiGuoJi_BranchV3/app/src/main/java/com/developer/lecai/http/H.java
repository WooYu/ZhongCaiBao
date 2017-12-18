package com.developer.lecai.http;

/**
 * Http请求配置，Url，参数等
 * Created by liuwei on 2017/5/10.
 */
public final class H {

    public static final String DOMAINName = "http://qt.ryk88.com";
    public static String HOST = DOMAINName + "/Api";

    /**
     * 通用参数
     */
    public static final class Param {
        public static final String account = "account";
        public static final String imei = "imei";
        public static final String signature = "signature";
        public static final String page = "page";
        public static final String type = "type";
        public static final String code = "code";
        public static final String password = "password";

        public static final String oldpwd = "oldpwd";
        public static final String username = "username";
        public static final String qq = "qq";
        public static final String mobile = "mobile";
        public static final String realname = "realname";
        public static final String idcard = "idcard";
        public static final String cpcode = "cpcode";
        public static final String orderby = "orderby";
        public static final String lcode = "lcode";
        public static final String totalfee = "totalfee";
        public static final String isstart = "isstart";
        public static final String list = "list";
        public static final String hmclasss = "hmclasss";
        public static final String zhclasss = "zhclasss";
        public static final String playtype = "playtype";
        public static final String othercode = "othercode";
        public static final String truename = "truename";
        public static final String cardcode = "cardcode";
        public static final String money = "money";
        public static final String bankname = "bankname";
        public static final String cardname = "cardname";
        public static final String cardnum = "cardnum";
        public static final String cardaddress = "cardaddress";
        public static final String bankcode = "bankcode";
        public static final String bankid = "bankid";
        public static final String intefee = "intefee";
        public static final String roomname = "roomname";
        public static final String invitecode = "invitecode";
        public static final String roomtype = "roomtype";
        public static final String years = "years";
        public static final String mouth = "mouth";
        public static final String openid = "openid";
        public static final String num = "num";
    }

    public static final class URL {
        public static final String Login = HOST + "/appaccount/login";
        public static final String Regist = HOST + "/appaccount/register";
        //发送信息
        public static final String CODEURL = HOST + "/appaccount/sendsms";
        //公告里的公告，轮播图
        public static final String Notice = HOST + "/app/notice";
        //首页强制弹出
        public static final String HOMETAN = HOST + "/app/systemnotice";
        //公告里的消息
        public static final String MESSAGE = HOST + "/app/message";
        //提现密码
        public static final String DRAWPWD = HOST + "/appaccount/drawpwd";
        //银行列表
        public static final String BANNERLIST = HOST + "/app/banks";
        //用户基本信息修改
        public static final String UPDUSERINFO = HOST + "/appaccount/upduserinfo";
        //首页数据
        public static final String HOMEDATA = HOST + "/appaccount/homedata";
        //开奖记录
        public static final String OPENRESULTLIST = HOST + "/APP/OpenResultList";
        //开奖详细记录
        public static final String OPENLISTBYCODE = HOST + "/APP/OpenListByCode";
        //聊天室id
        public static final String CHATROOM = HOST + "/lotterylk/getchatroom";
        //聊天室在线人数
        public static final String ROOMONLINE = HOST + "/lotterylk/roomonline";
        //房间信息
        public static final String ROOM = HOST + "/lotterylk/room";
        //获取余额
        public static final String GETMONEY = HOST + "/app/getmoney";

        //赔率说明
        public static final String LOTTERYPLAYS28 = HOST + "/app/lotteryplays28";
        //竞猜记录
        public static final String LASTLOTTERY = HOST + "/lotterylk/lastlottery";
        //彩种列表
        public static final String LOTTERYLIST = HOST + "/APP/LotteryList";
        //投注记录
        public static final String BETTRECORD = HOST + "/lotterylk/bettrecord";
        //彩票玩法
        public static final String LOTTERYPLAYS = HOST + "/App/LotteryPlays";
        //页面-合买
        public static final String HMBETTRECORD = HOST + "/lotterylk/hmbettrecord";
        //页面-合买详情
        public static final String HMBETTDETAIL = HOST + "/lotterylk/hmbettdetail";

        //登录密码
        public static final String LOGINPWD = HOST + "/appaccount/loginpwd";

        //系统返点模式
        public static final String BACKMODELLIST = HOST + "/App/BackModelList";
        //投注
        public static final String BETT = HOST + "/lotterylk/bett";
        //线下充值
        public static final String ADDREMIT = HOST + "/app/addremit";
        //我的银行卡
        public static final String MYBANKS = HOST + "/appaccount/mybanks";
        //绑定银行卡
        public static final String BINDBANK = HOST + "/appaccount/bindbank";
        //提现提款
        public static final String USERDRAW = HOST + "/app/userdraw";
        //积分兑换
        public static final String INTEFEEEXCHANGE = HOST + "/APPAccount/InteFeeExchange";
        //优惠大厅展示数据
        public static final String ACTIVELIST = HOST + "/APPAccount/activelist";
        //优惠大厅领取礼金
        public static final String GETSENDFEE = HOST + "/Appaccount/GetSendFee";
        //我的回水
        public static final String USERBACK = HOST + "/app/userback";
        //vip分享
        public static final String SHARERULELIST = HOST + "/APPAccount/sharerulelist";
        //我的收益
        public static final String SHAREACCOUNT = HOST + "/APP/agentsyfx";
        //首页中奖排行
        public static final String WIINERRECORD = HOST + "/APP/WinRecord";
        //收益分析
        public static final String AGENTSYFX = HOST + "/APP/agentsyfx";
        //代理后台首页信息
        public static final String AGENTHOME = HOST + "/APP/agenthome";
        //用户信息
        public static final String AGENTXJUSER = HOST + "/APP/agentxjuser";
        //投注详情
        public static final String BETTINGDETAIL = HOST + "/lotterylk/bettdetail";
        //撤单
        public static final String CANCELBETTING = HOST + "/lotterylk/cancelbett";
        //获取支付二维码
        //http://shzlhw.top/Home/GoOnlinePay?type=%@&money=%@&account=%@&imei=%@&signature=%@
        public static final String ONLINEQRCODEURL = DOMAINName + "/Home/GoOnlinePay?";

        //获取充值通道
        public static final String PAYINFO = HOST + "/app/payinfo";

        //参与合买
        public static final String PARTAKEHM = HOST + "/lotterylk/partakehm";
    }


    /**
     * 返回code,10000以下保留，作为通用状态码
     * xxxyy：xxx表示接口，yy表示该接口的状态码
     */
    public static final class Code {
        public static final int SUCCESS = 200; // 成功
        public static final int C301 = 301; // 重定向
        public static final int C10001 = 10001; //
        public static final int BankCard = 199; //
        public static final int BankCardResult = 299; //
        public static final int TiXianBankCard = 399; //
        public static final int TiXianBankCardResult = 499; //

    }


}
