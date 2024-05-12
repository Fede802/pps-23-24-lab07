package ex4

import ex4.controller.ControllerImpl
import view.FrameImpl


@main def runMatchFour =
  FrameImpl(ControllerImpl())
