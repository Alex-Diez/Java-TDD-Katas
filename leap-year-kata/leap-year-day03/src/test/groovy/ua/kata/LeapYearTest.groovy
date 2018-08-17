package ua.kata

import spock.lang.Specification

class LeapYearTest extends Specification {
  def 'leap year is divisible by 4'() {
    given: 'a year that is divisible by 4'
    def year = new Year(4 * 100 + 4)

    expect: 'that year is a leap year'
    year.leap
  }

  def 'year is not leap when is not divisible by 4'() {
    given: 'a year that is not divisible by 4'
    def year = new Year(4 * 100 + 1)

    expect: 'that year is not a leap year'
    year.notLeap
  }

  def 'year is not leap when is divisible by 4 and 100'() {
    given: 'a year that is divisible by 4 and 100'
    def year = new Year(4 * 100 + 100)

    expect: 'that year is not a leap year'
    year.notLeap
  }

  def 'year is leap when is divisible by 400'() {
    given: 'a year that is divisible by 400'
    def year = new Year(4 * 100)

    expect: 'that year is a leap year'
    year.leap
  }
}
