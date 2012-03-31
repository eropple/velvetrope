package com.edropple.velvetrope.user.roles

import collection.mutable

/**
 * Users possess Roles that indicate what features and parts of your
 * Play application they have access to. They can be specified fluently
 * to Scala Actions, with a hook used to determine the current user from
 * the request and then to check the user for permission to access the
 * Action.
 *
 * To create a new role, make a Scala object (not class or case class)
 * that extends Role. By default, the roles are named based on the simple
 * name of the object that represents them, but you can override 'name'
 * to change that.
 *
 * @author eropple
 * @since initial release
 */

abstract class Role {
    lazy val name = this.getClass.getSimpleName


    private val nameKey = name.trim.toLowerCase
    if (Role.nameMap.contains(nameKey)) {
        throw new IllegalStateException("Multiple Role objects with key '%s' added to the name map." format nameKey)
    } else {
        Role.nameMap += ((nameKey, this))
    }
}

/**
 * Helper object for Roles. When you create a new Role object, it gets
 * registered to a private map that you can access with Role.fromString
 * (intended for use in mapping permissions to database rows, that sort
 * of thing).
 *
 * @author eropple
 * @since initial release
 */
object Role {
    private val nameMap = mutable.Map[String, Role]()

    /**
     * Given a string, returns any role that matches 
     * @param name
     * @return
     */
    def fromString(name: String): Option[Role] = nameMap.get(name.trim().toLowerCase)
}