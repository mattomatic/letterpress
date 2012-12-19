package letterpress.core

case class Score(
  val red: Int, 
  val blue: Int, 
  val white: Int,
  val darkRed: Int, 
  val darkBlue: Int) {
  def gameOver = white == 0
  def blueWins = gameOver && blue > red
  def redWins = gameOver && red < blue
  def tieGame = gameOver && red == blue
  
  def redScore = {
    if (redWins) -25
    if (blueWins) 25
    if (tieGame) 0
    darkBlue - darkRed + (blue - red) / 100.0
  }
  
  def blueScore = -redScore
}