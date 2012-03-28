package com.edropple.velvetrope.user

import roles.Role


/**
 * Provides the concept of "something that has roles". Should, in probably all
 * cases, be implemented by your User object. (Its writable counterpart, MutableRoleOwner,
 * is for mutable User objects only, hence the separation.)
 *
 * @author eropple
 * @since initial release
 */

trait RoleOwner {
    /**
     * Checks for a role on this user.
     * @param role The role to check against.
     * @return True if the user possesses this role, false otherwise.
     */
    def hasRole(role: Role): Boolean

    /**
     * Gets all roles that are attached to this user.
     * @return All roles attached to this user.
     */
    def getAllRoles: Seq[Role]
}
