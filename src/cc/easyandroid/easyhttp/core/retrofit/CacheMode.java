package cc.easyandroid.easyhttp.core.retrofit;

public interface CacheMode {
	// 网络
	String LOAD_DEFAULT = "load-default";
	// 缓存---->网络
	String LOAD_CACHE_ELSE_NETWORK = "cache-else-network";
	// 网络---->缓存
	String LOAD_NETWORK_ELSE_CACHE = "network-else-cache";
	// 网络
	String LOAD_NETWORK_ONLY = "network-only";
}
