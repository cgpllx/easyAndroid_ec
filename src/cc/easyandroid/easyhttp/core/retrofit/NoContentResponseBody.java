/*
 * Copyright (C) 2015 Square, Inc.
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

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import okio.BufferedSource;

final class NoContentResponseBody extends ResponseBody {
	private final MediaType contentType;
	private final long contentLength;

	NoContentResponseBody(MediaType contentType, long contentLength) {
		this.contentType = contentType;
		this.contentLength = contentLength;
	}

	@Override
	public MediaType contentType() {
		return contentType;
	}

	@Override
	public long contentLength() throws IOException {
		return contentLength;
	}

	@Override
	public BufferedSource source() throws IOException {
		throw new IllegalStateException("Cannot read raw response body of a converted body.");
	}
}
