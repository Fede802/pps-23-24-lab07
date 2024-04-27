package ex1

import ex1.*
import org.scalatest.matchers.should.Matchers.*

class ParserTests extends org.scalatest.funsuite.AnyFunSuite:
  def parser = new BasicParser(Set('a', 'b', 'c'))
  // Note NonEmpty being "stacked" on to a concrete class
  // Bottom-up decorations: NonEmptyParser -> NonEmpty -> BasicParser -> Parser
  def parserNE = new NonEmptyParser(Set('0', '1'))
  def parserNTC = new NotTwoConsecutiveParser(Set('X', 'Y', 'Z'))
  // note we do not need a class name here, we use the structural type
  def parserNTCNE = new BasicParser(Set('X', 'Y', 'Z')) with NotTwoConsecutive[Char] with NonEmpty[Char]
  import Parsers.*
  def sparser: Parser[Char] = "abc".charParser()
  def parserSTNP: Parser[Char] = new ShortenThenNParser(Set('a', 'b', 'c'), 3)

  test("testBasicParser"):
    parser.parseAll("aabc".toList) shouldBe true
    parser.parseAll("aabcdc".toList) shouldBe false
    parser.parseAll("".toList) shouldBe true

  test("testNotEmptyParser"):
    parserNE.parseAll("0101".toList) shouldBe true
    parserNE.parseAll("0123".toList) shouldBe false
    parserNE.parseAll(List()) shouldBe false

  test("testNotTwoConsecutiveParser"):
    parserNTC.parseAll("XYZ".toList) shouldBe true
    parserNTC.parseAll("XYYZ".toList) shouldBe false
    parserNTC.parseAll(List()) shouldBe true

  test("testNotEmptyAndNotTwoConsecutiveParser"):
    parserNTCNE.parseAll("XYZ".toList) shouldBe true
    parserNTCNE.parseAll("XYYZ".toList) shouldBe false
    parserNTCNE.parseAll(List()) shouldBe false

  test("testStringParser"):
    sparser.parseAll("aabc".toList) shouldBe true
    sparser.parseAll("aabcdc".toList) shouldBe false
    sparser.parseAll(List()) shouldBe true

  test("testShortenThenNParser"):
    parserSTNP.parseAll("aca".toList) shouldBe true
    parserSTNP.parseAll("aabcc".toList) shouldBe false
    parserSTNP.parseAll(List()) shouldBe true


