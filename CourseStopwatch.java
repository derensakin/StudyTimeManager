package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame {
    private long startTime;
    private long pausedTime;
    private boolean isRunning;
    private boolean isPaused;

    private JLabel timeLabel;

    public Stopwatch(String className) {
        setTitle("Stopwatch - " + className);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timeLabel = new JLabel("0:00:00.0");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Start Study");
        JButton resetButton = new JButton("Reset");
        JButton pauseButton = new JButton("Pause");
        JButton finishButton = new JButton("Finish Study");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startStudy();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pause();
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishStudy();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(finishButton);

        // Ensure the content pane uses BorderLayout
        setLayout(new BorderLayout());

        // Add components to the content pane
        add(timeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    public void startStudy() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
            isPaused = false;
            pausedTime = 0;
        }
    }

    public void reset() {
        if (isRunning || isPaused) {
            isRunning = false;
            isPaused = false;
            pausedTime = 0;
            timeLabel.setText("0:00:00.0");
        }
    }

    public void pause() {
        if (isRunning) {
            isPaused = !isPaused;
            if (isPaused) {
                pausedTime = System.currentTimeMillis() - startTime;
            } else {
                startTime = System.currentTimeMillis() - pausedTime;
            }
        }
    }

    public void finishStudy() {
        if (isRunning || isPaused) {
            isRunning = false;
            isPaused = false;
            pausedTime = 0;
            // Prompt the user for a new class name
            String newClassName = JOptionPane.showInputDialog("Enter the new class you are going to study:");
            if (newClassName != null && !newClassName.isEmpty()) {
                // Create a new instance of Stopwatch with the new class name
                Stopwatch newStopwatch = new Stopwatch(newClassName);
                newStopwatch.setVisible(true);
                dispose(); // Close the current instance
            } else {
                JOptionPane.showMessageDialog(null, "Class name cannot be empty. Exiting.");
                System.exit(0);
            }
        }
    }

    private void updateTime() {
        long elapsedTime = (isRunning && !isPaused) ? System.currentTimeMillis() - startTime : pausedTime;
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        long milliseconds = elapsedTime % 1000 / 100;

        String timeString = String.format("%d:%02d:%02d.%d", hours, minutes, seconds, milliseconds);
        timeLabel.setText(timeString);
    }

    public static void main(String[] args) {
        // Ask the user for the initial class name
        String initialClassName = JOptionPane.showInputDialog("Enter the initial class you are going to study:");

        if (initialClassName != null && !initialClassName.isEmpty()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Stopwatch stopwatch = new Stopwatch(initialClassName);
                    stopwatch.setVisible(true);
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Class name cannot be empty. Exiting.");
            System.exit(0);
        }
    }
}
