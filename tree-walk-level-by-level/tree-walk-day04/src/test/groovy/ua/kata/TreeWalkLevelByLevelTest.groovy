package ua.kata

import spock.lang.Ignore
import spock.lang.Specification

class TreeWalkLevelByLevelTest extends Specification {
  def 'walk trough empty tree'() {
    given: 'an empty tree'
    def tree = new Tree(null)

    expect: 'the path is an empty string'
    tree.walkLevelByLevel() == ''
  }

  def 'walk through single item tree'() {
    given: 'a tree with single item'
    def tree = new Tree(10)

    expect: 'the path contains tree root'
    tree.walkLevelByLevel() == '10'
  }

  def 'walk through two levels tree'() {
    given: 'a tree with two levels'
    def tree = new Tree(10, new Tree(20), new Tree(30))

    expect: 'the path contains two levels separated by "\n"'
    tree.walkLevelByLevel() == '10\n20, 30'
  }

  def 'walk through three levels tree'() {
    given: 'a tree with three levels'
    def tree = new Tree(
        10,
        new Tree(20, new Tree(40), new Tree(50)),
        new Tree(30, new Tree(60), new Tree(70))
    )

    expect: 'the path contains three levels separated by "\n"'
    tree.walkLevelByLevel() == '10\n20, 30\n40, 50, 60, 70'
  }
}
