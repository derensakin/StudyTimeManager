package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch extends JFrame {
    private long startTime;
    private boolean isRunning;

    private JLabel timeLabel;

    public Stopwatch() {
        setTitle("Stopwatch");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timeLabel = new JLabel("0:00:00.0");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        setLayout(new BorderLayout());
        add(timeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create a Timer to update the time every 100 milliseconds
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    public void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    private void updateTime() {
        long elapsedTime = isRunning ? System.currentTimeMillis() - startTime : 0;
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;
        long milliseconds = elapsedTime % 1000 / 100; // Extract tenths of a second

        String timeString = String.format("%d:%02d:%02d.%d", hours, minutes, seconds, milliseconds);
        timeLabel.setText(timeString);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.setVisible(true);
            }
        });
    }
}
