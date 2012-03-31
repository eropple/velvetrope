package com.edropple.velvetrope

import play.api.mvc.{Result, Request}
import user.RoleOwner
import user.roles.Role


/**
 * Should be implemented by your Global class as defined in Play's
 * "application.global" setting.
 *
 * @author eropple
 * @since 26 Mar 2012
 */

trait VelvetropeGlobal {
    /**
     * Provides Velvetrope with the necessary logic to find the user from whatever
     * stateful data is available. This method is always run inside of an Akka.future
     * block.
     *
     * @param request The request object for this request
     * @return Some[RoleOwner] if the request contains user authentication information, None otherwise
     */
    def getRoleOwner[A](request: Request[A]): Option[RoleOwner]

    /**
     * Called when a visitor attempts to access an access-controlled resource without
     * having a role (via getRoleOwner).
     * @param request The visitor's request
     * @return a Result that should be presented to the visitor.
     */
    def onAuthenticationFailure[A](request: Request[A]): Result

    /**
     * Called when a user attempts to access an access-controlled resource without
     * having the correct role.
     * @param user the user accessing the resource
     * @param request the request of the resource access
     * @param missingRoles the missing roles that prevent the user from accessing the resource
     * @return a Result that should be presented to the visitor.
     */
    def onAuthorizationFailure[A](user: Option[RoleOwner], request: Request[A], missingRoles: Seq[Role]): Result
}
