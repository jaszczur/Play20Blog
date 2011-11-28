package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import models._
import anorm._

import java.util.Date


object BlogEntryController extends Controller {

  private val entryForm = Form(
    of(
      "title" -> requiredText,
      "content" -> requiredText,
      "location" -> text
    )
  )
  
  def index = Action {
    Redirect(controllers.routes.BlogEntryController.list)
  }
  
  def add = Action {
    Ok(views.html.addEntry(entryForm))
  }
  
  def save = Action { implicit request =>
    entryForm.bindFromRequest.fold(
      failedForm => Ok(views.html.addEntry(failedForm)),
      {
        case (title, content, location) => 
          BlogEntry(title, content, location, new Date()).save()    
          Redirect(controllers.routes.BlogEntryController.list)
      }
    )
  }
  
  def list = Action { implicit request =>
    Formats outputFormat {
      case HTMLFormat() => Ok(views.html.entryList(BlogEntry.all()))
      case JSONFormat() => Ok(views.txt.entryList(BlogEntry.all()))
    }
  }
  
  def get(id: Long) = Action { implicit request =>
    BlogEntry.findById(id) match {
      case Some(entry) => Formats outputFormat {
        case HTMLFormat() => Ok(views.html.display(entry))
        case JSONFormat() => Ok(views.txt.display(entry))
      }
      case None => Status(404)
    }
  }
}
