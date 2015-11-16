package cc.easyandroid.easyhttp;

import java.lang.reflect.Type;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import rx.Observable;
import cc.easyandroid.easycache.volleycache.DiskBasedCache;
import cc.easyandroid.easyhttp.config.EAConfiguration;
import cc.easyandroid.easyhttp.core.EAOkHttpCall;
import cc.easyandroid.easyhttp.core.OkHttpDownloadUtils;
import cc.easyandroid.easyhttp.core.OkHttpGetUtils;
import cc.easyandroid.easyhttp.core.OkHttpPostUtils;
import cc.easyandroid.easyhttp.core.OkHttpUpLoadUtil;
import cc.easyandroid.easyhttp.core.retrofit.Call;
import cc.easyandroid.easyhttp.core.retrofit.Converter;
import cc.easyandroid.easyhttp.core.retrofit.EACallAdapterFactory.SimpleCallAdapter;
import cc.easyandroid.easyhttp.core.retrofit.GsonConverterFactory;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public class EasyHttpUtils {
	private static final EasyHttpUtils mInstance = new EasyHttpUtils();
	private OkHttpClient mOkHttpClient;
	public Gson mGson;

	private EasyHttpUtils() {
	}

	public static EasyHttpUtils getInstance() {
		return mInstance;
	}

	private DiskBasedCache cache;

	public void init(EAConfiguration config) {
		if (config == null) {
			new IllegalArgumentException("EAConfiguration config 不能为null");
		}
		cache = config.getCache();
		mGson = config.getGson();
		mOkHttpClient = config.getOkHttpClient();
		// cookie enabled
		mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}

	private OkHttpGetUtils okHttpGetUtils;
	private OkHttpPostUtils okHttpPostUtils;
	private OkHttpUpLoadUtil okHttpUpLoadUtil;
	private OkHttpDownloadUtils okHttpDownloadUtils;

	public OkHttpPostUtils getOkHttpPostUtils() {
		if (okHttpPostUtils == null) {
			synchronized (EasyHttpUtils.class) {
				if (okHttpPostUtils == null) {
					okHttpPostUtils = new OkHttpPostUtils(mOkHttpClient);
				}
			}
		}
		return okHttpPostUtils;
	}

	public OkHttpUpLoadUtil getOkHttpUpLoadUtil() {
		if (okHttpUpLoadUtil == null) {
			synchronized (EasyHttpUtils.class) {
				if (okHttpUpLoadUtil == null) {
					okHttpUpLoadUtil = new OkHttpUpLoadUtil(mOkHttpClient);
				}
			}
		}
		return okHttpUpLoadUtil;
	}

	public OkHttpDownloadUtils getOkHttpDownloadUtils() {
		if (okHttpDownloadUtils == null) {
			synchronized (EasyHttpUtils.class) {
				if (okHttpDownloadUtils == null) {
					okHttpDownloadUtils = new OkHttpDownloadUtils(mOkHttpClient);
				}
			}
		}
		return okHttpDownloadUtils;
	}

	public OkHttpGetUtils getOkHttpGetUtils() {
		if (okHttpGetUtils == null) {
			synchronized (EasyHttpUtils.class) {
				if (okHttpGetUtils == null) {
					okHttpGetUtils = new OkHttpGetUtils(mOkHttpClient);
				}
			}
		}
		return okHttpGetUtils;
	}

	public OkHttpClient getOkHttpClient() {
		return mOkHttpClient;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Observable<T> executeHttpRequestToObservable(Request request, Type type) {
		checkNull(mOkHttpClient);
		Converter responseConverter = getConverterFactory().get(type);
		Call<T> call = new EAOkHttpCall<T>(mOkHttpClient, responseConverter, request);

		SimpleCallAdapter<T> simpleCallAdapter = new SimpleCallAdapter<T>(type);

		Observable<T> observable = simpleCallAdapter.adapt(call);

		return observable;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Call<T> executeHttpRequestToCall(Request request, Type type) {
		checkNull(mOkHttpClient);
		Converter responseConverter = getConverterFactory().get(type);
		Call<T> call = new EAOkHttpCall<T>(mOkHttpClient, responseConverter, request);
		
		 
		
		return call;
	}

	GsonConverterFactory converterFactory;// =KGsonConverterFactory.create(mGson,
											// cache);

	public GsonConverterFactory getConverterFactory() {
		if (converterFactory == null) {
			synchronized (EasyHttpUtils.class) {
				if (converterFactory == null) {
					checkNull(mGson);
					checkNull(cache);
					converterFactory = GsonConverterFactory.create(mGson, cache);
				}
			}
		}
		return converterFactory;
	}

	private void checkNull(Object object) {
		if (object == null) {
			new IllegalArgumentException("请先初始化EasyHttpUtils");
		}
	}
}
