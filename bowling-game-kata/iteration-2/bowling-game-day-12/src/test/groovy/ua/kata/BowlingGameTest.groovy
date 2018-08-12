package ua.kata

import spock.lang.Ignore
import spock.lang.Specification

class BowlingGameTest extends Specification {
  private def game

  def setup() {
    game = new BowlingGame()
    game.metaClass.rollMany = { times, pins -> (1..times).forEach { delegate.roll(pins) } }
    game.metaClass.rollSpare = {
      delegate.roll(4)
      delegate.roll(6)
    }
    game.metaClass.rollStrike = { delegate.roll(10) }
  }

  def 'create game'() {
    when: 'game is created'

    then: 'game is not null'
    game != null
  }

  def 'all zeros'() {
    when: 'player rolls all zeros'
    game.rollMany(20, 0)

    then: 'player gets zero points'
    game.score() == 0
  }

  def 'all ones'() {
    when: 'player rolls all ones'
    game.rollMany(20, 1)

    then: 'player gets twenty points'
    game.score() == 20
  }

  def 'one spare'() {
    when: 'player rolls one spare'
    game.rollSpare()
    and: 'rolls some pins'
    game.roll(3)
    and: 'rolls all zeros'
    game.rollMany(17, 0)

    then: 'player gets bonus that equals pins rolled after spare'
    game.score() == 10 + 3 + 3
  }

  def 'one strike'() {
    when: 'player rolls one strike'
    game.rollStrike()
    and: 'rolls some pins two times'
    game.roll(3)
    game.roll(4)
    and: 'rolls all zeros'
    game.rollMany(16, 0)

    then: 'player gets bonus that equals to the next two rolls after strike'
    game.score() == 10 + 3 + 4 + 3 + 4
  }

  def 'perfect game'() {
    when: 'player rolls all strikes'
    game.rollMany(12, 10)

    then: 'player gets max points'
    game.score() == 300
  }
}
