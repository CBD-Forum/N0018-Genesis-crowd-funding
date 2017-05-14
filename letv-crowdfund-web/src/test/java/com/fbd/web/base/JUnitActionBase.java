package com.fbd.web.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:fbd/*/spring/springmvc-servlet.xml" })
public class JUnitActionBase  {

    /**
     * default http port.
     */
    private static final int DEFAULT_PORT = 8080;

    /**
     * DefaultAnnotationHandlerMapping.
     */
    protected static HandlerMapping handlerMapping;
    /**
     * AnnotationMethodHandlerAdapter.
     */
    protected static HandlerAdapter handlerAdapter;

    /**
     * 读取配置文件
     */
    @BeforeClass
    public static void setUp() {
        if (handlerMapping == null) {
            String[] configs = { "classpath*:fbd/core/spring/springmvc-servlet.xml","classpath*:fbd/*/spring/applicationContext*.xml" };
            XmlWebApplicationContext context = new XmlWebApplicationContext();
            context.setConfigLocations(configs);
            MockServletContext msc = new MockServletContext();
            context.setServletContext(msc);
            context.refresh();
            msc.setAttribute(
                    WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                    context);
            
            handlerMapping = (HandlerMapping) context.getBean(RequestMappingHandlerMapping.class);

            handlerAdapter = (HandlerAdapter) context
                    .getBean(context.getBeanNamesForType(RequestMappingHandlerAdapter.class)[0]);

        }
    }

    /**
     * Simulate Request to URL appoint by MockHttpServletRequest.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     *             runtimeException
     */
    public final ModelAndView excuteAction(final HttpServletRequest request,final HttpServletResponse response) throws Exception {
        // 这里需要声明request的实际类型，否则会报错  
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true); 
        HandlerExecutionChain chain = this.handlerMapping.getHandler(request);
        final ModelAndView model = this.handlerAdapter.handle(request,
                response, chain.getHandler());
        return model;
    }

    /**
     * Simulate Request to URL appoint by MockHttpServletRequest, default POST,
     * port 80.
     * 
     * @param url
     *            requestURL
     * @param objects
     *            parameters
     * @return ModelAndView
     */
    public final ModelAndView excuteAction(final String url,
            final Object[]... objects) {
        return this.excuteAction("POST", url, JUnitActionBase.DEFAULT_PORT,
                objects);
    }

    /**
     * Simulate Request to URL appoint by MockHttpServletRequest, default POST.
     * 
     * @param url
     *            requestURL
     * @param port
     *            int
     * @param objects
     *            parameters
     * @return ModelAndView
     */
    public final ModelAndView excuteAction(final String url, final int port,
            final Object[]... objects) {
        return this.excuteAction("POST", url, port, objects);
    }

    /**
     * Simulate Request to URL appoint by MockHttpServletRequest.
     * 
     * @param method
     *            POST/GET
     * @param url
     *            requestURL
     * @param port
     *            int
     * @param objects
     *            parameters
     * @return ModelAndView
     */
    public final ModelAndView excuteAction(final String method,
            final String url, final int port, final Object[]... objects) {
        MockHttpServletRequest request = new MockHttpServletRequest(method, url);
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServerPort(port);
        request.setLocalPort(port);
        if (objects != null) {
            for (Object[] object : objects) {
                if (object != null && object.length == 2) {
                    request.addParameter(object[0].toString(),
                            object[1].toString());
                }
            }
        }
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        try {
            return this.excuteAction(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // InfoLogUtil.error(e.toString());
        }
        return null;
    }
}