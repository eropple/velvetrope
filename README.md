Velvetrope
==========

Velvetrope is a simple access control system for Play! 2.0 that tries to make
as few assumptions about your code as possible. I wrote it recently to refresh
my Scala (it's a little rough, I know) and to get started on building some
useful components for my own Play! apps.

You should be able to reference this git repo in your SBT file and have it
automatically download and build the repo.

Using VelvetRope
----------------

The project is pretty straightforward and I encourage you to read the code
before using it (there's not a lot of it). The highlights, though:

* Add the RoleOwner or MutableRoleOwner trait to your User object of choice. I
  separated out an immutable and mutable trait because I like to have separate
  User and MutableUser objects. If you prefer a single mutable object, just use
  MutableRoleOwner. (You will have to do some implementation for either trait.
  It should be straightforward.)
* Add the VelvetropeGlobal trait to your Global object and implement its
  defined methods. onAuthorizationFailure() is a Result definition that will be
  called if an ACL'd action fails its auth check; getRoleOwner() passes in the
  request object and should allow you to get whatever information you need in
  order to verify your user's auth token (or whatever).
* Add the AccessControlled trait to your Controller object.


From there it should be pretty straightforward. If you've got any questions, I
usually hang around #playframework as eropple and am on Twitter at @edropple.

http://edropple.com
