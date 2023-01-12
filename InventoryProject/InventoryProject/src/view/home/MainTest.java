package view.home;

public class MainTest {

	public static void main(String[] args) 
	{
		try 
		{
			Splash frame = new Splash();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			Thread.sleep(2200);
			
			frame.setVisible(false);
			
			LoginForm lForm = new LoginForm();
			lForm.setLocationRelativeTo(null);
			lForm.setVisible(true);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
