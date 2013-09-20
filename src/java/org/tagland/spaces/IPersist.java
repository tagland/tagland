/*
 * Copyright 2010 Laramie Crocker and tagland.org. This file is part of the software tagland.org.
 *    It is licensed under the GNU Affero General Public License.  You may read the software license in the project distribution called LICENSE.txt or here: http://www.gnu.org/licenses/
 *    Source files may be downloaded from https://sourceforge.net/projects/tagland
 */
package org.tagland.spaces;

/** Marker interface, means you are persistable, e.g. to DB4o, but specifically,
 *  maintainers are encouraged to keep this class lean in three ways: 1) not any
 *  smarts that you need to change in multiple installations--for those you'll want
 *  to use scriptable code bits, since they'll get persisted, etc, 2) don't link in
 *  huge graphs, or everything will go into the database; similarly, be sure to watch
 * and mark stuff transient as needed, 3) keep an eye to size, perhaps linking to large
 * objects by ID rather than Java reference.
 */
public interface IPersist {
    //marker interface
}
