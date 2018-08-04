package ua.kata

import spock.lang.Ignore
import spock.lang.Specification


class BowlingGameTest extends Specification {
  private def game

  def setup() {
    game = new BowlingGame()
    game.metaClass.rollMany = { times, pin -> (1..times).forEach { delegate.roll(pin) } }
    game.metaClass.rollSpare = { delegate.roll(4); delegate.roll(6) }
    game.metaClass.rollStrike = { delegate.roll(10) }
  }

  def 'gutter game'() {
    when: 'player rolls all zeros'
    game.rollMany(20, 0)

    then: 'player gets 0 points'
    game.score() == 0
  }

  def 'all ones'() {
    when: 'player rolls all ones'
    game.rollMany(20, 1)

    then: 'player gets 20 points'
    game.score() == 20
  }

  def 'one spare'() {
    when: 'player rolls spare'
    game.rollSpare()
    and: 'then rolls some pins'
    game.roll(3)
    and: 'then rolls all zeros'
    game.rollMany(17, 0)

    then: 'player gets bonus that equals to the next roll after spare'
    game.score() == 10 + 3 + 3
  }

  def 'one strike'() {
    when: 'player rolls strike'
    game.rollStrike()
    and: 'then rolls some pins in the next two rolls'
    game.roll(3)
    game.roll(4)
    and: 'then rolls all zeros'
    game.rollMany(16, 0)

    then: 'player gets bonus that equals to the two next rolls after strike'
    game.score() == 10 + 3 + 4 + 3 + 4
  }

  def 'perfect game'() {
    when: 'player rolls all strikes'
    game.rollMany(12, 10)

    then: 'player gets max score'
    game.score() == 300
  }
}
