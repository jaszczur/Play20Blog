package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.Date

case class BlogEntry(id: Pk[Long], title: String, content: String, creationDate: Date) {

  def save(): BlogEntry = {
    DB.withConnection { implicit connection =>
      
      val id: Long = this.id.getOrElse {
        SQL("select next value for blog_entry_seq").as(scalar[Long])
      }
      
      val formattedDate = {
        val h2DateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        h2DateFormatter.format(this.creationDate)
      }
      
      SQL(
        """
          insert into blog_entry (
            id, title, content, creation_date
          ) values (
            {id}, {title}, {content}, {creationDate}
          )
        """
      ).on(
        'id -> id,
        'title -> this.title,
        'content -> this.content,
        'creationDate -> formattedDate
      ).executeUpdate()
      
      this.copy(id = Id(id))
      
    }
  }
}

object BlogEntry {

  def apply(title: String, content: String, creationDate: Date) : BlogEntry = BlogEntry(NotAssigned, title, content, creationDate)

  // -- Parsers
  
  val simple = {
    def parseDate(s: String) : Date = new Date(java.lang.Long.valueOf(s))
  
    get[Pk[Long]]("blog_entry.id") ~/
    get[String]("blog_entry.title") ~/
    get[String]("blog_entry.content") ~/
    get[Date]("blog_entry.creation_date") ^^ {
      case id~title~content~creationDate => BlogEntry(
        id, title, content, creationDate
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
}  
