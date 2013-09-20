/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import java.util.HashMap;

/**  Just like a String keyed map, but reserves keywords:
 *   @see #RESULT
 */

public class OutputObject extends HashMap<String, Object> {
    /** Reserved keyword in the map keys.  */
    public static final String RESULT = "RESULT";

    public Object getResult() {
        return get(OutputObject.RESULT);
    }

    public void setResult(Object obj) {
        put(OutputObject.RESULT, obj);
    }
}




