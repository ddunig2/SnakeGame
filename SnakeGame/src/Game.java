
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean running, onTheMove;
	private JPanel contentPane, gamePanel, menuPanel;
	private JButton menu, playGame, ok, cancel, viewHS, pause, exit, reset;
	private JFrame menuFrame;
	private JTextField name;
	private JLabel scoreL, nameL, PauseL;
	private Point grub;
	private ArrayList<Point> snake = new ArrayList<>();
	private Thread movingT;
	private int direction, score;
	private String pName;
	private boolean pauseB;

	public Game() {
		mainMenu();
		setBounds(0, 0, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		Font pauseF = new Font("TimesRoman", Font.BOLD, 72);
		score = 0;
		PauseL = new JLabel("Paused");
		PauseL.setFont(pauseF);
		PauseL.setForeground(new Color(150, 150, 150, 50));
		pause = new JButton("Pause");
		pause.setBounds(550, 160, 100, 30);
		pause.setFocusable(false);
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseGame();
			}
		});
		reset = new JButton("Reset");
		reset.setBounds(550, 200, 100, 30);
		reset.setFocusable(false);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				snake.clear();
				Point p = new Point();
				p.setBounds(0, 0, 10, 10);
				snake.add(p);
				gamePanel.removeAll();
				gamePanel.add(snake.get(0));
				score = 0;
				scoreL.setText("Score: " + score);
				direction = 0;
				gamePanel.setBorder(BorderFactory.createLineBorder(Color.green));
				placeGrub();
				repaint();
				revalidate();
				movingT.resume();
			}
		});
		exit = new JButton("Exit");
		exit.setBounds(550, 240, 100, 30);
		exit.setFocusable(false);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				// TODO Auto-generated method stub

			}
		});
		menu = new JButton("Main Menu");
		menu.setBounds(550, 120, 100, 30);
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				menuFrame.setVisible(true);
			}
		});
		Font f = new Font("TimesRoman", Font.BOLD, 25);
		scoreL = new JLabel("Score: " + score);
		scoreL.setForeground(Color.WHITE);
		scoreL.setBounds(550, 90, 150, 20);
		scoreL.setFont(f);
		nameL = new JLabel();
		nameL.setFont(f);
		nameL.setBounds(550, 60, 250, 20);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		JLabel bg = new JLabel(new ImageIcon("dogImage.jpg"));
		bg.setBounds(0, 0, 800, 700);
		contentPane.add(bg);
		gamePanel = new JPanel();
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.green));
		gamePanel.setLayout(null);
		gamePanel.setBackground(new Color(0, 0, 0, 200));
		gamePanel.setBounds(10, 60, 500, 500);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gamePanel.requestFocus();
		gamePanel.add(PauseL);
		PauseL.setBounds((gamePanel.getWidth() / 2) - 130, (gamePanel.getHeight() / 2) - 35, 260, 70);
		PauseL.setVisible(false);
		menu.setFocusable(false);
		bg.add(gamePanel);
		bg.add(menu);
		bg.add(pause);
		bg.add(reset);
		bg.add(exit);
		bg.add(scoreL);
		bg.add(nameL);
		setContentPane(contentPane);
		Point p = new Point();
		p.setBounds(0, 0, 10, 10);
		snake.add(p);
		gamePanel.add(snake.get(0));
		gamePanel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (!pauseB) {
					if (code == 37 && direction != 3) {
						direction = 1;
					} else if (code == 38 && direction != 4) {
						direction = 2;
					} else if (code == 39 && direction != 1) {
						direction = 3;
					} else if (code == 40 && direction != 2) {
						direction = 4;
					}
					
				}
				if(e.getKeyChar() == KeyEvent.VK_SPACE) {
					pauseGame();
				}
			}
		});
		
	}
	public void pauseGame() {
		if (pause.getText().equals("Pause")) {
			movingT.suspend();
			pause.setText("Resume");
			PauseL.setVisible(true);
			repaint();
			revalidate();
			pauseB = true;

		} else {
			movingT.resume();
			pause.setText("Pause");
			PauseL.setVisible(false);
			repaint();
			revalidate();
			pauseB = false;
		}
	}

	public void start() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while (running) {
					System.out.println("ok1");
				}
			}
		});
		thread.start();
		menuFrame.setVisible(true);
		placeGrub();
		moving();

	}

	public void update() {

	}

	public void gameLoop() {

	}

	public void move() {
		// left
		if (direction == 1 && !collision(10, 0, 0, 0)) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setBounds(snake.get(i - 1).getBounds());
			}
			snake.get(0).setBounds(snake.get(0).getX() - 10, snake.get(0).getY(), snake.get(0).getWidth(),
					snake.get(0).getHeight());
		}
		// up
		else if (direction == 2 && !collision(0, 10, 0, 0)) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setBounds(snake.get(i - 1).getBounds());
			}
			snake.get(0).setBounds(snake.get(0).getX(), snake.get(0).getY() - 10, snake.get(0).getWidth(),
					snake.get(0).getHeight());

		}
		// right
		else if (direction == 3 && !collision(0, 0, 20, 0)) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setBounds(snake.get(i - 1).getBounds());
			}
			snake.get(0).setBounds(snake.get(0).getX() + 10, snake.get(0).getY(), snake.get(0).getWidth(),
					snake.get(0).getHeight());

		}
		// down
		else if (direction == 4 && !collision(0, 0, 0, 20)) {
			for (int i = snake.size() - 1; i > 0; i--) {
				snake.get(i).setBounds(snake.get(i - 1).getBounds());
			}
			snake.get(0).setBounds(snake.get(0).getX(), snake.get(0).getY() + 10, snake.get(0).getWidth(),
					snake.get(0).getHeight());
		}
		if (yum()) {
			gamePanel.remove(grub);
			placeGrub();
			addTail();
		}
		repaint();
		revalidate();

	}

	public boolean collision(int L, int U, int R, int D) {
		if (snake.get(0).getX() - L < 0 || snake.get(0).getX() + R > gamePanel.getWidth()) {
			direction = 0;

			gameOver();
			movingT.suspend();
			return true;
		} else if (snake.get(0).getY() - U < 0 || snake.get(0).getY() + D > gamePanel.getHeight()) {
			direction = 0;
			gameOver();
			movingT.suspend();
			return true;
		}
		return false;
	}

	public void gameOver() {
		gamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), "Game Over",
				TitledBorder.CENTER, TitledBorder.BELOW_TOP, new Font("TimesRoman", Font.BOLD, 14), Color.white));
		repaint();
		revalidate();
	}

	public void moving() {
		onTheMove = true;
		movingT = new Thread(new Runnable() {
			public void run() {
				while (onTheMove) {
					pause(100);
					move();
				}
			}
		});
		movingT.start();
	}

	public boolean yum() {
		if (snake.get(0).getX() == grub.getX() && snake.get(0).getY() == grub.getY()) {
			score++;
			scoreL.setText("Score: " + score);
			repaint();
			revalidate();
			return true;
		}
		return false;
	}

	public void addTail() {
		Point p = new Point();
		p.setBorder(BorderFactory.createEtchedBorder());
		p.setBackground(Color.lightGray);
		gamePanel.add(p);
		snake.add(p);
	}

	public void placeGrub() {
		Random rand = new Random();
		int X = (rand.nextInt(49)) * 10;
		int Y = (rand.nextInt(49)) * 10;
		boolean placed;
		while (true) {
			placed = true;
			for (Point p : snake) {
				if (p.getX() == X || p.getY() == Y) {
					X = (rand.nextInt(49)) * 10;
					Y = (rand.nextInt(49)) * 10;
					placed = false;
					break;
				}
			}
			if (placed) {
				grub = new Point();
				grub.setBackground(Color.green);
				grub.setBounds(X, Y, 10, 10);
				gamePanel.add(grub);
				break;
			}
		}
	}

	public void pause(int millis) {
		try {
			Thread.sleep(millis);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mainMenu() {
		JLabel label = new JLabel(new ImageIcon("dogImage.jpg"));
		label.setBounds(0, 0, 300, 350);
		menuPanel = new JPanel();
		menuPanel.setLayout(null);
		menuPanel.setBackground(Color.darkGray);
		menuFrame = new JFrame("Snake Game Menu");
		menuFrame.setResizable(false);
		menuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		menuFrame.setSize(300, 350);
		playGame = new JButton("Play Game");
		playGame.setBounds((menuFrame.getWidth() / 2) - 50, 50, 100, 30);
		viewHS = new JButton("View High Score");
		viewHS.setBounds((menuFrame.getWidth() / 2) - 80, 90, 160, 30);
		name = new JTextField("Player1");
		ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!name.getText().isEmpty()) {
					pName = name.getText();
					nameL.setText("Player: " + pName);
					repaint();
					revalidate();
					menuFrame.setVisible(false);
					//moving();
					setVisible(true);
				}
			}

		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args0) {
				Thread t3 = new Thread(new Runnable() {
					@Override
					public void run() {
						while (viewHS.getY() > 90) {
							pause(6);
							viewHS.setBounds(viewHS.getX(), viewHS.getY() - 1, viewHS.getWidth(), viewHS.getHeight());
							menuPanel.repaint();
						}
					}
				});
				t3.start();
				Thread t4 = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							pause(1);
							if (name.getX() > -name.getWidth()) {
								name.setBounds(name.getX() - 1, name.getY(), name.getWidth(), name.getHeight());
								repaint();
							} else {
								break;
							}
							if (name.getX() < 20) {
								if (ok.getX() > -ok.getWidth()) {
									ok.setBounds(ok.getX() - 2, ok.getY(), ok.getWidth(), ok.getHeight());
									repaint();
								}
								if (cancel.getX() > name.getX() + (name.getWidth() - 80)) {
									cancel.setBounds(cancel.getX() + 2, cancel.getY(), cancel.getWidth(),
											cancel.getHeight());
									repaint();
								}

							}

						}
					}
				});
				t4.start();
				playGame.setEnabled(true);

			}
		});
		playGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name.setBounds(-160, playGame.getY() + 50, 160, 20);
				ok.setBounds(-50, name.getY() + 30, 50, 20);
				cancel.setBounds(300, name.getY() + 30, 80, 20);

				label.add(name);
				label.add(ok);
				label.add(cancel);
				label.repaint();

				Thread t1 = new Thread(new Runnable() {

					@Override
					public void run() {
						while (viewHS.getY() < ok.getY() + ok.getHeight() + 10) {
							pause(3);
							viewHS.setBounds(viewHS.getX(), viewHS.getY() + 1, viewHS.getWidth(), viewHS.getHeight());
							menuPanel.repaint();
						}

					}
				});
				t1.start();
				Thread t2 = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							pause(1);
							if (name.getX() < playGame.getX() - 30) {
								name.setBounds(name.getX() + 1, name.getY(), name.getWidth(), name.getHeight());
								repaint();
							}
							if (name.getX() > 20) {
								if (ok.getX() < name.getX()) {
									ok.setBounds(ok.getX() + 2, ok.getY(), ok.getWidth(), ok.getHeight());
									repaint();
								}
								if (cancel.getX() > name.getX() + (name.getWidth() - 80)) {
									cancel.setBounds(cancel.getX() - 2, cancel.getY(), cancel.getWidth(),
											cancel.getHeight());
									repaint();
								}
								if (!(ok.getX() < name.getX())
										&& !(cancel.getX() > name.getX() + (name.getWidth() - 80))) {
									break;
								}
							}

						}
					}
				});
				t2.start();
				playGame.setEnabled(false);

			}
		});
		menuPanel.add(label);
		label.add(playGame);
		label.add(viewHS);
		menuFrame.setContentPane(menuPanel);
	}

}
