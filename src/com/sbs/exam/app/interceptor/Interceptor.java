package com.sbs.exam.app.interceptor;

import com.sbs.exam.app.Rq;

public interface Interceptor {
	boolean run(Rq rq);
}
