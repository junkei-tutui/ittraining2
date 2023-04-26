package jp.zein.it.training.was.security.session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import jp.zein.it.training.was.security.WasSecurityConsts;

/**
 * 認証トークンをHTTPヘッダーおよびQueryStringから解決
 *
 */
public class HeaderAndQueryStringHttpSessionIdResolver implements HttpSessionIdResolver {

	/**
	 * {@link HeaderHttpSessionIdResolver}
	 */
	private final HeaderHttpSessionIdResolver headerResolver = HeaderHttpSessionIdResolver.xAuthToken();

	@Override
	public List<String> resolveSessionIds(HttpServletRequest request) {
		return Stream.of(
				// 認証トークンをHTTPヘッダーから取得
				headerResolver.resolveSessionIds(request),
				// 認証トークンをQueryStringから取得
				Optional.ofNullable(request.getParameter(WasSecurityConsts.QUERY_STRING_TOKEN)).map(List::of)
						.orElse(List.of()))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
		headerResolver.setSessionId(request, response, sessionId);
	}

	@Override
	public void expireSession(HttpServletRequest request, HttpServletResponse response) {
		headerResolver.expireSession(request, response);
	}

}
