package ua.kata

import spock.lang.Specification

class LeapYearTest extends Specification {
  def 'year is a leap when divisible by 4'() {
    given: 'a year divisible by 4'
    def year = new Year(4 * 100 + 4)

    expect: 'year is a leap year'
    year.isLeap()
  }

  def 'year is not a leap when not divisible by 4'() {
    given: 'a year not divisible by 4'
    def year = new Year(4 * 100 + 1)

    expect: 'year is not a leap year'
    year.isNotLeap()
  }

  def 'year is not a leap when divisible by 4 and 100'() {
    given: 'a year divisible by 4 and 100'
    def year = new Year(4 * 100 + 100)

    expect: 'year is not a leap year'
    year.isNotLeap()
  }

  def 'year is a leap when divisible by 400'() {
    given: 'a year divisible by 400'
    def year = new Year(4 * 100)

    expect: 'year is a leap year'
    year.isLeap()
  }
}
