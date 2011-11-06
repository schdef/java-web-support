package de.schdef.slash.coding.support.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.schdef.slash.coding.support.http.HttpBufferedRequestWrapper;
import de.schdef.slash.coding.support.http.HttpBufferedResponseWrapper;


public class LoggingRequestResponseWrappingFilter
    extends AbstractFilter {

    private static final Logger LOG = LoggerFactory
            .getLogger(LoggingRequestResponseWrappingFilter.class);

    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long startTime = System.currentTimeMillis();
        Map<String, String> requestMap = this
                .getTypesafeRequestMap(request);
        HttpBufferedRequestWrapper bufferedReqest = new HttpBufferedRequestWrapper(
                request);
        HttpBufferedResponseWrapper bufferedResponse = new HttpBufferedResponseWrapper(response);

        final String logMessage = new StringBuilder("Request - ")
                .append("[HTTP METHOD:").append(request.getMethod())
                .append("] [PATH INFO:")
                .append(request.getPathInfo())
                .append("] [REQUEST PARAMETERS:").append(requestMap)
                .append("] [REQUEST BODY:")
                .append(bufferedReqest.getRequestBody())
                .append("] [REMOTE ADDRESS:")
                .append(request.getRemoteAddr()).append("]")
                .toString();

        StringBuilder initLogMessage = new StringBuilder(logMessage)
                .append(" - is being processed... ");
        LOG.info(initLogMessage.toString());

        chain.doFilter(bufferedReqest, bufferedResponse);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        StringBuilder successLogMessage = new StringBuilder(logMessage)
                .append(" - processed succesfully in ").append(totalTime)
                .append("ms ");

        LOG.info(successLogMessage.toString());
        LOG.info("RESPONSE: " + bufferedResponse.getContentAsString());
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();

        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }

        return typesafeRequestMap;
    }

}