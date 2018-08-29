package com.example.opus.models;


/**
 * Created by Shoukhin on 7/23/2018.
 */

public class ShowItemModel {

    public static final String CODE_WITH_NAME = "CodeWithName";
    public static final String JSON_ID = "ID";
    public static final String ITEM_CAT_ID = "ItemCatID";
    public static final String ITEM_CAT_NAME = "ItemCatName";
    public static final String ITEM_CODE = "ItemCode";
    public static final String ITEM_DESCRIPTION = "ItemDescription";
    public static final String ITEM_NAME = "ItemName";
    public static final String ITEM_SPAC = "ItemSpac";
    public static final String MAIN_CAT_NAME = "MainCatName";
    public static final String M_CAT_ID = "MCatID";
    public static final String REORDER_LEVEL = "ReOrderLevel";
    public static final String SUB_CAT_ID = "SubCatID";
    public static final String SUB_CAT_NAME = "SubCatName";
    public static final String UNIT_NAME = "UnitName";

    private String codeWithName;
    private String ID;
    private String itemCatID;
    private String itemCatName;
    private String itemCode;
    private String itemDescription;
    private String itemName;
    private String itemSpac;
    private String mainCatName;
    private String mCatID;
    private String reOrderLevel;
    private String subCatID;
    private String subCatName;
    private String unitName;

    public ShowItemModel(){

    }

    public String getCodeWithName() {
        return codeWithName;
    }

    public void setCodeWithName(String codeWithName) {
        this.codeWithName = codeWithName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getItemCatID() {
        return itemCatID;
    }

    public void setItemCatID(String itemCatID) {
        this.itemCatID = itemCatID;
    }

    public String getItemCatName() {
        return itemCatName;
    }

    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpac() {
        return itemSpac;
    }

    public void setItemSpac(String itemSpac) {
        this.itemSpac = itemSpac;
    }

    public String getMainCatName() {
        return mainCatName;
    }

    public void setMainCatName(String mainCatName) {
        this.mainCatName = mainCatName;
    }

    public String getmCatID() {
        return mCatID;
    }

    public void setmCatID(String mCatID) {
        this.mCatID = mCatID;
    }

    public String getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(String reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public String getSubCatID() {
        return subCatID;
    }

    public void setSubCatID(String subCatID) {
        this.subCatID = subCatID;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
