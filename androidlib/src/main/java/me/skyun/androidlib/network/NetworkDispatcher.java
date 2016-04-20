//package me.skyun.androidlib.network;
//
//import android.content.Context;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONObject;
//import org.simple.eventbus.EventBus;
//import org.simple.eventbus.Subscriber;
//
//import java.lang.reflect.InvocationTargetException;
//
///**
// * Created by linyun on 15-6-15.
// */
//public class NetworkDispatcher {
//
//    private Context mContext;
//    private RequestQueue mRequestQueue;
////    private DebugResponseProcessor mResponseProcessor;
//
//    public NetworkDispatcher(Context context) {
//        mContext = context;
//        mRequestQueue = Volley.newRequestQueue(mContext);
//
////        if (AppConf.isOnTest()) {
////            mResponseProcessor = new DebugResponseProcessor(mContext);
////            CYEventBus.getDefault().register(mResponseProcessor);
////        }
//    }
//
//    @Subscriber
//    private void onRequest(NetworkRequestEvent event) {
//        try {
//            event.onPreRequest();
//            dispatchRequest(event);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void dispatchRequest(final NetworkRequestEvent requestEvent)
//        throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//
//        INetworkRequest request = requestEvent.getRequest();
//        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                EventBus.getDefault().post(response);
//            }
//        };
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                EventBus.getDefault().post(error.getCause());
//            }
//        };
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, request.getUrl(), listener, errorListener);
//        mRequestQueue.add(jsonObjReq);
//    }
//}
//
