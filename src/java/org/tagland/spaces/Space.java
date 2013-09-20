/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 * The fundamental containing unit in Tagland Spaces, which contain
 * menus, views, objects, and references, all visible accordingly for
 * the permission level of the requestor.
 * <p/>
 * If a Space is locked, then
 * all data are encrypted and placed under "restricted to authorized
 * group" access.
 */
public class Space {
    public static String menuItem2MenuBundle(MenuItem root) {

        XStream xstream = new XStream(
            new XppDriver() {
                public HierarchicalStreamWriter createWriter(Writer out) {
                    return new PrettyPrintWriter(out) {
                        protected void writeText(QuickWriter writer, String text) {
                            writer.write(text);
                        }
                    };
                }
            }
        );
        xstream.registerConverter(new CDataSourceConverter()); //  @see: http://xstream.codehaus.org/converter-tutorial.html
        String xml = xstream.toXML(root); // serialize to XML
        return xml;
    }

    public static MenuItem menuBundle2MenuItem(String xml) {
        XStream xstream = new XStream();
        xstream.registerConverter(new CDataSourceConverter()); //  @see: http://xstream.codehaus.org/converter-tutorial.html
        org.xmlpull.v1.XmlPullParser p;
        org.xmlpull.v1.XmlPullParserException pe;
        Object myObject2 = xstream.fromXML(xml); // deserialize from XML
        MenuItem root = (MenuItem) myObject2;
        return root;
    }

    //============================================================

    /* This works.
        XStream xstream = new XStream(
            new XppDriver() {
                public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    boolean cdata = false;
                    public void startNode(String name, Class clazz){
                        super.startNode(name, clazz);

                        cdata = clazz.isAssignableFrom(CDataSource.class);
                                //&& name.equals("description") || name.equals("name"));
                    }
                    protected void writeText(QuickWriter writer, String text) {
                        if(cdata) {
                            //writer.write("<![CDATA[");
                            writer.write(text);
                            //writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
                }
            }
        );
        */
}
