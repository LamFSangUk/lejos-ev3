package maze_escape;

public class save_path {
	public static enum Direction {
		RIGHT(0),UP(1),LEFT(2),DOWN(3);
		private final int value;
		private Direction(int value){
			this.value=value;
		}
		public int getValue(){
			return value;
		}
	
	}
	public static Direction[] path = new Direction[10000];
	public static int index=0;
	public static Direction current_dir;
	public static int angle=0;
	public static void changeDirection(int angle)
	{
		angle = angle % 360;
		if( (0<=angle && angle <45) || (315 <= angle && angle <360) ){
			current_dir=Direction.RIGHT;
		}
		else if(45<=angle && angle <135){
			current_dir=Direction.UP;
		}
		else if(135<=angle && angle <225){
			current_dir=Direction.LEFT;
		}
		if(225<=angle && angle <315){
			current_dir=Direction.DOWN;
		}
	}
	
	public static void saveToPath(Direction direction){
		path[index++]=direction;
	}

}
