package com.buswe.moudle.cms.freemarker;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.buswe.base.config.ContextHolder;
import com.buswe.moudle.cms.entity.Tags;
import com.buswe.moudle.cms.service.ArticleService;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

public class TagsVarDirective implements TemplateDirectiveModel {
private	ArticleService artiCleservice;
    private static final String PARAM_NAME_MAXSIZE = "maxSize";
	public TagsVarDirective() {
		artiCleservice = ContextHolder.getBean(ArticleService.class);
	}

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
 
        int maxSize = 0;

        Iterator paramIter = params.entrySet().iterator();
        while (paramIter.hasNext()) {
            Map.Entry ent = (Map.Entry) paramIter.next();

            String paramName = (String) ent.getKey();
            TemplateModel paramValue = (TemplateModel) ent.getValue();

            if(paramName.equals(PARAM_NAME_MAXSIZE)) {
                if (!(paramValue instanceof TemplateNumberModel)) {
                    throw new TemplateModelException(
                            "The \"" + PARAM_NAME_MAXSIZE + "\" parameter "
                                    + "must be a number.");
                }
                maxSize = ((TemplateNumberModel) paramValue).getAsNumber().intValue();

                if (maxSize < 0) {
                    throw new TemplateModelException(
                            "The \"" + PARAM_NAME_MAXSIZE + "\" parameter "
                                    + "can't be negative.");
                }
            } else {
                throw new TemplateModelException(
                        "Unsupported parameter: " + paramName);
            }
        }
        List<Tags> tagList =artiCleservice.getTopTags(maxSize,"1");
        env.setVariable("tagList", new DefaultObjectWrapperBuilder(Configuration.getVersion()).build().wrap(tagList));
        if(body != null){
            body.render(env.getOut());
        }
	}

}
