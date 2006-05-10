package com.rohanclan.cfml.views.packageview.objects;

import com.rohanclan.cfml.parser.docitems.TagItem;
import com.rohanclan.cfml.util.CFPluginImages;

/**
 * This object represents a <cfargument> tag, defined in a cfc method.
 * @author markd
 * @author mike nimer 
 */
public class ArgumentNode extends TreeParent
{
    private String argType;
    private TagItem argumentTag;

    public ArgumentNode(TagItem argumentTag)
    {
        super( argumentTag.getAttributeValue("name") );
        this.argumentTag = argumentTag;
        this.argType = argumentTag.getAttributeValue("type");
    }


    public String getImage()
    {       
        return CFPluginImages.ICON_ATTR;
    }

    public String getPackageName()
    {
        return getName();
    }

    public String toString()
    {
        String retName;
        if( this.argType != null )
        {
            retName = this.getName() +" : " +this.argType.toUpperCase();
        }
        else
        {
            retName = this.getName() +" : ANY";
        }

        return retName;
    }

}
