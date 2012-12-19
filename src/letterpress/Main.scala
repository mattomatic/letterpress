package letterpress

object Main extends App {
  val sowpodsFile = "/Users/mattomatic/Projects/hwf/sowpods/sowpods.txt"
  val boardFile = "/Users/mattomatic/Projects/letterpress/randos-0.txt"
  val played = List("cat")
  val board = core.Board.loadBoard(boardFile)
  val dict = utils.getWords(sowpodsFile).toList.filter(!played.contains(_))
  val best = solver.solve(board, dict, 'b')
  println(best)
  for (tile <- best._2.reverse) {
    println(tile)
  }
}