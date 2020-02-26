package frc.robot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SigmoidTest {
  @Test
  @DisplayName("First Sigmoid")
  void sigmoid0() {
    Sigmoid sigmoid = new Sigmoid(1.0, 1.0, 1.0, false, 0, 0);
    assertEquals(0.5, sigmoid.calculate(0), 0.01, "(0, 0.5)");
    assertEquals(1, sigmoid.calculate(40), 0.01, "(40, 1)");
  }

  @Test
  @DisplayName("Second Sigmoid")
  void simgoid1() {
    Sigmoid sigmoid = new Sigmoid(2.0, 2.0, 2.0, false, 0, 0);
    assertEquals(2, sigmoid.calculate(0), 0.01, "(0, 2)");
    assertEquals(2.49, sigmoid.calculate(1), 0.01, "(1, 2.49)");
    assertEquals(3.81, sigmoid.calculate(6), 0.01, "(6, 3.81)");
  }
}
