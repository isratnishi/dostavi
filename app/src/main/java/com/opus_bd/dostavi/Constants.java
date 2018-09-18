package com.opus_bd.dostavi;

public class Constants {

    //private static final String SERVER = "http://192.168.0.50/";
     private static final String SERVER = "http://119.148.9.154/";

    private static final String BASE_SERVER_ADDRESS = SERVER + "OAK/api/OAKAPI/";
    public static final String LOGIN_URL = SERVER + "OAK/Account/LoginAPI";

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
    public static final String POST_APPROVED_DATA = SERVER + "OAK/api/OAKAPI/postRequisitionApprovalLog";
    public static final String GET_REQUISITION_LIST_FOR_APPROVE = SERVER + "OAK/api/OAKAPI/GetRequisitionForApprove";
    public static final String GET_REQUISITION_HOME_DATA = BASE_SERVER_ADDRESS + "GetRequisitionForStatus";
    public static final String GET_PRA_BASIC_INFO = SERVER + "OAK/BuyerPreview/GetRequisitionBasicInfo";
    public static final String GET_PRA_STATUS_INFO = SERVER + "OAK/BuyerPreview/GetRequisitionHistoryLog";

    // User Information API
    public static final String GET_USER_INFORMATION = BASE_SERVER_ADDRESS + "getUserInformation";

    // Bundle Constants
    public static final String REQUISITION_ID = "requisitionid";
    public static final String REQUISITION_MODEL = "requisitionmodel";
    public static final String REQUISITION_MASTER = "requisitionmastermodel";
    public static final String REQUISITION_APPROVAL_LIST_MODEL = "requistionapprovallistmodel";
    public static final String MASTER_ID = "masterid";
    public static final String IOU_APPROVAL_MODEL = "iouapprovalmodel";

    // Constants varies according to logged data
    public static String USER_EMAIL = "";
    public static String userCode = "";
    public static String userName = "";
    public static String nextEmpCode = "";

    // IOU Approval
    public static String GET_IOU_APPROVAL = BASE_SERVER_ADDRESS + "GetIOUListForApprove";
    public static String GET_IOU_ITEMS = SERVER + "OAK/IOU/ItemDetailsForIOUApprove";
    public static String POST_SAVE_ITEMS = BASE_SERVER_ADDRESS + "PostIouItems";
    public static String POST_IOU_APPROVE = BASE_SERVER_ADDRESS + "PostIOUApprove";

    // IOU Constants
    public static final int RETURN = 3;
    public static final int REJECT = -1;
    public static final int APPROVED = 4;

    // PO Approve
    public static String GET_PO_APPROVE_INFO = BASE_SERVER_ADDRESS + "GetCSListForApprove";
    public static String POST_PO_APPROVE_INFO = BASE_SERVER_ADDRESS + "PostCSApprove";


}
