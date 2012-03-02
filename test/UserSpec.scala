package test

import org.specs2._
import domain._

object AuthorizationSpec extends mutable.Specification {
  val sut = new AuthController()
  
  "Authorization controller" should {
    "allow admin to log in" in {
      sut.logIn("admin", "pass") must beTrue
    }

    "not allow hacker to log in" in {
      sut.logIn("admin", "xxx") must beFalse
    }
  }

}

