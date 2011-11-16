package models

import java.util._
import javax.persistence._

import play.db.ebean._
import play.data.format._
import play.data.validation._

@Entity
class BlogEntry extends Model {
  @Id
  var id: Long = 0
  
  @Constraints.Required
  var title: String = null
  
  
  var content: String = null
}

object BlogEntry {  
  val finder = new Model.Finder(classOf[Long], classOf[BlogEntry])
}  
