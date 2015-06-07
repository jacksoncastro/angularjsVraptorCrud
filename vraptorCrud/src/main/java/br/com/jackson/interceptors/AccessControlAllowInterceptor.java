package br.com.jackson.interceptors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.HttpMethod;

@Intercepts
public class AccessControlAllowInterceptor {

	@Inject
	private HttpServletResponse response;

	@Inject
	private HttpServletRequest request;

    @BeforeCall
    public void intercept() {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "false");
    }

    @Accepts
    public boolean accepts(ControllerMethod method) {
        return !HttpMethod.OPTIONS.toString().equals(request.getMethod());
    }
}