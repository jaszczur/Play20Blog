package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date

case class BlogEntry(id: Pk[Long], title: String, content: String, location: String, creationDate: Date) {

  def isNew() : Boolean = creationDate.after(new Date(System.currentTimeMillis() - 30*1000))

  def save(): BlogEntry = BlogEntry.save(this)
}

object BlogEntry {

  def apply(title: String, content: String, location: String, creationDate: Date) : BlogEntry = BlogEntry(NotAssigned, title, content, location, creationDate)

  // -- Parsers
  
  val simple = {
    def parseDate(s: String) : Date = new Date(java.lang.Long.valueOf(s))
  
    get[Pk[Long]]("blog_entry.id") ~/
    get[String]("blog_entry.title") ~/
    get[String]("blog_entry.content") ~/
    get[String]("blog_entry.location") ~/
    get[Date]("blog_entry.creation_date") ^^ {
      case id~title~content~location~creationDate => BlogEntry(
        id, title, content, location, creationDate
      )
    }
  }
  
  // -- Queries
  
  def findById(id: Long): Option[BlogEntry] = {
    DB.withConnection { implicit connection =>
      SQL("select * from blog_entry where id = {id}").on(
        'id -> id
      ).as(BlogEntry.simple ?)
    }
  }
  
  def all(): Seq[BlogEntry] = {
    DB.withConnection { implicit connection =>
      SQL("select * from blog_entry order by creation_date desc").as(BlogEntry.simple *)
    }
  }
  
  def save(entry: BlogEntry): BlogEntry = {
    DB.withConnection { implicit connection =>
      
      val id: Long = entry.id.getOrElse {
        SQL("select next value for blog_entry_seq").as(scalar[Long])
      }
      
      val formattedDate = {
        val h2DateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        h2DateFormatter.format(entry.creationDate)
      }
      
      SQL(
        """
          insert into blog_entry (
            id, title, content, location, creation_date
          ) values (
            {id}, {title}, {content}, {location}, {creationDate}
          )
        """
      ).on(
        'id -> id,
        'title -> entry.title,
        'content -> entry.content,
        'location -> entry.location,
        'creationDate -> formattedDate
      ).executeUpdate()
      
      entry.copy(id = Id(id))
      
    }
  }
}

