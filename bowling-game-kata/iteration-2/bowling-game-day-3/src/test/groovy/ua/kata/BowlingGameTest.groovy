package ua.kata

import spock.lang.Ignore
import spock.lang.Specification

class BowlingGameTest extends Specification {
  def game

  def setup() {
    game = new BowlingGame()
    game.metaClass.rollMany = { times, pin -> (1..times).forEach { delegate.roll(pin) } }
    game.metaClass.rollSpare = { delegate.roll(4); delegate.roll(6) }
  }

  def 'gutter game'() {
    when: 'player rolls all zeros'
    game.rollMany(20, 0)

    then: 'player has 0 points'
    game.score() == 0
  }

  def 'all ones'() {
    when: 'player rolls all ones'
    game.rollMany(20, 1)

    then: 'player has 20 points'
    game.score() == 20
  }

  def 'one spare'() {
    when: 'player rolls spare'
    game.rollSpare()
    and: 'rolls some pins'
    game.roll(3)
    and: 'rolls all zeros'
    game.rollMany(17, 0)

    then: 'player gets bonus of the next roll after spare frame'
    game.score() == 10 + 3 + 3
  }

  def 'one strike'() {
    when: 'player rolls strike'
    game.roll(10)
    and: 'rolls pins on the next two rolls'
    game.roll(4)
    game.roll(3)
    and: 'rolls all zeros'
    game.rollMany(16, 0)

    then: 'player gets bonus of the next two rolls after strike frame'
    game.score() == 10 + 4 + 3 + 4 + 3
  }

  def 'perfect game'() {
    when: 'player rolls all strikes'
    game.rollMany(12, 10)

    then: 'player gets max game score'
    game.score() == 300
  }
}
