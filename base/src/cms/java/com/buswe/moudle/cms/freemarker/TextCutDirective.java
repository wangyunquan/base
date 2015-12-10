package com.buswe.moudle.cms.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.buswe.moudle.cms.util.TextUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 文本字符串截断
 * 
            [@text_cut s=t.description len=descLen append=append/]
 */
public class TextCutDirective implements TemplateDirectiveModel {
	public static final String PARAM_S = "s";
	public static final String PARAM_LEN = "len";
	public static final String PARAM_APPEND = "append";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String s = FreemarkerDataUtil.getString(PARAM_S, params);
		Integer len = new Integer(FreemarkerDataUtil.getString(PARAM_LEN, params));
		String append = FreemarkerDataUtil.getString(PARAM_APPEND, params);
		if (s != null) {
			Writer out = env.getOut();
				out.append(TextUtil.textCut(s, len, append));
		}
	}
}