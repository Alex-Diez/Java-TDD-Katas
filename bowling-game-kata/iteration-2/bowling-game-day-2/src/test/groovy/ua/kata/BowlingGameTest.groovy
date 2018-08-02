package ua.kata

import spock.lang.Ignore
import spock.lang.Specification

class BowlingGameTest extends Specification {
  def game

  def setup() {
    game = new BowlingGame()

    game.metaClass.rollMany = { times, pin -> (1..times).forEach { delegate.roll(pin) } }
    game.metaClass.rollSpare = { delegate.roll(4); delegate.roll(6) }
    game.metaClass.rollStrike = { delegate.roll(10) }
  }

  def 'gutter game'() {
    when: 'player rolls 0 pins in each frame'
    game.rollMany(20, 0)

    then: 'game score is 0'
    game.score() == 0
  }

  def 'all ones'() {
    when: 'player rolls 1 pins in each frame'
    game.rollMany(20, 1)

    then: 'game score is 20'
    game.score() == 20
  }

  def 'one spare'() {
    when: 'player rolls spare'
    game.rollSpare()
    and: 'rolls some pins'
    game.roll(3)
    and: 'rolls 0 in the next frames'
    game.rollMany(17, 0)

    then: 'player has bonus of the next roll after spare'
    game.score() == 10 + 3 + 3
  }

  def 'one strike'() {
    when: 'player rolls strike'
    game.rollStrike()
    and: 'rolls some pins two rolls in row'
    game.roll(3)
    game.roll(4)
    and: 'rolls 0 in the next frames'
    game.rollMany(16, 0)

    then: 'player has bonus of the two next rolls after strike'
    game.score() == 10 + 3 + 4 + 3 + 4
  }

  def 'perfect game'() {
    when: 'player rolls all strikes'
    game.rollMany(12, 10)

    then: 'player gets max score - 300'
    game.score() == 300
  }
}
