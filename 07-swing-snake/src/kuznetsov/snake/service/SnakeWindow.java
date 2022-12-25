package kuznetsov.snake.service;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import kuznetsov.snake.domain.SnakeBoard;

public final class SnakeWindow extends JFrame {
  public SnakeWindow(final SnakeConfiguration snakeConfiguration) {
    super("Змейка");

    getContentPane().add(new SnakeBoard(snakeConfiguration));
    pack();

    setWindow();
  }

  private void setWindow() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationInCenter();
    setVisible(true);
  }

  private void setLocationInCenter() {
    setLocationRelativeTo(null);

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(
        screenSize.width / 2 - this.getSize().width / 2,
        screenSize.height / 2 - this.getSize().height / 2);
  }
}
