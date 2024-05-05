package ex4

import Monads.*

object IOs:
  case class IO[A](exec: () => A)

   // minimal set of operations


  object IO:
    def read(): IO[String] = IO(() => scala.io.StdIn.readLine)
    def write[A](a: A): IO[A] = IO(() => {
      println(a); a
    })
    def compute[A](a: => A): IO[A] = IO(() => a)
    def nop(): IO[Unit] = compute(())

// extending IO to be a Monad !
  given Monad[IO] with
//
// // unit : an IO that just returns the boxed value
//
    def unit[A](a: A): IO[A] = IO(() => a)
//
//
//  flatMap : opens the box , executes , and creates a new box with result
    extension[A] (m: IO[A])
      def flatMap[B](f: A => IO[B]): IO[B] =
        m match
          case IO(e) => f(e())
//

