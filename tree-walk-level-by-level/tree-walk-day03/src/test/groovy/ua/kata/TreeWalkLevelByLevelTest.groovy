package ua.kata


import spock.lang.Specification

class TreeWalkLevelByLevelTest extends Specification {
  def 'walk empty tree'() {
    given: 'an empty tree'
    def tree = new Tree(null)

    expect: 'the path is an empty string'
    tree.walkLevelByLevel() == ''
  }

  def 'walk single item tree'() {
    given: 'a tree with root item'
    def tree = new Tree(10)

    expect: 'the path contains tree root item'
    tree.walkLevelByLevel() == '10'
  }

  def 'walk two levels tree'() {
    given: 'a tree with two levels'
    def tree = new Tree(10, new Tree(20), new Tree(30))

    expect: 'the path contains second tree level from left to right'
    tree.walkLevelByLevel() == '10, 20, 30'
  }

  def 'walk three levels tree'() {
    given: 'a tree with three levels'
    def tree = new Tree(10, new Tree(20, new Tree(40), new Tree(50)), new Tree(30, new Tree(60), new Tree(70)))

    expect: 'the path contains second then third level from left to right'
    tree.walkLevelByLevel() == '10, 20, 30, 40, 50, 60, 70'
  }
}
