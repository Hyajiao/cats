//*********************************************************************
//系统名					medlive
//子系统名				cas 单点登录
//class名					AuthenticationFilter
//主要功能				认证 过滤器
//
//=================================================
//version          变更日          		变更者         变更内容
//-------------------------------------------------------------------------------------
//1.00             2012.08.26     		刘领娣         作成
//*********************************************************************
package org.jasig.cas.client.authentication;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter extends AbstractCasFilter {
	private final static String[] spiders = { "Googlebot", "msnbot", "Baiduspider", "bingbot", "Sogou web spider",
			"Sogou inst spider", "Sogou Pic Spider", "JikeSpider", "Sosospider", "Slurp", "360Spider", "YodaoBot",
			"OutfoxBot", "fast-webcrawler", "lycos_spider", "scooter", "ia_archiver", };

    private final static String[] staticResource = { ".css", ".js", ".png", ".jpeg", ".bmp", ".jpg",  };

	/**
	 * The URL to the CAS Server login.
	 */
	private String casServerLoginUrl;

	/**
	 * Whether to send the renew request or not.
	 */
	private boolean renew = false;

	/**
	 * Whether to send the gateway request or not.
	 */
	private boolean gateway = false;

	private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

	protected void initInternal(final FilterConfig filterConfig) throws ServletException {
		if (!isIgnoreInitConfiguration()) {
			super.initInternal(filterConfig);
			setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
			log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
			setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
			log.trace("Loaded renew parameter: " + this.renew);
			setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
			log.trace("Loaded gateway parameter: " + this.gateway);

			final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

			if (gatewayStorageClass != null) {
				try {
					this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
				} catch (final Exception e) {
					log.error(e, e);
					throw new ServletException(e);
				}
			}
		}
	}

	public void init() {
		super.init();
		CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
	}

	public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpSession session = request.getSession(false);
		final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

		if (assertion != null) {
			filterChain.doFilter(request, response);
			return;
		}

		final String serviceUrl = constructServiceUrl(request, response);

		/** 2012-08-26 liuld upd begin 验证问题 */
		if (serviceUrl.indexOf("/admin/") > 0 || serviceUrl.indexOf("/api/") > 0
				|| serviceUrl.indexOf("/static/") > 0 || serviceUrl.indexOf("favicon.ico") > 0
                || serviceUrl.indexOf("/test/") > 0
				) {
			// 后台管理/admin/、api、ajax请求、static资源，不参与cas 认证过滤
			filterChain.doFilter(request, response);
			return;
		}

		if (request.getMethod().equalsIgnoreCase("POST")) {
			filterChain.doFilter(request, response);
			return;
		}

		boolean is_spider = isRequestFromSpider(request);
		if (is_spider) {
			filterChain.doFilter(request, response);
			return;
		}
		/** 2012-08-26 liuld upd end */

		final String ticket = CommonUtils.safeGetParameter(request, getArtifactParameterName());
		final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

		if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
			filterChain.doFilter(request, response);
			return;
		}

		final String modifiedServiceUrl;

		log.debug("no ticket and no assertion found");
		if (this.gateway) {
			log.debug("setting gateway attribute in session");
			modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
		} else {
			modifiedServiceUrl = serviceUrl;
		}

		if (log.isDebugEnabled()) {
			log.debug("Constructed service url: " + modifiedServiceUrl);
		}

		final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl,
				getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

		if (log.isDebugEnabled()) {
			log.debug("redirecting to \"" + urlToRedirectTo + "\"");
		}

		// UPDATE 修改跳转状态码为301跳转，为了百度统计提升  20160122 START
//		response.sendRedirect(urlToRedirectTo);
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", response.encodeRedirectURL(urlToRedirectTo));
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Expires", "Thu, 19 Nov 1981 08:52:00 GMT");
		// UPDATE 修改跳转状态码为301跳转，为了百度统计提升  20160122 END
	}

	/**
	 * 判断是否是爬虫的访问请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isRequestFromSpider(HttpServletRequest request) {
		boolean is_spider = false;
		String user_agent = request.getHeader("User-Agent");
		if (user_agent != null && user_agent.trim().length() > 0) {
			user_agent = user_agent.trim().toLowerCase();
			for (String spider : spiders) {
				if (user_agent.indexOf(spider.toLowerCase()) >= 0) {
					is_spider = true;
					break;
				}
			}
		}
		return is_spider;
	}


	public final void setRenew(final boolean renew) {
		this.renew = renew;
	}

	public final void setGateway(final boolean gateway) {
		this.gateway = gateway;
	}

	public final void setCasServerLoginUrl(final String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
		this.gatewayStorage = gatewayStorage;
	}
}
