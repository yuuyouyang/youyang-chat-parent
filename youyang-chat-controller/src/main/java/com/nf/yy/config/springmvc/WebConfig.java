package com.nf.yy.config.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smile
 * @EnableWebMvc 开启MVC配置, 实现这个类, 可以进行mvc的一些配置
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.nf.yy.controller"})
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置时间格式转换器 这个是在数据绑定的时候将字符串转换为日期格式(请求时)
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(simpleDateFormat);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        StringHttpMessageConverter encoding = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        jsonConverter.setSupportedMediaTypes(mediaTypes);
        // 添加时间格式转换器
        converters.add(0, converter);
        // 添加字符编码转换器
        converters.add(1, encoding);
        // 添加json格式转换器
        converters.add(2, jsonConverter);
    }

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:8848")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

}
