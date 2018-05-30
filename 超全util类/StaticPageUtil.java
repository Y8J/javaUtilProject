package com.zjht.youoil.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zjht.youoil.common.resolver.RealPathResolver;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 根据freemarker模版页面生成静态页面生产工具类
 * @author lijunjie
 *
 */
public class StaticPageUtil implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(StaticPageUtil.class);
    private MessageSource messageSource;
    @Autowired
    private RealPathResolver realPathResolver;
    private Configuration conf;
    /**
     * 根据freemarker模版生成静态页面
     * @param data 页面数据
     * @param tpl 模版页面路径（根目录是/WebRoot/WEB-INF/）
     * @param pagePath 生成页面全路径（根目录是/WebRoot）
     * @throws UnsupportedEncodingException 编码异常
     * @throws FileNotFoundException 找不到文件异常
     * @throws IOException 读写异常
     * @throws TemplateException 模版解析异常
     */
    public void genStaticPage(Map<String, Object> data,String tpl,String pagePath) throws UnsupportedEncodingException, FileNotFoundException,IOException, TemplateException{
        long time = System.currentTimeMillis();
        if (StringUtils.isBlank(pagePath)||StringUtils.isBlank(tpl)) {
            log.error("模版和生产指定路径为空，生成中止！");
            return;
        }
        String filePath=realPathResolver.get(pagePath);//根据页面相对路径返回全路径
        File f = new File(filePath);
        File parent = f.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        Writer out = null;
        try {
            //FileWriter不能指定编码，用OutputStreamWriter代替了。
            out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            Template template = conf.getTemplate(tpl);//模版文件地址
            template.process(data, out);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
        time = System.currentTimeMillis() - time;
        log.info("create "+pagePath+" page, in {} ms", time);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(conf, "freemarker configuration cannot be null!");
        Assert.notNull(messageSource,"tplMessageSource configuration cannot be null!");
    }

    public void setFreeMarkerConfigurer(
            FreeMarkerConfigurer freeMarkerConfigurer) {
        this.conf = freeMarkerConfigurer.getConfiguration();
    }
    
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @Autowired
    public void setRealPathResolver(RealPathResolver realPathResolver) {
        this.realPathResolver = realPathResolver;
    }

    public void setConf(Configuration conf) {
        this.conf = conf;
    }
}
