package com.edropple.velvetrope.actions

import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.Play
import com.edropple.velvetrope.user.{RoleOwner, VelvetropeGlobal}
import com.edropple.velvetrope.user.roles.Role

/**
 * The base trait for ACL'd controllers. Mixing this into your controller
 * allows you to use AccessControlled in your action definitions.
 *
 * @author eropple
 * @since 26 Mar 2012
 */

trait AccessControlled {
    private lazy val global = Play.current.global.asInstanceOf[VelvetropeGlobal]
    
    def AccessControlled(role: Role): AccessControlledBase[AnyContent] = AccessControlled(role, parse.anyContent)
    
    def AccessControlled(roles: Seq[Role]) : AccessControlledBase[AnyContent] = AccessControlledBase(roles, parse.anyContent)
    def AccessControlled[A](role: Role, bp: BodyParser[A]) = AccessControlledBase(Seq(role), bp)


    case class AccessControlledBase[A](roles: Seq[Role], bp: BodyParser[A]) {
        def apply( f: (RoleOwner, Request[A]) => Result ): Action[A] = {
            Action(bp) { request => {
                    val user: Option[RoleOwner] = global.getRoleOwner(request)
                    if (user.isEmpty) {
                        global.onAuthorizationFailure(user, request, roles)
                    }

                    val missingRoles = user.get.getAllRoles.intersect(roles)
                
                    if (missingRoles.length > 0) {
                        global.onAuthorizationFailure(user, request, missingRoles)
                    }

                    f(user.get, request)
                }
            }
        }
    }
}