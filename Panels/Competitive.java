package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

class SwapActionCompetitive4x4 implements ActionListener {
    private int iter = 0;
    private int b1, b2;
    private int pos_x[], pos_y[];
    private Competitive parent;
    Timer timer;
    private int flag;

    SwapActionCompetitive4x4(Competitive parent, int flag, int b1, int b2, int pos_x[], int pos_y[]) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.parent = parent;
        this.b1 = b1;
        this.b2 = b2;
        this.flag = flag;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.timer = (Timer) e.getSource();
        if (flag == 1) {
            if (iter < 5) {
                parent.buttonArray1[b1].setLocation(this.pos_x[iter + 1], this.pos_y[iter + 1]);
                parent.buttonArray1[b2].setLocation(this.pos_x[4 - iter], this.pos_y[4 - iter]);
                parent.repaint();
                parent.revalidate();
                iter++;
            } else {
                timer.stop();
                parent.setMoving1(false);
                // for (int i = 0; i < 16; i++) {
                // parent.buttonArray1[i].setEnabled(true);
                // }
            }
        } else if (flag == 2) {
            if (iter < 5) {
                parent.buttonArray2[b1].setLocation(this.pos_x[iter + 1], this.pos_y[iter + 1]);
                parent.buttonArray2[b2].setLocation(this.pos_x[4 - iter], this.pos_y[4 - iter]);
                parent.repaint();
                parent.revalidate();
                iter++;
            } else {
                timer.stop();
                parent.setMoving2(false);
                // for (int i = 0; i < 16; i++) {
                // parent.buttonArray2[i].setEnabled(true);
                // }
            }
        }
    }
}

public class Competitive extends JPanel implements KeyListener {
    private AppInterface parent;
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    JButton[] buttonArray1 = new JButton[16];
    JButton[] buttonArray2 = new JButton[16];
    int[] gameState1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    int[] gameState2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    int[] actualGameState = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    Color bg = Color.RED;
    private int moves1 = 0;
    private int moves2 = 0;
    JButton backButton = new JButton("BACK");
    JLabel movesLabel1 = new JLabel("MOVES : " + moves1, 0);
    JLabel movesLabel2 = new JLabel("MOVES : " + moves2, 0);
    private boolean moving1 = false;
    private boolean moving2 = false;

    public void setMoving1(boolean moving1) {
        this.moving1 = moving1;
    }

    public void setMoving2(boolean moving2) {
        this.moving2 = moving2;
    }

    public Competitive(AppInterface p) {
        super(null);
        this.addKeyListener(this);
        parent = p;
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setMaximumSize(getSize());
        setMinimumSize(getSize());
        setPreferredSize(getSize());
        setLayout(null);
        setBackground(new Color(50, 0, 255));
        // getContentPane().setBackground(bg);
        JPanel gamePanel1 = new JPanel(null);
        JPanel gamePanel2 = new JPanel(null);
        gamePanel1.setBackground(bg);
        gamePanel2.setBackground(bg);
        add(gamePanel1);
        add(gamePanel2);
        gamePanel1.setBounds(55, 20, 365, 365);
        gamePanel2.setBounds(470, 20, 365, 365);

        // Back and moves;
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.yellow);
        backButton.setFont(new Font("Times New Roman", 1, 20));
        movesLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movesLabel1.setBackground(Color.BLACK);
        movesLabel1.setOpaque(true);
        movesLabel1.setForeground(Color.YELLOW);
        movesLabel1.setFont(new Font("Times New Roman", 1, 20));

        movesLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        movesLabel2.setBackground(Color.BLACK);
        movesLabel2.setOpaque(true);
        movesLabel2.setForeground(Color.YELLOW);
        movesLabel2.setFont(new Font("Times New Roman", 1, 20));

        backButton.setBounds(345, 400, 200, 50);
        movesLabel1.setBounds(135, 400, 200, 50);
        movesLabel2.setBounds(550, 400, 200, 50);

        add(backButton);
        add(movesLabel1);
        add(movesLabel2);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.removeChild();
            }
        });

        JButton bnull = new JButton("");
        bnull.setBounds(275, 275, 85, 85);
        bnull.setBorder(BorderFactory.createEmptyBorder());
        bnull.setBackground(bg);
        bnull.setEnabled(false);

        JButton bnull2 = new JButton("");
        bnull2.setBounds(275, 275, 85, 85);
        bnull2.setBorder(BorderFactory.createEmptyBorder());
        bnull2.setBackground(bg);
        bnull2.setEnabled(false);

        for (int i = 0; i < 15; i++) {
            buttonArray1[i] = new JButton("" + (i + 1));
            buttonArray2[i] = new JButton("" + (i + 1));
            buttonArray1[i].setBounds(90 * (i % 4) + 5, 90 * (i / 4) + 5, 85, 85);
            buttonArray2[i].setBounds(90 * (i % 4) + 5, 90 * (i / 4) + 5, 85, 85);
            buttonArray1[i].setBorder(solidBorder);
            buttonArray2[i].setBorder(solidBorder);
            buttonArray1[i].addActionListener(e -> {
                this.requestFocusInWindow();
            });
            buttonArray2[i].addActionListener(e -> {
                this.requestFocusInWindow();
            });
            gamePanel1.add(buttonArray1[i]);
            gamePanel2.add(buttonArray2[i]);
        }
        bnull.addActionListener(e -> {
            this.requestFocusInWindow();
        });
        bnull2.addActionListener(e -> {
            this.requestFocusInWindow();
        });
        buttonArray1[15] = bnull;
        gamePanel1.add(bnull);
        buttonArray2[15] = bnull2;
        gamePanel2.add(bnull2);
        this.setFocusable(true);
        this.requestFocusInWindow();
        SwingUtilities.invokeLater(() -> shuffle(50));
    }

    public int getNullPos(int flag) {
        if (flag == 1) {
            for (int i = 0; i < 16; i++) {
                if (this.gameState1[i] == 0) {
                    return i;
                }
            }
        } else if (flag == 2) {
            for (int i = 0; i < 16; i++) {
                if (this.gameState2[i] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int btnpos = -1;
        if (keyCode == KeyEvent.VK_W && !moving1) {
            btnpos = getNullPos(1);
            if (btnpos == -1)
                return;
            if ((btnpos + 4) / 4 >= 4)
                return;
            makeMove(gameState1[btnpos + 4], 1);
            System.out.println("W key pressed" + btnpos + " " + (btnpos + 4));
        } else if (keyCode == KeyEvent.VK_S && !moving1) {
            System.out.println("D key pressed");
            btnpos = getNullPos(1);
            if (btnpos == -1)
                return;
            if ((btnpos - 4) / 4 < 0)
                return;
            makeMove(gameState1[btnpos - 4], 1);
        } else if (keyCode == KeyEvent.VK_A && !moving1) {
            System.out.println("A key pressed");
            btnpos = getNullPos(1);
            if (btnpos == -1)
                return;
            if ((btnpos + 1) % 4 == 0)
                return;
            makeMove(gameState1[btnpos + 1], 1);
        } else if (keyCode == KeyEvent.VK_D && !moving1) {
            System.out.println("D key pressed");
            btnpos = getNullPos(1);
            if (btnpos == -1)
                return;
            if ((btnpos - 1) % 4 == 3)
                return;
            makeMove(gameState1[btnpos - 1], 1);

        } else if (keyCode == KeyEvent.VK_UP && !moving2) {
            btnpos = getNullPos(2);
            if (btnpos == -1)
                return;
            if ((btnpos + 4) / 4 >= 4)
                return;
            makeMove(gameState2[btnpos + 4], 2);
        } else if (keyCode == KeyEvent.VK_DOWN && !moving2) {
            btnpos = getNullPos(2);
            if (btnpos == -1)
                return;
            if ((btnpos - 4) / 4 < 0)
                return;
            makeMove(gameState2[btnpos - 4], 2);
        } else if (keyCode == KeyEvent.VK_LEFT && !moving2) {
            System.out.println("A key pressed");
            btnpos = getNullPos(2);
            if (btnpos == -1)
                return;
            if ((btnpos + 1) % 4 == 0)
                return;
            makeMove(gameState2[btnpos + 1], 2);
        } else if (keyCode == KeyEvent.VK_RIGHT && !moving2) {
            System.out.println("A key pressed");
            btnpos = getNullPos(2);
            if (btnpos == -1)
                return;
            if ((btnpos - 1) % 4 == 3)
                return;
            makeMove(gameState2[btnpos - 1], 2);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    public void animateSwap(int ind1, int ind2, int flag) {
        int[] x_pos = new int[6];
        int[] y_pos = new int[6];
        if (flag == 1) {
            Rectangle bounds1 = buttonArray1[ind1].getBounds();
            Rectangle bounds2 = buttonArray1[ind2].getBounds();

            for (int i = 0; i < 6; i++) {
                x_pos[i] = bounds1.x + (i) * (bounds2.x - bounds1.x) / 5;
                y_pos[i] = bounds1.y + (i) * (bounds2.y - bounds1.y) / 5;
            }
            // for (int i = 0; i < 16; i++) {
            // buttonArray1[i].setEnabled(false);
            // }
            moving1 = true;
            Timer animator = new Timer(0, new SwapActionCompetitive4x4(this, flag, ind2, ind1, x_pos, y_pos));
            animator.start();
            // here change
            // for (int i = 0; i < 16; i++) {
            // buttonArray1[i].setEnabled(true);
            // }
        } else if (flag == 2) {
            Rectangle bounds1 = buttonArray2[ind1].getBounds();
            Rectangle bounds2 = buttonArray2[ind2].getBounds();

            for (int i = 0; i < 6; i++) {
                x_pos[i] = bounds1.x + (i) * (bounds2.x - bounds1.x) / 5;
                y_pos[i] = bounds1.y + (i) * (bounds2.y - bounds1.y) / 5;
            }
            // for (int i = 0; i < 16; i++) {
            // buttonArray2[i].setEnabled(false);
            // }
            moving2 = true;
            Timer animator = new Timer(0, new SwapActionCompetitive4x4(this, flag, ind2, ind1, x_pos, y_pos));
            animator.start();
            // for (int i = 0; i < 16; i++) {
            // buttonArray2[i].setEnabled(true);
            // }
        }

    }

    public void swap(int ind1, int ind2, int flag) {
        if (flag == 1) {
            int temp = gameState1[ind1];
            gameState1[ind1] = gameState1[ind2];
            gameState1[ind2] = temp;
            animateSwap(ind1, ind2, flag);
            JButton tempButton = buttonArray1[ind1];
            buttonArray1[ind1] = buttonArray1[ind2];
            buttonArray1[ind2] = tempButton;

            buttonArray1[ind1].setBounds(90 * (ind1 % 4) + 5, 90 * (ind1 / 4) + 5, 85, 85);
            buttonArray1[ind2].setBounds(90 * (ind2 % 4) + 5, 90 * (ind2 / 4) + 5, 85, 85);
        } else if (flag == 2) {
            int temp = gameState2[ind1];
            gameState2[ind1] = gameState2[ind2];
            gameState2[ind2] = temp;
            animateSwap(ind1, ind2, flag);
            JButton tempButton = buttonArray2[ind1];
            buttonArray2[ind1] = buttonArray2[ind2];
            buttonArray2[ind2] = tempButton;

            buttonArray2[ind1].setBounds(90 * (ind1 % 4) + 5, 90 * (ind1 / 4) + 5, 85, 85);
            buttonArray2[ind2].setBounds(90 * (ind2 % 4) + 5, 90 * (ind2 / 4) + 5, 85, 85);
        }
        revalidate();
        repaint();
    }

    public int makeMove(int num, int flag) {
        if (num == 0) {
            return 0;
        }
        int buttonPos = -1, nullpos = -1;
        for (int i = 0; i < 16; i++) {
            if (flag == 1) {
                if (gameState1[i] == num) {
                    buttonPos = i;
                }
                if (gameState1[i] == 0) {
                    nullpos = i;
                }
            } else if (flag == 2) {
                if (gameState2[i] == num) {
                    buttonPos = i;
                }
                if (gameState2[i] == 0) {
                    nullpos = i;
                }
            }
        }

        if (buttonPos == -1 || nullpos == -1) {
            return 0;
        }

        if (canMove(buttonPos, nullpos)) {// do here

            swap(buttonPos, nullpos, flag);// and here

            if (flag == 1) {
                moves1++;
                movesLabel1.setText("MOVES : " + this.moves1);
                if (checkWinner(flag)) {
                    JOptionPane.showMessageDialog(this, "PLAYER 1 WINS!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (flag == 2) {
                moves2++;
                movesLabel2.setText("MOVES : " + this.moves2);
                if (checkWinner(flag)) {
                    JOptionPane.showMessageDialog(this, "PLAYER 2 WINS!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return 1;
    }

    private boolean canMove(int buttonPos, int nullpos) {
        return (buttonPos + 1 == nullpos && (buttonPos % 4) != 3) ||
                (buttonPos - 1 == nullpos && (buttonPos % 4) != 0) ||
                (buttonPos + 4 == nullpos) ||
                (buttonPos - 4 == nullpos);
    }

    public boolean checkWinner(int flag) {
        if (flag == 1) {
            return Arrays.equals(gameState1, actualGameState);
        } else if (flag == 2) {
            return Arrays.equals(gameState2, actualGameState);
        }
        return false;
    }

    public void shuffle(int shuffle_no) {
        int nullpos = 15;
        moves1 = 0;
        moves2 = 0;
        movesLabel1.setText("MOVES : " + this.moves1);
        movesLabel2.setText("MOVES : " + this.moves2);
        for (int i = 0; i < gameState1.length; i++) {
            if (gameState1[i] == 0) {
                nullpos = i;
                break;
            }
        }

        Random rand = new Random();
        int next = -1, prev = -1;
        for (int i = 0; i < shuffle_no; i++) {
            int[] possible_moves = getValidMoves(nullpos);
            if (possible_moves.length > 0) {
                int found = 0;
                for (int j = 0; j < possible_moves.length; j++) {
                    if (found == 1) {
                        int tempMoveVar = possible_moves[j];
                        possible_moves[j] = possible_moves[j - 1];
                        possible_moves[j - 1] = tempMoveVar;
                    }
                    if (possible_moves[j] == prev) {
                        found = 1;
                    }
                }
                next = possible_moves[rand.nextInt(possible_moves.length - found)];
                // System.out.printf("%d,", next);
                int move = next;

                swap(nullpos, move, 1);
                swap(nullpos, move, 2);

                prev = nullpos;
                nullpos = move;
            } else {
                System.out.println("No valid moves");// throw error here!
            }
        }
        this.requestFocusInWindow();
    }

    public int[] getValidMoves(int nullpos) {
        int row = nullpos / 4;
        int col = nullpos % 4;
        int[] moves_possible = new int[4];
        int count = 0;

        if (col > 0)
            moves_possible[count++] = nullpos - 1;
        if (col < 3)
            moves_possible[count++] = nullpos + 1;
        if (row > 0)
            moves_possible[count++] = nullpos - 4;
        if (row < 3)
            moves_possible[count++] = nullpos + 4;

        return Arrays.copyOf(moves_possible, count);
    }
}
