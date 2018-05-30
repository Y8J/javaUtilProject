package com.zjht.youoil.util;

public class AccountDictionary {

    public static final String CHECK_ANONYMIT_MOBILE_REGEX           = "^(0400)\\d{7}$";

    public static final String ANONYMIT_USER_ACCOUNT_MEMO_SPLIT_CHAR = "@";

    public static final String ANONYMIT_REGISTER_USER_TO_SEND        = "【匿名注册用户，待发短信告知用户】";

    public static final String ANONYMIT_REGISTER_USER_SENDED         = "【匿名注册用户，已发短信告知用户】";

    public static final String GET_ACCOUNT_TOKEN                     = "0";

    public static final String ANONYMIT_MOBILE                       = "04008666666";

    public static final int    ANONYMIT_TICKET_ASSIST_CODE           = 16;

    public static final String LOGIN_TYPE_USERNAME                   = "1";

    public static final String LOGIN_TYPE_MOBILE                     = "2";

    public static final String LOGIN_TYPE_EMAIL                      = "3";

    public static final String LOGIN_TYPE_MERCHANT                   = "4";

    public static final String LOGIN_TYPE_ZJ_CARD                    = "5";

    public static final String LOGIN_TYPE_TB_CARD                    = "6";

    public static final String ACCOUNT_TYPE_PERSON                   = "1";

    public static final String ACCOUNT_TYPE_ENTERPRISE               = "2";

    public static final String COUPON_CODE                           = "99999999";

    public static final String LOGIN_TYPE_NAME_USERNAME              = "name";

    public static final String LOGIN_TYPE_NAME_MOBILE                = "mobile";

    public static final String LOGIN_TYPE_NAME_EMAIL                 = "email";

    public static final String PERSON_USER_TYPE                      = "1";

    public static final String MERCHANT_USER_TYPE                    = "2";

    public static final String DEFAULT_CREATER_UPDATER               = "system";

    public static final String DEFAULT_MEMO                          = "create by system";

    public static final String DYNAMIC_DEFAULT_PASSWORD              = "123456";

    public static final String STATUS_VALIDATE                       = "1";

    public static final String STATUS_INVALIDATE                     = "0";

    public static final String STATUS_FREEZE                         = "2";

    public static final String ORDER_PAYMENT_TYPE_BANK_CARD          = "0";

    public static final String ORDER_PAYMENT_TYPE_ZJ_CARD            = "1";

    public static final String ORDER_PAYMENT_TYPE_CASH               = "2";

    public static final String ORDER_PAYMENT_TYPE_POINT              = "3";

    public static final String ORDER_PAYMENT_TYPE_TB_CARD            = "6";

    public static final String TXN_TYPE_2                            = "2";

    public static final String TXN_TYPE_3                            = "3";

    public static final String TXN_TYPE_5                            = "5";

    public static final String TXN_TYPE_120                          = "120";

    public static final String TXN_TYPE_121                          = "121";

    public static final String TXN_TYPE_122                          = "122";

    public static final String TXN_TYPE_123                          = "123";

    public static final String TXN_TYPE_124                          = "124";

    public static final String TXN_TYPE_125                          = "125";

    public static final String TXN_TYPE_126                          = "126";

    public static final String TXN_TYPE_127                          = "127";

    public static final String TXN_TYPE_128                          = "128";

    public static final String TXN_TYPE_129                          = "129";

    public static final String TXN_TYPE_130                          = "130";

    public static final String TXN_TYPE_131                          = "131";

    public static final String TXN_TYPE_132                          = "132";

    public static final String TXN_TYPE_133                          = "133";

    public static final String TXN_TYPE_134                          = "134";

    public static final String TXN_TYPE_135                          = "135";

    public static final String TXN_TYPE_136                          = "136";

    public static final String TXN_TYPE_137                          = "137";

    public static final String TXN_TYPE_138                          = "138";

    public static final String TXN_TYPE_139                          = "139";

    public static final String TXN_TYPE_200                          = "200";

    public static final String TXN_RESULT_SUCCESS                    = "00";

    public static final String TXN_RESULT_SUCCESS_DESC               = "success";

    public static final String TXN_RESULT_FAILURE                    = "99";

    public static final String PAYINCODE_SHOP_CODE                   = "payincode";

    public static final String PAYINCODE_SHOP_NAME                   = "汇生活";

    public static final String TRADE_PAY_RECHARGE                    = "1";

    public static final String TRADE_REVOKE_PAY_RECHARGE             = "2";

    public static final String TRADE_REVERSAL_PAY_RECHARGE           = "3";

    public static final String TRADE_ADJUST_PAY_RECHARGE             = "4";

    public static final String POINT_TYPE_BASE                       = "1";

    public static final String POINT_TYPE_AWARD                      = "2";

    public static final String CASH_TXN_STATUS_CUSUME_REVOKE         = "124";

    public static final String CASH_TXN_STATUS_CUSUME_REVERSAL       = "125";

    public static final String CASH_TXN_STATUS_RECHARGE              = "126";

    public static final String CASH_TXN_STATUS_RECHARGE_REVOKE       = "127";

    public static final String CASH_TXN_STATUS_RECHARGE_REVERSAL     = "128";

    public static final String POINT_TXN_STATUS_CUSUME               = "129";

    public static final String POINT_TXN_STATUS_CUSUME_REVOKE        = "130";

    public static final String POINT_TXN_STATUS_CUSUME_REVERSAL      = "131";

    public static final String POINT_TXN_STATUS_ACQUIRE              = "132";

    public static final String POINT_TXN_STATUS_ACQUIRE_REVOKE       = "133";

    public static final String POINT_TXN_STATUS_ACQUIRE_REVERSAL     = "134";

    public static final String TXN_STATUS_SUCCESS                    = "00";

    public static final String TXN_STATUS_REVOKE                     = "01";

    public static final String TXN_STATUS_REVERSAL                   = "02";

    public static final String TXN_STATUS_MAKEOVER                   = "03";

    public static final String TXN_STATUS_ADJUST                     = "04";

    public static final String ACCOUNT_PREFIX                        = "00";

    public static final String ACCOUNT_CASH_PREFIX                   = "10";

    public static final String ACCOUNT_POINT_PREFIX                  = "20";

    public static final String ACCOUNT_TICKET_PREFIX                 = "30";

    public static final String ACCOUNT_AREA_44                       = "44";

    public static final long   ACCOUNT_SEQUENCE_INIT                 = 10000000000000L;

    public static final String ACCOUNT_SEQUENCE_CATEGORY             = "ACCOUNT";

    public static final String CASH_ACCOUNT_TYPE_CREDIT              = "1";

    public static final String CASH_ACCOUNT_TYPE_PAYMENT             = "2";

    public static final String ERROR_CODE_N1                         = "-1";

    public static final String ERROR_CODE_N1_DESC                    = "参数不能为空";

    public static final String ERROR_CODE_N2                         = "-2";

    public static final String ERROR_CODE_N2_DESC                    = "版本号不对";

    public static final String ERROR_CODE_N3                         = "-3";

    public static final String ERROR_CODE_N3_DESC                    = "token不正确";

    public static final String ERROR_CODE_N4                         = "-4";

    public static final String ERROR_CODE_N4_DESC                    = "摘要解密出错";

    public static final String ERROR_CODE_N5                         = "-5";

    public static final String ERROR_CODE_N5_DESC                    = "摘要有误，摘要不可为空";

    public static final String ERROR_CODE_N6                         = "-6";

    public static final String ERROR_CODE_N6_DESC                    = "该平台不能接入系统";

    public static final String ERROR_CODE_N7                         = "-7";

    public static final String ERROR_CODE_N7_DESC                    = "请求的IP不能接入系统";

    public static final String ERROR_CODE_N8                         = "-8";

    public static final String ERROR_CODE_N8_DESC                    = "该平台不可以进行该操作";

    public static final String ERROR_CODE_N9                         = "-9";

    public static final String ERROR_CODE_N9_DESC                    = "加密类型不正确";

    public static final String ERROR_CODE_N10                        = "-10";

    public static final String ERROR_CODE_N10_DESC                   = "数据库异常";

    public static final String ERROR_CODE_N11                        = "-11";

    public static final String ERROR_CODE_N11_DESC                   = "请求超时";

    public static final String ERROR_CODE_N12                        = "-12";

    public static final String ERROR_CODE_N12_DESC                   = "请求参数格式错误";

    public static final String ERROR_CODE_N13                        = "-13";

    public static final String ERROR_CODE_N13_DESC                   = "请求过程异常";

    public static final String ERROR_CODE_N14                        = "-14";

    public static final String ERROR_CODE_N14_DESC                   = "保存交易结果时发生数据库异常";

    public static final String ERROR_CODE_N15                        = "-15";

    public static final String ERROR_CODE_N15_DESC                   = "参数不可用";

    public static final String ERROR_CODE_N16                        = "-16";

    public static final String ERROR_CODE_N16_DESC                   = "事务回滚异常";

    public static final String ERROR_CODE_N17                        = "-17";

    public static final String ERROR_CODE_N17_DESC                   = "请求的IP为空";

    public static final String ERROR_CODE_N18                        = "-18";

    public static final String ERROR_CODE_N18_DESC                   = "IP地址对应的用户名/密码不正确";

    public static final String ERROR_CODE_N19                        = "-19";

    public static final String ERROR_CODE_N19_DESC                   = "IP地址对应的用户名/密码不能为空";

    public static final String ERROR_CODE_N20                        = "-20";

    public static final String ERROR_CODE_N20_DESC                   = "IP地址还没有配置用户名/密码";

    public static final String ERROR_CODE_N21                        = "-21";

    public static final String ERROR_CODE_N21_DESC                   = "报文头为空";

    public static final String ERROR_CODE_N22                        = "-22";

    public static final String ERROR_CODE_N22_DESC                   = "报文头中没有找到授权信息";

    public static final String ERROR_CODE_N23                        = "-23";

    public static final String ERROR_CODE_N23_DESC                   = "找不到role next 报文头";

    public static final String ERROR_CODE_N24                        = "-24";

    public static final String ERROR_CODE_N24_DESC                   = "系统未授权给任何IP访问";

    public static final String ERROR_CODE_N25                        = "-25";

    public static final String ERROR_CODE_N25_DESC                   = "暂不支持该服务";

    public static final String ERROR_CODE_N99                        = "-99";

    public static final String ERROR_CODE_N99_DESC                   = "系统内部异常";

    public static final String ERROR_CODE_01                         = "01";

    public static final String ERROR_CODE_01_DESC                    = "用户名已经存在";

    public static final String ERROR_CODE_02                         = "02";

    public static final String ERROR_CODE_02_DESC                    = "Email已经存在";

    public static final String ERROR_CODE_03                         = "03";

    public static final String ERROR_CODE_03_DESC                    = "手机号已经存在";

    public static final String ERROR_CODE_04                         = "04";

    public static final String ERROR_CODE_04_DESC                    = "密码和确认密码不一致";

    public static final String ERROR_CODE_05                         = "05";

    public static final String ERROR_CODE_05_DESC                    = "账户未激活";

    public static final String ERROR_CODE_06                         = "06";

    public static final String ERROR_CODE_06_DESC                    = "账户已停用";

    public static final String ERROR_CODE_07                         = "07";

    public static final String ERROR_CODE_07_DESC                    = "账户已销户";

    public static final String ERROR_CODE_08                         = "08";

    public static final String ERROR_CODE_08_DESC                    = "账户已冻结";

    public static final String ERROR_CODE_09                         = "09";

    public static final String ERROR_CODE_09_DESC                    = "其他原因导致账户不可用";

    public static final String ERROR_CODE_10                         = "10";

    public static final String ERROR_CODE_10_DESC                    = "登录类型不存在";

    public static final String ERROR_CODE_11                         = "11";

    public static final String ERROR_CODE_11_DESC                    = "用户名/密码不匹配";

    public static final String ERROR_CODE_12                         = "12";

    public static final String ERROR_CODE_12_DESC                    = "交易类型错误，不可用的交易类型";

    public static final String ERROR_CODE_13                         = "13";

    public static final String ERROR_CODE_13_DESC                    = "账号非正常状态，操作失败";

    public static final String ERROR_CODE_14                         = "14";

    public static final String ERROR_CODE_14_DESC                    = "账号不存在，操作失败";

    public static final String ERROR_CODE_15                         = "15";

    public static final String ERROR_CODE_15_DESC                    = "账户不存在，操作失败";

    public static final String ERROR_CODE_16                         = "16";

    public static final String ERROR_CODE_16_DESC                    = "订单号已存在，操作失败";

    public static final String ERROR_CODE_17                         = "17";

    public static final String ERROR_CODE_17_DESC                    = "订单不存在，操作失败";

    public static final String ERROR_CODE_18                         = "18";

    public static final String ERROR_CODE_18_DESC                    = "订单有重复，操作失败";

    public static final String ERROR_CODE_19                         = "19";

    public static final String ERROR_CODE_19_DESC                    = "订单已结算，不能撤销";

    public static final String ERROR_CODE_20                         = "20";

    public static final String ERROR_CODE_20_DESC                    = "支付已撤销，不可以进行撤销/冲正";

    public static final String ERROR_CODE_21                         = "21";

    public static final String ERROR_CODE_21_DESC                    = "有重复的订单，撤销失败";

    public static final String ERROR_CODE_22                         = "22";

    public static final String ERROR_CODE_22_DESC                    = "原订单无效，撤销/冲正订单失败";

    public static final String ERROR_CODE_23                         = "23";

    public static final String ERROR_CODE_23_DESC                    = "该账户没任何可用于支付的现金/积分/电子券账户";

    public static final String ERROR_CODE_24                         = "24";

    public static final String ERROR_CODE_24_DESC                    = "可支付余额/积分数/次数/量数不足";

    public static final String ERROR_CODE_25                         = "25";

    public static final String ERROR_CODE_25_DESC                    = "订单超过了撤销期限，不能撤销";

    public static final String ERROR_CODE_26                         = "26";

    public static final String ERROR_CODE_26_DESC                    = "撤销订单的账号跟原订单的账号不一致";

    public static final String ERROR_CODE_27                         = "27";

    public static final String ERROR_CODE_27_DESC                    = "撤销/冲正订单的金额/积分/次数/量数跟原订单的金额/积分/次数/量数不一致";

    public static final String ERROR_CODE_28                         = "28";

    public static final String ERROR_CODE_28_DESC                    = "撤销订单的积分数跟原订单的积分数不一致";

    public static final String ERROR_CODE_29                         = "29";

    public static final String ERROR_CODE_29_DESC                    = "撤销订单的电子券次/量数跟订单的电子券次/量数不一致";

    public static final String ERROR_CODE_30                         = "30";

    public static final String ERROR_CODE_30_DESC                    = "现金账号不存在";

    public static final String ERROR_CODE_31                         = "31";

    public static final String ERROR_CODE_31_DESC                    = "积分账号不存在";

    public static final String ERROR_CODE_32                         = "32";

    public static final String ERROR_CODE_32_DESC                    = "电子券账号不存在";

    public static final String ERROR_CODE_33                         = "33";

    public static final String ERROR_CODE_33_DESC                    = "电子券不存在";

    public static final String ERROR_CODE_34                         = "34";

    public static final String ERROR_CODE_34_DESC                    = "支付账号不正确";

    public static final String ERROR_CODE_35                         = "351";

    public static final String ERROR_CODE_35_DESC                    = "电子券不可用";

    public static final String ERROR_CODE_36                         = "36";

    public static final String ERROR_CODE_36_DESC                    = "电子券已经过期";

    public static final String ERROR_CODE_37                         = "37";

    public static final String ERROR_CODE_37_DESC                    = "交易失败，发生并发交易，账号产生了负值";

    public static final String ERROR_CODE_38                         = "38";

    public static final String ERROR_CODE_38_DESC                    = "发生并发撤销，撤销失败";

    public static final String ERROR_CODE_39                         = "39";

    public static final String ERROR_CODE_39_DESC                    = "电子券有效期应大于当前日期";

    public static final String ERROR_CODE_40                         = "40";

    public static final String ERROR_CODE_40_DESC                    = "找不到要撤销的订单，订单号不正确或撤销的金额/积分数/次数/量数不正确";

    public static final String ERROR_CODE_41                         = "41";

    public static final String ERROR_CODE_41_DESC                    = "账户没有可用现金账户";

    public static final String ERROR_CODE_42                         = "42";

    public static final String ERROR_CODE_42_DESC                    = "账户没有可用积分账户";

    public static final String ERROR_CODE_43                         = "43";

    public static final String ERROR_CODE_43_DESC                    = "账户没有可用电子券账户";

    public static final String ERROR_CODE_44                         = "44";

    public static final String ERROR_CODE_44_DESC                    = "原订单无效，操作失败";

    public static final String ERROR_CODE_45                         = "45";

    public static final String ERROR_CODE_45_DESC                    = "不可用的积分类型";

    public static final String ERROR_CODE_46                         = "46";

    public static final String ERROR_CODE_46_DESC                    = "不可用的电子券类型";

    public static final String ERROR_CODE_47                         = "47";

    public static final String ERROR_CODE_47_DESC                    = "不可用的交易类型";

    public static final String ERROR_CODE_48                         = "48";

    public static final String ERROR_CODE_48_DESC                    = "不可用的账户类型";

    public static final String ERROR_CODE_49                         = "49";

    public static final String ERROR_CODE_49_DESC                    = "支付已冲正，不可以再进行冲正/撤消";

    public static final String ERROR_CODE_50                         = "50";

    public static final String ERROR_CODE_50_DESC                    = "用户已失效";

    public static final String ERROR_CODE_51                         = "51";

    public static final String ERROR_CODE_51_DESC                    = "用户已被锁定";

    public static final String ERROR_CODE_52                         = "52";

    public static final String ERROR_CODE_52_DESC                    = "交易金额/积分/次数/量数不正确";

    public static final String ERROR_CODE_53                         = "53";

    public static final String ERROR_CODE_53_DESC                    = "类型错误";

    public static final String ERROR_CODE_54                         = "54";

    public static final String ERROR_CODE_54_DESC                    = "密码/确认密码不可为空";

    public static final String ERROR_CODE_55                         = "55";

    public static final String ERROR_CODE_55_DESC                    = "电子券已经使用，不能撤销/冲正";

    public static final String ERROR_CODE_56                         = "56";

    public static final String ERROR_CODE_56_DESC                    = "原密码不正确";

    public static final String ERROR_CODE_57                         = "57";

    public static final String ERROR_CODE_57_DESC                    = "新密码不能为空";

    public static final String ERROR_CODE_58                         = "58";

    public static final String ERROR_CODE_58_DESC                    = "手机/邮箱未认证";

    public static final String ERROR_CODE_59                         = "59";

    public static final String ERROR_CODE_59_DESC                    = "用户名/手机/邮箱不存在";

    public static final String ERROR_CODE_60                         = "60";

    public static final String ERROR_CODE_60_DESC                    = "购买的电子券已被撤消，操作失败";

    public static final String ERROR_CODE_61                         = "61";

    public static final String ERROR_CODE_61_DESC                    = "购买的电子券已被冲正，操作失败";

    public static final String ERROR_CODE_62                         = "62";

    public static final String ERROR_CODE_62_DESC                    = "购买的电子券已被转让，操作失败";

    public static final String ERROR_CODE_63                         = "63";

    public static final String ERROR_CODE_63_DESC                    = "平台标识号或服务标识号不正确";

    public static final String ERROR_CODE_64                         = "64";

    public static final String ERROR_CODE_64_DESC                    = "动态口令不正确";

    public static final String ERROR_CODE_65                         = "65";

    public static final String ERROR_CODE_65_DESC                    = "动态口令已过期";

    public static final String ERROR_CODE_66                         = "66";

    public static final String ERROR_CODE_66_DESC                    = "动态口令不能验证多次";

    public static final String ERROR_CODE_67                         = "67";

    public static final String ERROR_CODE_67_DESC                    = "无动态口令";

    public static final String ERROR_CODE_68                         = "68";

    public static final String ERROR_CODE_68_DESC                    = "电子券类型错误";

    public static final String ERROR_CODE_69                         = "69";

    public static final String ERROR_CODE_69_DESC                    = "电子券使用范围类型错误";

    public static final String ERROR_CODE_70                         = "70";

    public static final String ERROR_CODE_70_DESC                    = "电子券支付方式错误";

    public static final String ERROR_CODE_71                         = "71";

    public static final String ERROR_CODE_71_DESC                    = "电子券是否热门错误";

    public static final String ERROR_CODE_72                         = "72";

    public static final String ERROR_CODE_72_DESC                    = "电子券是否促销错误";

    public static final String ERROR_CODE_73                         = "73";

    public static final String ERROR_CODE_73_DESC                    = "电子券状态错误";

    public static final String ERROR_CODE_74                         = "74";

    public static final String ERROR_CODE_74_DESC                    = "电子券票面价格应为整数，单位为分";

    public static final String ERROR_CODE_75                         = "75";

    public static final String ERROR_CODE_75_DESC                    = "电子券原价应为整数，单位为分";

    public static final String ERROR_CODE_76                         = "76";

    public static final String ERROR_CODE_76_DESC                    = "电子券售价应为整数，单位为分";

    public static final String ERROR_CODE_77                         = "77";

    public static final String ERROR_CODE_77_DESC                    = "电子券总数应为整数";

    public static final String ERROR_CODE_78                         = "78";

    public static final String ERROR_CODE_78_DESC                    = "帐户不是商家帐户";

    public static final String ERROR_CODE_79                         = "79";

    public static final String ERROR_CODE_79_DESC                    = "重复添加相同CODE的券，操作错误";

    public static final String ERROR_CODE_80                         = "80";

    public static final String ERROR_CODE_80_DESC                    = "找不到指定CODE的券";

    public static final String ERROR_CODE_81                         = "81";

    public static final String ERROR_CODE_81_DESC                    = "指定券不属于指定商家";

    public static final String ERROR_CODE_82                         = "82";

    public static final String ERROR_CODE_82_DESC                    = "供应商不存在";

    public static final String ERROR_CODE_83                         = "83";

    public static final String ERROR_CODE_83_DESC                    = "供应商所属的门店不存在";

    public static final String ERROR_CODE_84                         = "84";

    public static final String ERROR_CODE_84_DESC                    = "创建电子券快照出错";

    public static final String ERROR_CODE_85                         = "85";

    public static final String ERROR_CODE_85_DESC                    = "查询电子券查询条件中排序项错误";

    public static final String ERROR_CODE_86                         = "86";

    public static final String ERROR_CODE_86_DESC                    = "查询页码数应为整数";

    public static final String ERROR_CODE_87                         = "87";

    public static final String ERROR_CODE_87_DESC                    = "查询每页显示数应为整数";

    public static final String ERROR_CODE_88                         = "88";

    public static final String ERROR_CODE_88_DESC                    = "电子券被浏览次数应为整数";

    public static final String ERROR_CODE_89                         = "89";

    public static final String ERROR_CODE_89_DESC                    = "找不到指定CODE的门店";

    public static final String ERROR_CODE_90                         = "90";

    public static final String ERROR_CODE_90_DESC                    = "电子券在非允许使用时段使用";

    public static final String ERROR_CODE_99                         = "99";

    public static final String ERROR_CODE_99_DESC                    = "其它未知错误";

    public static final String ERROR_CODE_100                        = "100";

    public static final String ERROR_CODE_100_DESC                   = "手机号不能为空，并且认证状态必须是1";

    public static final String ERROR_CODE_101                        = "101";

    public static final String ERROR_CODE_101_DESC                   = "驳回，审核未通过（商家登录时）";

    public static final String ERROR_CODE_104                        = "104";

    public static final String ERROR_CODE_104_DESC                   = "新注册商家，并未审核过";

    public static final String ERROR_CODE_200                        = "200";

    public static final String ERROR_CODE_200_DESC                   = "更新订单失败";

    public static final String ERROR_CODE_201                        = "201";

    public static final String ERROR_CODE_201_DESC                   = "订单已被更新，不能再更新";

    public static final String ERROR_CODE_202                        = "202";

    public static final String ERROR_CODE_202_DESC                   = "订单状态不可用";

    public static final String ERROR_CODE_203                        = "203";

    public static final String ERROR_CODE_203_DESC                   = "订单不属于该会员";

    public static final String ERROR_CODE_204                        = "204";

    public static final String ERROR_CODE_204_DESC                   = "订单金额小于0";

    public static final String ERROR_CODE_205                        = "205";

    public static final String ERROR_CODE_205_DESC                   = "找不到订单对应的劵";

    public static final String ERROR_CODE_206                        = "206";

    public static final String ERROR_CODE_206_DESC                   = "日期格式不可用";

    public static final String ERROR_CODE_207                        = "207";

    public static final String ERROR_CODE_207_DESC                   = "状态值不可用";

    public static final String ERROR_CODE_208                        = "208";

    public static final String ERROR_CODE_208_DESC                   = "手机号未经验证,不能获取用户资料";

    public static final String ERROR_CODE_209                        = "209";

    public static final String ERROR_CODE_209_DESC                   = "邮箱未经验证,不能获取用户资料";

    public static final String ERROR_CODE_210                        = "210";

    public static final String ERROR_CODE_210_DESC                   = "交易的电子券数量不可用，必须是整数且大于0";

    public static final String ERROR_CODE_211                        = "211";

    public static final String ERROR_CODE_211_DESC                   = "电子券库存不足";

    public static final String ERROR_CODE_212                        = "212";

    public static final String ERROR_CODE_212_DESC                   = "发生并发购买券，库存出现负数错误";

    public static final String ERROR_CODE_213                        = "213";

    public static final String ERROR_CODE_213_DESC                   = "交易流水不存在";

    public static final String ERROR_CODE_214                        = "214";

    public static final String ERROR_CODE_214_DESC                   = "会员类型不可用";

    public static final String ERROR_CODE_215                        = "215";

    public static final String ERROR_CODE_215_DESC                   = "账号关联的用户类型不可用";

    public static final String ERROR_CODE_216                        = "216";

    public static final String ERROR_CODE_216_DESC                   = "商家暂不能绑定email";

    public static final String ERROR_CODE_217                        = "217";

    public static final String ERROR_CODE_217_DESC                   = "商户不存在";

    public static final String ERROR_CODE_218                        = "218";

    public static final String ERROR_CODE_218_DESC                   = "会员类型不正确";

    public static final String ERROR_CODE_219                        = "219";

    public static final String ERROR_CODE_219_DESC                   = "用户已失效";

    public static final String ERROR_CODE_220                        = "220";

    public static final String ERROR_CODE_220_DESC                   = "用户已被锁定";

    public static final String ERROR_CODE_221                        = "221";

    public static final String ERROR_CODE_221_DESC                   = "用户待审核状态，不能登录";

    public static final String ERROR_CODE_222                        = "222";

    public static final String ERROR_CODE_222_DESC                   = "用户状态不可用";

    public static final String ERROR_CODE_223                        = "223";

    public static final String ERROR_CODE_223_DESC                   = "会员不存在";

    public static final String ERROR_CODE_224                        = "224";

    public static final String ERROR_CODE_224_DESC                   = "卡号已被绑定";

    public static final String ERROR_CODE_225                        = "225";

    public static final String ERROR_CODE_225_DESC                   = "登录名称已经存在";

    public static final String ERROR_CODE_226                        = "226";

    public static final String ERROR_CODE_226_DESC                   = "会员没有可用的顶级现金账号";

    public static final String ERROR_CODE_227                        = "227";

    public static final String ERROR_CODE_227_DESC                   = "登录账号不可用，请先激活登录账号";

    public static final String ERROR_CODE_228                        = "228";

    public static final String ERROR_CODE_228_DESC                   = "冻结/解冻状态不可用，必须是：1 解冻  2 冻结";

    public static final String ERROR_CODE_229                        = "229";

    public static final String ERROR_CODE_229_DESC                   = "数组参数个数不匹配";

    public static final String ERROR_CODE_230                        = "230";

    public static final String ERROR_CODE_230_DESC                   = "券不允许在该门店使用";

    public static final String ERROR_CODE_231                        = "231";

    public static final String ERROR_CODE_231_DESC                   = "手机号或交易码不正确";

    public static final String ERROR_CODE_232                        = "232";

    public static final String ERROR_CODE_232_DESC                   = "流水记录不存在";

    public static final String ERROR_CODE_233                        = "233";

    public static final String ERROR_CODE_233_DESC                   = "登录用户不存在";

    public static final String ERROR_CODE_234                        = "234";

    public static final String ERROR_CODE_234_DESC                   = "券已达有效数量，不允许消费";

    public static final String ERROR_CODE_235                        = "235";

    public static final String ERROR_CODE_235_DESC                   = "实体对象不存在";

    public static final String ERROR_CODE_236                        = "236";

    public static final String ERROR_CODE_236_DESC                   = "电子券重复";

    public static final String ERROR_CODE_237                        = "237";

    public static final String ERROR_CODE_237_DESC                   = "实体对象已经存在";

    public static final String SS_AC_MID                             = "01";

    public static final String SS_AC_TID                             = "44010609";

    public static final String SS_AC_TELLER                          = "account_center";

    public static final String SS_AC_ACQID                           = "ZJHT";

    public static final String SS_AC_ISSUEID                         = "ac_issueid";

    public static final String SS_AC_ORGCODE                         = "ZJHT";

    public static final String SS_AC_PASSWORD                        = "888888";

    public static final String SS_ERROR_CODE_300                     = "300";

    public static final String SS_ERROR_CODE_300_DESC                = "卡不存在";

    public static final String SS_ERROR_CODE_301                     = "301";

    public static final String SS_ERROR_CODE_301_DESC                = "卡支付失败";

    public static final String SS_ERROR_CODE_302                     = "302";

    public static final String SS_ERROR_CODE_302_DESC                = "撤消卡支付失败";

    public static final String SS_ERROR_CODE_303                     = "303";

    public static final String SS_ERROR_CODE_303_DESC                = "卓望动态码发送失败";

    public static final String SS_ERROR_CODE_304                     = "304";

    public static final String SS_ERROR_CODE_304_DESC                = "卓望动态码认证失败";

    public static final String SS_ERROR_CODE_305                     = "305";

    public static final String SS_ERROR_CODE_305_DESC                = "不记名卡不允许在线消费";

    public static final String SS_ERROR_CODE_306                     = "306";

    public static final String SS_ERROR_CODE_306_DESC                = "ESB系统登录失败，无法完成操作";

    public static final String SS_ERROR_CODE_0010                    = "0010";

    public static final String SS_ERROR_CODE_307_DESC                = "无效sessionId，请重新认证";

    public static final String DG_ACTIVITY                           = "DGYD00100100019";

    public static final String DG_SALE_BARCODE_FAILURE_CODE          = "01";

    public static final String DG_RECHARGE_PAN_FAILURE_CODE          = "02";

    public static final String UP_ERROR_CODE_01                      = "UP01";

    public static final String UP_ERROR_CODE_01_DESC                 = "获取银联交易流水号失败，报文为空";

    public static final String UP_ERROR_CODE_02                      = "UP02";

    public static final String UP_ERROR_CODE_02_DESC                 = "获取银联交易流水号失败，签名错误";

    public static final String UP_ERROR_CODE_03                      = "UP03";

    public static final String UP_ERROR_CODE_03_DESC                 = "获取银联交易流水号失败，支付推送响应失败";

    public static final String UP_ERROR_CODE_04                      = "UP04";

    public static final String UP_ERROR_CODE_04_DESC                 = "获取银联交易流水号失败，交易流水号为空";

    public static final String UP_ERROR_CODE_05                      = "UP05";

    public static final String UP_ERROR_CODE_05_DESC                 = "获取银联交易流水号失败，订单号、订单时间重复";

    public static final String UP_ERROR_CODE_06                      = "UP06";

    public static final String UP_ERROR_CODE_06_DESC                 = "会员号不能为空";

    public static final String UP_ERROR_CODE_07                      = "UP07";

    public static final String UP_ERROR_CODE_07_DESC                 = "订单号不能为空";

    public static final String MP_TYPE_RECHARGE_CASH_ACCOUNT         = "1";

    public static final String MP_TYPE_BUY_TICKET                    = "2";

    public static final String MP_ERROR_CODE_01                      = "MP01";

    public static final String MP_ERROR_CODE_01_DESC                 = "交易出错，支付平台不可用";

    public static final String MP_ERROR_CODE_02                      = "MP02";

    public static final String MP_ERROR_CODE_02_DESC                 = "交易出错，手机认证失败";
}