package ex4.ticTacToe

import ex4.ticTacToe.controller.Controller
import ex4.ticTacToe.view.View

@main def runConnectThree =
  View(Controller()).showMenu()
