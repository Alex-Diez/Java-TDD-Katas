package ua.kata

import spock.lang.Ignore
import spock.lang.Specification

class TreeWalkLevelByLevelTest extends Specification {
  def 'walk through empty tree'() {
    given: 'an empty tree'
    def tree = new Tree(null)

    expect: 'the path contains no items'
    tree.walkLevelByLevel() == '[]'
  }

  def 'walk through one level tree'() {
    given: 'a single level tree'
    def tree = new Tree(10)

    expect: 'the path contains item from first level'
    tree.walkLevelByLevel() == '[[10]]'
  }

  def 'walk through two levels tree'() {
    given: 'a two levels tree'
    def tree = new Tree(10, new Tree(20), new Tree(30))

    expect: 'the path contains items from both tree\'s levels'
    tree.walkLevelByLevel() == '[[10], [20, 30]]'
  }
}
