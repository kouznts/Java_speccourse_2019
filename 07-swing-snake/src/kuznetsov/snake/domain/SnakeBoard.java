package kuznetsov.snake.domain;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import kuznetsov.snake.service.SnakeConfiguration;

public class SnakeBoard extends JPanel implements ActionListener {
  private final int dotSize;

  private final SnakeApple apple;
  private final Random random;

  private Snake snake;

  private boolean isPlaying;
  private Timer timer;

  public SnakeBoard(final SnakeConfiguration snakeConfiguration) {
    dotSize = snakeConfiguration.getDotSize();
    apple = new SnakeApple(new ImageIcon(snakeConfiguration.getAppleImageLocation()).getImage());
    random = new Random();
    snake = new Snake(new ImageIcon(snakeConfiguration.getSnakeDotImageLocation()).getImage(),
        dotSize, snakeConfiguration.getAllDotsNumber(), getWidth() / 2);
    isPlaying = true;
    timer = new Timer(100, this);

    addKeyListener(new FieldKeyListener());

    setSize(new Dimension(snakeConfiguration.getWindowSizePerDimension(),
        snakeConfiguration.getWindowSizePerDimension()));
    setPreferredSize(getSize());
    setBackground(Color.black);
    setFocusable(true);
    setVisible(true);

    startGame();
  }

  private void startGame() {
    isPlaying = true;
    timer.start();

    snake = new Snake(
        snake.getSnakeDotImage(),
        dotSize,
        snake.getMaxDotsNumber(),
        getWidth() / 2);

    randomAppleCoords();
  }

  private void randomAppleCoords() {
    apple.setX(random.nextInt(getWidth() / dotSize) * dotSize);
    apple.setY(random.nextInt(getHeight() / dotSize) * dotSize);
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (isPlaying
        && !isBadCollision()) {

      if (isAppleCollision()) {
        snake.incSize();
        randomAppleCoords();
      }

      snake.move();
    }

    repaint();
  }

  private boolean isBadCollision() {
    return isSnakeCollision() || isBorderCollision();
  }

  private boolean isSnakeCollision() {
    for (int i = snake.getSize(); i > 0; i--) {
      if (i > 4
          && snake.getX(0) == snake.getX(i)
          && snake.getY(0) == snake.getY(i)) {

        isPlaying = false;
        return true;
      }
    }

    return false;
  }

  private boolean isBorderCollision() {
    if (snake.getX(0) >= getWidth()
        || snake.getX(0) < 0
        || snake.getY(0) >= getHeight()
        || snake.getY(0) < 0) {

      isPlaying = false;
      return true;
    }

    return false;
  }

  private boolean isAppleCollision() {
    return snake.getX(0) == apple.getX()
        && snake.getY(0) == apple.getY();
  }

  @Override
  protected void paintComponent(Graphics gr) {
    super.paintComponent(gr);

    if (isPlaying) {
      drawSnake(gr);
      drawApple(gr);
    } else {
      timer.stop();
      drawGameOver(gr);
    }
  }

  private void drawSnake(Graphics gr) {
    for (int snakeDot = 0; snakeDot < snake.getSize(); snakeDot++) {
      gr.drawImage(snake.getSnakeDotImage(), snake.getX(snakeDot), snake.getY(snakeDot), this);
    }
  }

  private void drawApple(Graphics gr) {
    gr.drawImage(apple.getImage(), apple.getX(), apple.getY(), this);
  }

  private void drawGameOver(Graphics gr) {
    gr.setColor(Color.WHITE);
    gr.drawString("Игра окончена. Нажмите ENTER, чтобы сыграть ещё", getWidth() / 2,
        getHeight() / 2);
  }

  private class FieldKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent ev) {
      super.keyPressed(ev);
      int key = ev.getKeyCode();

      if (key == KeyEvent.VK_LEFT && !snake.isMovingRight()) {
        snake.setMovingDirection(Snake.Directions.LEFT);
      } else if (key == KeyEvent.VK_RIGHT && !snake.isMovingLeft()) {
        snake.setMovingDirection(Snake.Directions.RIGHT);
      } else if (key == KeyEvent.VK_UP && !snake.isMovingDown()) {
        snake.setMovingDirection(Snake.Directions.UP);
      } else if (key == KeyEvent.VK_DOWN && !snake.isMovingUp()) {
        snake.setMovingDirection(Snake.Directions.DOWN);
      } else if (key == KeyEvent.VK_ENTER && !isPlaying) {
        startGame();
      }
    }
  }
}
