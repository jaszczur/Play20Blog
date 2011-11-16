package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models._

object Application extends Controller {

private def entryForm() = Form(
  of(
    "title" -> text(),
    "content" -> text()
  )
)
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def add = Action {
    Ok(views.html.addEntry(entryForm()))
  }
  
  def save = Action { implicit request =>
    val entry = new BlogEntry()
    val (title, content) = entryForm().bindFromRequest.get
    entry.title = title
    entry.content = content
    entry.save()
    
    Ok("goood")
  }
  
  def list = Action {
    Ok(views.html.entryList(BlogEntry.finder.all()))
  }
}
