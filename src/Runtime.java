public class Runtime {
	
	public static void main(String[] args) {

		Window tempWindow = new Window();
		tempWindow.active();
		
		new Thread() {
    		@Override
            public void run() {
    			try {
    				while(!isInterrupted()) {
    					tempWindow.update();
    					sleep(1000/60);
        			}
    			} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	}.start();

	}
	
}
