package ceshi.handover.scinan.com.huishoubaobigrecycling.api;


import java.util.Map;

import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.BaseResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.DeviceState_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Erweima;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Face_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LunBo_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Lunbo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Version_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result_info;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
 */
public interface APIService {

    /**
     * getLogin()
     *
     * @return
     */
    @POST("api/user/phoneLogin")
    Observable<BaseResult> getLogin();

    @GET("member/get")
    Observable<BaseResult> getUserInfo(@Header("Authorization") String Authorization);

    @FormUrlEncoded
    @POST("api/ad/getAdList")
    Observable<Lunbo> getOneLunBo(@Field("token") String token,
                                  @Field("groupId") String groupId,
                                  @Field("type") String type,
                                  @Field("positions") String positions);

    @GET("index/get_scan")
    Observable<Erweima> getErweima();

    @GET("recovery/get_init")
    Observable<BaseResult> getInitializ(@Header("Authorization") String Authorization, @Query("identity") String identity);

    @GET("recovery/get_status")
    Observable<DeviceState_Info> getDeviceState(@Header("Authorization") String Authorization, @Query("identity") String identity);

    @FormUrlEncoded
    @POST("recovery/edit_open")
    Observable<BaseResult> getOPenBarn(@Header("Authorization") String Authorization, @Field("alias") String alias, @Field("identity") String identity);

    //index/login_face
    @Multipart
    @POST
    Observable<BaseResult> getFace(@Url String url, @PartMap Map<String, RequestBody> files);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("recovery/edit_settle")
    Observable<request_result_info> getRequest(@Header("Authorization") String Authorization, @Body request_result result);

    @FormUrlEncoded
    @POST("recovery/edit_finish")
    Observable<BaseResult> getJieSu(@Header("Authorization") String Authorization, @Field("identity") String identity);

    @GET("appversion/get")
    Observable<Version_Info> getDeviceVersion(@Query("type") String type);

    @FormUrlEncoded
    @POST("recovery/edit_close")
    Observable<BaseResult> getClose(@Header("Authorization") String Authorization, @Field("identity") String identity);

    @FormUrlEncoded
    @POST("sync/edit_page")
    Observable<BaseResult> getSync(@Header("Authorization") String Authorization, @Field("page") String page);


    /*  @FormUrlEncoded
    @POST("member/edit_push")
    Observable <BaseResult> getChangeInfo(@Field("enable_push") boolean enable_push);
    @FormUrlEncoded
    @POST("member/edit_nick")
    Observable <BaseResult> getChangeUsername(@Field("nick") String nick);
    @GET("member/edit_unbind_wx")
    Observable <BaseResult> getUntie();
    @GET("region/get")
    Observable <Province_Info> getAddress(@Query("pid") String pid);
    @FormUrlEncoded
    @POST("member/edit_address")
    Observable <BaseResult> getChangeAddress(@Field("province") String province, @Field("province_id") String province_id, @Field("city") String city, @Field("city_id") String city_id, @Field("area") String area, @Field("area_id") String area_id, @Field("village") String village, @Field("address") String address);
    @FormUrlEncoded
    @POST("index/get_mobile_vertify")
    Observable <BaseResult> getCode(@Field("mobile") String mobile);
    @FormUrlEncoded
    @POST("index/login_mobile")
    Observable <logon_info> getLogin(@Field("mobile") String mobile, @Field("code") String code);
    @GET("money/get_more")
    Observable <DetailTranslation_info> getDetail(@Query("page") int page, @Query("rows") int rows);
    @FormUrlEncoded
    @POST("member/edit_identity")
    Observable <BaseResult> getRenZheng(@Field("name") String name, @Field("idcard") String idcard, @Field("idcard1") String idcard1, @Field("idcard2") String idcard2);
    @GET("money/get_statistics")
    Observable <Mygongyijin_info> getMygongyijin();
    @FormUrlEncoded
    @POST("member/bind_face")
    Observable <BaseResult> getFace(@Field("face_str") String face_str);
    @FormUrlEncoded
    @POST("member/edit_face")
    Observable <BaseResult> getReface(@Field("face_str") String face_str);
    //轮播图
    @GET("sowing/list")
    Observable <Sowing_map> getSowingmap();*/
    /* @FormUrlEncoded
    @POST("sys/login/login")
    Observable <BaseResult> getlogin(@Field("username") String username, @Field("password") String password);

    @GET("sys/manager/getlist")
    Observable <UserManagementInfo> getUser(@Query("page") String page, @Query("rows") String rows);
    @FormUrlEncoded
    @POST("sys/manager/editstatus")
    Observable <BaseResult> getstate(@Field("id") String id, @Field("status") String status);
    @FormUrlEncoded
    @POST("sys/manager/editpassword")
    Observable <BaseResult> getrepassword(@Field("id") String id, @Field("password") String password, @Field("repassword") String repassword);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("sys/manager/adduser")
    Observable<BaseResult> getadduser(@Body AddUser_body user_body);*/
    /*   @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("syapp/v1/login/getAccessTokenAndLoginInfo")
    Observable <Token> gettoken(@Header("accessCode") String accessCode);
    //微信扫码支付
   *//* @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("pc/v1/wxpay/native")
    Observable <weixin_use_bean> getweixin_use(@Header("accessToken") String accessToken, @Body weixin_use_body weixin);*//*
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("pc/v1/wxpay/micropay")
    Observable<weixin_tenant_bean> getwexin_tenant(@Header("accessToken") String accessToken, @Body weixin_tenant_body weinxin);
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("pc/v1/alipay/micropay")
    Observable<zhifubao_tenant_bean> getzhifubao_tenant(@Header("accessToken") String accessToken, @Body zhifubao_tenant_body zhifubao);

    //商户信息查询
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("syapp/v1/merchant/accurateQuery")
    Observable<business_information_bean> getbusiness(@Header("accessToken") String accessToken, @Body business_information_body business);
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pc/v1/statistics/all/{start}/{end}")
    Observable<main_today_bean> gettoday(@Header("accessToken") String accessToken, @Query("start") String start, @Query("end") String end);
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("pc/v1/wxpay/native")
    Observable<weixin_receivable_bean> getweixin_receivable(@Header("accessToken") String accessToken, @Body weixin_receivable_body business);
    @Headers({"Content-Type:application/json"})
    @POST("ocr/bizlicense")
    Observable<business_photos_bean> gettengxun(@Header("Host") String Host, @Header("Authorization") String Authorization, @Body tenxun_body tenxun);
   *//* @Headers({"Content-Type: application/json"})
    @POST("ocr/idcard")
    Observable<tenxun_shen_bean> gettengxun_shen(@Header("Authorization") String Authorization,@Header("Host") String Host,@Body tengxun_shen_body tenxun_shen);*//*
   @POST("ocr/idcard")
   Observable<tenxun_shen_bean> gettengxun_shen(@QueryMap Map<String, RequestBody> params);*/
   /* @GET("http://ip.taobao.com/service/getIpInfo.php")
    Observable<TestResponse> getTestResult(@Query("ip") String keyword);

    @GET("http://203.130.41.104:8050/guizi-app-jiqimao/haier/user/userLoginNew.json")
    Observable<JavaResponse> login_bean(@Query("phone") String name,
                                   @Query("password") String password,
                                   @Query("userType") int userType);

    @GET("http://203.130.41.104:8050/guizi-app-jiqimao/haier/version/findAdvertImg.json?attType=5")
    Observable<JavaResponse<List<Advertisement>>> getBannerImages();*/
}
