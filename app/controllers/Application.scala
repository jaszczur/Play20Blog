package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models._
import anorm._

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
    val (title, content) = entryForm().bindFromRequest.get
    val entry = BlogEntry(NotAssigned, title, content)
    entry.save()
    
    Ok(views.html.entryList(BlogEntry.all()))
  }
  
  def list = Action {
    Ok(views.html.entryList(BlogEntry.all()))
  }
  
  def get(id: Long) = Action {
    BlogEntry.findById(id) match {
        case Some(entry) => Ok(views.html.display(entry))
        case None => Ok("Not found")
    }
  }
}
