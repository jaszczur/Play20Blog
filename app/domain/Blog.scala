package domain

class Blog(val storage : Storage) {

  def addNewEntry(entry : BlogEntry) : BlogEntry = {
    storage.entries.save(entry)
  }
    
  def listEntries() : Seq[BlogEntry] = {
    storage.entries.list()
  }
    
  def findEntryById(id : Long) : Option[BlogEntry] = {
    storage.entries.find(id)
  }
    
}


