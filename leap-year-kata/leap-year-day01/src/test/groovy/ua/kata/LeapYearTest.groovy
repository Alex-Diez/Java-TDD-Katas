package ua.kata

import spock.lang.Specification

class LeapYearTest extends Specification {
  def 'year is leap when divisible by 4'() {
    given: 'a val that is divisible by 4'
    def year = new Year(4 * 100 + 4)

    expect: 'val is leap'
    year.isLeap()
  }

  def 'year is not leap when not divisible by 4'() {
    given: 'a val that is not divisible by 4'
    def year = new Year(4 * 100 + 1)

    expect: 'val is not leap'
    year.isNotLeap()
  }

  def 'year is not leap when divisible by 4 and 100'() {
    given: 'a val that is divisible by 4 and 100'
    def year = new Year(4 * 100 + 100)

    expect: 'val is not leap'
    year.isNotLeap()
  }

  def 'year is leap when divisible by 400'() {
    given: 'a val that is divisible by 400'
    def year = new Year(4 * 100)

    expect: 'val is leap'
    year.isLeap()
  }
}
