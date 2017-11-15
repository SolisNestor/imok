package com.fox.imok.domain.io;

/**
 * Created by nestorso on 8/11/2017.
 */

public class ConstantsUrls {

    public static final String IP = "https://1uscv40ln2.execute-api.us-east-1.amazonaws.com";
    public static final String PATH = "/dev";

    public static final String ENDPOINT_LIST_USERS = PATH + "/backend/listusers";
    public static final String ENDPOINT_SAVE_FCM = PATH + "/registerdevice";
    public static final String ENDPOINT_SAVE_SMS = PATH + "/reciveMSM";

    public class Params {
        public static final String COUNTRY = "country";
        public static final String ARN = "arn";
        public static final String ID = "id";
        public static final String MESSAGE = "message";
        public static final String STATUS ="status";
        public static final String EVENT = "event";

    }
}
