package letterpress.core

case class Board (val tiles: Array[Tile]) {
  require(tiles.length == 25)
  
  def score() = {
    var red, blue, white, darkRed, darkBlue = 0
    
    for (row <- 0 until 5;
         col <- 0 until 5) {
      val d = if (dark(row, col)) 1 else 0
      val c = getColor(row, col)
      
      c match {
        case 'r' => { red += 1; darkRed += d }
        case 'b' => { blue += 1; darkBlue += d }
        case 'w' => white += 1
        case _ => throw new Exception
      }
    }
    
    Score(red, blue, white, darkRed, darkBlue)
  }
  
  def play(color: Char, move: Set[Tile]) = {
    val update = tiles.map(t => 
      if (move.contains(t)) Tile(t.letter, color, t.row, t.col)
      else t)
    Board(update)
  }
  
  private def dark(row: Int, col: Int) = {
    val bound = (x: Int) => {math.min(math.max(x, 0), 4)}
    val u = getColor(bound(row + 1), col)
    val d = getColor(bound(row - 1), col)
    val l = getColor(row, bound(col - 1))
    val r = getColor(row, bound(col + 1))
    List(u, d, l, r).forall(_ == u)
  }
  
  private def getColor(row: Int, col: Int) = tiles(row * 5 + col).color
  private def getLetter(row: Int, col: Int) = tiles(row * 5 + col).letter
}

object Board {
  def loadBoard(file: String) = {
    val items = scala.io.Source.fromFile(file).mkString.replace('\n', ' ').split(' ')
    assert(items.length == 25)
    
    val tiles = for (
      row <- 0 until 5;
      col <- 0 until 5;
      item = items(row * 5 + col);
      letter = item.split('=')(0)(0);
      color = item.split('=')(1)(0))
      yield Tile(letter, color, row + 1, col + 1)

    new Board(tiles.toArray)
  }
}