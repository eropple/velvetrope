package com.edropple.velvetrope.user

import roles.Role


/**
 * Goes beyond the basic RoleOwner to define a consistent interface for
 * adding and removing roles. This is separate from RoleOwner in order to
 * allow for "User" and "MutableUser" being separate concepts (a pattern
 * I personally prefer).
 *
 * Please note that while token Java support is available, it may be removed
 * at any point if I don't think it's a big enough win.
 *
 * @author eropple
 * @since initial release
 */

trait MutableRoleOwner extends RoleOwner {
    /**
     * Adds a role to this user.
     * @param role The role to add to this user.
     */
    def addRole(role: Role)

    /**
     * Removes a role from this user.
     * @param role The role to remove from this user.
     * @return True if a role was actually removed from the user, false otherwise.
     */
    def removeRole(role: Role): Boolean
}
