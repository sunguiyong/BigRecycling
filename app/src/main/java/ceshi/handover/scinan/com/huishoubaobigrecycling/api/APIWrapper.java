package ceshi.handover.scinan.com.huishoubaobigrecycling.api;


import java.util.Map;

import ceshi.handover.scinan.com.huishoubaobigrecycling.api.net.RetrofitUtil;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.BaseResult;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.DeviceState_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Erweima;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LunBo_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Lunbo;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.LunboV;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Response;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.VersionApk;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.Version_Info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result;
import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result_info;
import ceshi.handover.scinan.com.huishoubaobigrecycling.exception.ApiException;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;


public class APIWrapper extends RetrofitUtil {

    private static APIWrapper mAPIWrapper;

    public APIWrapper() {
    }

    public static APIWrapper getInstance() {
        if (mAPIWrapper == null) {
            mAPIWrapper = new APIWrapper();
        }
        return mAPIWrapper;
    }

    public Observable<Lunbo> queryOneLunBO(String groupId, String type, String positions) {
        Observable<Lunbo> observable = getAPIService(2).getOneLunBo(groupId, type, positions);
        return observable;
    }

    public Observable<VersionApk> versionNumber(String groupId){
        Observable<VersionApk> observable=getAPIService(2).versionNumber(groupId);
        return observable;
    }
    /**
     * @return
     */
    public Observable<LunboV> getAdList(String groupId, String zoneId) {
        Observable<LunboV> observable = getAPIService(2).getAdList(groupId, zoneId);
        return observable;
    }

    public Observable<Erweima> queryErweima() {
        Observable<Erweima> observable = getAPIService(2).getErweima();
        return observable;
    }

    public Observable<BaseResult> querInitializ(String token, String identity) {
        Observable<BaseResult> observable = getAPIService(2).getInitializ(token, identity);
        return observable;
    }

    public Observable<DeviceState_Info> querDeviceState(String token, String identity) {
        Observable<DeviceState_Info> observable = getAPIService(2).getDeviceState(token, identity);
        return observable;
    }

    public Observable<BaseResult> querOPenBarn(String token, String alias, String identity) {
        Observable<BaseResult> observable = getAPIService(2).getOPenBarn(token, alias, identity);
        return observable;
    }

    public Observable<BaseResult> querFace(String url, Map<String, RequestBody> files) {
        Observable<BaseResult> observable = getAPIService(1).getFace(url, files);
        return observable;
    }

    public Observable<request_result_info> querRequest(String token, request_result alias) {
        Observable<request_result_info> observable = getAPIService(2).getRequest(token, alias);
        return observable;
    }

    public Observable<BaseResult> querJieSu(String token, String biaoshi) {
        Observable<BaseResult> observable = getAPIService(2).getJieSu(token, biaoshi);
        return observable;
    }

    public Observable<BaseResult> querUserInfo(String token) {
        Observable<BaseResult> observable = getAPIService(2).getUserInfo(token);
        return observable;
    }

    public Observable<Version_Info> querVersion(String token) {
        Observable<Version_Info> observable = getAPIService(2).getDeviceVersion(token);
        return observable;
    }

    public Observable<BaseResult> querClose(String token, String identity) {
        Observable<BaseResult> observable = getAPIService(2).getClose(token, identity);
        return observable;
    }

    public Observable<BaseResult> querSync(String token, String identity) {
        Observable<BaseResult> observable = getAPIService(2).getSync(token, identity);
        return observable;
    }
   /* public Observable <BaseResult> querylogin(String username,String password) {
        Observable <BaseResult> observable = getAPIService(1).getlogin(username,password);
        return observable;
    }
    public Observable <UserManagementInfo> queryUser(String page, String rows) {
        Observable <UserManagementInfo> observable = getAPIService(1).getUser(page,rows);
        return observable;
    }
    public Observable <BaseResult> querystate(String username,String password) {
        Observable <BaseResult> observable = getAPIService(1).getstate(username,password);
        return observable;
    }
    public Observable <BaseResult> queryrepassword(String username,String password,String repassword) {
        Observable <BaseResult> observable = getAPIService(1).getrepassword(username,password,repassword);
        return observable;
    }
    public Observable <BaseResult> queryadduser(AddUser_body addUser_body) {
        Observable <BaseResult> observable = getAPIService(1).getadduser(addUser_body);
        return observable;
    }*/
   /* public Observable <Token> queryToken(String accessCode) {
        Observable <Token> observable = getAPIService().gettoken(accessCode);
        return observable;
    }
   *//* public Observable <weixin_use_bean> queryweixin_use(String accessToken, weixin_use_body weixin) {
        Observable<weixin_use_bean> observable = getAPIService().getweixin_use(accessToken,weixin);
        return observable;
    }*//*
    public Observable <weixin_tenant_bean> queryweixin_tenant(String accessToken, weixin_tenant_body weixin_tenant) {
        Observable<weixin_tenant_bean> observable = getAPIService().getwexin_tenant(accessToken,weixin_tenant);
        return observable;
    }
    public Observable <zhifubao_tenant_bean> queryzhifubao_tenant(String accessToken, zhifubao_tenant_body weixin_tenant) {
        Observable<zhifubao_tenant_bean> observable = getAPIService().getzhifubao_tenant(accessToken,weixin_tenant);
        return observable;
    }
    public Observable <business_information_bean> querybusiness(String accessToken, business_information_body business) {
        Observable<business_information_bean> observable = getAPIService().getbusiness(accessToken,business);
        return observable;
    }
    public Observable <main_today_bean> querytoday(String accessToken, String start,String end) {
        Observable<main_today_bean> observable = getAPIService().gettoday(accessToken,start,end);
        return observable;
    }
    public Observable <weixin_receivable_bean> queryweixin_receivable(String accessToken, weixin_receivable_body weixin_recevable) {
        Observable<weixin_receivable_bean> observable = getAPIService().getweixin_receivable(accessToken,weixin_recevable);
        return observable;
    }

*//*
    public Observable<TestResponse> queryTestLookUp(String keyword) {
        Observable<TestResponse> observable = getAPIService().getTestResult(keyword);
        return observable;
    }

    public Observable<JavaResponse> login_bean(String name, String password) {
        Observable<JavaResponse> observable = getAPIService().login_bean(name, Util.getMD5Text(password),4);
        return observable;
    }

    public Observable<JavaResponse<List<Advertisement>>> getBannerImages() {
        Observable<JavaResponse<List<Advertisement>>> observable = getAPIService().getBannerImages();
        return observable;
    }*//*

    public  <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
*/

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class ResponseFunc<T> implements Func1<Response<T>, T> {

        @Override
        public T call(Response<T> response) {
            if (response.total == 0) {
                throw new ApiException(100);
            }
            return response.result;
        }
    }
}
