package utils;

interface UseRandomProps {
  public int randomSpeed();

}

public class useRandom implements UseRandomProps {
  public int randomSpeed() {
    return (int) (Math.random() * 5) - 1;

  }

}
