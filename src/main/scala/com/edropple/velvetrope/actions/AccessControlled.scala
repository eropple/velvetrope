package com.edropple.velvetrope.actions

import play.api.mvc._
import play.api.mvc.BodyParsers._
import play.api.Play
import com.edropple.velvetrope.VelvetropeGlobal
import com.edropple.velvetrope.user.RoleOwner
import com.edropple.velvetrope.user.roles.Role
import play.api.libs.concurrent.Akka

/**
 * The base trait for ACL'd controllers. Mixing this into your controller
 * allows you to use AccessControlled in your action definitions.
 *
 * Some examples of using AccessControlled follow:
 *
 * {{{
 *
 *  val readSomethingSecret = AccessControlled(SomeSecretPermission) {
 *      (user, request) => {
 *          Ok("here it is!")
 *      }
 *  }
 *
 *  val readSecretJson = AccessControlled(SomeSecretPermission, parse.json) {
 *      request =>
 *          (request.body \ "name").asOpt[String].map { name =>
 *              Ok("Hello " + name)
 *      }.getOrElse {
 *          BadRequest("Missing parameter [name]")
 *      }
 *  }
 *
 * }}}
 *
 * AccessControlled also supports a Seq[Role] instead of a Role, but I'm
 * conflicted about the utility of that feature and may remove it in the
 * future.
 *
 * Also note that this will fail, and fail hard, if your global object doesn't
 * implement VelvetropeGlobal. There are no checks on that cast.
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
                    val userPromise = Akka.future {
                        global.getRoleOwner(request)
                    }

                    Results.Async {
                        userPromise.map( userOpt => {
                            if (userOpt.isEmpty) {
                                global.onAuthenticationFailure(request)
                            }
                            
                            val user = userOpt.get

                            val missingRoles = user.getAllRoles.intersect(roles)

                            if (missingRoles.length > 0) {
                                global.onAuthorizationFailure(userOpt, request, missingRoles)
                            }

                            f(user, request)
                        })
                    }
                }
            }
        }
    }
}