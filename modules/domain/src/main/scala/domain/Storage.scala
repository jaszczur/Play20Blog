package domain

class Storage(
  val entries : DAO[Long, BlogEntry]
)


trait DAO[PK, E] {
  def save(entity : E) : E
  def list() : Seq[E]
  def find(id : PK) : Option[E]
}

