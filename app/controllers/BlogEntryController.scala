package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import domain._

import java.util.Date


object BlogEntryController extends Controller {
  private val blog = Application.blog

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
          blog.addNewEntry(BlogEntry(title, content, location, new Date()))
          Redirect(controllers.routes.BlogEntryController.list)
      }
    )
  }
  
  def list = Action { implicit request =>
    Formats outputFormat {
      case HTMLFormat() => Ok(views.html.entryList(blog.listEntries()))
      case JSONFormat() => Ok(views.txt.entryList(blog.listEntries()))
    }
  }
  
  def get(id: Long) = Action { implicit request =>
    blog.findEntryById(id) match {
      case Some(entry) => Formats outputFormat {
        case HTMLFormat() => Ok(views.html.display(entry))
        case JSONFormat() => Ok(views.txt.display(entry))
      }
      case None => Status(404)
    }
  }
}


object Application {
  private val storage = new Storage(new dao.BlogEntryDAO())
  val blog = new Blog(storage)
  
  
}

