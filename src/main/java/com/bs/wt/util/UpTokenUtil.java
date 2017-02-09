package com.bs.wt.util;

import com.qiniu.util.Auth;

public class UpTokenUtil {
	static final String BUCKET = "bsfit";

	public static String genUpToken(String ak, String sk) {
		//Zone z = Zone.autoZone();
		//Configuration c = new Configuration(z);

		//UploadManager uploadManager = new UploadManager(c);
		Auth auth = Auth.create(ak, sk);
		String token = auth.uploadToken(BUCKET);
		return token;
	}
}
