import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class Window extends JFrame{
	
	ButtonStyled pauseButton;
	
	public Window(){
		super();
		this.initialize();
		this.create();
		this.refresh();
	}

	protected void initialize(){
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setSize(812, 624 + 35);
	}
	
	protected void create() {
		ViewMusic viewMusic = new ViewMusic();
		viewMusic.setBounds(0, 35, 812, 624);
		this.add(viewMusic);
		
		int placeX = 662;
		this.pauseButton = new ButtonStyled(GraphicsController.DARK_HOVER_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				super.onMouseClicked(e);
				if(MusicController.getPlayStatus() > 1) {
					MusicController.replay();
				}else if(MusicController.getPlayStatus() > 0) {
					MusicController.pause();
				}
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				if(MusicController.getPlayStatus() > 1) {
					graphics.setColor(new Color(0, 150, 0));
					graphics.fillPolygon(new int[]{18, 18, 33}, new int[]{9, 25, 17}, 3);
				} else if(MusicController.getPlayStatus() > 0) {
					graphics.setColor(new Color(150, 0, 0));
					graphics.fillRect(17, 10, 5, 15);
					graphics.fillRect(27, 10, 5, 15);
				}
			}
		};
		this.pauseButton.setBounds(placeX, 0, 50, 35);
		this.add(this.pauseButton);
		
		placeX += 50;
		ButtonStyled playButton= new ButtonStyled(GraphicsController.DARK_HOVER_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				super.onMouseClicked(e);
				if(MusicController.getPlayStatus() > 0) {
					MusicController.stop();
				}else {
					JFileChooser fileChooser = new JFileChooser("./");
	    			FileFilter filter = new FileNameExtensionFilter("WAV Format", "wav");
	    			fileChooser.addChoosableFileFilter(filter);
	    			fileChooser.setAcceptAllFileFilterUsed(false);
					if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
						MusicController.play(fileChooser.getSelectedFile().getPath());
					}
				}
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				if(MusicController.getPlayStatus() > 0) {
					graphics.setColor(new Color(150, 0, 0));
					graphics.fillRect(17, 10, 15, 15);
				}else {
					graphics.setColor(new Color(0, 150, 0));
					graphics.fillPolygon(new int[]{18, 18, 33}, new int[]{9, 25, 17}, 3);
				}
			}
		};
		playButton.setBounds(placeX, 0, 50, 35);
		this.add(playButton);
		
		placeX += 50;
		ButtonStyled closeButton= new ButtonStyled(GraphicsController.WARNING_COLOR, GraphicsController.EMPTY_COLOR, GraphicsController.DARK_TOUCH_COLOR) {
			@Override
			protected void onMouseClicked(MouseEvent e) {
				System.exit(0);
			}
			@Override
			protected void paint(Graphics2D graphics) {
				super.paint(graphics);
				graphics.setColor(Color.BLACK);
				graphics.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
				graphics.drawLine(17 + 1, 10 + 1, 32 - 1, 25 - 1);
				graphics.drawLine(17 + 1, 25 - 1, 32 - 1, 10 + 1);
			}
		};
		closeButton.setBounds(placeX, 0, 50, 35);
		this.add(closeButton);
		
		ButtonStyled moveView = new ButtonStyled() {
			int startX, startY;
			
			@Override
			protected void onMousePressed(MouseEvent e) {
				super.onMousePressed(e);
				startX = e.getXOnScreen();
                startY = e.getYOnScreen();
			}
			
	        @Override
	        protected void onMouseDragged(MouseEvent e) {
	        	super.onMouseDragged(e);
	        	int deltaX = e.getXOnScreen() - startX;
	        	int deltaY = e.getYOnScreen() - startY;
	        	Point place = Window.this.getLocationOnScreen();
	        	Window.this.setLocation(place.x + deltaX, place.y + deltaY);
	        	startX = e.getXOnScreen();
                startY = e.getYOnScreen();
	        }
	        
	        @Override
	        public void paint(Graphics2D graphics) {
	        	super.paint(graphics);
	        	if(MusicController.getPlayStatus() > 0) {
		    		graphics.setColor(MusicController.getPlayColor());
		    		graphics.fillRoundRect(this.shade, this.shade, this.getWidth() - this.shade * 2, this.getHeight() - this.shade * 2, this.radius * 2, this.radius * 2);
	        	}
	        }
	        
	    	@Override
	    	public void update() {
	    		super.update();
	    		if(MusicController.getPlayStatus() > 0) {
	    			this.revalidate();
	    			this.repaint();
	    		}
	    	}
	        
		};
		moveView.setBounds(0, 0, 812, 35);
		moveView.setText("VMP Station");
		this.add(moveView);
	}

	/**
	 * Update interface.
	 **/
	public void update() {
		this.pauseButton.setVisible(MusicController.getPlayStatus() > 0);
		for (Component c: this.getContentPane().getComponents()) {
			Controllable.updateChild(c);
		}
	}

	public void refresh() {
		this.revalidate();
		this.repaint();
		for (Component c: this.getContentPane().getComponents()) {
			Controllable.refreshChild(c);
		}
	}
    
	public void active() {
		this.setVisible(true);
	}
	
	public void deactive() {
		this.setVisible(false);
	}
	
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setLocationRelativeTo(null);
    }

}
