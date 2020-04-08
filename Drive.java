public class Drive {

	public static void main(String[] args) {

		int numLanes = 3;

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk);
		controlDesk.subscribe( cdv );

	}
}
