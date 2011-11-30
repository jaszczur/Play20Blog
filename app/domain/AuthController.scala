package domain

class AuthController {
  def logIn(user : String, passwd : String)  : Boolean = (user, passwd) match {
    case ("admin", "pass") => true
    case _ => false
  }
}

