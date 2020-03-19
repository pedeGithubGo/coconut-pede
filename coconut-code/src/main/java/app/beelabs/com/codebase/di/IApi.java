package app.beelabs.com.codebase.di;


import android.content.Context;

import okhttp3.Interceptor;

/**
 * Created by arysuryawan on 8/21/17.
 */

public interface IApi {
    Object getApiService(String apiDomain, boolean allowUntrusted, Class<IApiService> clazz, int timeout);

    Object getApiService(Interceptor interceptor, String apiDomain, boolean allowUntrusted, Class clazz, int timeout);
}
