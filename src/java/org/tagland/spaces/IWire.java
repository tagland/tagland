/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

import java.io.Serializable;

/** Marker Interface, means this class is designed to go over the
 *  wire, through some kind of text-based serialization, so the
 *  sub-classer and maintainer are encouraged to keep this class
 *  serializable, lean, and be aware of implications when holding
 *  on to references.
 */
public interface IWire extends Serializable {
    //Marker Interface
}
