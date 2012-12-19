package letterpress

import core.{Tile, Board}

package object utils {
  def getWords(filename: String) = {
    for (line <- scala.io.Source.fromFile(filename).getLines;
         trimmed = line.trim) yield trimmed
  }
  
  def filterWords(words: Iterable[String], tiles: List[Tile]) = {
    def filter(word: List[Char], tiles: List[Tile]): Boolean = {
      if (word.isEmpty) true
      else {
        val c = word.head
        val o = tiles.find(_.letter == c)
        
        o match {
          case None => false
          case Some(t) => filter(word.tail, tiles - t)
        }
      }
    }
    
    words.filter(x => filter(x.toList, tiles))
  }
  
  def getPermutations(word: List[Char], tiles: Iterable[Tile]) = {
    def getPerms(
      word: List[Char], 
      played: List[Tile],
      unplayed: List[Tile], 
      perms: List[List[Tile]]): Iterable[List[Tile]] = {
      if (word.isEmpty) played :: perms
      else {
        val c = word.head
        val m = unplayed.filter(_.letter == c)
        m.flatMap(t => getPerms(word.tail, t :: played, unplayed - t, perms))
      }
    }
    
    getPerms(word, List[Tile](), tiles.toList, List[List[Tile]]())
  }
}