package com.backend.event.management.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter {

	public CORSFilter() {
		System.out.println("Object Created: CORSFilter");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init method : object created");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter method: object created");

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		System.out.println("CORS Filter Http Request : ");

		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",
				"GET, OPTIONS, HEAD, PUT, POST, DELETE");
		((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");

		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, X-Requested-With");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Max-Age", "3600");

		HttpServletResponse resp = (HttpServletResponse) servletResponse;

		if (request.getMethod().equals("OPTIONS")) {
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
		chain.doFilter(request, servletResponse);
	}

	@Override
	public void destroy() {
		System.out.println("CORS Destroy Method :Object Created");
	}
}
