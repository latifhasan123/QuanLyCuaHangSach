/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.SwingUtilities;

public class MainUser {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientMainFrame();
        });
    }
}