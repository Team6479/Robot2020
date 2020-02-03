package frc.robot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SigmoidTest {
  @Test
  @DisplayName("1 + 1 = 2")
  void calculateSigmoid() {
    Sigmoid sigmoid = new Sigmoid(1.0, 1.0, 1.0, true, 0, 0);
    assertEquals(0.5, sigmoid.calculate(0), "");
    assertEquals(1, sigmoid.calculate(40), "40 -> 1");
  }
}
