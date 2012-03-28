package com.edropple.velvetrope.user

import play.api.mvc.{Result, Request}
import roles.Role


/**
 * Should be implemented by your Global class as defined in Play's
 * "application.global" setting.
 *
 * @author eropple
 * @since 26 Mar 2012
 */

trait VelvetropeGlobal {
    def getRoleOwner[A](request: Request[A]): Option[RoleOwner]

    def onAuthorizationFailure[A](user: Option[RoleOwner], request: Request[A], missingRoles: Seq[Role]): Result
}
