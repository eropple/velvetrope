package com.edropple.velvetrope.user.roles

/**
 * Users possess Roles that indicate what features and parts of your
 * Play application they have access to. They can be specified fluently
 * to Scala Actions, with a hook used to determine the current user from
 * the request and then to check the user for permission to access the
 * Action.
 *
 * To create a new role, make a Scala object that extends Role.
 *
 * @author eropple
 * @since initial release
 */

abstract class Role
