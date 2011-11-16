package controllers

import play.api._
import play.api.mvc._
import models._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def add = Action {
    val entry = new BlogEntry()
    entry.title = "Whatever"
    entry.content = "Blah blah blah"
    entry.save()
    Ok("Everythink's fine")
  }
  
  def save = Action {
    Ok("goood")
  }
  
  def list = Action {
    Ok(views.html.entryList(BlogEntry.finder.all()))
  }
}
