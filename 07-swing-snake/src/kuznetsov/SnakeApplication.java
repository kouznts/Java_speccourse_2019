package kuznetsov;

import kuznetsov.snake.service.SnakeConfiguration;
import kuznetsov.snake.service.SnakeWindow;

public class SnakeApplication {
  public static void main(String[] args) {
    new SnakeWindow(new SnakeConfiguration(
        SnakeConfiguration.DOT_SIZE,
        SnakeConfiguration.DOTS_NUMBER_PER_DIMENSION,
        SnakeConfiguration.SNAKE_DOT_IMAGE_LOCATION,
        SnakeConfiguration.APPLE_IMAGE_LOCATION
    ));
  }
}
