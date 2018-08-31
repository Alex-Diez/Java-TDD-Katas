package ua.kata

import spock.lang.Specification


class TreeWalkLevelByLevelTest extends Specification {
  def 'walking empty tree'() {
    given: 'an empty tree'
    def tree = new Tree(null)

    expect: 'the path is an empty string'
    tree.walkLevelByLevel() == ''
  }

  def 'walking single item tree'() {
    given: 'a single item tree'
    def tree = new Tree(10)

    expect: 'root value in the path'
    tree.walkLevelByLevel() == '10'
  }

  def 'walking two levels tree'() {
    given: 'a tree with two levels'
    def tree = new Tree(10, new Tree(20), new Tree(30))

    expect: 'second level items in the path from left to right'
    tree.walkLevelByLevel() == '10, 20, 30'
  }
}
