/*
 * Created on May 11, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.cfcmethods;
import java.util.Comparator;
import com.rohanclan.cfml.parser.TagItem;
/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CFCMethodsComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        if (o1 instanceof TagItem && o1 instanceof TagItem) {
            TagItem t1 = (TagItem)o1;
            TagItem t2 = (TagItem)o2;
            return t1.getAttribute("name").compareTo(t2.getAttribute("name"));
        }
        return 0; 
    }
    
}
