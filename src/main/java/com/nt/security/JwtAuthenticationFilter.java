package com.nt.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserServiceDetails userServ;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader=request.getHeader("Authorization");
		System.out.println("Servlet Path: " + request.getServletPath());
		String username=null;
		String jwttoken=null;
		if (request.getServletPath().startsWith("/user/login")
				||request.getServletPath().startsWith("/admin/login")
				||request.getServletPath().startsWith("/blog/allblogs")
			    || request.getServletPath().startsWith("/user/register")
			    || request.getServletPath().startsWith("/swagger")
			    || request.getServletPath().startsWith("/v3/api-docs")
			    || request.getServletPath().startsWith("/swagger-ui")
			    || request.getServletPath().startsWith("/swagger-resources")
			    || request.getServletPath().startsWith("/webjars")
			    || request.getServletPath().startsWith("/actuator")) {
			    filterChain.doFilter(request, response);
			    return;
			}
		if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
			jwttoken=authHeader.substring(7);
			try {
				username=jwtUtil.extractUsername(jwttoken);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails=userServ.loadUserByUsername(username);
			 if (jwtUtil.validateToken(jwttoken, userDetails)) {
	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                System.out.println("Authenticated: " + userDetails.getUsername());
	                System.out.println("Authorities: " + userDetails.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
		}
		
		filterChain.doFilter(request, response);
	}



}
