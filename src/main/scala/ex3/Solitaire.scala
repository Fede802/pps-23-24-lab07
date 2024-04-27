package ex3


object Solitaire extends App:

  type Position = (Int, Int)
  type Solution = Iterable[Position]
  type IterableFactory = Solution => Iterable[Solution]
  val width = 7
  val height: Int = 5
  given IterableFactory = LazyList(_)

  def inside(position: (Int, Int)): Boolean =
    val (x, y) = position
    x >= 0 && x < width && y >= 0 && y < height

  def isSafe(position: Position, solution: Iterable[Position]): Boolean =
    !solution.exists(p => p == position) && inside(position)

  def placeMarks(markPlaced: Int = 0, grid: List[Position] = List())(using factory: IterableFactory): Iterable[Solution] = markPlaced match
  case 0 => placeMarks(1, List((width / 2, height / 2)))
  case n if n == width*height => factory(grid)
  case _ =>
    for
      move <- List((-3, 0), (3, 0), (0, -3), (0, 3), (2, 2), (-2, -2), (2, -2), (-2, 2))
      lastMarkPosition = grid.head
      newMarkPosition = (lastMarkPosition._1 + move._1, lastMarkPosition._2 + move._2)
      if isSafe(newMarkPosition, grid)
      solution <- placeMarks(markPlaced + 1, newMarkPosition :: grid)
    yield
      solution

  def render(solution: Seq[(Int, Int)], width: Int, height: Int): String =
    val reversed = solution.reverse
    val rows =
      for y <- 0 until height 
        row = for x <- 0 until width
        number = reversed.indexOf((x, y)) + 1
        yield if number > 0 then "%-2d ".format(number) else "X  "
    yield row.mkString
    rows.mkString("\n")

  placeMarks().zipWithIndex.foreach({solution => println(); println(s"sol ${solution._2}"); println(render(solution._1.toSeq,width,height))})

