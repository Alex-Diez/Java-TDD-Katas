package ua.kata


import spock.lang.Specification

class TreeWalkLevelByLevelTest extends Specification {
  def 'walk empty tree'() {
    given: 'an empty tree'
    def tree = new Tree(null)

    expect: 'the path is an empty string'
    tree.walkLevelByLevel() == '[]'
  }

  def 'walk through one level tree'() {
    given: 'a one level tree'
    def tree = new Tree(10)

    expect: 'the path contains tree\'s root element'
    tree.walkLevelByLevel() == '[[10]]'
  }

  def 'walk through two levels tree'() {
    given: 'a two levels tree'
    def tree = new Tree(10, new Tree(20), new Tree(30))

    expect: 'the path contains both tree\'s levels'
    tree.walkLevelByLevel() == '[[10], [20, 30]]'
  }

  def 'walk through three levels tree'() {
    given: 'a three levels tree'
    def tree = new Tree(
        10,
        new Tree(
            20,
            new Tree(40), new Tree(50)
        ),
        new Tree(
            30,
            new Tree(60), new Tree(70)
        )
    )

    expect: 'the path contains both tree\'s levels'
    tree.walkLevelByLevel() == '[[10], [20, 30], [40, 50, 60, 70]]'
  }
}
