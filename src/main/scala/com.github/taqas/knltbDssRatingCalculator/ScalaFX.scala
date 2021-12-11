package com.github.taqas.knltbDssRatingCalculator

import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.paint.Color._
import scalafx.scene.paint._
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

object ScalaFX extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {

      title = "Speelsterkte rekentool"
      scene = new Scene(600, 400) {

        val labelWinner = new Label("Winnaar")
        labelWinner.layoutX = 30
        labelWinner.layoutY = 20

        val radioButtonHome = new RadioButton("Thuisspeler")
        radioButtonHome.layoutX = 230
        radioButtonHome.layoutY = 20
        radioButtonHome.selected = true

        val radioButtonAway = new RadioButton("Uitspeler")
        radioButtonAway.layoutX = 330
        radioButtonAway.layoutY = 20
        val toggleGroup = new ToggleGroup()
        toggleGroup.toggles = List(radioButtonHome, radioButtonAway)

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
          labelWinner,
          radioButtonHome,
          radioButtonAway
        )

        val tabPane = new TabPane
        val singlesTab = new Tab()
        singlesTab.text = "Enkelspel"
        singlesTab.setClosable(false)

        singlesTab.setContent(group)
        val doublesTab = new Tab
        doublesTab.text = "Dubbelspel"
        doublesTab.setClosable(false)
        tabPane.tabs = List(singlesTab, doublesTab)

        root = tabPane

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
              labelWinner,
              radioButtonHome,
              radioButtonAway
            )
            singlesTab.setContent(groupNew)
          }

          val homeRating = textFieldHomePlayer.text.value.toDouble
          val awayRating = textFieldAwayPlayer.text.value.toDouble

          val q = 1.824
          val K = 0.275
          val probHome =
            (1 / (1.0 + math.exp(-q * (awayRating - homeRating))) * 100).toInt

          val homeRatingNew =
            homeRating + K * (probHome / 100.0 - radioButtonHome.isSelected
              .compare(false)
              .toInt)

          val awayRatingNew =
            awayRating + K * ((100 - probHome) / 100.0 - radioButtonAway.isSelected
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
    }
  }
}
