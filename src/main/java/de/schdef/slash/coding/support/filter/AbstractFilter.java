package de.schdef.slash.coding.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.schdef.slash.coding.support.util.Assert;

public abstract class AbstractFilter 
    implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Assert.isTrueState(this.filterConfig == null, "filter already initialized");
        this.filterConfig = filterConfig;
        init();
    }

    protected void init() {
        // hook for subclasses
    }

    protected FilterConfig getFilterConfig() {
        Assert.isTrueState(this.filterConfig != null, "filter not yet initialized");
        return this.filterConfig;
    }

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        doFilter(request, response, chain);
    }

    protected abstract void doFilter(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {
        // hook for subclasses
    }
}