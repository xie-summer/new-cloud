package com.monitor.api.api;

/**
 * @author summer
 * 对外接口常量配置
 */
public interface ApiConfigure {
    /**
     * 字符编码
     */
    String CHARSET = "UTF-8";

    /**
     * appid
     */
    String APPID = "openid";


    String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrGZVy5vpK9NMzBpns072CXbmN\n" +
            "zqPGncWDO64g2cN0V2yFmX0Rw0Q9QN59C9QVV8k02LD5dg0AxkweNM4hp4ZcLbA1\n" +
            "G/tGiCvpVYGlYKX/Nv5w2g8FxmnV3+k18MkBdXFK6Q0w1vf35TdSx/AdPEFKvAPm\n" +
            "N0bbA+N1Urnwz04SbQIDAQAB";

    String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKsZlXLm+kr00zMG\n" +
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
            "sDL+SMAVKbgpJQ==";
    String GATEWAY = "/gateway";
    String GATEWAY_CALL_BACK = "/gateway/apiAuthFailure";
    String QUERYURL = "/exceptionrecord/consumption";
}
