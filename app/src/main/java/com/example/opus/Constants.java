package com.example.opus;

public class Constants {
    private static final String BASE_SERVER_ADDRESS = "http://192.168.0.50/OAK/api/OAKAPI/";
    //private static final String BASE_SERVER_ADDRESS = "http://119.148.9.154/OAK/api/OAKAPI/";
    public static String USER_EMAIL = "";
    public static final String LOGTAG = "logtag";
    public static final String REQUEST_TAG = "com.example.opus";
    public static final String GET_REQUISITION_INFO = BASE_SERVER_ADDRESS + "GetRequisitionEntryInfo";
    public static final String GET_REQUISITION_NUMBER = BASE_SERVER_ADDRESS + "GetRequisitionAutoNumber";
    public static final String POST_REQUISITION_INFO = BASE_SERVER_ADDRESS + "req";
    public static final String POST_REQUISITION_LOG = BASE_SERVER_ADDRESS + "postRequisitionLog";
    public static final String POST_REQUISITION_DETAIL = BASE_SERVER_ADDRESS + "postRequisitionDetail";
    public static final String POST_REQUISITION_STATUS_LOG = BASE_SERVER_ADDRESS + "SaveRequisitionStatusLog";
    public static final String GET_ALL_LIST = BASE_SERVER_ADDRESS +
            "GETITEMLIST";

    public static final String GET_REQUISITION_APPROVAL_LIST = BASE_SERVER_ADDRESS +
            "GetRequisitionApprovalData";

    public static final String LOGIN_URL = "http://192.168.0.50/OAK/Account/LoginAPI";
    //public static final String LOGIN_URL = "http://119.148.9.154/OAK/Account/LoginAPI";

    public static final String REQUISITION_MODEL = "requisitionmodel";
    public static final String REQUISITION_MASTER = "requisitionmastermodel";

}
