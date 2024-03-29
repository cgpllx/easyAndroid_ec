/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.easyandroid.easyhttp.core.retrofit;

import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Convert objects to and from their representation as HTTP bodies. Register a
 * converter with Retrofit using
 * {@link Retrofit.Builder#addConverter(Type, Converter)} or
 * {@link Retrofit.Builder#addConverterFactory(Factory)}.
 */
public interface Converter<T> {
	/**
	 * Convert an HTTP response body to a concrete object of the specified type.
	 */
	T fromBody(ResponseBody body) throws IOException;

	/** Convert an object to an appropriate representation for HTTP transport. */
	// RequestBody toBody1(T value);

	interface Factory {
		/**
		 * Create a converter for {@code type}. Returns null if the type cannot
		 * be handled.
		 */
		Converter<?> get(Type type);
	}
}
