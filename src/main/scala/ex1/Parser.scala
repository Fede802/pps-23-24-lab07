package ex1

/** Consider the Parser example shown in previous lesson. Analogously to
  * NonEmpty, create a mixin NotTwoConsecutive, which adds the idea that one
  * cannot parse two consecutive elements which are equal. Use it (as a mixin)
  * to build class NotTwoConsecutiveParser, used in the testing code at the end.
  * Note we also test that the two mixins can work together!!
  */

abstract class Parser[T]:
  def parse(t: T): Boolean // is the token accepted?
  def end: Boolean // is it ok to end here
  def parseAll(seq: Seq[T]): Boolean =
    (seq forall parse) & end // note &, not &&

object Parsers:
  extension (s: String)
    def charParser(): Parser[Char] = new BasicParser(s.toSet)

class BasicParser(chars: Set[Char]) extends Parser[Char]:
  override def parse(t: Char): Boolean = chars.contains(t)
  override def end: Boolean = true

trait NonEmpty[T] extends Parser[T]:
  private[this] var empty = true
  abstract override def parse(t: T): Boolean =
    empty = false;
    super.parse(t) // who is super??
  abstract override def end: Boolean = !empty && super.end

class NonEmptyParser(chars: Set[Char])
    extends BasicParser(chars)
    with NonEmpty[Char]

trait NotTwoConsecutive[T] extends Parser[T]:
  private[this] var lastToken: Option[T] = None
  abstract override def parse(t: T): Boolean = lastToken match
    case Some(`t`) => false
    case _         => lastToken = Some(t); super.parse(t)

class NotTwoConsecutiveParser(chars: Set[Char]) extends BasicParser(chars) with NotTwoConsecutive[Char]

trait ShortenThenN[T](n: Int) extends Parser[T]:
  private[this] var length: Int = 0
  abstract override def parse(t: T): Boolean =
    if length < n then
      length += 1
      super.parse(t)
    else false

class ShortenThenNParser(chars: Set[Char], n: Int) extends BasicParser(chars) with ShortenThenN[Char](n)


