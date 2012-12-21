package letterpress

import core.{Board, Tile}

package object solver {
  def solve(
    board: Board, 
    dict: List[String],
    color: Char) = {
    val tiles = board.tiles.toList
    val words = utils.filterWords(dict, tiles).toList
    val plays = play(words, tiles, board, color)
    plays.head
  }
  
  private def play(
  words: List[String], 
  tiles: List[Tile], 
  board: Board, 
  color: Char) = {

  val raw = for (
    word <- words;
    perm <- utils.getPermutations(word.toList, tiles);
    next = board.play(color, perm.toSet);
    score = next.score;
    value = if (color == 'b') score.blueScore else score.redScore)
    yield (word, perm, next, value)

  raw.sortBy(_._4)
  }
}