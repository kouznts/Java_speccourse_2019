package kuznetsov.snake.domain;

import java.awt.Image;
public final class Snake {
  private final Image snakeDotImage;
  private final int dotSize;
  private final int[] xs;
  private final int[] ys;
  private int size;
  private Directions movingDirection;

  public Snake(Image snakeDotImage,
               final int dotSize,
               final int maxSize,
               final int startX) {

    this.snakeDotImage = snakeDotImage;
    this.dotSize = dotSize;
    size = 3;

    xs = new int[maxSize];
    ys = new int[xs.length];
    initCoords(startX);

    movingDirection = Directions.RIGHT;
  }

  private void initCoords(final int startX) {
    for (int dotIndex = 0; dotIndex < getSize(); dotIndex++) {
      xs[dotIndex] = startX - dotIndex * dotSize;
      ys[dotIndex] = startX;
    }
  }

  public Image getSnakeDotImage() {
    return snakeDotImage;
  }

  public int getSize() {
    return size;
  }

  public void incSize() {
    size++;
  }

  public int getX(int index) {
    return xs[index];
  }

  public int getY(int index) {
    return ys[index];
  }

  public void setX(int index, int value) {
    xs[index] = value;
  }

  public void setY(int index, int value) {
    ys[index] = value;
  }

  public void move() {
    moveTail();
    moveHead();
  }

  private void moveTail() {
    for (int i = getSize(); i > 0; i--) {
      setX(i, getX(i - 1));
      setY(i, getY(i - 1));
    }
  }

  private void moveHead() {
    if (isMovingLeft()) {
      xs[0] -= dotSize;
    } else if (isMovingRight()) {
      xs[0] += dotSize;
    } else if (isMovingUp()) {
      ys[0] -= dotSize;
    } else if (isMovingDown()) {
      ys[0] += dotSize;
    }
  }

  public void setMovingDirection(Directions direction) {
    movingDirection = direction;
  }

  public boolean isMovingLeft() {
    return movingDirection == Directions.LEFT;
  }

  public boolean isMovingUp() {
    return movingDirection == Directions.UP;
  }

  public boolean isMovingRight() {
    return movingDirection == Directions.RIGHT;
  }

  public boolean isMovingDown() {
    return movingDirection == Directions.DOWN;
  }

  public int getMaxDotsNumber() {
    return xs.length;
  }

  public enum Directions {
    LEFT, UP, RIGHT, DOWN
  }
}
