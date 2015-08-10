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

package com.buswe.crawler.model;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.buswe.crawler.util.RegexRule;

/**
 *
 * @author hu
 */
public class Links extends ArrayList<String> {

    public void addAllFromDocument(Document doc) {
        Elements as = doc.select("a[href]");
        for (Element a : as) {
            String href = a.attr("abs:href");
            this.add(href);
        }
    }
    
    public void addAllFromDocument(Document doc, String cssSelector) {
        Elements as = doc.select(cssSelector).select("a[href]");
        for (Element a : as) {
            String href = a.attr("abs:href");
            this.add(href);
        }
    }
    
    public void addAllFromDocument(Document doc, RegexRule regexRule) {
        Elements as = doc.select("a[href]");
        for (Element a : as) {
            String href = a.attr("abs:href");
            if (regexRule.satisfy(href)) {
                this.add(href);
            }
        }
    }
    
    public void addAllFromDocument(Document doc, String cssSelector, RegexRule regexRule) {
        Elements as = doc.select(cssSelector).select("a[href]");
        for (Element a : as) {
            String href = a.attr("abs:href");
            if (regexRule.satisfy(href)) {
                this.add(href);
            }
        }
    }
}
