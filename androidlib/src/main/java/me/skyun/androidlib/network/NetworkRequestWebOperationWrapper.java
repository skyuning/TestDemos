package me.skyun.androidlib.network;

/**
 * Created by linyun on 15-7-31.
 * <p/>
 * 将NetowrkRequest包装成WebOperation
 */
//class NetworkRequestWebOperationWrapper extends WebOperation {
//
//    private INetWorkRequest mNetWorkRequest;
//
//    public NetworkRequestWebOperationWrapper(INetWorkRequest netWorkRequest) {
//        super(null);
//        mNetWorkRequest = netWorkRequest;
//    }
//
//    @Override
//    public String buildUrlQuery() {
//        String url = mNetWorkRequest.getUrl();
//        if (getMethod() == G7HttpMethod.GET)
//            url += buildQueryString();
//        return url;
//    }
//
//    @Override
//    protected G7HttpMethod getMethod() {
//        ReqAttr attr = mNetWorkRequest.getClass().getAnnotation(ReqAttr.class);
//        return attr.method();
//    }
//
//    private String buildQueryString() {
//        String[] params = mNetWorkRequest.getParams();
//        if (params == null || params.length == 0)
//            return "";
//
//        String queryString = "?" + params[0] + "=" + params[1];
//        for (int i = 2; i < params.length; i += 2) {
//            queryString += params[i] + "=" + params[i + 1];
//        }
//        return queryString;
//    }
//
//    @Override
//    protected String[] getPostData() {
//        if (getMethod() == G7HttpMethod.POST)
//            return mNetWorkRequest.getParams();
//        else
//            return new String[0];
//    }
//
//    @Override
//    public void onRequestReturn(G7HttpResponse response) {
//        CYEventBus.getDefault().post(response);
//    }
//}
