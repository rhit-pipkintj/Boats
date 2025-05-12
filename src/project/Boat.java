package project;

public class Boat {
  private int x;
  private int y;
  private int speedX;
  private int speedY;
  // will want to set up an actual img for the boat to appear, as well as a draw class.

  public Boat(int spawnX, int spawnY){
    x = spawnX;
    y = spawnY;
  }

  public Move(){
    x += speedX;
    y += speedY;
  }

  public setSpeed(int xSpeed, int ySpeed){
    //will want to use for actionListeners. the integers can be zero.
    speedX = xSpeed;
    speedY = ySpeed;
  }

}
