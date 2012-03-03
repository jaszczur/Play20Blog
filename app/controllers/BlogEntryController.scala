package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import domain._

import java.util.Date
//import org.picocontainer._

object BlogEntryController extends Controller {
  private val blog = Application.blog

  private val entryForm = Form(
    tuple(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "locationLat" -> text,
      "locationLong" -> text,
      "locationName" -> text
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
        case (title, content, locationLat, locationLong, locationName) => 
          blog.addNewEntry(BlogEntry(title, content, new Date(), Location(locationLat.toDouble, locationLong.toDouble, locationName)))
          Redirect(controllers.routes.BlogEntryController.list)
      }
    )
  }
  
  def list = Action { implicit request =>
    Formats outputFormat {
      case HTMLFormat => Ok(views.html.entryList(blog.listEntries()))
      case JSONFormat => Ok(views.txt.entryList(blog.listEntries()))
    }
  }
  
  def get(id: Long) = Action { implicit request =>
    blog.findEntryById(id) match {
      case Some(entry) => Formats outputFormat {
        case HTMLFormat => Ok(views.html.display(entry))
        case JSONFormat => Ok(views.txt.display(entry))
      }
      case None => Status(404)
    }
  }
}


object Application {
  //private val pico : DefaultPicoContainer = new PicoBuilder().withConstructorInjection().build().asInstanceOf[DefaultPicoContainer]
  private val storage = new Storage(new dao.BlogEntryDAO())
  val blog = new Blog(storage)
  
  
}

