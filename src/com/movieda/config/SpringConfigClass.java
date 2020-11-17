package com.movieda.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//public class SpringConfigClass implements WebApplicationInitializer{
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		System.out.println("onStartup");
//		// Spring MVC 占쏙옙占쏙옙占쏙옙트 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쌜쇽옙占싹댐옙 클占쏙옙占쏙옙占쏙옙 占쏙옙체占쏙옙 占쏙옙占쏙옙占싼댐옙.
//		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
//		servletAppContext.register(ServletAppContext.class);
//		
//		// 占쏙옙청 占쌩삼옙 占쏙옙 占쏙옙청占쏙옙 처占쏙옙占싹댐옙 占쏙옙占쏙옙占쏙옙 DispatcherServlet占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쌔댐옙.
//		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
//		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
//		
//		// 占싸곤옙 占쏙옙占쏙옙
//		servlet.setLoadOnStartup(1);
//		servlet.addMapping("/");
//		
//		// Bean占쏙옙 占쏙옙占쏙옙占싹댐옙 클占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙
//		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
//		rootAppContext.register(RootAppContext.class);
//		
//		// 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
//		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
//		servletContext.addListener(listener);
//		
//		// 占식띰옙占쏙옙占� 占쏙옙占쌘듸옙 占쏙옙占쏙옙
//		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
//		filter.setInitParameter("encoding", "UTF-8");
//		filter.addMappingForServletNames(null, false, "dispatcher");
//		
//		
//	}
//
//}

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	// DispatcherServlet占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙청 占쌍소몌옙 占쏙옙占쏙옙占싼댐옙.
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}

	// Spring MVC 占쏙옙占쏙옙占쏙옙트 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 클占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletAppContext.class};
	}
	// 占쏙옙占쏙옙占쏙옙트占쏙옙占쏙옙 占쏙옙占쏙옙占� Bean占쏙옙占쏙옙 占쏙옙占쏙옙占싹깍옙 占쏙옙占쏙옙 클占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {RootAppContext.class};
	}
	// 占식띰옙占쏙옙占� 占쏙옙占쌘듸옙 占쏙옙占쏙옙
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	// 파일 설정
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		// 파라미터 (업로드파일경로, 업로드파일용량 KB, 파일 데이터를 포함한 전체 입력 데이터 용량 KB, 파일 임계값)
		MultipartConfigElement config1 = new MultipartConfigElement(null, 52428800, 524288000, 0);
		registration.setMultipartConfig(config1);
	}
}
