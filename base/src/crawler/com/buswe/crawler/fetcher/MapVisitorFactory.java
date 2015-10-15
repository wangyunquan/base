/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.buswe.crawler.fetcher;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 *
 * @author hu
 */
public class MapVisitorFactory implements VisitorFactory{

    public LinkedHashMap<String, Visitor> visitorMap=new LinkedHashMap<String, Visitor>();

    public void addVisitor(String urlPattern,Visitor visitor){
        visitorMap.put(urlPattern, visitor);
    }
    
    @Override
    public Visitor createVisitor(String url, String contentType) {
        for(String urlPattern:visitorMap.keySet()){
            if(Pattern.matches(urlPattern, url)){
                return visitorMap.get(urlPattern);
            }
        }
        return null;
    }

    public LinkedHashMap<String, Visitor> getVisitorMap() {
        return visitorMap;
    }

    public void setVisitorMap(LinkedHashMap<String, Visitor> visitorMap) {
        this.visitorMap = visitorMap;
    }
    
    
    
}