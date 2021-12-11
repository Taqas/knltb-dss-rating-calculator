package com.github.taqas.knltbDssRatingCalculator

import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.{Group, Scene}
import scalafx.scene.control.{
  Button,
  Label,
  RadioButton,
  SplitPane,
  Tab,
  TabPane,
  TextArea,
  TextField,
  ToggleGroup
}
import scalafx.scene.layout.{FlowPane, VBox}
import scalafx.scene.text.Text

case class WinnerSelection(
    labelWinner: Label,
    radioButtonHome: RadioButton,
    radioButtonAway: RadioButton,
    toggleGroup: ToggleGroup
)

object ScalaFX extends JFXApp3 {

  def createWinnerSelection(): WinnerSelection = {
    val labelWinner = new Label("Winnaar")
    labelWinner.layoutX = 30
    labelWinner.layoutY = 50

    val radioButtonHome = new RadioButton("Thuisspeler")
    radioButtonHome.layoutX = 230
    radioButtonHome.layoutY = 50
    radioButtonHome.selected = true

    val radioButtonAway = new RadioButton("Uitspeler")
    radioButtonAway.layoutX = 330
    radioButtonAway.layoutY = 50
    val toggleGroup = new ToggleGroup()
    toggleGroup.toggles = List(radioButtonHome, radioButtonAway)

    WinnerSelection(labelWinner, radioButtonHome, radioButtonAway, toggleGroup)
  }

  def createSinglesTab(tab: Tab): Unit = {
    tab.text = "Enkelspel"
    tab.setClosable(false)
    val winnerSelection: WinnerSelection = createWinnerSelection()

    val labelHomePlayer = new Label("""Thuisspeler
                |Huidige rating:
                |""".stripMargin)
    labelHomePlayer.layoutX = 30
    labelHomePlayer.layoutY = 80

    val textFieldHomePlayer = new TextField()
    textFieldHomePlayer.layoutX = 30
    textFieldHomePlayer.layoutY = 120

    val labelAwayPlayer = new Label("""Uitspeler
                |Huidige rating:
                |""".stripMargin)
    labelAwayPlayer.layoutX = 230
    labelAwayPlayer.layoutY = 80

    val textFieldAwayPlayer = new TextField()
    textFieldAwayPlayer.layoutX = 230
    textFieldAwayPlayer.layoutY = 120

    val button = new Button("Bereken rating")
    button.layoutX = 30
    button.layoutY = 170

    val labelWinnerTextHome = new Label("""Winstkans:
                |Nieuwe rating:
                |Rating verschil:
                |""".stripMargin)
    labelWinnerTextHome.layoutX = 30
    labelWinnerTextHome.layoutY = 170

    val labelWinnerTextAway = new Label("""Winstkans:
                |Nieuwe rating:
                |Rating verschil:
                |""".stripMargin)
    labelWinnerTextAway.layoutX = 230
    labelWinnerTextAway.layoutY = 170

    val labelWinnerCalcsHome = new Label()
    labelWinnerCalcsHome.layoutX = 170
    labelWinnerCalcsHome.layoutY = 170

    val labelWinnerCalcsAway = new Label()
    labelWinnerCalcsAway.layoutX = 370
    labelWinnerCalcsAway.layoutY = 170

    val group = new Group(
      textFieldHomePlayer,
      textFieldAwayPlayer,
      labelHomePlayer,
      labelAwayPlayer,
      button,
      winnerSelection.labelWinner,
      winnerSelection.radioButtonHome,
      winnerSelection.radioButtonAway
    )

    tab.setContent(group)

    button.onAction = (e: ActionEvent) => {
      button.layoutY = 270

      if (!group.getChildren.contains(labelWinnerTextHome)) {
        val groupNew = new Group(
          labelWinnerTextHome,
          labelWinnerTextAway,
          labelWinnerCalcsHome,
          labelWinnerCalcsAway,
          textFieldHomePlayer,
          textFieldAwayPlayer,
          labelHomePlayer,
          labelAwayPlayer,
          button,
          winnerSelection.labelWinner,
          winnerSelection.radioButtonHome,
          winnerSelection.radioButtonAway
        )
        tab.setContent(groupNew)
      }

      val homeRating = textFieldHomePlayer.text.value.toDouble
      val awayRating = textFieldAwayPlayer.text.value.toDouble

      val q = 1.824
      val K = 0.275
      val probHome =
        (1 / (1.0 + math.exp(-q * (awayRating - homeRating))) * 100).toInt

      val homeRatingNew =
        homeRating + K * (probHome / 100.0 - winnerSelection.radioButtonHome.isSelected
          .compare(false)
          .toInt)

      val awayRatingNew =
        awayRating + K * ((100 - probHome) / 100.0 - winnerSelection.radioButtonAway.isSelected
          .compare(false)
          .toInt)

      val homeRatingDiff = homeRatingNew - homeRating
      val awayRatingDiff = awayRatingNew - awayRating

      labelWinnerCalcsHome.text =
        f"$probHome%%%n$homeRatingNew%1.4f%n$homeRatingDiff%1.4f"
      labelWinnerCalcsAway.text =
        f"${100 - probHome}%%%n$awayRatingNew%1.4f%n$awayRatingDiff%1.4f"
    }
  }

  def createDoublesTab(tab: Tab): Unit = {
    tab.text = "Dubbelspel"
    tab.setClosable(false)
    val winnerSelection: WinnerSelection = createWinnerSelection()

    val labelHomePlayer1 = new Label("""Thuisspelers
                                      |Speler1:
                                      |""".stripMargin)
    labelHomePlayer1.layoutX = 30
    labelHomePlayer1.layoutY = 80

    val textFieldHomePlayer1 = new TextField()
    textFieldHomePlayer1.layoutX = 30
    textFieldHomePlayer1.layoutY = 120

    val labelHomePlayer2 = new Label("Speler 2:")
    labelHomePlayer2.layoutX = 30
    labelHomePlayer2.layoutY = 150

    val textFieldHomePlayer2 = new TextField()
    textFieldHomePlayer2.layoutX = 30
    textFieldHomePlayer2.layoutY = 170

    val labelAwayPlayer1 = new Label("""Uitspelers
                                      |Speler 1:
                                      |""".stripMargin)
    labelAwayPlayer1.layoutX = 230
    labelAwayPlayer1.layoutY = 80

    val textFieldAwayPlayer1 = new TextField()
    textFieldAwayPlayer1.layoutX = 230
    textFieldAwayPlayer1.layoutY = 120

    val labelAwayPlayer2 = new Label("Speler 2:")
    labelAwayPlayer2.layoutX = 230
    labelAwayPlayer2.layoutY = 150

    val textFieldAwayPlayer2 = new TextField()
    textFieldAwayPlayer2.layoutX = 230
    textFieldAwayPlayer2.layoutY = 170

    val button = new Button("Bereken rating")
    button.layoutX = 30
    button.layoutY = 210

    val labelWinnerTextHome = new Label("""Thuis gecombineerd:
                                          |Winstkans:
                                          |Speler 1 nieuw:
                                          |Speler 2 nieuw:
                                          |Rating verschil:
                                          |""".stripMargin)
    labelWinnerTextHome.layoutX = 30
    labelWinnerTextHome.layoutY = 210

    val labelWinnerTextAway = new Label("""Uit gecombineerd:
                                          |Winstkans:
                                          |Speler 1 nieuw:
                                          |Speler 2 nieuw:
                                          |Rating verschil:
                                          |""".stripMargin)
    labelWinnerTextAway.layoutX = 230
    labelWinnerTextAway.layoutY = 210

    val labelWinnerCalcsHome = new Label()
    labelWinnerCalcsHome.layoutX = 170
    labelWinnerCalcsHome.layoutY = 210

    val labelWinnerCalcsAway = new Label()
    labelWinnerCalcsAway.layoutX = 370
    labelWinnerCalcsAway.layoutY = 210

    val group = new Group(
      textFieldHomePlayer1,
      textFieldHomePlayer2,
      textFieldAwayPlayer1,
      textFieldAwayPlayer2,
      labelHomePlayer1,
      labelHomePlayer2,
      labelAwayPlayer1,
      labelAwayPlayer2,
      button,
      winnerSelection.labelWinner,
      winnerSelection.radioButtonHome,
      winnerSelection.radioButtonAway
    )

    tab.setContent(group)

    button.onAction = (e: ActionEvent) => {
      button.layoutY = 320

      if (!group.getChildren.contains(labelWinnerTextHome)) {
        val groupNew = new Group(
          labelWinnerTextHome,
          labelWinnerTextAway,
          labelWinnerCalcsHome,
          labelWinnerCalcsAway,
          textFieldHomePlayer1,
          textFieldHomePlayer2,
          textFieldAwayPlayer1,
          textFieldAwayPlayer2,
          labelHomePlayer1,
          labelHomePlayer2,
          labelAwayPlayer1,
          labelAwayPlayer2,
          button,
          winnerSelection.labelWinner,
          winnerSelection.radioButtonHome,
          winnerSelection.radioButtonAway
        )
        tab.setContent(groupNew)
      }

      val homeRatingPlayer1 = textFieldHomePlayer1.text.value.toDouble
      val homeRatingPlayer2 = textFieldHomePlayer2.text.value.toDouble
      val homeRating = (homeRatingPlayer1 + homeRatingPlayer2) / 2.0

      val awayRatingPlayer1 = textFieldAwayPlayer1.text.value.toDouble
      val awayRatingPlayer2 = textFieldAwayPlayer2.text.value.toDouble
      val awayRating = (awayRatingPlayer1 + awayRatingPlayer2) / 2.0

      val q = 1.824
      val K = 0.275
      val probHome =
        (1 / (1.0 + math.exp(-q * (awayRating - homeRating))) * 100).toInt

      val homeRatingNew =
        homeRating + K * (probHome / 100.0 - winnerSelection.radioButtonHome.isSelected
          .compare(false)
          .toInt)

      val awayRatingNew =
        awayRating + K * ((100 - probHome) / 100.0 - winnerSelection.radioButtonAway.isSelected
          .compare(false)
          .toInt)

      val homeRatingDiff = homeRatingNew - homeRating
      val awayRatingDiff = awayRatingNew - awayRating

      labelWinnerCalcsHome.text =
        f"$homeRating%n$probHome%%%n${homeRatingPlayer1 + homeRatingDiff}%1.4f%n${homeRatingPlayer2 + homeRatingDiff}%1.4f%n$homeRatingDiff%1.4f"
      labelWinnerCalcsAway.text =
        f"$awayRating%n${100 - probHome}%%%n${awayRatingPlayer1 + awayRatingDiff}%1.4f%n${awayRatingPlayer2 + awayRatingDiff}%1.4f%n$awayRatingDiff%1.4f"
    }

  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {

      title = "Speelsterkte rekentool"
      scene = new Scene(600, 400) {

        val tabPane = new TabPane
        val singlesTab = new Tab()
        createSinglesTab(singlesTab)

        val doublesTab = new Tab()
        createDoublesTab(doublesTab)

        tabPane.tabs = List(singlesTab, doublesTab)

        root = tabPane
      }
    }
  }
}
