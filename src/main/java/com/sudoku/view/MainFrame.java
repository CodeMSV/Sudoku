package com.sudoku.view;

import com.sudoku.model.DifficultyGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainFrame extends JFrame {

    public final JPanel boardPanel = new JPanel();
    public final JButton newGameBtn = new RoundButton("♻ New Game");
    public final JComboBox<DifficultyGame> difficultyCombo = new JComboBox<>(DifficultyGame.values());
    public final JButton checkBtn = new RoundButton("✔ Check");
    public final JButton hintBtn = new RoundButton("💡 Hint");
    public final JButton solveBtn = new RoundButton("▶ Solve");
    public final JTextField[][] cells = new JTextField[9][9];
    private final JLabel statusBar = new JLabel("Ready");

    private static final int CELL_PADDING = 4;
    private static final int ARC_WIDTH = 16;
    private static final int ARC_HEIGHT = 16;
    private static final Color CELL_BG_NORMAL = Color.WHITE;
    private static final Color HIGHLIGHT_BG = new Color(200, 230, 255);
    private static final Color BOARD_BG = new Color(245, 245, 245);
    private static final Color BUTTON_BG = new Color(100, 149, 237, 176);
    private static final Color BUTTON_FG = Color.BLACK;


    public MainFrame() {
        this.setTitle("Sudoku");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(850, 980);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(0, 5));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        top.setBackground(UIManager.getColor("Panel.background"));
        top.setBorder(new EmptyBorder(10, 10, 0, 10));

        this.difficultyCombo.setPreferredSize(new Dimension(100, 30));

        styleButton(newGameBtn);
        styleButton(checkBtn);
        styleButton(hintBtn);
        styleButton(solveBtn);

        top.add(new JLabel("Difficulty:"));
        top.add(difficultyCombo);
        top.add(newGameBtn);
        top.add(checkBtn);
        top.add(hintBtn);
        top.add(solveBtn);
        add(top, BorderLayout.NORTH);

        // Board panel
        final int GAP_INNER = 2;
        final int GAP_BLOCK = 10;

        boardPanel.setLayout(new GridLayout(3, 3, GAP_BLOCK, GAP_BLOCK));
        boardPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        boardPanel.setBackground(BOARD_BG);

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                JPanel subGrid = new JPanel(new GridLayout(3, 3, GAP_INNER, GAP_INNER));
                subGrid.setBackground(BOARD_BG);
                subGrid.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

                for (int r = blockRow * 3; r < blockRow * 3 + 3; r++) {
                    for (int c = blockCol * 3; c < blockCol * 3 + 3; c++) {
                        JTextField cell = createCell(r, c);
                        cells[r][c] = cell;
                        subGrid.add(cell);
                    }
                }

                boardPanel.add(subGrid);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        statusBar.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);
    }


    /**
     * Applies a consistent style to the buttons.
     * @param btn The button to style.
     */
    private void styleButton(JButton btn) {
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
        btn.setBackground(BUTTON_BG);
        btn.setForeground(BUTTON_FG);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 32));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(BUTTON_BG.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(BUTTON_BG);
            }
        });
    }

    /**
     * Creates a cell for the Sudoku board.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return A JTextField representing the cell.
     */
    private JTextField createCell(int row, int col) {
        JTextField cell = new JTextField();
        cell.setHorizontalAlignment(JTextField.CENTER);
        cell.setFont(new Font("Dialog", Font.PLAIN, 20));
        cell.setOpaque(true);
        cell.setBackground(CELL_BG_NORMAL);

        cell.setBorder(BorderFactory.createCompoundBorder(
                new CellBlockBorder(row, col),
                BorderFactory.createEmptyBorder(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING)
        ));

        final int r = row, c = col;

        cell.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                highlight(r, c);
                statusBar.setText("Row: " + (r + 1) + ", Col: " + (c + 1));
            }

            @Override
            public void focusLost(FocusEvent e) {
                clearHighlights();
                statusBar.setText("Ready");
            }
        });

        return cell;
    }

    /**
     * Clears the highlights from all cells.
     */
    private void clearHighlights() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                cells[r][c].setBackground(CELL_BG_NORMAL);
    }

    /**
     * Highlights the row, column, and block of the specified cell.
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     */
    private void highlight(int row, int col) {
        clearHighlights();
        for (int i = 0; i < 9; i++) {
            cells[row][i].setBackground(HIGHLIGHT_BG);
            cells[i][col].setBackground(HIGHLIGHT_BG);
        }
        int br = (row / 3) * 3, bc = (col / 3) * 3;
        for (int r = br; r < br + 3; r++)
            for (int c = bc; c < bc + 3; c++)
                cells[r][c].setBackground(HIGHLIGHT_BG);
    }

    private static class RoundButton extends JButton {
        public RoundButton(String text) { super(text); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
            g2.dispose(); super.paintComponent(g);
        }
        @Override protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground().darker());
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, ARC_WIDTH, ARC_HEIGHT);
            g2.dispose();
        }
        @Override public boolean isContentAreaFilled() { return false; }
    }

    private static class CellBlockBorder extends javax.swing.border.AbstractBorder {
        private final int row, col; private static final int THICK = 2;
        public CellBlockBorder(int row, int col) { this.row = row; this.col = col; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create(); g2.setColor(Color.DARK_GRAY);
            int top    = (row % 3 == 0) ? THICK : 1;
            int bottom = (row == 8)       ? THICK : 1;
            int left   = (col % 3 == 0) ? THICK : 1;
            int right  = (col == 8)     ? THICK : 1;
            g2.setStroke(new BasicStroke(top));    g2.drawLine(x, y, x+w, y);
            g2.setStroke(new BasicStroke(bottom)); g2.drawLine(x, y+h-1, x+w, y+h-1);
            g2.setStroke(new BasicStroke(left));   g2.drawLine(x, y, x, y+h);
            g2.setStroke(new BasicStroke(right));  g2.drawLine(x+w-1, y, x+w-1, y+h);
            g2.dispose();
        }
    }
}
