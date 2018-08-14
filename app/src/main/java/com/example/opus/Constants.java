package com.example.opus;

public class Constants {

    private static final String SERVER = "http://192.168.0.50/";
    //private static final String SERVER = "http://192.168.0.50/";

    private static final String BASE_SERVER_ADDRESS = SERVER + "OAK/api/OAKAPI/";
    //private static final String BASE_SERVER_ADDRESS = SERVER + "OAK/api/OAKAPI/";
    public static final String LOGIN_URL = SERVER + "OAK/Account/LoginAPI";
    //public static final String LOGIN_URL = SERVER + "OAK/Account/LoginAPI";

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
    public static final String GET_PRA_APPROVER = BASE_SERVER_ADDRESS + "GetPraApprover";
    public static final String GET_REQUISITION_LIST = BASE_SERVER_ADDRESS + "GetMasterWiseRequisitionDetails";

    public static final String GET_REQUISITION_APPROVAL_LIST = BASE_SERVER_ADDRESS +
            "GetRequisitionApprovalData";

    public static final String GET_APPROVED_HISTORY = SERVER + "OAK/Project/GetPRApprovadHistory";
    public static final String POST_APPROVED_DATA = SERVER + "OAK/Requisition/SaveRequisitionApprovalLog";

    public static final String REQUISITION_MODEL = "requisitionmodel";
    public static final String REQUISITION_MASTER = "requisitionmastermodel";

}
