package ua.kata

import spock.lang.Specification

class TreeWalkLevelByLevelTest extends Specification {
  def 'walk empty tree'() {
    given: 'an empty tree'
    def tree = Tree.EMPTY_LEAF

    expect: 'an empty string'
    tree.walkLevelByLevel() == ''
  }

  def 'single item tree'() {
    given: 'a tree with root only item'
    def tree = new Tree(1)

    expect: 'a string with root item rendered'
    tree.walkLevelByLevel() == '1'
  }

  def 'two levels tree'() {
    given: 'a tree with two levels of items'
    def tree = new Tree(1, new Tree(2), new Tree(3))

    expect: 'a string with all items of tree'
    tree.walkLevelByLevel() == '1, 2, 3'
  }
}
