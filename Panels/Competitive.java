package Panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

class SwapActionCompetitive4x4 implements ActionListener {
    private int iter = 0;
    private int b1, b2;
    private int pos_x[], pos_y[];
    private Competitive parent;
    Timer timer;

    SwapActionCompetitive4x4(Competitive parent, int b1, int b2, int pos_x[], int pos_y[]) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.parent = parent;
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.timer = (Timer) e.getSource();
        if (iter < 5) {
            parent.buttonArray[b1].setLocation(this.pos_x[iter + 1], this.pos_y[iter + 1]);
            parent.buttonArray[b2].setLocation(this.pos_x[4 - iter], this.pos_y[4 - iter]);
            parent.repaint();
            parent.revalidate();
            iter++;
        } else {
            timer.stop();
            for (int i = 0; i < 16; i++) {
                parent.buttonArray[i].setEnabled(true);
            }
        }
    }
}

public class Competitive extends JPanel {
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    JButton[] buttonArray = new JButton[16];
    int[] gameState = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    int[] actualGameState = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0 };
    Color bg = Color.RED;
    private int moves = 0;
    JButton shuffleButton = new JButton("SHUFFLE");
    JLabel Labelmoves = new JLabel("MOVES : " + moves, 0);

    public Competitive() {
        super(null);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setMaximumSize(getSize());
        setMinimumSize(getSize());
        setPreferredSize(getSize());
        setLayout(null);
        setBackground(new Color(50, 0, 255));
        // getContentPane().setBackground(bg);
        JPanel gamePanel = new JPanel(null);
        gamePanel.setBackground(bg);
        add(gamePanel);
        gamePanel.setBounds(60, 47, 365, 365);
        JButton bnull = new JButton("");

        // shuffle and moves;
        shuffleButton.setBackground(Color.BLACK);
        shuffleButton.setForeground(Color.yellow);
        shuffleButton.setFont(new Font("Times New Roman", 1, 20));
        Labelmoves.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Labelmoves.setBackground(Color.BLACK);
        Labelmoves.setOpaque(true);
        Labelmoves.setForeground(Color.YELLOW);
        Labelmoves.setFont(new Font("Times New Roman", 1, 20));

        shuffleButton.setBounds(500, 150, 200, 50);
        Labelmoves.setBounds(500, 300, 200, 50);

        add(shuffleButton);
        add(Labelmoves);

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Shuffle Button clicked");

                shuffle(50);

            }
        });
        bnull.setBounds(275, 275, 85, 85);
        bnull.setBorder(BorderFactory.createEmptyBorder());
        bnull.setBackground(bg);
        bnull.setEnabled(false);

        for (int i = 0; i < 15; i++) {
            buttonArray[i] = new JButton("" + (i + 1));
            buttonArray[i].setBounds(90 * (i % 4) + 5, 90 * (i / 4) + 5, 85, 85);
            buttonArray[i].setBorder(solidBorder);
            buttonArray[i].addActionListener(e -> {
                JButton clickedButton = (JButton) e.getSource();
                String buttonText = clickedButton.getText();
                makeMove(buttonText.equals("") ? 0 : Integer.parseInt(buttonText));
            });
            gamePanel.add(buttonArray[i]);
        }
        buttonArray[15] = bnull;
        gamePanel.add(bnull);

        SwingUtilities.invokeLater(() -> shuffle(50));
    }

    public void animateSwap(int ind1, int ind2) {
        Rectangle bounds1 = buttonArray[ind1].getBounds();
        Rectangle bounds2 = buttonArray[ind2].getBounds();
        int[] x_pos = new int[6];
        int[] y_pos = new int[6];

        for (int i = 0; i < 6; i++) {
            x_pos[i] = bounds1.x + (i) * (bounds2.x - bounds1.x) / 5;
            y_pos[i] = bounds1.y + (i) * (bounds2.y - bounds1.y) / 5;
        }
        for (int i = 0; i < 16; i++) {
            buttonArray[i].setEnabled(false);
        }
        Timer animator = new Timer(25, new SwapActionCompetitive4x4(this, ind2, ind1, x_pos, y_pos));
        animator.start();
        for (int i = 0; i < 16; i++) {
            buttonArray[i].setEnabled(true);
        }

    }

    public void swap(int ind1, int ind2) {
        int temp = gameState[ind1];
        gameState[ind1] = gameState[ind2];
        gameState[ind2] = temp;
        animateSwap(ind1, ind2);

        JButton tempButton = buttonArray[ind1];
        buttonArray[ind1] = buttonArray[ind2];
        buttonArray[ind2] = tempButton;

        buttonArray[ind1].setBounds(90 * (ind1 % 4) + 5, 90 * (ind1 / 4) + 5, 85, 85);
        buttonArray[ind2].setBounds(90 * (ind2 % 4) + 5, 90 * (ind2 / 4) + 5, 85, 85);

        revalidate();
        repaint();
    }

    public int makeMove(int num) {
        if (num == 0) {
            return 0;
        }
        int buttonPos = -1, nullpos = -1;
        for (int i = 0; i < 16; i++) {
            if (gameState[i] == num) {
                buttonPos = i;
            }
            if (gameState[i] == 0) {
                nullpos = i;
            }
        }

        if (buttonPos == -1 || nullpos == -1) {
            return 0;
        }

        if (canMove(buttonPos, nullpos)) {
            swap(buttonPos, nullpos);
            moves++;
            Labelmoves.setText("MOVES : " + this.moves);
            if (checkWinner()) {
                JOptionPane.showMessageDialog(this, "You WIN!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
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

    public boolean checkWinner() {
        return Arrays.equals(gameState, actualGameState);
    }

    public void shuffle(int shuffle_no) {
        int nullpos = 15;
        moves = 0;
        Labelmoves.setText("MOVES : " + this.moves);
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == 0) {
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
                swap(nullpos, move);
                prev = nullpos;
                nullpos = move;
            } else {
                System.out.println("No valid moves");// throw error here!
            }
        }
    }

    public int[] getValidMoves(int nullpos) {
        int row = nullpos / 4;
        int col = nullpos % 4;
        int[] moves = new int[4];
        int count = 0;

        if (col > 0)
            moves[count++] = nullpos - 1;
        if (col < 3)
            moves[count++] = nullpos + 1;
        if (row > 0)
            moves[count++] = nullpos - 4;
        if (row < 3)
            moves[count++] = nullpos + 4;

        return Arrays.copyOf(moves, count);
    }
}
