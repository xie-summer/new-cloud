# gateway

## 公共参数
#### 请求地址



环境 | HTTPS请求地址
---|---
正式环境| https://invoke.neweplatform.com/gateway(目前待定，预留的上线使用的二级域名)

#### 公共请求参数


参数|类型|是否必填|最大长度|描述|示例值
---|---|---|---|---|---
app_id |String|是|32|分配给开发者的应用ID|appid
method |String|是|128|接口名称(需要查询的接口方法，详见下面，具体查询方法文档)|/material/consumption
format |String|否|40|仅支持JSON|JSON
charset|String|是|10|请求使用的编码格式，如utf-8,gbk,gb2312等|utf-8
sign_type|String|是|10|生成签名字符串所使用的签名算法类型，目前支持RSA|RSA
sign|String|是|256|请求参数的签名串，详见签名(详见签名生成方法)|详见示例
timestamp|String|是|19|发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"|2014-07-24 03:07:50
version|String|是|3|调用的接口版本，固定为：1.0|1.0
biz_content|String|是|               |请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档|

###### 私钥

            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKsZlXLm+kr00zMG\n" +
            "mezTvYJduY3Oo8adxYM7riDZw3RXbIWZfRHDRD1A3n0L1BVXyTTYsPl2DQDGTB40\n" +
            "ziGnhlwtsDUb+0aIK+lVgaVgpf82/nDaDwXGadXf6TXwyQF1cUrpDTDW9/flN1LH\n" +
            "8B08QUq8A+Y3RtsD43VSufDPThJtAgMBAAECgYEAgtb5lfhThmZZ6zctFhGFZd/O\n" +
            "OUa/ru6PT5+ftLdR4/HGpxOMtLyhvkWQ3gbhk8ChuKvMCbfgkasSIjc3Oj0lXK93\n" +
            "9bTWf49/qrFPl2/qEMyyhPtVgCh+9qFdSK6bqujTL3rvRrsu1U21v78fBiNVRbU7\n" +
            "xdpKbih2j5FEfumMTXECQQDcwfTBEChF+aUyUdyzHYwPEsTvk8r41ZEOB/eUZ0aD\n" +
            "1pPuxWXDQ71Rf2WaknwbHDdLEPUWy2eY0X0IJHZO/oLjAkEAxmowhAxW7auvlmT3\n" +
            "U/gheQVjOkyAwQm9g3XB97/a//+mdRD2z4MwJ1QQn5HefzgyDo74ScZqbv1VMwEd\n" +
            "1DYGbwJAZDAGU2Rw0qklBvxODGA5g1HCXPSXJm1Ogq6s9wYT45uL0c52i7L/hbAq\n" +
            "vYcDmg4pLgFJKgowPxO8Cej6ZIlvfwJAPJ0yJxTx9XeOk55SpHdYGnWX47Z9WvuT\n" +
            "xrZRxFL7H1vFSVweWjSUoF8hGO5iD3FmcWYh2b/gwflobsV3jF3YxwJAeAujTHoT\n" +
            "k+HV5C7qQMbjMQURB2/1slQexiVAS0wJXEv9aCyhjA2YM36pESMWt+0qcpB3Z3Qf\n" +
            "sDL+SMAVKbgpJQ=="

##### 签名生成方法
```
签名生成方法：把公共请求参数和请求参数一起进行RSA加密得到。（getSign是自己获取RSA签名方法）
eq:
`       Map<String, String> params = Maps.newHashMap();
        params.put("app_id", ApiConfigure.APPID);
        params.put("method", ApiConfigure.QUERYURL);
        params.put("charset", ApiConfigure.CHARSET);
        params.put("sign_type", "RSA");
        params.put("timestamp", DateUtil.getCurFullTimestampStr());
        params.put("version", "1.0");
        Map<String, String> bizMap = Maps.newHashMap();
        bizMap.put("id", "1");
        params.put("biz_content", JsonUtils.writeMapToJson(bizMap));
        String sign = SignUtil.getSign(params, ApiConfigure.PRIVATEKEY);
`
```
### 请求参数(根据具体查询方法参数而定，eq:查询物料详情)
参数|类型|是否必填|最大长度|描述|示例值
---|---|---|---|---|---
id|String|必选|32|物料的id|1
##### 具体查询方法文档(具体数据查询对应的需求参数和响应结果)

    https://invoke.neweplatform.com/swagger-ui.html#!

### 请求示例（RESTFUL API）
###### HTTP请求源码（GET方式sign需要URL编码2次以上，POST无需）
```
http://localhost:8000/gateway?charset=UTF-8&biz_content={"id":"1"}&method=/material/consumption/&sign=eH96%252bEa%252fxmqzG45C68rG2tJkcUEsq%252b%252blDFuOkcV4c8JOSz%252f6VYqi66ugFvLUg32ajE%252fibbp4pMmpzH56eYISyJhCQlFrcrdDXBUXKciiTf0fwp0kZ2mTgCDq6gqWIXJNSsnwsWAJRoHUp2fRZRFQ5Rb%252bZf8bu3RRU1KufeeNYC0%253d&app_id=openid&sign_type=RSA&version=1.0&timestamp=2017-11-20 11:38:18
```
### 响应示例（响应参数请参考具体查询方法的响应文档）
```
{
  "errcode": "0000",
  "exception": null,
  "msg": "",
  "retval": {
    "conTime": 1497652974000,
    "conUnit": "kg",
    "conWeight": 0,
    "deleteFlag": 0,
    "id": 1,
    "matCode": "N/A",
    "matName": "N/A",
    "notes": "",
    "opName": "0004B4000001_F",
    "reservedDate1": null,
    "reservedDate2": null,
    "reservedInteger1": 0,
    "reservedInteger2": 0,
    "reservedVarchar2": "000000000002",
    "reservedVarchar3": "",
    "sysTime": 1497652974000,
    "tagname": "0AFC"
  },
  "success": true
}
```
### 异常示例

```
{
  "allHeaders": {},
  "cookies": [],
  "msg": "签名验证失败",
  "response": "{}",
  "status": 401,
  "success": false
}
```
### 业务错误码
###### 公共错误码（HTTP状态码）
错误码|错误描述|解决方案
---|---|---|---|---|---
401|授权验证失败|检查验签传递参数和签名|
404|页面找不到|检测method方法路径
500|服务器异常| |
200|请求成功| |
###### 业务错误码（errcode状态码）
错误码|错误描述|解决方案
---|---|---|---|---|---
0000|请求成功| |
9999|未知错误| |
4005|数据异常| |
4000|服务器资源存在| |
5000|服务器内部异常| |
1000|授权错误| |


