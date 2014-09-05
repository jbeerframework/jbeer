package com.jbeer.framework.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jbeer.framework.exception.RenderingViewException;
import com.jbeer.framework.ioc.JBeerIOCContainerContext;
import com.jbeer.framework.web.viewrender.Render;

public class RenderingView {

    private static List<Render> renders;
    
    public static void rendering(HttpServletRequest request, HttpServletResponse response,
                                 ModelAndView modelView) throws RenderingViewException {
        if (modelView == null) {
            return;
        }
       
        try {
            
            
            Map<String, Render> rendersMap = JBeerIOCContainerContext.getBeansByType(Render.class);
            Collection<Render> renderCollection = rendersMap.values();
            if(renders==null){
            renders = new ArrayList<Render>(renderCollection);
            Collections.sort(renders, new Comparator<Render>() {
                @Override
                public int compare(Render o1, Render o2) {
                    return o1.order()>o2.order()?1:0;
                }
            });
            }
            for (Render render : renders) {
                if(render.isSupport(modelView)){
                    render.render(request, response, modelView);
                    return;
                }
            }
        } catch (Exception e) {
            throw new RenderingViewException(e);
        }
    }

    static enum RenderType {

        JSTL("jstl"), FREEMARKER("freemarker"), VELOCITY("velocity");
        private String name;

        private RenderType(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }
}
