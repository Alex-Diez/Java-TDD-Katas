package ua.kata


import spock.lang.Specification

class BowlingGameTest extends Specification {
  private def game

  def setup() {
    game = new BowlingGame() as BowlingGameExt
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

    then: 'player gets bonus equals to the next rolled pins after spare'
    game.score() == 10 + 3 + 3
  }

  def 'one strike'() {
    when: 'player rolls one strike'
    game.rollStrike()
    and: 'rolls some pins two times'
    game.roll(4)
    game.roll(3)
    and: 'rolls all zeros'
    game.rollMany(16, 0)

    then: 'player gets bonus equals to the next two rolled pins after strike'
    game.score() == 10 + 4 + 3 + 4 + 3
  }

  def 'perfect game'() {
    when: 'player rolls all strikes'
    game.rollMany(12, 10)

    then: 'player gets max game score'
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
