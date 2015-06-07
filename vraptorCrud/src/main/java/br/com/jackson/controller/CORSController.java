package br.com.jackson.controller;

import java.util.Set;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.HttpMethod;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.view.Results;
import br.com.jackson.core.RequestInfo;

@Controller
public class CORSController {

	@Inject
	private Result result;

	@Inject
	private Router router;

	@Inject
	private RequestInfo requestInfo;

	@Options("/*")
	public void options() {
		Set<HttpMethod> allowed = router.allowedMethodsFor(requestInfo.getRequestedUri());
		String allowMethods = allowed.toString().replaceAll("\\[|\\]", "");

		result.use(Results.status()).header("Allow", allowMethods);

		result.use(Results.status()).header("Access-Control-Allow-Methods", allowMethods);

		result.use(Results.status()).header("Access-Control-Allow-Headers", "Content-Type, accept, Authorization, X-Tenant, X-Filial, X-Requested-With, X-Auth-Token, Origin");

		result.use(Results.status()).header("Access-Control-Allow-Origin", "*");
		result.use(Results.status()).header("Access-Control-Allow-Credentials", "false");

		result.use(Results.status()).ok();
	}
}