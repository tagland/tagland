/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package us.anarchia;

import us.anarchia.obj.Author;

/**
 */
public class AuthorCtl {
    private Author author;

    public void handleInput(String fieldName, String propertyValue){
        handleInput(fieldName, propertyValue, "");
    }
    public void handleInput(String fieldName, String fieldValue, String inputType){
       /* TODO:        Bean bean = BeanUtils.foo(author);
        bean.
        pretty interesting that lobobrowser defines a bean...
        I think I want the Java lang one, that gives you introspection.
                

        BeanProp prop = bean.getBeanProperty(fieldName);
        bean.set(prop, fieldValue);

        notifySubscribers();
        */
    }
}
