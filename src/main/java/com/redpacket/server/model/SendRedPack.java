package com.redpacket.server.model;

/**
 * Created by 桑博 on 2016/12/19.
 */
public class SendRedPack {

	/**
	 * 随机字符串，不长于32位
	 */
    private String nonce_str;
    
    /**
     * 签名
     * 详见签名生成算法
     * @see https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3
     */
    private String sign;
    
    /**
     * 商户订单号
     * 商户订单号（每个订单号必须唯一。取值范围：0~9，a~z，A~Z）
     * 接口根据商户订单号支持重入，如出现超时可再调用。
     */
    private String mch_billno;
    
    /**
     * 商户号
     * 微信支付分配的商户号
     */
    private String mch_id;
    
    /**
     * 公众账号appid
     * 微信分配的公众账号ID（企业号corpid即为此appId）。
     * 接口传入的所有appid应该为公众号的appid（在mp.weixin.qq.com申请的），不能为APP的appid（在open.weixin.qq.com申请的）。
     */
    private String wxappid;
    
    /**
     * 商户名称
     * 红包发送者名称
     */
    private String send_name;
    
    /**
     * 用户openid
     * 接受红包的用户
     * 用户在wxappid下的openid
     */
    private String re_openid;
    
    /**
     * 付款金额
     * 单位分
     */
    private Integer total_amount;
    
    /**
     * 红包发放总人数
     * total_num=1
     */
    private Integer total_num;
    
    /**
     * 红包祝福语
     */
    private String wishing;
    
    /**
     * ip地址
     * 调用接口的机器Ip地址
     */
    private String client_ip;
    
    /**
     * 活动名称
     */
    private String act_name;
    
    /**
     * 备注
     */
    private String remark;

    /**
     * 场景id
     * 发放红包使用场景，红包金额大于200时必传
     * PRODUCT_1:商品促销
     * PRODUCT_2:抽奖
     * PRODUCT_3:虚拟物品兑奖 
     * PRODUCT_4:企业内部福利
     * PRODUCT_5:渠道分润
     * PRODUCT_6:保险回馈
     * PRODUCT_7:彩票派奖
     * PRODUCT_8:税务刮奖
     */
    private String scene_id;
    
    /**
     * 活动信息
     * posttime:用户操作的时间戳
     * mobile:业务系统账号的手机号，国家代码-手机号。不需要+号
     * deviceid :mac 地址或者设备唯一标识 
     * clientversion :用户操作的客户端版本
     * 把值为非空的信息用key=value进行拼接，再进行urlencode
     * urlencode(posttime=xx& mobile =xx&deviceid=xx)
     */
    private String risk_info;
    
    /**
     * 资金授权商户号
     * 服务商替特约商户发放时使用
     */
    private String consume_mch_id;

    public SendRedPack(String nonce_str, String mch_billno, String mch_id, String wxappid, String send_name, String re_openid, Integer total_amount, Integer total_num, String wishing, String client_ip, String act_name, String remark, String scene_id) {
        this.nonce_str = nonce_str;
        this.mch_billno = mch_billno;
        this.mch_id = mch_id;
        this.wxappid = wxappid;
        this.send_name = send_name;
        this.re_openid = re_openid;
        this.total_amount = total_amount;
        this.total_num = total_num;
        this.wishing = wishing;
        this.client_ip = client_ip;
        this.act_name = act_name;
        this.remark = remark;
        this.scene_id = scene_id;
    }


    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getRisk_info() {
        return risk_info;
    }

    public void setRisk_info(String risk_info) {
        this.risk_info = risk_info;
    }

    public String getConsume_mch_id() {
        return consume_mch_id;
    }

    public void setConsume_mch_id(String consume_mch_id) {
        this.consume_mch_id = consume_mch_id;
    }
}
