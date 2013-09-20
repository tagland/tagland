/* Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
   It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
   Source files may be downloaded from https://sourceforge.net/projects/tagland/  */
package org.tagland.spaces;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 */
public class CDataSourceConverter implements Converter {

        public CDataSourceConverter() {
            super();
        }

        public static final String CDATA_START = "<![CDATA[";
        public static final String CDATA_END = "]]>";


        public boolean canConvert(Class clazz) {
            return CDataSource.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
            CDataSource source = (CDataSource)value;
            String script = source.getSource();
            String str = CDATA_START+script+CDATA_END;
            writer.setValue(str);
        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            String value = reader.getValue();
            String result = value;
            String vtrim = value.trim();
            boolean OK = vtrim.startsWith(CDATA_START)
                    && vtrim.endsWith(CDATA_END);
            //if (!OK) throw new ConversionException("CDATA enclosure not found in: " + value);
            if (OK){
                result = vtrim.substring(CDATA_START.length());
                result = result.substring(0, CDATA_END.length());
            }
            CDataSource bsh = new CDataSource();
            bsh.setSource(result);
            return bsh;
        }
    
}
