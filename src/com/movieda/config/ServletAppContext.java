package com.movieda.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.movieda.mapper.BoardMapper;
import com.movieda.mapper.MovieMapper;
import com.movieda.mapper.UserMapper;

// Spring MVC 占쏙옙占쏙옙占쏙옙트占쏙옙 占쏙옙占시듸옙 占쏙옙占쏙옙占쏙옙 占싹댐옙 클占쏙옙占쏙옙
@Configuration
// Controller 占쏙옙占쏙옙占쏙옙抉占쏙옙占� 占쏙옙占시되억옙占쌍댐옙 클占쏙옙占쏙옙占쏙옙 Controller占쏙옙 占쏙옙占쏙옙磯占�.
@EnableWebMvc
// 占쏙옙캔占쏙옙 占쏙옙키占쏙옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
@ComponentScan("com.movieda.controller")
@ComponentScan("com.movieda.service")
@ComponentScan("com.movieda.dao")

@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer {
	
	@Value("${db.classname}")
	private String db_classname;
	
	@Value("${db.url}")
	private String db_url;
	
	@Value("${db.username}")
	private String db_username;
	
	@Value("${db.password}")
	private String db_password;
	
	// Controller占쏙옙 占쌨쇽옙占썲가 占쏙옙환占싹댐옙 jsp占쏙옙 占싱몌옙 占쌌뒤울옙 占쏙옙恝占� 확占쏙옙占쌘몌옙 占쏙옙占쏙옙占쌍듸옙占쏙옙 占쏙옙占쏙옙占싼댐옙.
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.configureViewResolvers(registry);
//		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙罐占� 占쏙옙占쏙옙占싼댐옙.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/views/");
	}
	
	// MyBatis �ㅼ��
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);		
		return source;
	}
	
	// 荑쇰━臾멸낵 ������蹂대�� 愿�由ы���� 媛�泥�
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	
	// UserMapper 객체 등록
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	// MovieMapper 객체 등록
	@Bean
	public MapperFactoryBean<MovieMapper> getMovieMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<MovieMapper> factoryBean = new MapperFactoryBean<MovieMapper>(MovieMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	// BoardMapper 객체 등록
	@Bean
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<BoardMapper> factoryBean = new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	// 파일 처리를 위한 객체
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	
}
