package letterpress

object Main extends App {
  val config = Config.fromFile(
    "/Users/mattomatic/Projects/letterpress/cfg/config.xml")
  val board = core.Board.loadBoard(config.board)
  val dict = utils.getWords(config.dictionary).toList
  val best = solver.solve(board, dict, config.color)
  println(best)
}