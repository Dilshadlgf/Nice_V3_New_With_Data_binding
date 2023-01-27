package com.example.testproject.database.convertor;

import androidx.room.TypeConverter;

import com.example.testproject.model.AIDLocationResponseDataModel;
import com.example.testproject.model.ActiveKnowledgeDomainResponseDataModel;
import com.example.testproject.model.AidCategoryDataModel;
import com.example.testproject.model.ContentModel;
import com.example.testproject.model.CreatedByFarmer;
import com.example.testproject.model.CreatedModel;
import com.example.testproject.model.CropDataModel;
import com.example.testproject.model.CropRef;
import com.example.testproject.model.CropSeasonDataModel;
import com.example.testproject.model.DataModel;
import com.example.testproject.model.DataModel2;
import com.example.testproject.model.DataModelTwo;
import com.example.testproject.model.FarmerListDataModel;
import com.example.testproject.model.FarmerListModel;
import com.example.testproject.model.FarmerOrg;
import com.example.testproject.model.FarmerRefModel;
import com.example.testproject.model.Farmerlistnewdatamodel;
import com.example.testproject.model.GeneralResponseModel;
import com.example.testproject.model.InterCropModel;
import com.example.testproject.model.KnowledgeDomainDataModel;
import com.example.testproject.model.LivestocksArrayModel;
import com.example.testproject.model.LocationDataModel;
import com.example.testproject.model.Pagination;
import com.example.testproject.model.Pagination1;
import com.example.testproject.model.Productconfig;
import com.example.testproject.model.RoleModel;
import com.example.testproject.model.RootOneModel;
import com.example.testproject.model.RootOneResModel;
import com.example.testproject.model.RootTwoModel;
import com.example.testproject.model.RootTwoResModel;
import com.example.testproject.model.SeasonDataModel;
import com.example.testproject.model.SubDomainDataModel;
import com.example.testproject.model.livestock.LiveStockDataModel;
import com.example.testproject.model.livestock.RootLiveStockResponse;
import com.example.testproject.model.livestock.VarietyModel;
import com.example.testproject.model.query.AccessPrivilegeModel;
import com.example.testproject.model.query.AssignedToModel;
import com.example.testproject.model.query.ChatborddataModel;
import com.example.testproject.model.query.ChatbotqueryModel;
import com.example.testproject.model.query.ChatbotquerydataModel;
import com.example.testproject.model.query.chatbotresponseModel;
import com.example.testproject.model.stagemodel;
import com.example.testproject.model.varietymodel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoleConvertor {

    @TypeConverter
    public static RoleModel fromRoleModelToObject(String value) {
        Type listType = new TypeToken<RoleModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRoleModel(RoleModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static RootOneResModel fromRootOneResModelToObject(String value) {
        Type listType = new TypeToken<RootOneResModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRootOneResModel(RootOneResModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static RootTwoResModel fromRootTwoResModelToObject(String value) {
        Type listType = new TypeToken<RootTwoResModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRootTwoResModel(RootTwoResModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static DataModel2 fromDataModel2ToObject(String value) {
        Type listType = new TypeToken<DataModel2>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToDataModel2(DataModel2 list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static DataModelTwo fromDataModelTwoToObject(String value) {
        Type listType = new TypeToken<DataModelTwo>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToDataModelTwo(DataModelTwo list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static varietymodel fromvarietymodelToObject(String value) {
        Type listType = new TypeToken<varietymodel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectTovarietymodel(varietymodel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public static LivestocksArrayModel fromLivestocksArrayModelToObject(String value) {
        Type listType = new TypeToken<LivestocksArrayModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToLivestocksArrayModel(LivestocksArrayModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
  @TypeConverter
    public static stagemodel fromstagemodelToObject(String value) {
        Type listType = new TypeToken<stagemodel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectTostagemodel(stagemodel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static CropDataModel fromCropDataModelToObject(String value) {
        Type listType = new TypeToken<CropDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToCropDataModel(CropDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static CropRef fromCropRefToObject(String value) {
        Type listType = new TypeToken<CropRef>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToCropCropRef(CropRef list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static InterCropModel fromInterCropModelToObject(String value) {
        Type listType = new TypeToken<InterCropModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToInterCropModel(InterCropModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static SeasonDataModel fromSeasonDataModelToObject(String value) {
        Type listType = new TypeToken<SeasonDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToSeasonDataModel(SeasonDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static FarmerOrg fromFarmerOrgToObject(String value) {
        Type listType = new TypeToken<FarmerOrg>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToFarmerOrg(FarmerOrg list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static CreatedByFarmer fromCreatedByFarmerToObject(String value) {
        Type listType = new TypeToken<CreatedByFarmer>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToCreatedByFarmer(CreatedByFarmer list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static LiveStockDataModel fromLiveStockDataModelToObject(String value) {
        Type listType = new TypeToken<LiveStockDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToLiveStockDataModel(LiveStockDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static RootLiveStockResponse fromRootLiveStockResponseToObject(String value) {
        Type listType = new TypeToken<RootLiveStockResponse>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRootLiveStockResponse(RootLiveStockResponse list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static VarietyModel fromVarietyModelToObject(String value) {
        Type listType = new TypeToken<VarietyModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToVarietyModel(VarietyModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static AccessPrivilegeModel fromAccessPrivilegeModelToObject(String value) {
        Type listType = new TypeToken<AccessPrivilegeModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToAccessPrivilegeModel(AccessPrivilegeModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static AssignedToModel fromAssignedToModelToObject(String value) {
        Type listType = new TypeToken<AssignedToModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToAssignedToModel(AssignedToModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static ChatborddataModel fromChatborddataModelToObject(String value) {
        Type listType = new TypeToken<ChatborddataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToChatborddataModel(ChatborddataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static ChatbotquerydataModel fromChatbotquerydataModelToObject(String value) {
        Type listType = new TypeToken<ChatborddataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToChatbotquerydataModel(ChatbotquerydataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static ChatbotqueryModel fromChatbotqueryModelToObject(String value) {
        Type listType = new TypeToken<ChatbotqueryModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToChatbotqueryModel(ChatbotqueryModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static chatbotresponseModel fromchatbotresponseModellToObject(String value) {
        Type listType = new TypeToken<ChatbotqueryModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectTochatbotresponseModel(chatbotresponseModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static ActiveKnowledgeDomainResponseDataModel fromActiveKnowledgeDomainResponseDataModelToObject(String value) {
        Type listType = new TypeToken<ActiveKnowledgeDomainResponseDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToActiveKnowledgeDomainResponseDataModel(ActiveKnowledgeDomainResponseDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static AidCategoryDataModel fromAidCategoryDataModelToObject(String value) {
        Type listType = new TypeToken<AidCategoryDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToAidCategoryDataModel(AidCategoryDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static AIDLocationResponseDataModel fromAIDLocationResponseDataModelToObject(String value) {
        Type listType = new TypeToken<AIDLocationResponseDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToAIDLocationResponseDataModel(AIDLocationResponseDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static ContentModel fromContentModelToObject(String value) {
        Type listType = new TypeToken<ContentModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToContentModel(ContentModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static CreatedModel fromCreatedModelToObject(String value) {
        Type listType = new TypeToken<CreatedModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToCreatedModel(CreatedModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static CropSeasonDataModel fromCropSeasonDataModelToObject(String value) {
        Type listType = new TypeToken<CropSeasonDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToCropSeasonDataModel(CropSeasonDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static DataModel fromDataModelToObject(String value) {
        Type listType = new TypeToken<DataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToDataModel(DataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static FarmerListDataModel fromFarmerListDataModelToObject(String value) {
        Type listType = new TypeToken<FarmerListDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToFarmerListDataModel(FarmerListDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static FarmerListModel fromFarmerListModelToObject(String value) {
        Type listType = new TypeToken<FarmerListModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToFarmerListModel(FarmerListModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static Farmerlistnewdatamodel fromFarmerlistnewdatamodelToObject(String value) {
        Type listType = new TypeToken<Farmerlistnewdatamodel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToFarmerlistnewdatamodel(Farmerlistnewdatamodel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static GeneralResponseModel fromGeneralResponseModelToObject(String value) {
        Type listType = new TypeToken<GeneralResponseModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToGeneralResponseModel(GeneralResponseModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static KnowledgeDomainDataModel fromKnowledgeDomainDataModelToObject(String value) {
        Type listType = new TypeToken<KnowledgeDomainDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToKnowledgeDomainDataModel(KnowledgeDomainDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static LocationDataModel fromLocationDataModelToObject(String value) {
        Type listType = new TypeToken<LocationDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToLocationDataModel(LocationDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static Pagination fromPaginationToObject(String value) {
        Type listType = new TypeToken<Pagination>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToPagination(Pagination list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static Pagination1 fromPagination1ToObject(String value) {
        Type listType = new TypeToken<Pagination1>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToPagination1(Pagination1 list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static Productconfig fromProductconfigToObject(String value) {
        Type listType = new TypeToken<Productconfig>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToProductconfig(Productconfig list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static RootOneModel fromRootOneModelToObject(String value) {
        Type listType = new TypeToken<RootOneModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRootOneModel(RootOneModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static RootTwoModel fromRootTwoModelToObject(String value) {
        Type listType = new TypeToken<RootTwoModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToRootTwoModel(RootTwoModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
@TypeConverter
    public static SubDomainDataModel fromSubDomainDataModelToObject(String value) {
        Type listType = new TypeToken<SubDomainDataModel>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String fromObjectToSubDomainDataModel(SubDomainDataModel list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


}
