package ua.kata


import spock.lang.Specification

class BowlingGameTest extends Specification {
  private def game

  def setup() {
    game = new BowlingGame() as BowlingGameExt
  }

  def 'gutter game'() {
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
    and: 'then rolls some points two times'
    game.roll(3)
    game.roll(4)
    and: 'then rolls all zeros'
    game.rollMany(16, 0)

    then: 'player gets bonus that equals to the next two rolls after strike'
    game.score() == 10 + 4 + 3 + 4 + 3
  }

  def 'perfect game'() {
    when: 'player rolls all strike'
    game.rollMany(12, 10)

    then: 'player gets max points'
    game.score() == 300
  }

  trait BowlingGameExt {
    def rollMany(times, pins) {
      (1..times).forEach { roll(pins) }
    }

    def rollSpare() {
      roll(4)
      roll(6)
    }

    def rollStrike() {
      roll(10)
    }
  }
}
