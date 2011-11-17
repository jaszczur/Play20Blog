package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class BlogEntry(id: Pk[Long], title: String, content: String) {

  def save(): BlogEntry = {
    DB.withConnection { implicit connection =>
      
      val id: Long = this.id.getOrElse {
        SQL("select next value for blog_entry_seq").as(scalar[Long])
      }
      
      SQL(
        """
          insert into blog_entry values (
            {id}, {title}, {content}
          )
        """
      ).on(
        'id -> id,
        'title -> this.title,
        'content -> this.content
      ).executeUpdate()
      
      this.copy(id = Id(id))
      
    }
  }
}

object BlogEntry {

  def apply(title: String, content: String) : BlogEntry = BlogEntry(NotAssigned, title, content)

  // -- Parsers
  
  val simple = {
    get[Pk[Long]]("blog_entry.id") ~/
    get[String]("blog_entry.title") ~/
    get[String]("blog_entry.content") ^^ {
      case id~title~content => BlogEntry(
        id, title, content
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
      SQL("select * from blog_entry").as(BlogEntry.simple *)
    }
  }
}  
